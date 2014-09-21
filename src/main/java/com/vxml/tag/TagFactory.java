package com.vxml.tag;

import java.lang.reflect.Constructor;

import org.w3c.dom.Node;

public class TagFactory {

	public static Tag get(Node node) {
		String tagName = getTagName(node);
		try {
			Constructor c = Class.forName(tagName).getConstructor(Node.class);
			Tag tag = (Tag) c.newInstance(node);
			return tag;
		} catch (Exception e) {
			System.err.println("NO CLASS:" + e.getMessage());
			return new DoNothingTag(node);
		}
	}

	private static String getTagName(Node node) {
		String nodeName = node.getNodeName();
		if (nodeName.equals("cisco-data")) {
		    nodeName = "data";
		}
		if (nodeName.startsWith("#")) {
			nodeName = nodeName.substring(1);
		}
		if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
		    nodeName = "CDATASection";
		}
		return "com.vxml.tag." + Character.toUpperCase(nodeName.charAt(0)) + nodeName.substring(1) + "Tag";
	}

}
