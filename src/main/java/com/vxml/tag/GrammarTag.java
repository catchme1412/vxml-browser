package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class GrammarTag extends AbstractTag {

    private boolean isSlientModeBackup;
    
	public GrammarTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
	    isSlientModeBackup = VxmlBrowser.getContext().isSlientMode();
		VxmlBrowser.getContext().setSlientMode(true);
	}
	@Override
	public void execute() {
		
	}
	
	@Override
	public void endTag() {
		VxmlBrowser.getContext().setSlientMode(isSlientModeBackup);
	}
	
}
