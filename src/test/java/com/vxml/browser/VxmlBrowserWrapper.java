package com.vxml.browser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

import com.vxml.browser.event.Event;
import com.vxml.core.VxmlException;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlExecutionContext;
import com.vxml.parser.event.OutputListener;
import com.vxml.tag.AbstractTag;
import com.vxml.tag.Tag;

public class VxmlBrowserWrapper {
	OutputListener audioEventListener;
	LinkedBlockingDeque<String> queue;
	private VxmlBrowser vxmlBrowser;

	private LinkedBlockingDeque<String> keyInputList;

	private static String baseUrl = "http://localhost:8080/vxml-browser";
	
	
	public VxmlBrowserWrapper(VxmlBrowser vxmlBrowser) throws IOException {
		keyInputList = new LinkedBlockingDeque<String>();
		this.vxmlBrowser = vxmlBrowser;
		VxmlExecutionContext.setSlientMode(true);

		queue = new LinkedBlockingDeque<String>();
		audioEventListener = new OutputListener() {
			@Override
			public void invoke(Tag tag) {
			    if (((AbstractTag) tag).getNode().getParentNode().getNodeName().equals("prompt")) {
			        queue.add(tag.toString());
			    }
			}
		};
		VxmlBrowser.getContext().getEventHandler().register("#text", audioEventListener);
	}

	public void start() throws VxmlException, URISyntaxException, Event {
		vxmlBrowser.start();
	}

	public void inputDtmf(String i) {
		keyInputList.add(i);
	}

	public String nextOuput() {
		if (!queue.isEmpty()) {
			return queue.pop();
		}
		return null;
	}

	public String nextTTS() {

		return null;
	}

	public Object isDisconnected() {

		return null;
	}

	class FakeInputStream extends BufferedReader {

		public FakeInputStream(Reader in) {
			super(in);
		}

		@Override
		public String readLine() throws IOException {
			return keyInputList.pop();
		}

	}

	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public static String getFullUri(String uri) {
	    if (!uri.startsWith("http")) {
	        return getBaseUrl() + uri;
	    }
	    return uri;
	}

	public static String getBaseUrl() {
		return baseUrl;
	}

	public static void setBaseUrl(String baseUrl) {
		VxmlBrowserWrapper.baseUrl = baseUrl;
	}

    public void setDtmlInputSequence(String input, String delimiter) {
        Scanner dtmfSource = new Scanner(input);
        dtmfSource.useDelimiter(delimiter);
        VxmlBrowser.getContext().setDtmfSource(dtmfSource);
        
    }
}
