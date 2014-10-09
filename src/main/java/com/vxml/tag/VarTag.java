package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;

public class VarTag extends AbstractTag {

    public VarTag(Node node) {
        super(node);
    }

    @Override
    public void execute() {
        String subdialogName = (String) VxmlBrowser.getContext().getScriptVar(
                VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + SubdialogTag.SUBDIALOG_NAME);
        String name = getAttribute("name");
        String expr = getAttribute("expr");
        String varName = name;
        Object value = expr;
        if (expr != null && !expr.equals("'true'")) {
            value = VxmlBrowser.getContext().executeScript(expr);
            value = value != null ? value : expr;
        } else if (subdialogName != null){
            // could be subdialog
            value = VxmlBrowser.getContext().getScriptVar(subdialogName + "." + name);
            if (value != null) {
                varName = subdialogName + "." + name;
            }
        }

        VxmlBrowser.getContext().assignScriptVar(varName, value);

    }

    public static void main(String[] args) {
        VxmlBrowser.getContext().assignScriptVar("application.UUID", "{'A':true}");
    }
}
