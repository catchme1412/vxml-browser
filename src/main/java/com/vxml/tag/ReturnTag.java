package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.browser.event.ReturnFromSubdialogEvent;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;

public class ReturnTag extends AbstractTag {

    private String subdialogName;

    public ReturnTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        subdialogName = (String) VxmlBrowser.getContext().getScriptVar(
                VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + SubdialogTag.SUBDIALOG_NAME);
//        setSkipExecute(subdialogName == null);
    }

    @Override
    public void execute() throws ReturnFromSubdialogEvent {
        String namelist = getAttribute("namelist");
        for (String name : namelist.split(" ")) {
            String subDialogVariableName = subdialogName + "." + name;
//            System.err.println("SETTING: " + subdialogName + ":" + name);
            VxmlBrowser.getContext().assignScriptVar(subDialogVariableName,
                    VxmlBrowser.getContext().getScriptVar(name));
        }
        throw new ReturnFromSubdialogEvent();
    }

    @Override
    public void endTag() {
    }

}
