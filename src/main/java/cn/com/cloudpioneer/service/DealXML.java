package cn.com.cloudpioneer.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author：Huanghai
 * @CreateTime：2016-09-20 22:18:38
 * @Description：DOM操作XML文件，增删查改
 */
public class DealXML {
    public static void main(String[] args) {
        try {
            // Document-->Node
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ClassLoader.getSystemResourceAsStream("products.xml"));
            Element root = document.getDocumentElement();

            // 增加一个元素节点
            Element newChild = document.createElement("project");
            newChild.setAttribute("id", "NP001");// 添加id属性

            Element nelement = document.createElement("name");// 元素节点
            nelement.setTextContent("New Project");
            newChild.appendChild(nelement);
            Element selement = document.createElement("start-date");
            selement.setTextContent("March 20 1999");
            newChild.appendChild(selement);
            Element eelement = document.createElement("end-date");
            eelement.setTextContent("July 30 2004");
            newChild.appendChild(eelement);

            root.appendChild(newChild);

            // 查找一个元素节点
            String expression = "/projects/project[3]";
            Element node = (Element) selectSingleNode(expression, root);// 转型一下
            // 修改一个元素节点
            node.getAttributeNode("id").setNodeValue("new "+node.getAttribute("id"));
            // root.getElementsByTagName("project").item(2).setTextContent("");
            expression = "/projects/project";
            NodeList nodeList = selectNodes(expression, root);
            nodeList.item(1).getAttributes().getNamedItem("id").setNodeValue("New Id");
            // 删除一个元素节点
            expression = "/projects/project[2]";
            node = (Element) selectSingleNode(expression, root);
            root.removeChild(root.getFirstChild());

            output(root, "newProjects.xml");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void output(Node node, String filename) {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transFactory.newTransformer();
            // 设置各种输出属性
            transformer.setOutputProperty("encoding", "gb2312");
            transformer.setOutputProperty("indent", "yes");
            DOMSource source = new DOMSource();
            // 将待转换输出节点赋值给DOM源模型的持有者(holder)
            source.setNode(node);
            StreamResult result = new StreamResult();
            if (filename == null) {
                // 设置标准输出流为transformer的底层输出目标
                result.setOutputStream(System.out);
            } else {
                result.setOutputStream(new FileOutputStream(filename));
            }
            // 执行转换从源模型到控制台输出流
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 查找一个单独的节点
    private static Node selectSingleNode(String expression, Object source) {
        try {
            return (Node) XPathFactory.newInstance().newXPath().evaluate(expression, source, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 查找所有的节点
    private static NodeList selectNodes(String expression, Object source) {
        try {
            return (NodeList) XPathFactory.newInstance().newXPath().evaluate(expression, source, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return null;
        }
    }

}
