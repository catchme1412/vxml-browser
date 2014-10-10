package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.browser.event.Event;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.parser.VxmlDoc;

public class GrammarTag extends AbstractTag {

    private boolean isSlientModeBackup;
    private String mode;
    
	public GrammarTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
	    mode = getAttribute("mode");
	    isSlientModeBackup = VxmlBrowser.getContext().isSlientMode();
		VxmlBrowser.getContext().setSlientMode(true);
		if (mode != null && mode.equals("dtmf")) {
		    setSkipExecute(false);
		}
	}
	@Override
	public void execute() throws Event {
	    String srcexpr = getAttribute("srcexpr");
	    String val = (String) VxmlBrowser.getContext().executeScript(srcexpr);
	    val = val != null ? val : srcexpr;
	    if(val != null) {
	        new VxmlDoc(val).play();
	    }
	}
	
	@Override
	public void endTag() {
		VxmlBrowser.getContext().setSlientMode(isSlientModeBackup);
	}
	
}
