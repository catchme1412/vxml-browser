package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlExecutionContext;

public class ItemTag extends AbstractTag {

    public ItemTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        VxmlExecutionContext.setTtsAllowed(false);
    }

    @Override
    public void execute() {
        try {
            String input = getNode().getFirstChild().getTextContent();
            String inputMapping = getNode().getChildNodes().item(1).getTextContent();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void endTag() {
        VxmlExecutionContext.setTtsAllowed(true);
    }
}
