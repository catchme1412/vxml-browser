package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class PromptTag extends AbstractTag {

    private boolean isSkipConditionBackup;
    
    public PromptTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        isSkipConditionBackup = isSkipExecute();
    }

    @Override
    public void execute() {
        // System.out.println("PROMPT:"+ getNode());
        String cond = getAttribute("cond");
        if (cond != null) {
            Boolean isTrue = (Boolean) VxmlBrowser.getContext().executeScript(cond);
            if (!isTrue) {
                setSkipExecute(true);
            }
        }
    }
    
    @Override
    public void endTag() {
        setSkipExecute(isSkipConditionBackup);
    }

}
