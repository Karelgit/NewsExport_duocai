package cn.com.cloudpioneer.util;

import org.htmlcleaner.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

/**
 * <类详细说明:Xpath解析测试类>
 *
 * @Author： Huanghai
 * @Version: 2016-09-26
 **/
public class ParseTest {
    private static final Logger LOG = LoggerFactory.getLogger(ParseTest.class);

    @Test
    public void crawlByXPath() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException,XPatherException {

        String url = "http://www.gygov.gov.cn/art/2016/9/25/art_10683_1028792.html";
        String exp = "//td[@class='title']";

        String html = null;
        try {
            Connection connect = Jsoup.connect(url);
            html = connect.get().body().html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HtmlCleaner hc = new HtmlCleaner();
        TagNode tn = hc.clean(html);
        Document dom = new DomSerializer(new CleanerProperties()).createDOM(tn);
        XPath xPath = XPathFactory.newInstance().newXPath();
        Object result;
        result = xPath.evaluate(exp, dom, XPathConstants.NODESET);
        System.out.println("result: " + result);
        /*if (result instanceof NodeList) {
            NodeList nodeList = (NodeList) result;
            System.out.println(nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                System.out.println(node.getNodeValue() == null ? node.getTextContent() : node.getNodeValue());
            }
        }*/
    }

    @Test
    public void test()  {
        LOG.info("info level!");
        LOG.debug("debug level!");
    }
}

