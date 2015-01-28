package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;

public class ElseTag extends AbstractTag {

    public ElseTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        // Boolean isIfCondition = isIfConditionTrue();
        Boolean isElseIfCondition = checkElseIfCondition();
        boolean isElseIfPresent = isElseIfCondition != null;

        boolean isAllParentIfsTrue = checkParentIfs();

        if (isAllParentIfsTrue && !isSkipExecute()) {
            if (isElseIfPresent && !isElseIfCondition) {
                setSkipExecute(false);
            } else if (!isSkipExecute()) {
                setSkipExecute(true);
            } else {
                setSkipExecute(false);
            }
        } else {
            setSkipExecute(true);
        }
    }

    private Boolean checkElseIfCondition() {
        return (Boolean) VxmlBrowser.getContext().getScriptVar(
                VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + ".elseIfCondition_" + ifConditionLevel);
    }

    private Boolean isIfConditionTrue() {
        return (Boolean) VxmlBrowser.getContext().getScriptVar(
                VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + IfTag.IF_CONDITION_LEVEL_PREFIX + ifConditionLevel);
    }

    private boolean checkParentIfs() {
        for (int i = 0; i < ifConditionLevel - 1; i++) {
            Boolean t = (Boolean) VxmlBrowser.getContext().getScriptVar(
                    VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + IfTag.IF_CONDITION_LEVEL_PREFIX + i);
            if (t != null && t == false) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute() {

    }
}
