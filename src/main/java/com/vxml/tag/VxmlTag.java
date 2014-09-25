package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.browser.event.Event;
import com.vxml.parser.VxmlDoc;

public class VxmlTag extends AbstractTag {

    private static boolean isApplicationUriProcessed;
    
	public VxmlTag(Node item) {
		super(item);
	}

	@Override
	public void execute() throws Event {
		String application = getAttribute("application");
		if (application != null && !isApplicationUriProcessed) {
		    if (!(application.startsWith("/") || application.startsWith("http") || application.startsWith("file"))) {
		        String file = getNode().getOwnerDocument().getDocumentURI();
		        file = file.substring(0, file.lastIndexOf("/")+1);
		        application = file + application;
		    }
		    new VxmlDoc(application).play();
		    isApplicationUriProcessed = true;
			System.err.println("APPLICATION URI IS DONE");
		}
	}

}
