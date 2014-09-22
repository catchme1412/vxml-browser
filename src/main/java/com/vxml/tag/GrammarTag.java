package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlExecutionContext;

public class GrammarTag extends AbstractTag {

    private boolean isSlientModeBackup;
    
	public GrammarTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
	    isSlientModeBackup = VxmlExecutionContext.isSlientMode();
		VxmlExecutionContext.setSlientMode(true);
	}
	@Override
	public void execute() {
		
	}
	
	@Override
	public void endTag() {
		VxmlExecutionContext.setSlientMode(isSlientModeBackup);
	}
	
}
