package com.vxml.tag;

import com.vxml.browser.event.Event;



public interface Tag {

	public void startTag();
	public void execute() throws Event;
	public void endTag();
}
