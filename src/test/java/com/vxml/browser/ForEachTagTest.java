package com.vxml.browser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vxml.browser.event.Event;
import com.vxml.core.VxmlException;
import com.vxml.core.browser.VxmlBrowser;

public class ForEachTagTest {

	private VxmlBrowser vxmlBrowser;
	private Scanner dtmfSource;

	@BeforeMethod
	public void init() {
		vxmlBrowser = new VxmlBrowser();
	}

	@BeforeClass
	@AfterMethod
	public void cleanUp() {
		if (dtmfSource != null) {
			dtmfSource.close();
		}
	}

	@Test
	public void testForEach() throws VxmlException, URISyntaxException, Event, IOException {
		final String DTMF_INPUT = "1";
		final String[] FOR_LOOP_ITEMS = {"mango", "cotton candy", "apple pie", "cheesecake", "raspberry"};

		vxmlBrowser.setEntryPointUrl(VxmlBrowserWrapper.getFullUri("/forEach.vxml"));

		dtmfSource = new Scanner(DTMF_INPUT);
		dtmfSource.useDelimiter(",");
		VxmlBrowser.getContext().setDtmfSource(dtmfSource);
		VxmlBrowserWrapper verifier = new VxmlBrowserWrapper(vxmlBrowser);
		verifier.start();
		AssertJUnit.assertEquals(verifier.nextOuput(), "TTS:Welcome to ABC Frozen Yogurt.");
		AssertJUnit.assertEquals(verifier.nextOuput(), "TTS:The current flavors of the week are");
		for (int i = 0; i < FOR_LOOP_ITEMS.length ; i++) {
			AssertJUnit.assertEquals(verifier.nextOuput(), FOR_LOOP_ITEMS[i]);
		}
		AssertJUnit.assertEquals(verifier.nextOuput(), "TTS:Thank you for calling ABC Frozen Yogurt. We hope you stop by soon.");
	}

}
