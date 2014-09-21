package com.vxml.core.browser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.script.ScriptException;

import com.vxml.browser.event.Event;
import com.vxml.core.VxmlException;
import com.vxml.parser.VxmlDoc;

public class VxmlBrowser {

	private String entryPointUrl;
	private static VxmlExecutionContext context;

	public VxmlBrowser() {
		if (context != null) {
			try {
				context = new VxmlExecutionContext();
				//default input is from keyboard
				context.setDtmfSource(new BufferedReader(new InputStreamReader(System.in)));
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void start() throws VxmlException, URISyntaxException, Event {
		URI uri = new URI(entryPointUrl);
		context.setDocBaseUrl(uri.getScheme() + "://" + uri.getAuthority());
		
		// Document xml = new DocumentStore().getDoc(uri);
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
		vxmlBrowser.setEntryPointUrl("http://localhost:8080/javascript/index.html");
		vxmlBrowser.start();
	}

	public static VxmlExecutionContext getContext() {
		if (context == null) {
			try {
				context = new VxmlExecutionContext();
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		return context;
	}

	public void setContext(VxmlExecutionContext context) {
		this.context = context;
	}
}
