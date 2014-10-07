package com.vxml.tag;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.vxml.browser.event.Event;
import com.vxml.browser.event.ReturnFromSubdialogEvent;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlExecutionContext;
import com.vxml.core.browser.VxmlScriptEngine;
import com.vxml.parser.VxmlDoc;

public class SubdialogTag extends AbstractTag {

    public static final String SUBDIALOG_NAME = ".subdialogName";
    
    private String subdialogName;

    public SubdialogTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        subdialogName = getAttribute("name");
    }

    @Override
    public void execute() {
        VxmlBrowser.getContext().assignScriptVar(VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + SUBDIALOG_NAME, subdialogName);
//        VxmlBrowser.getContext().executeScript("var " + name + "={}");
        String srcexpr = getAttribute("srcexpr");
        String src = getAttribute("src");
        String cond = getAttribute("cond");
//        String target = getAttribute("name");
        src = src != null ? src : (String) VxmlBrowser.getContext().getScriptVar(srcexpr);

        StringBuilder url = getUrl(src);
        try {
            new VxmlDoc(url.toString()).play();
        } catch (Event e) {
            if (e instanceof ReturnFromSubdialogEvent) {
                System.err.println("Returning from subdialog.........");
            }
        }

    }

    private StringBuilder getUrl(String src) {
        StringBuilder url = new StringBuilder();
        url.append(VxmlExecutionContext.getDocBaseUrl());
        url.append(src);
        url.append("?");
        NodeList paramList = getNode().getChildNodes();
        for (int i = 0; i < paramList.getLength(); i++) {
            Node node = paramList.item(i);
            if ("param".equals(node.getNodeName())) {
                String name = node.getAttributes().getNamedItem("name").getNodeValue();
                String expr = node.getAttributes().getNamedItem("expr").getNodeValue();
                url.append(name);
                url.append("=");
                Object val = VxmlBrowser.getContext().executeScript(expr);
                VxmlBrowser.getContext().assignScriptVar(subdialogName + "." + name, val);
                url.append(val);
                url.append("&");
            }
        }
        return url;
    }

    @Override
    public void endTag() {
        VxmlBrowser.getContext().assignScriptVar(VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + SUBDIALOG_NAME, null);
        System.err.println("SUBDIALOG ENDS" + subdialogName);
    }

    public static void main(String[] args) {
        String r = "<xml v='ww'></xml>";
        VxmlBrowser.getContext().executeScript("var " + "test" + "='" + r.replaceAll("'", "\\\\'") + "'");
    }
}
