package com.vxml.browser;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.*;

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
		verifier.start();
		verifier.inputDtmf("3");
		Assert.assertEquals(verifier.next(), null);
		Assert.assertEquals(verifier.isDisconnected(),null);
	}
}
