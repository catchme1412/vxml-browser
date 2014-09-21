package com.vxml.tag;

import org.w3c.dom.Node;

public class NomatchTag extends AbstractTag {

	public NomatchTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
		setSkipExecute(true);
	}

	@Override
	public void execute() {
	}

	@Override
	public void endTag() {
		setSkipExecute(false);
	}

}
