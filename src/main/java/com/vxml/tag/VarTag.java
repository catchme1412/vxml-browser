package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;

public class VarTag extends AbstractTag {

    private String subdialogName;

    public VarTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        subdialogName = (String) VxmlBrowser.getContext().getScriptVar(
                VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + SubdialogTag.SUBDIALOG_NAME);
    }
    @Override
    public void execute() {
        String name = getAttribute("name");
        String expr = getAttribute("expr");
        String varName = name;
        Object value = expr;
        if (expr != null && !expr.equals("'true'")) {
            value = VxmlBrowser.getContext().executeScript(expr);
            value = value != null ? value : expr;
        } else if (subdialogName != null) {
            //in subdialog
            //get value from local scope
            value = getFromSubdialogScope(name);
            //check the global scope
            if (value == null && expr == null) {
                value = VxmlBrowser.getContext().getScriptVar(name);
            } else {
//                varName = subdialogName + "." + name;
            }
            value = value != null ? value : expr;
        }

        VxmlBrowser.getContext().assignScriptVar(varName, value);

    }

    private Object getFromSubdialogScope(String name) {
        String localVarName = subdialogName + "." + name;
        return  VxmlBrowser.getContext().getScriptVar(localVarName);
    }

    public static void main(String[] args) {
        VxmlBrowser.getContext().assignScriptVar("application.UUID", "{'A':true}");
    }
}
