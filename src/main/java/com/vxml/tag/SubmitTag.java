package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.browser.event.Event;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.parser.VxmlDoc;

public class SubmitTag extends AbstractTag {

    public SubmitTag(Node node) {
        super(node);
    }

    @Override
    public void execute() throws Event {
        
        if(!getNode().getParentNode().getNodeName().equals("catch")) {
            String exprValue = getAttribute("expr");
            String expr = null;
            if (exprValue != null) {
                expr = (String) VxmlBrowser.getContext().executeScript(exprValue);
            }
            String next = getAttribute("next");
            String src = expr != null ? expr : next;
            
            StringBuilder queryParams = new StringBuilder();
            src= VxmlBrowser.getContext().getDocBaseUrl() + src;
            
            queryParams.append(src);
            queryParams.append("?");
            String namelist = getAttribute("namelist");
            String nameListArray[] = namelist.split(" ");
            for (int i = 0; i < nameListArray.length; i++) {
                queryParams.append(nameListArray[i]);
                queryParams.append("=");
                queryParams.append(VxmlBrowser.getContext().executeScript(nameListArray[i] + ";"));
                queryParams.append("&");
            }
				//TODO enable POST method
//				result = new DocumentStore().getDoc(new URI(queryParams.toString()));
				new VxmlDoc(queryParams.toString()).play();
        }
        
    }
    
}
