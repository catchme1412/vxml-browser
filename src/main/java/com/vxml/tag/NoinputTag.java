package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class NoinputTag extends AbstractTag {

    public NoinputTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        if ("noinput".equals(VxmlBrowser.getContext().getEventHandler().getLastEvent())) {
            setSkipExecute(false);
            VxmlBrowser.getContext().getEventHandler().clearEvent();
        } else {
            setSkipExecute(true);
        }
        VxmlBrowser.getContext().getEventHandler().addEventHandle("noinput", this);
    }

    @Override
    public void execute() {
    }

    @Override
    public void endTag() {
        setSkipExecute(false);
    }

}
