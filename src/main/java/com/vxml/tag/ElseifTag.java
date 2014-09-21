package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;

public class ElseifTag extends AbstractTag {


    public ElseifTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
    }

    @Override
    public void execute() {
        Boolean isIfCondition = isIfConditionTrue();
        if (!isIfCondition) {
            String cond = getAttribute("cond");
            Boolean elseIfCondition = (Boolean) VxmlBrowser.getContext().executeScript(cond);

            if (elseIfCondition != null && elseIfCondition) {
                setSkipExecute(false);
                // Just to skip else tag
                VxmlBrowser.getContext().executeScript(VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE
                        + ".ifCondition=true");
            } else {
                setSkipExecute(true);
            }
        } else {
            setSkipExecute(true);
        }
    }

    private Boolean isIfConditionTrue() {
        return (Boolean) VxmlBrowser.getContext().executeScript(
                "_vxmlExecutionContext.ifConditionLevel_" + ifConditionLevel);
    }

    @Override
    public void endTag() {
    }
}
