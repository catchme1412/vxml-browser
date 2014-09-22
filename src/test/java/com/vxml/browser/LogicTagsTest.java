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

public class LogicTagsTest {

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
        
        vxmlBrowser.setEntryPointUrl(VxmlBrowserWrapper.getFullUri("/ifElseIfElse.vxml"));

        dtmfSource = new Scanner(DTMF_INPUT);
        dtmfSource.useDelimiter(",");
        VxmlBrowser.getContext().setDtmfSource(dtmfSource);
        VxmlBrowserWrapper verifier = new VxmlBrowserWrapper(vxmlBrowser);
        verifier.start();
        AssertJUnit.assertEquals(verifier.next(), "TextTag:Input 1 or 2 or something else?");
        AssertJUnit.assertEquals(verifier.next(), "TextTag:Input is one");
    }
    
    @Test
    public void testElseIf() throws VxmlException, URISyntaxException, Event, IOException {
        final String DTMF_INPUT = "2";
        
        vxmlBrowser.setEntryPointUrl(VxmlBrowserWrapper.getFullUri("/ifElseIfElse.vxml"));

        dtmfSource = new Scanner(DTMF_INPUT);
        dtmfSource.useDelimiter(",");
        VxmlBrowser.getContext().setDtmfSource(dtmfSource);
        VxmlBrowserWrapper verifier = new VxmlBrowserWrapper(vxmlBrowser);
        verifier.start();
        AssertJUnit.assertEquals("TextTag:Input 1 or 2 or something else?", verifier.next());
        AssertJUnit.assertEquals("TextTag:Input is two", verifier.next());
        
    }
    

    @Test
    public void testNestedIf() throws VxmlException, URISyntaxException, Event, IOException {
        final String DTMF_INPUT = "true,true,false";
        
        vxmlBrowser.setEntryPointUrl(VxmlBrowserWrapper.getFullUri("/nestedIf.vxml"));

        dtmfSource = new Scanner(DTMF_INPUT);
        dtmfSource.useDelimiter(",");
        VxmlBrowser.getContext().setDtmfSource(dtmfSource);
        VxmlBrowserWrapper verifier = new VxmlBrowserWrapper(vxmlBrowser);
        verifier.start();
        AssertJUnit.assertEquals("TextTag:Input first if", verifier.next());
        AssertJUnit.assertEquals("TextTag:Input second if", verifier.next());
        AssertJUnit.assertEquals("TextTag:Input nested Else If", verifier.next());
        AssertJUnit.assertEquals("TextTag:Input nested Else If", verifier.next());
    }
    
    

}
