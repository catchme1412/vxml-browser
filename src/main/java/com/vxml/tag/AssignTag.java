package com.vxml.tag;

import org.w3c.dom.Node;

import sun.org.mozilla.javascript.internal.Undefined;

import com.vxml.core.browser.VxmlBrowser;

public class AssignTag extends AbstractTag {

    private String name;

    public AssignTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        name = getAttribute("name");
        VxmlBrowser.getContext().assignScriptVar(name, null);
    }

    @Override
    public void execute() {
        name = getAttribute("name");
        String expr = getAttribute("expr");
        expr = cleanup(expr);
        if (expr != null) {
            Object exprResult = expr;
            Object val = VxmlBrowser.getContext().getScriptVar(expr);
            if (!(val instanceof Undefined || val == null)) {
                exprResult = val;
            }
            VxmlBrowser.getContext().assignScriptVar(name, exprResult);
        }
    }

    private String cleanup(String expr) {
       if (expr.startsWith("'") && expr.endsWith("'")) {
           return expr.substring(1, expr.length() -1);
       }
       return expr;
    }
}
