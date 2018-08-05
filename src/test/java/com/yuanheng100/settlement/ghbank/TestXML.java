package com.yuanheng100.settlement.ghbank;

import com.yuanheng100.settlement.ghbank.utils.XMLUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jlqian on 2017/4/17.
 */
public class TestXML
{
    private String source = "NVnwXP2rp6vOBT0MUM7ksYWuVU9JH/yK6Emtf8BLHTgMyyBjG2uy0nEzEJ40ch0dk0Q0bjvGw8/EFp7ccofRz07f5/gYJZq4Dwj7d+X8vyg2hnjyLfTJDCoDsBZ26ypULjYyPO5EjOLEUPTVbwX3/4L49nXtIStjYk6RgCDoV6LUjr3lJgOt+EkETdLRiYb0";

    private String target = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><header><encryptData></encryptData><serverFlow>OGW01201603161000008948</serverFlow><status>0</status><serverTime>175419</serverTime><errorCode>0</errorCode><errorMsg></errorMsg><serverDate>20160316</serverDate></header><body><TRANSCODE>OGW00019</TRANSCODE><XMLPARA>NVnwXP2rp6vOBT0MUM7ksYWuVU9JH/yK6Emtf8BLHTgMyyBjG2uy0nEzEJ40ch0dk0Q0bjvGw8/EFp7ccofRz07f5/gYJZq4Dwj7d+X8vyg2hnjyLfTJDCoDsBZ26ypULjYyPO5EjOLEUPTVbwX3/4L49nXtIStjYk6RgCDoV6LUjr3lJgOt+EkETdLRiYb0</XMLPARA></body></Document>";

    @Test
    public void testCreateXML()
    {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("Document");
        //构建Header
        Element header = root.addElement("header");
        header.addElement("encryptData").addText("");
        header.addElement("serverFlow").addText("OGW01201603161000008948");
        header.addElement("status").addText("0");
        header.addElement("serverTime").addText("175419");
        header.addElement("errorCode").addText("0");
        header.addElement("errorMsg").addText("");
        header.addElement("serverDate").addText("20160316");

        //构建Body
        Element body = root.addElement("body");
        body.addElement("TRANSCODE").addText("OGW00019");
        body.addElement("XMLPARA").addText(source);

        String xml = XMLUtils.formatXml_UTF8(document);
        Assert.assertEquals(xml, target);

    }

    @Test
    public void testParseXML()
    {
        try
        {
            Document document = DocumentHelper.parseText(target);

            Element rootElement = document.getRootElement();
            Element body = rootElement.element("body");
            String xmlpara = body.elementText("XMLPARA");

            Assert.assertEquals(source, xmlpara);

        } catch (DocumentException e)
        {
            e.printStackTrace();
        }
    }
}
