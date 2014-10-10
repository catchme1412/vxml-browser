package com.vxml.tag;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.vxml.core.browser.VxmlBrowser;

public class ItemTag extends AbstractTag {

    private boolean isSlientModeBackup;
    
    public ItemTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        isSlientModeBackup = VxmlBrowser.getContext().isSlientMode();
        VxmlBrowser.getContext().setSlientMode(true);
    }

    @Override
    public void execute() {
        System.out.println(getNode().getTextContent());
        System.out.println(getNode().getChildNodes());
        NodeList childNodes = getNode().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            System.out.println(childNodes.item(i));
            Node n = childNodes.item(i);
            if(n.getNodeName().equals("#text")) {
                Node t = childNodes.item(++i);
                if (t != null && t.getTextContent() != null) {
                    
                    String[] split = t.getTextContent().split("=");
                    System.out.println(split);
                    if(split.length > 1) {
                        addDtmfTranslation(n.getTextContent(), split[1].replaceAll("'", ""));
                    }
                }
            }
        }
    }

    @Override
    public void endTag() {
    	VxmlBrowser.getContext().setSlientMode(isSlientModeBackup);
    }
}
