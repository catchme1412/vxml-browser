package com.vxml.utils;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class XmlUtils {

	public static NodeList getNodesByXPath(Node node, String xpathStr) {
		XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr;
		try {
			expr = xpath.compile(xpathStr);
			NodeList favoris = (NodeList) expr.evaluate(node, XPathConstants.NODESET);
			return favoris;
		} catch (XPathExpressionException e) {
			e.printStackTrace();																																													
		}
		return null;
	}
	
	public static String getAttribute(Node node, String key) {
		Node namedItem = node.getAttributes().getNamedItem(key);
		return namedItem != null ? namedItem.getNodeValue() : null;
	}
	
	
	public static String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			System.err.println("nodeToString Transformer Exception");
		}
		return sw.toString();
	}

	
	public static boolean  isEmptyOrComment(Node node) {
		return node.getNodeType() == Node.COMMENT_NODE
                || (node.getNodeType() == Node.TEXT_NODE && node.getTextContent().trim().isEmpty());
	}
}
