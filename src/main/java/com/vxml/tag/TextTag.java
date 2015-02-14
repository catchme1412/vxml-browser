package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class TextTag extends AbstractTag {

    private String text;
    
	public TextTag(Node node) {
		super(node);
		text = getNode().getTextContent().trim();
		
	}

	@Override
	public void execute() {
		if (!text.isEmpty() && !VxmlBrowser.getContext().isSlientMode()) {
			try {
			    System.out.println(text);
			    this.getNode();
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
