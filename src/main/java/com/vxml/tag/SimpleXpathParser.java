package com.vxml.tag;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SimpleXpathParser {

    private static String fileName = "servers.xml";
    private static final String pathExp = "/serverMappings/serverMapping[@id='adt']";

    private static SimpleXpathParser xpathParser;

    private SimpleXpathParser(String fileName) {
        this.fileName = fileName;
    }

    public static synchronized SimpleXpathParser getInstance() {
        if (xpathParser == null) {
            xpathParser = new SimpleXpathParser(fileName);
        }
        return xpathParser;
    }

    private Document getDocument(String filename) throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(is);
        return doc;
    }

    public List getByPath(String pathExp) {
        List serverList = new ArrayList();
        try {
            Document doc = getDocument(fileName);
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile(pathExp);
            NodeList favoris = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < favoris.getLength(); i++) {
                Element workflow = (Element) favoris.item(i);
                NodeList values = (NodeList) xpath.evaluate("server/text()", workflow, XPathConstants.NODESET);
                for (int k = 0; k < values.getLength(); k++) {
                    serverList.add(values.item(k).getNodeValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return serverList;
    }

    public static void main(String[] args) throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("servers.xml");
        if (is == null) {
            is = ClassLoader.getSystemClassLoader ().getResourceAsStream("servers.xml");
        }
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(is);
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile("/serverMappings/serverMapping[@id='adt']");
        NodeList favoris = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        
        for (int i = 0; i < favoris.getLength(); i++) {
            Element workflow = (Element) favoris.item(i);
            NodeList States = (NodeList) xpath.evaluate("server/text()", workflow, XPathConstants.NODESET);
            for (int k = 0; k < States.getLength(); k++) {
                System.out.println(States.item(k).getNodeValue());
            }
        }
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List l = new SimpleXpathParser("servers.xml").getByPath(pathExp);
        System.out.println(l);
    }
}
