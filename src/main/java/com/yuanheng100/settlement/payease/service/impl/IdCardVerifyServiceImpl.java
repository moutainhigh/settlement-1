package com.yuanheng100.settlement.payease.service.impl;

import com.capinfo.crypt.Md5;
import com.yuanheng100.settlement.payease.consts.IdType;
import com.yuanheng100.settlement.payease.mapper.IdcardVerifyMapper;
import com.yuanheng100.settlement.payease.model.other.IdcardVerify;
import com.yuanheng100.settlement.payease.service.IIdCardVerifyService;
import com.yuanheng100.settlement.payease.util.HttpUtils;
import com.yuanheng100.util.ConfigFile;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlqian on 2016/7/25.
 */
public class IdCardVerifyServiceImpl implements IIdCardVerifyService {

    private static Logger logger = Logger.getLogger(IdCardVerifyServiceImpl.class);
    /**
     * 商户编号
     */
    private static final String V_MID = ConfigFile.getProperty("payease.groupId");
    /**
     * 证件类型，目前支持身份证
     */
    private static final String V_IDTYPE_IDCARD = IdType.IDENTITY_CARD.getCode();

    @Autowired
    private IdcardVerifyMapper idcardVerifyMapper;


    @Override
    public List<IdcardVerify> getIdCardVerifyList(Map<String, Object> param)
    {
        return idcardVerifyMapper.getIdCardVerifyList(param);
    }

    @Override
    public int getIdCardVerifyListCount(Map<String, Object> param)
    {
        return idcardVerifyMapper.getIdCardVerifyListCount(param);
    }

    @Override
    public boolean verifyIdcard(String name , String idcardNo) {
        //查询数据库是否对当前姓名、身份证号进行验证过
        IdcardVerify idcardVerify = idcardVerifyMapper.selectByNameAndIdcardNo(name, idcardNo);
        if(idcardVerify!=null){
            Short status = idcardVerify.getStatus();
            if(status==1){
                return true;
            }else{
                return false;
            }
        }
        logger.info("开始向首信易发送请求："+"姓名："+name+"，身份证号码："+idcardNo);

        String v_mid = V_MID;
        String v_idtype=V_IDTYPE_IDCARD;
        String v_idnumber = idcardNo;
        String v_idname= name;

        String md5str = v_mid+v_idtype+v_idnumber+v_idname;
        try{
            md5str = URLEncoder.encode(md5str,"utf-8");
        }catch(Exception e){
            logger.error("首信易md5str转码失败");
        }

        Map<String,String> reqMap = new HashMap<String, String>();
        reqMap.put("v_mid",v_mid);
        reqMap.put("v_idtype",v_idtype);
        reqMap.put("v_idnumber",v_idnumber);
        reqMap.put("v_idname",v_idname);

        Md5 md5 = new Md5("");
        String digestString = "";
        try{
            md5.hmac_Md5(md5str, ConfigFile.getProperty("payease.recharg.key"));
            byte b[]= md5.getDigest();
            digestString = md5.stringify(b) ;
        }catch(Exception e){
            logger.error("首信易md5方法：md5.stringify(b)方法异常,未能成功转换成字符串");
        }
        reqMap.put("v_mac",digestString);

        try {
            String resStr = HttpUtils.post(ConfigFile.getProperty("payease.idcard.verify.url"), reqMap);
            logger.info(resStr);

            StringReader read = new StringReader(resStr);
            InputSource source = new InputSource(read);
            SAXBuilder sb = new SAXBuilder();

            Document doc = sb.build(source);
            Element root = doc.getRootElement();
            List<Element> jiedian = root.getChildren();
            Element et = null;

            et = (Element) jiedian.get(0);
            Element statusEleHead = et.getChild("status");
            String statusHead=statusEleHead.getTextTrim();
            if(statusHead.equals("0")){

                Element statusEleBody = (Element) jiedian.get(1);
                String statusBody=statusEleBody.getChildText("verifystatus");
                idcardVerify = new IdcardVerify();
                idcardVerify.setName(name);
                idcardVerify.setIdcardNo(idcardNo);
                idcardVerify.setVerifyTime(new Date());
                if(statusBody.equals("1")){
                    idcardVerify.setStatus((short) 1);
                    idcardVerifyMapper.insert(idcardVerify);
                    return true;
                }else{
                    idcardVerify.setStatus((short) 2);
                    idcardVerifyMapper.insert(idcardVerify);
                    return false;
                }
            } else {
                return false;
            }
        } catch (JDOMException e) {
            logger.error("身份证认证：返回的数据格式错误");
        } catch (IOException e) {
            logger.error("身份证认证：网络异常");
        }
        return false;
    }
}
