package cn.com.cloudpioneer.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author：Huanghai
 * @CreateTime：2016-09-20 22:18:38
 * @Description：采用DOM解析XML文件
 */
public class DomXML {
    public void domXMl(String fileName) {
        try {
            DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStream input = ClassLoader.getSystemResourceAsStream(fileName);
            Document doc = domBuilder.parse(input);
            Element root = doc.getDocumentElement();
            NodeList students = root.getChildNodes();
            if (students != null) {
                for (int i = 0, size = students.getLength(); i < size; i++) {
                    Node student = students.item(i);
                    if (student.getNodeType() == Node.ELEMENT_NODE) {
                        String sexString = student.getAttributes().getNamedItem("性别").getNodeValue();
                        System.out.println(sexString);
                    }

                    for (Node node = student.getFirstChild(); node != null; node = node.getNextSibling()) {
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            if (node.getNodeName().equals("姓名")) {
                                String name = node.getFirstChild().getNodeValue();
                                System.out.println(name);
                            }
                            if (node.getNodeName().equals("年龄")) {
                                String age = node.getFirstChild().getNodeValue();
                                System.out.println(age);
                            }
                            if (node.getNodeName().equals("电话")) {
                                String tel = node.getFirstChild().getNodeValue();
                                System.out.println(tel);
                            }
                        }
                    }

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        DomXML xmlTest = new DomXML();
        String fileName = "students.xml";
        xmlTest.domXMl(fileName);
    }

}
