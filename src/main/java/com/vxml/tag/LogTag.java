package com.vxml.tag;

import java.util.logging.Logger;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlExecutionContext;

public class LogTag extends AbstractTag {

    private static final Logger log = Logger.getLogger(LogTag.class.getName());

    public LogTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        VxmlExecutionContext.setTtsAllowed(false);
    }

    @Override
    public void execute() {
        String message = getNode().getTextContent();
        log.info(message);
    }

    @Override
    public void endTag() {
        VxmlExecutionContext.setTtsAllowed(true);
    }
}
