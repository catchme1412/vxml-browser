package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class ElseTag extends AbstractTag {

    public ElseTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {

    }

    @Override
    public void execute() {
        Boolean isIfCondition = (Boolean) VxmlBrowser.getContext().executeScript(
                "_vxmlExecutionContext.ifConditionLevel_" + ifConditionLevel);
        if (isIfCondition) {
            setSkipExecute(true);
        } else {
            setSkipExecute(false);
        }
    }
}
