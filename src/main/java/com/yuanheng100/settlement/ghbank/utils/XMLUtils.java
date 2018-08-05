package com.yuanheng100.settlement.ghbank.utils;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.StringWriter;

/**
 * Created by jlqian on 2017/4/17.
 */
public class XMLUtils
{
    public static String formatXml_UTF8(Document doc) {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            format.setIndent(false);
            format.setNewlines(false);
            format.setNewLineAfterDeclaration(false);
			/*
			format.setLineSeparator("\n");
			 */
            StringWriter out = new StringWriter();
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(doc);
            writer.close();
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
