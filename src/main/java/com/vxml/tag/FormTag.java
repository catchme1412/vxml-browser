package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class FormTag extends AbstractTag {

	public FormTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {

		String id = getAttribute("id");
		if (id != null) {
			VxmlBrowser.getContext().storeTag(id, this);
		}
	}

	@Override
	public void execute() {

	}

}
