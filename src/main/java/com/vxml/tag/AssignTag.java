package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class AssignTag extends AbstractTag {

	private String name;

	public AssignTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
		name = getAttribute("name");
		VxmlBrowser.getContext().assignScriptVar(name, null);
	}

	@Override
	public void execute() {
		name = getAttribute("name");
		String expr = getAttribute("expr");

		Object val = VxmlBrowser.getContext().executeScript(expr);
		if (val == null) {
			val = VxmlBrowser.getContext().getScriptVar(expr);
		}
		VxmlBrowser.getContext().assignScriptVar(name, val);
	}

}
