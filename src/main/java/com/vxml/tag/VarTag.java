package com.vxml.tag;

import org.w3c.dom.Node;

import sun.org.mozilla.javascript.internal.Undefined;

import com.vxml.core.browser.VxmlBrowser;

public class VarTag extends AbstractTag {

    public VarTag(Node node) {
        super(node);
    }

    @Override
    public void execute() {
        // System.out.println(getAttribute("name"));
        String name = getAttribute("name");
        String expr = getAttribute("expr");
        if (expr != null) {
            Object v = VxmlBrowser.getContext().getScriptVar(expr);
            if (v != null && !(v instanceof Undefined)) {
                VxmlBrowser.getContext().assignScriptVar(name, v);
            }
            Object value = VxmlBrowser.getContext().executeScript(expr);
            if (value != null && !(value instanceof Undefined)) {
                VxmlBrowser.getContext().assignScriptVar(name, value);
            }
        } else {
            VxmlBrowser.getContext().assignScriptVar(name, null);
        }
    }

    public static void main(String[] args) {
        System.out.println("FFF");
        VxmlBrowser.getContext().assignScriptVar("application.UUID", "{'A':true}");
    }
}
