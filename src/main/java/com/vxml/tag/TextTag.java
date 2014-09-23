package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.audio.NativeCommand;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlExecutionContext;

public class TextTag extends AbstractTag {

    private String text;
    
	public TextTag(Node node) {
		super(node);
		text = getNode().getTextContent().trim();
		
	}

	@Override
	public void execute() {
		if (!text.isEmpty() && !VxmlExecutionContext.isSlientMode()) {
			try {
			    System.out.println(text);
				VxmlBrowser.getContext().playTTS(text);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String toString() {
	    return "TTS:"+ text;
	}

}
