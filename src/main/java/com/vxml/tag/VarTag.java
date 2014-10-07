package com.vxml.tag;

import org.w3c.dom.Node;

import sun.org.mozilla.javascript.internal.Undefined;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;

public class VarTag extends AbstractTag {

    public VarTag(Node node) {
        super(node);
    }

    // VxmlBrowser.getContext().assignScriptVar(VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE
    // + ".subdialogName", name);
    // VxmlBrowser.getContext().assignScriptVar(VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE
    // + SUBDIALOG_NAME, name);

    @Override
    public void execute() {
        // System.out.println(getAttribute("name"));
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
        System.out.println("FFF");
        VxmlBrowser.getContext().assignScriptVar("application.UUID", "{'A':true}");
    }
}
