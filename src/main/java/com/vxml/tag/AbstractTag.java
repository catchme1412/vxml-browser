package com.vxml.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.vxml.browser.event.Event;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.parser.event.OutputListener;
import com.vxml.utils.XmlUtils;

public abstract class AbstractTag implements Tag {

    private Node node;

    private static boolean isSkipExecute;
    
    protected static int ifConditionLevel;

    public AbstractTag(Node node) {
        this.node = node;
    }

    public void startTag() {
    	//do nothing
    }

    public void endTag() {
    	//do nothing
    }

    public Node getNode() {
        return node;
    }

    public String getAttribute(String key) {
        return XmlUtils.getAttribute(getNode(), key);
    }

    // similar to walk
    public void executeChildTree(Node startNode) throws Event {
        if (XmlUtils.isEmptyOrComment(startNode)) {
            return;
        }

        // recurse
        for (Node child = startNode.getFirstChild(); child != null; child = child.getNextSibling()) {
            AbstractTag tag = (AbstractTag) TagFactory.get(child);
            if (XmlUtils.isEmptyOrComment(child)) {
                continue;
            }
            // System.out.println("START:" + node.getNodeType() + "::" + tag);
            // stack.add(tag);
            tag.startTag();
            ((AbstractTag) tag).tryExecute();
            executeChildTree(child);
            tag.endTag();
        }

        // System.out.println("END:" + tag);
    }

    public String nodeToString() {
        return XmlUtils.nodeToString(getNode());
    }

    @Override
    public String toString() {
        String xml = nodeToString();
        String tag = xml.substring(0, xml.indexOf(">") + 1);
        if (tag.isEmpty()) {
            return "\t#text:" + getNode().getTextContent().trim();
        }
        return tag;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void tryExecute() throws Event {
        if (isSkipExecute()) {
            // System.out.println("SKIPPING:" + this);
        } else {
            System.out.println("EXECUTING:" + this + "\t<=\t" + getNode().getOwnerDocument().getDocumentURI());
            execute();
            VxmlBrowser.getContext().getEventHandler().invokeListeners(this);
        }
    }

    public static boolean isSkipExecute() {
        return isSkipExecute;
    }

    public static void setSkipExecute(boolean isSkip) {
        AbstractTag.isSkipExecute = isSkip;
    }

}
