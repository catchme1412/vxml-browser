package com.vxml.browser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import com.vxml.browser.event.Event;
import com.vxml.core.VxmlException;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.parser.event.OutputListener;
import com.vxml.tag.Tag;

public class VxmlBrowserVerifier {
	OutputListener audioEventListener;
	LinkedBlockingDeque<String> queue;
	private VxmlBrowser vxmlBrowser;

	private LinkedBlockingDeque<String> keyInputList;

	public VxmlBrowserVerifier(VxmlBrowser vxmlBrowser) throws IOException {
		keyInputList = new LinkedBlockingDeque<String>();
		this.vxmlBrowser = vxmlBrowser;

		queue = new LinkedBlockingDeque<String>();
		audioEventListener = new OutputListener() {
			@Override
			public void invoke(Tag tag) {
				System.out.println(tag);
			}
		};
		VxmlBrowser.getContext().getEventHandler().register("audio", audioEventListener);
	}

	public void start() throws VxmlException, URISyntaxException, Event {
		vxmlBrowser.start();
	}

	public void inputDtmf(String i) {
		keyInputList.add(i);
	}

	public String next() {
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
}
