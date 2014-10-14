package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.browser.event.Event;
import com.vxml.browser.event.ReturnFromSubdialogEvent;
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
            String next = getAttribute("next");
            if(next.contains("sao.vxml")) {
                next = "/ivr/testing/" + next;
            }
            String src = exprValue != null ? exprValue : next;
            if (exprValue != null) {
                src = (String) VxmlBrowser.getContext().executeScript(src);
            }
            
            StringBuilder queryParams = new StringBuilder();
            src= VxmlBrowser.getContext().getDocBaseUrl() + src;
            
            queryParams.append(src);
            queryParams.append("?");
            String namelist = getAttribute("namelist");
            if (namelist != null) {
                String nameListArray[] = namelist.split(" ");
                for (int i = 0; i < nameListArray.length; i++) {
                    queryParams.append(nameListArray[i]);
                    queryParams.append("=");
                    queryParams.append(VxmlBrowser.getContext().executeScript(nameListArray[i] + ";"));
                    queryParams.append("&");
                }
            }
				//TODO enable POST method
//				result = new DocumentStore().getDoc(new URI(queryParams.toString()));
				try {
                    new VxmlDoc(queryParams.toString()).play();
                } catch (Event e) {
                    if (e instanceof ReturnFromSubdialogEvent) {
                        System.err.println("Returning from submit .........");
                    }
                }
        }
        
    }
    
}
