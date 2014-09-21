package com.vxml.tag;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.utils.XmlUtils;

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
