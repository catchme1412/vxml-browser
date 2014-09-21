package com.vxml.tag;

import org.w3c.dom.Node;

public class DoNothingTag extends AbstractTag {

    
    public DoNothingTag() {
        super(null);
    }
    
	public DoNothingTag(Node node) {
		super(node);
	}

	@Override
	public void execute() {

	}

}
