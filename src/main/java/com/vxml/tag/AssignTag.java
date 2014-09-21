package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class AssignTag extends AbstractTag {

    private String name;

    public AssignTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        name = getAttribute("name");
        VxmlBrowser.getContext().executeScript("var " + name);
    }

    @Override
    public void execute() {
        name = getAttribute("name");

        String expr = getAttribute("expr");
        if (expr != null) {
            Object exprResult = expr;
            if (!(expr.endsWith("'") && expr.startsWith("'"))) {
                exprResult = VxmlBrowser.getContext().executeScriptNullIfUndefined(expr);
            }
            if (exprResult instanceof String) {
                String e = (String) exprResult;
                if (!(e.startsWith("'") && e.startsWith("'"))) {
                    e = "'" + e + "'";
                }
                VxmlBrowser.getContext().executeScript(name + "=" + e + "");
            } else {
                VxmlBrowser.getContext().assignScriptVar(name, exprResult);
            }
        }

    }

}
