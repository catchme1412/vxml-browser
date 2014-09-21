package com.vxml.parser;

import java.net.URI;

import org.w3c.dom.Document;

import com.vxml.browser.event.Event;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.store.DocumentStore;

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
        this.setDocumentUrl(string);
        URI uri;
        uri = VxmlBrowser.getContext().getFullUri(string);
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
