package com.vxml.tag;

import java.util.logging.Logger;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class LogTag extends AbstractTag {

    private static final Logger log = Logger.getLogger(LogTag.class.getName());
    private boolean isSlientModeBackup;

    public LogTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        isSlientModeBackup = VxmlBrowser.getContext().isSlientMode();
        VxmlBrowser.getContext().setSlientMode(true);
    }

    @Override
    public void execute() {
        String message = getNode().getTextContent();
        log.info(message);
    }

    @Override
    public void endTag() {
    	VxmlBrowser.getContext().setSlientMode(isSlientModeBackup);
    }
}
