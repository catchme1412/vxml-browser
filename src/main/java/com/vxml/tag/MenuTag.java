package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.browser.event.Event;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;


public class MenuTag extends AbstractTag {

    public MenuTag(Node node) {
        super(node);
    }

    @Override
    public void execute() throws Event {
    }

    @Override
    public void endTag() {
        VxmlBrowser.getContext().assignScriptVar(VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + ".dtmfInput",
                null);
    }

}
