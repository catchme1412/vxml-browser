package com.vxml.tag;

import org.w3c.dom.Node;

public class CatchTag extends AbstractTag {

    private boolean isSkipTag;
    
	public CatchTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
	    isSkipTag = isSkipExecute();
		setSkipExecute(true);
	}

	@Override
	public void execute() {
	}

	@Override
	public void endTag() {
		setSkipExecute(isSkipTag);
	}

}
