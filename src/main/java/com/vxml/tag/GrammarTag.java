package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlExecutionContext;

public class GrammarTag extends AbstractTag {

	public GrammarTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
		VxmlExecutionContext.setTtsAllowed(false);
	}
	@Override
	public void execute() {
		
	}
	
	@Override
	public void endTag() {
		VxmlExecutionContext.setTtsAllowed(true);
	}
	
}
