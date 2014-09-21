package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.audio.NativeCommand;
import com.vxml.core.browser.VxmlBrowser;

public class ValueTag extends AbstractTag {

	public ValueTag(Node node) {
		super(node);
	}

	@Override
	public void execute() {
	    if (getNode().getParentNode().getNodeName().equals("prompt")) {
	        String expr = getAttribute("expr");
	        Object value = VxmlBrowser.getContext().executeScript(expr);
	        try {
				new NativeCommand().speak(value.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

}
