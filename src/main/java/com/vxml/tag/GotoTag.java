package com.vxml.tag;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.vxml.browser.event.Event;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.parser.VxmlDoc;

public class GotoTag extends AbstractTag {

    private String target;

    public GotoTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        String src = getAttribute("src");
        String next = getAttribute("next");
        String expr = getAttribute("expr");
        target = src != null ? src : next;
        String executeScript = null;
        try {
            if (expr != null) {
                executeScript = (String) VxmlBrowser.getContext().executeScript(expr);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        target = (String) (target != null ? target : executeScript);
        String newTarget  = (String) VxmlBrowser.getContext().executeScript(target);
        System.out.println(this);
        if (newTarget != null) {
            target = newTarget;
        }
    }

    @Override
    public void execute() throws Event {
        if (target.startsWith("#")) {
            Tag form = VxmlBrowser.getContext().getTag(target.substring(1));
            if (form == null) {
                NodeList forms = getNode().getOwnerDocument().getElementsByTagName("form");
                for (int i = 0; i < forms.getLength(); i++) {
                    String formName = forms.item(i).getAttributes().getNamedItem("id").getNodeValue();
                    if (formName.equals(target.substring(1))) {
                        Node formNode = forms.item(i);
                        form = TagFactory.get(formNode);
                    }
                }
            } 
            ((AbstractTag) form).tryExecute();
        } else {
            new VxmlDoc(target).play();
        }
    }

}
