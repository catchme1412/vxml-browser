package com.vxml.core.browser;

import java.net.URI;
import java.net.URISyntaxException;

import javax.script.ScriptException;

import com.vxml.browser.event.Event;
import com.vxml.core.VxmlException;
import com.vxml.parser.VxmlDoc;

public class VxmlBrowser {

	private String entryPointUrl;
	// singleton instance
	private static VxmlExecutionContext singletonExecutionContext;

	public VxmlBrowser() {
		try {
			singletonExecutionContext = new VxmlExecutionContext();
			// default input is from keyboard
//			singletonExecutionContext.setDtmfSource(new Scanner(System.in));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public void start() throws VxmlException, URISyntaxException, Event {
		URI uri = new URI(entryPointUrl);
		VxmlExecutionContext.setDocBaseUrl(uri.getScheme() + "://" + uri.getAuthority());
		
		VxmlDoc vxmlDoc = new VxmlDoc(entryPointUrl);
		vxmlDoc.play();
	}

	public String getEntryPointUrl() {
		return entryPointUrl;
	}

	public void setEntryPointUrl(String entryPointUrl) {
		this.entryPointUrl = entryPointUrl;
	}

	public static void main(String[] args) throws VxmlException, URISyntaxException, Event {
		VxmlBrowser vxmlBrowser = new VxmlBrowser();
		vxmlBrowser.setEntryPointUrl("http://localhost:8585/ivr/testing/sao.vxml");
		vxmlBrowser.start();
	}

	public static VxmlExecutionContext getContext() {
		if (singletonExecutionContext == null) {
			try {
				singletonExecutionContext = new VxmlExecutionContext();
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		return singletonExecutionContext;
	}

	public void setContext(VxmlExecutionContext context) {
		VxmlBrowser.singletonExecutionContext = context;
	}

}
