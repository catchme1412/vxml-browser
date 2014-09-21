package com.vxml.browser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vxml.browser.event.Event;
import com.vxml.core.VxmlException;
import com.vxml.core.browser.VxmlBrowser;

public class VxmlBrowserTest {

	private VxmlBrowser vxmlBrowser;

	@BeforeMethod
	public void init() {
		vxmlBrowser = new VxmlBrowser();
	}

	@Test
	public void f() throws VxmlException, URISyntaxException, Event, IOException {
		vxmlBrowser.setEntryPointUrl("http://localhost:8080/javascript/index.html");
		VxmlBrowserVerifier verifier = new VxmlBrowserVerifier(vxmlBrowser);
		verifier.inputDtmf("2");
		Scanner dtmfSource = new Scanner("noinput,3");
		dtmfSource.useDelimiter(",");
		vxmlBrowser.getContext().setDtmfSource(dtmfSource);
		verifier.start();
		verifier.resume();
		verifier.inputDtmf("3");
		Assert.assertEquals(verifier.next(), "d");
		Assert.assertEquals(verifier.isDisconnected(),null);
	}
}
