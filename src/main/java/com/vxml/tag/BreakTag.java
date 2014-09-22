package com.vxml.tag;

import org.w3c.dom.Node;


public class BreakTag extends DoNothingTag {

    public BreakTag(Node node) {
        super(node);
    }
    
    @Override
    public void execute() {
    	// TODO Auto-generated method stub
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}
