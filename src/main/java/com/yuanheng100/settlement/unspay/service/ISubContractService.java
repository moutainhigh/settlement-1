package com.yuanheng100.settlement.unspay.service;

import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.unspay.model.UnspaySubContract;
import com.yuanheng100.settlement.unspay.model.UnspaySubContractResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银生宝子协议相关的接口
 * Created by qianjl on 2016-6-21.
 */
public interface ISubContractService {

    /**
     * 获取子协议上传记录页
     * @param searchConditions
     * @param page
     */
    void getUploadListPage(HashMap<String, Object> searchConditions, Page<Map<String,Object>> page);

    /**
     * 获取上传记录页详情页数据
     * @param filename
     * @return
     */
    List<UnspaySubContract> uploadFileDetail(String filename);

    /**
     * 子协议录入接口(异步)
     * @param unspaySubContract
     * @return 成功时返回true,请求失败返回false
     */
    boolean asyncSign(UnspaySubContract unspaySubContract);

    /**
     * 子协议录入接口(同步)
     * @param unspaySubContract
     * @return 成功时返回true,请求失败返回false
     */
    UnspaySubContractResponse syncSign(UnspaySubContract unspaySubContract);

    /**
     * 子协议号查询（查询远程银生宝服务）：根据银行卡号，用户姓名，身份证号进行查询(通过银生宝)
     * @param unspaySubContract
     * @return
     */
    String queryRemote(UnspaySubContract unspaySubContract);

    /**
     * 子协议号查询(查询本地数据库)：根据进件编号进行
     * @param loanApplyId
     * @return
     */
    String queryLocal(Long loanApplyId);

    /**
     * 子协议查询(查询本地数据库)：根据进件编号进行
     * @param loanApplyId
     * @return
     */
    UnspaySubContract querySubContractLocal(Long loanApplyId);

    /**
     * 子协议修改(远程发送+本地修改)
     * @param unspaySubContract
     * @return
     */
    boolean edit(UnspaySubContract unspaySubContract);

    /**
     * 子协议延期：必须属性：子协议编号，子协议开始时间，子协议结束时间
     * @param unspaySubContract
     * @return
     */
    boolean extension(UnspaySubContract unspaySubContract);

    /**
     * 子协议列表页面
     * @param searchConditions
     * @param page
     */
    void getListPage(HashMap<String, Object> searchConditions, Page<UnspaySubContract> page);


}
