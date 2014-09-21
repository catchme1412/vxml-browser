package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.browser.event.Event;

public class CDATASectionTag extends AbstractTag {

    public CDATASectionTag(Node node) {
        super(node);
    }

    @Override
    public void execute() throws Event {
    }

}
