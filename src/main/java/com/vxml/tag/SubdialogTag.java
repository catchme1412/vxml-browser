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

    private String name;

    public SubdialogTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        name = getAttribute("name");
        VxmlBrowser.getContext().executeScript("var " + name + "={}");
        VxmlBrowser.getContext().executeScript(
                VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + ".subdialogName='" + name+"'");
    }

    @Override
    public void execute() {
        String srcexpr = getAttribute("srcexpr");
        String src = getAttribute("src");
//        String target = getAttribute("name");
        src = src != null ? src : (String) VxmlBrowser.getContext().executeScript(srcexpr);

        StringBuilder url = getUrl(src);
        try {
            new VxmlDoc(url.toString()).play();
        } catch (Event e) {
            if (e instanceof ReturnFromSubdialogEvent) {
                System.out.println("Returning from subdialog.........");
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
                url.append(VxmlBrowser.getContext().executeScript(expr));
                url.append("&");
            }
        }
        return url;
    }

    @Override
    public void endTag() {
        System.err.println("SUBDIALOG ENDS" + name);
    }

    public static void main(String[] args) {
        String r = "<xml v='ww'></xml>";
        VxmlBrowser.getContext().executeScript("var " + "test" + "='" + r.replaceAll("'", "\\\\'") + "'");
    }
}
