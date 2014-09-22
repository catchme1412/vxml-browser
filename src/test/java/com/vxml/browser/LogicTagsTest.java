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
import com.vxml.tag.AbstractTag;

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
        AssertJUnit.assertEquals(verifier.nextOuput(), "TTS:Input 1 or 2 or something else?");
        AssertJUnit.assertEquals(verifier.nextOuput(), "TTS:Input is one");
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
        AssertJUnit.assertEquals("TTS:Input 1 or 2 or something else?", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Input is two", verifier.nextOuput());
        
    }
    

    @Test
    public void testNestedIf() throws VxmlException, URISyntaxException, Event, IOException {
        String DTMF_INPUT = "true,true,false";
        
        vxmlBrowser.setEntryPointUrl(VxmlBrowserWrapper.getFullUri("/nestedIf.vxml"));

        dtmfSource = new Scanner(DTMF_INPUT);
        dtmfSource.useDelimiter(",");
        VxmlBrowser.getContext().setDtmfSource(dtmfSource);
        VxmlBrowserWrapper verifier = new VxmlBrowserWrapper(vxmlBrowser);
        verifier.start();
        AssertJUnit.assertEquals("TTS:Input first if", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Input second if", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Input nested Else If", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Both if true", verifier.nextOuput());
    }
    
    @Test
    public void testNestedElseIf() throws VxmlException, URISyntaxException, Event, IOException {
        String DTMF_INPUT = "true,false,true";
        dtmfSource = new Scanner(DTMF_INPUT);
        dtmfSource.useDelimiter(",");
        VxmlBrowser.getContext().setDtmfSource(dtmfSource);
        
        VxmlBrowserWrapper.setBaseUrl("http://localhost:8080/vxml-browser");
        vxmlBrowser.setEntryPointUrl(VxmlBrowserWrapper.getFullUri("/nestedIf.vxml"));

        VxmlBrowserWrapper verifier = new VxmlBrowserWrapper(vxmlBrowser);
        verifier.start();

        AssertJUnit.assertEquals("TTS:Input first if", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Input second if", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Input nested Else If", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Nested else if is true", verifier.nextOuput());
    }
    
    @Test
    public void testNestedElse() throws VxmlException, URISyntaxException, Event, IOException {
        String DTMF_INPUT = "true,false,false";
        dtmfSource = new Scanner(DTMF_INPUT);
        dtmfSource.useDelimiter(",");
        VxmlBrowser.getContext().setDtmfSource(dtmfSource);
        
        VxmlBrowserWrapper.setBaseUrl("http://localhost:8080/vxml-browser");
        vxmlBrowser.setEntryPointUrl(VxmlBrowserWrapper.getFullUri("/nestedIf.vxml"));

        VxmlBrowserWrapper verifier = new VxmlBrowserWrapper(vxmlBrowser);
        verifier.start();

        AssertJUnit.assertEquals("TTS:Input first if", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Input second if", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Input nested Else If", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Nested else is true", verifier.nextOuput());
    }
    
    @Test
    public void testOuterElseIf() throws VxmlException, URISyntaxException, Event, IOException {
        String DTMF_INPUT = "false,false,false";
        dtmfSource = new Scanner(DTMF_INPUT);
        dtmfSource.useDelimiter(",");
        VxmlBrowser.getContext().setDtmfSource(dtmfSource);
        
        VxmlBrowserWrapper.setBaseUrl("http://localhost:8080/vxml-browser");
        vxmlBrowser.setEntryPointUrl(VxmlBrowserWrapper.getFullUri("/nestedIf.vxml"));

        VxmlBrowserWrapper verifier = new VxmlBrowserWrapper(vxmlBrowser);
        verifier.start();

        AssertJUnit.assertEquals("TTS:Input first if", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Input second if", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Input nested Else If", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:outer else if condition is true", verifier.nextOuput());
    }
    
    @Test
    public void testNestedElseIfOuter2() throws VxmlException, URISyntaxException, Event, IOException {
        String DTMF_INPUT = "outerElseShouldBeExecuted,true,false";
        dtmfSource = new Scanner(DTMF_INPUT);
        dtmfSource.useDelimiter(",");
        VxmlBrowser.getContext().setDtmfSource(dtmfSource);
        
        VxmlBrowserWrapper.setBaseUrl("http://localhost:8080/vxml-browser");
        vxmlBrowser.setEntryPointUrl(VxmlBrowserWrapper.getFullUri("/nestedIf.vxml"));

        VxmlBrowserWrapper verifier = new VxmlBrowserWrapper(vxmlBrowser);
        verifier.start();

        AssertJUnit.assertEquals("TTS:Input first if", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Input second if", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Input nested Else If", verifier.nextOuput());
        AssertJUnit.assertEquals("TTS:Outer else condition is true", verifier.nextOuput());
    }
    

}
