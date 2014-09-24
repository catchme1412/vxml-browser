package com.vxml.core.browser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.script.ScriptException;

import com.vxml.audio.NativeCommand;
import com.vxml.core.VxmlException;
import com.vxml.parser.event.EventHandler;
import com.vxml.tag.FormTag;
import com.vxml.tag.Tag;

public class VxmlExecutionContext {

    private ScriptExecutionContext scriptExecutionContext;
    private static boolean isSlientMode = true;
    private static String docBaseUrl;
    private EventHandler eventHandler;
    private NativeCommand nativeCommand;
    
    private boolean isSuspended;
    
    //mainly for form when referred from goto
    private Map<String, FormTag> formMap;
	private Scanner dtmfSource; 

    public VxmlExecutionContext() throws ScriptException {
        scriptExecutionContext = new ScriptExecutionContext();
        eventHandler = new EventHandler();
        //default
        dtmfSource = new Scanner(System.in);
        formMap = new HashMap<String, FormTag>();
        nativeCommand = new NativeCommand();
    }

    public Object executeScript(String script) {
        return executeScriptNullIfUndefined(script);
    }
    
    public Object executeScriptNullIfUndefined(String script) {
            return scriptExecutionContext.executeScriptNullIfUndefined(script);
    }
    
    public Object executeScript(InputStream script) {
        try {
            return scriptExecutionContext.executeScript(script);
        } catch (ScriptException e) {
            throw new VxmlException("Script failure:" + script , e);
        }
    }
    
    public void assignScriptVar(String var, Object val) {
        scriptExecutionContext.put(var, val);
    }
    
    public Object getScriptVar(String var) {
        return scriptExecutionContext.get(var);
    }

    public static String getDocBaseUrl() {
        return docBaseUrl;
    }

    public static void setDocBaseUrl(String docBaseUrl) {
        VxmlExecutionContext.docBaseUrl = docBaseUrl;
    }

    public URI getFullUri(String uri) {
        URI u = null;
        try {
            u = new URI(uri);
            if (u.getScheme() == null) {
                u = new URI(docBaseUrl + uri);
            }
        } catch (URISyntaxException e) {
            throw new VxmlException(e);
        }
        return u;

    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void storeTag(String id, Tag tag) {
        formMap.put(id, tag);
    }
    
    public FormTag getTag(String formId) {
        return formMap.get(formId);
    }

	public Scanner getDtmfSource() {
		return dtmfSource;
	}

	public void setDtmfSource(Scanner dtmfSource) {
		this.dtmfSource = dtmfSource;
	}

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    public void resume() {
        isSuspended = false;
        
    }

    public static boolean isSlientMode() {
        return isSlientMode;
    }

    public static void setSlientMode(boolean isSlientMode) {
        VxmlExecutionContext.isSlientMode = isSlientMode;
    }

    public void playAudio(String audioUrl) {
        nativeCommand.play(audioUrl);
    }

    public void playTTS(String text) throws IOException, InterruptedException {
        nativeCommand.speak(text);
    }
}
