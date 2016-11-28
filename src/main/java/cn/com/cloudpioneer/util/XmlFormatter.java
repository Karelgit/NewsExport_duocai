package cn.com.cloudpioneer.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by Administrator on 2016/10/18.
 */
public class XmlFormatter {

    public static String format(String str)
    {

        StringWriter out = new StringWriter();

        SAXReader reader = new SAXReader();

        StringReader in = new StringReader(str);

        Document doc;
        try {
            doc = reader.read(in);

            OutputFormat formater = OutputFormat.createPrettyPrint();

            formater.setEncoding("utf-8");

            XMLWriter writer = new XMLWriter(out, formater);
            writer.setEscapeText(false);

            writer.write(doc);

            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toString();

    }
}
