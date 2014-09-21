package com.vxml.tag;

import java.io.InputStream;
import java.net.URI;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlExecutionContext;
import com.vxml.store.DocumentStore;

public class ScriptTag extends AbstractTag {

    public ScriptTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        VxmlExecutionContext.setTtsAllowed(false);
    }

    @Override
    public void execute() {
        String src = getAttribute("src");
        if (src != null) {
            URI uri = VxmlBrowser.getContext().getFullUri(src);
            InputStream script;
            try {
                script = new DocumentStore().getInputStream(uri);
                VxmlBrowser.getContext().executeScript(script);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            VxmlBrowser.getContext().executeScript(getNode().getTextContent());
            
        }
    }
    
    @Override
    public void endTag() {
        VxmlExecutionContext.setTtsAllowed(true);
    }

}
