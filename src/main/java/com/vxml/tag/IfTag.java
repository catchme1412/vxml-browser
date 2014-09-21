package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class IfTag extends AbstractTag {

    private Boolean isIfConditionTrue;
    private boolean isSkipBackup;

    public IfTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        ifConditionLevel++;
//        VxmlBrowser.getContext().assignScriptVar("_vxmlExecutionContext.ifConditionLevel_" + ifConditionLevel, null);
        VxmlBrowser.getContext().executeScript("_vxmlExecutionContext.ifConditionLevel_" + ifConditionLevel + "=false");
        isSkipBackup = isSkipExecute();
    }

    @Override
    public void execute() {
        String cond = getAttribute("cond");
        isIfConditionTrue = (Boolean) VxmlBrowser.getContext().executeScript(cond);
        isIfConditionTrue = isIfConditionTrue != null ? isIfConditionTrue : false;
        VxmlBrowser.getContext().executeScript(
                "_vxmlExecutionContext.ifConditionLevel_" + ifConditionLevel + "=" + isIfConditionTrue);
        if (isIfConditionTrue) {
            setSkipExecute(false);
        } else {
            setSkipExecute(true);
        }
    }

    @Override
    public void endTag() {
        VxmlBrowser.getContext().executeScript("_vxmlExecutionContext.ifConditionLevel_" + ifConditionLevel+"=false");
        ifConditionLevel--;
        setSkipExecute(isSkipBackup);
    }

}
