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
        String name = getAttribute("name");
        String expr = getAttribute("expr");
        Object value = expr;
        if (expr != null) {
            value = VxmlBrowser.getContext().executeScript(expr);
            VxmlBrowser.getContext().assignScriptVar(name, value);
        }
//        if (expr != null) {
//            Object value = VxmlBrowser.getContext().executeScript(expr);
//
//            if (value != null && !(value instanceof Undefined)) {
//                VxmlBrowser.getContext().assignScriptVar(name, value);
//            } else {
//                Object v = VxmlBrowser.getContext().getScriptVar(expr);
//                if (v != null && !(v instanceof Undefined)) {
//                    VxmlBrowser.getContext().assignScriptVar(name, v);
//                }
//            }
//        } else {
//            VxmlBrowser.getContext().assignScriptVar(name, null);
//        }
    }

    public static void main(String[] args) {
        System.out.println("FFF");
        VxmlBrowser.getContext().assignScriptVar("application.UUID", "{'A':true}");
    }
}
