package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlExecutionContext;

public class ItemTag extends AbstractTag {

    private boolean isSlientModeBackup;
    
    public ItemTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        isSlientModeBackup = VxmlExecutionContext.isSlientMode();
        VxmlExecutionContext.setSlientMode(true);
    }

    @Override
    public void execute() {}

    @Override
    public void endTag() {
        VxmlExecutionContext.setSlientMode(isSlientModeBackup);
    }
}
