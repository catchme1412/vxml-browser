package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class FieldTag extends AbstractTag {

	public FieldTag(Node node) {
		super(node);
	}

	@Override
	public void execute() {
		String name = getAttribute("name");
		VxmlBrowser.getContext().executeScript("var " + name +";");
	}

}
