package com.vxml.browser;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.vxml.browser.event.Event;
import com.vxml.core.VxmlException;
import com.vxml.core.browser.VxmlBrowser;

public class SubdialogTest {

    private VxmlBrowser vxmlBrowser;
   
    @Test
    public void testIf() throws VxmlException, URISyntaxException, Event, IOException {
        vxmlBrowser = new VxmlBrowser();
        String fullUri = VxmlBrowserWrapper.getFullUri("/mainDialog.vxml");
        vxmlBrowser.setEntryPointUrl(fullUri);

        VxmlBrowserWrapper verifier = new VxmlBrowserWrapper(vxmlBrowser);
        verifier.start();
        AssertJUnit.assertEquals(verifier.nextOuput(), "TTS:Input 1 or 2 or something else?");
        AssertJUnit.assertEquals(verifier.nextOuput(), "TTS:Input is one");
    }
}
