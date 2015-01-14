package com.vxml.tag;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import com.vxml.browser.event.Event;
import com.vxml.parser.VxmlDoc;

public class VxmlTag extends AbstractTag {

    private static Map<String, String> appRootMap =new HashMap<String, String>();
    
	public VxmlTag(Node item) {
		super(item);
	}

	@Override
	public void startTag() {
	    ifConditionLevel = 0;
//	    try {
//	        ifConditionLevel = ifConditionLevelStack.pop();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
	}
	
	@Override
	public void execute() throws Event {
		String application = getAttribute("application");
		if (application != null && !appRootMap.containsKey(application)) {
		    appRootMap.put (application, application);
		    if (!(application.startsWith("/") || application.startsWith("http") || application.startsWith("file"))) {
		        String file = getNode().getOwnerDocument().getDocumentURI();
		        file = file.substring(0, file.lastIndexOf("/")+1);
		        application = file + application;
		    }
		    new VxmlDoc(application).play();
		}
	}

}
