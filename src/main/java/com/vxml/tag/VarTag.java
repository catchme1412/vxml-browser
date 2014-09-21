package com.vxml.tag;

import org.w3c.dom.Node;

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
        VxmlBrowser.getContext().executeScript("var " + name);
        if (expr != null) {
            try {
                VxmlBrowser.getContext().assignScriptVar(name, VxmlBrowser.getContext().executeScript(expr));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // try {
            // Object value = VxmlBrowser.getContext().executeScript(expr);
            // // override if possible
            // VxmlBrowser.getContext().assignScriptVar(name, value);
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
        }
    }

    public static void main(String[] args) {
        System.out.println("FFF");
        VxmlBrowser.getContext().assignScriptVar("application.UUID", "{'A':true}");
    }
}
