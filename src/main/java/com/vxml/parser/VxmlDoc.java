package com.vxml.parser;

import java.net.URI;
import java.util.Scanner;

import org.w3c.dom.Document;

import com.vxml.browser.event.Event;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;
import com.vxml.store.DocumentStore;
import com.vxml.tag.AbstractTag;

public class VxmlDoc {

    private String documentUrl;
    
    private Document xmlDoc;
//    private Stack<Tag> stack;
    private DocumentStore documentStore;

//    public VxmlDoc(Document xml) {
//        xmlDoc = xml;
////        stack = new Stack<Tag>();
//    }

    public VxmlDoc(String string) {
        
    	AbstractTag.setSkipExecute(false);
    	VxmlBrowser.getContext().assignScriptVar(VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + ".dtmfInput",
                null);
    	AbstractTag.clearDtmfTranslations();
        this.setDocumentUrl(string);
        URI uri;
        uri = VxmlBrowser.getContext().getFullUri(string);
        String query = uri.getQuery();
        if (query != null) {
            
            Scanner sc = new Scanner(query);
            sc.useDelimiter("&");
            while(sc.hasNext()) {
                String t = sc.next();
                if (t.contains("ani")) {
                    VxmlBrowser.getContext().assignScriptVar("application.ANI", t.split("=")[1]);
                }
                if (t.contains("dnis")) {
                    VxmlBrowser.getContext().assignScriptVar("application.DNIS", t.split("=")[1]);
                }
            }
        }
        documentStore = new DocumentStore();
        xmlDoc = documentStore.getDoc(uri);
//        stack = new Stack<Tag>();

    }

    public Document getXmlDoc() {
        return xmlDoc;
    }

    // private void walk(Node node) {
    // Tag tag = TagFactory.get(node);
    // System.out.println("START:" + tag);
    // stack.add(tag);
    // tag.startTag();
    // ((AbstractTag) tag).tryExecute();
    // int type = node.getNodeType();
    //
    // // recurse
    // for (Node child = node.getFirstChild(); child != null; child =
    // child.getNextSibling()) {
    // walk(child);
    // }
    //
    // // without this the ending tags will miss
    // if (type == Node.ELEMENT_NODE) {
    // Tag t = stack.pop();
    // System.out.println("END:" + t);
    // t.endTag();
    // } else {
    // System.out.println("END:" + tag);
    // tag.endTag();
    //
    // }
    //
    // }

    public void play() throws Event {
        new VxmlParser().parse(this);
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

}
