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

public class SubdialogTest {

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
    public void testIf() throws VxmlException, URISyntaxException, Event, IOException {
        final String DTMF_INPUT = "1";

        vxmlBrowser.setEntryPointUrl(VxmlBrowserWrapper.getFullUri("/mainDialog.vxml"));

        dtmfSource = new Scanner(DTMF_INPUT);
        dtmfSource.useDelimiter(",");
        VxmlBrowser.getContext().setDtmfSource(dtmfSource);
        VxmlBrowserWrapper verifier = new VxmlBrowserWrapper(vxmlBrowser);
        verifier.start();
        AssertJUnit.assertEquals(verifier.nextOuput(), "TTS:Input 1 or 2 or something else?");
        AssertJUnit.assertEquals(verifier.nextOuput(), "TTS:Input is one");
    }
}
