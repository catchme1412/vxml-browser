package com.vxml.tag;
import org.w3c.dom.Node;


public class NoActionTag extends AbstractTag {

	public NoActionTag(Node node) {
		super(node);
	}

	@Override
	public void execute() {
//		System.out.println("No action:" + getNode().getNodeName());
	}

}
