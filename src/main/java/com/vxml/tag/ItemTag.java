package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class ItemTag extends AbstractTag {

    private boolean isSlientModeBackup;
    
    public ItemTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        isSlientModeBackup = VxmlBrowser.getContext().isSlientMode();
        VxmlBrowser.getContext().setSlientMode(true);
    }

    @Override
    public void execute() {}

    @Override
    public void endTag() {
    	VxmlBrowser.getContext().setSlientMode(isSlientModeBackup);
    }
}
