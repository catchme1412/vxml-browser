package com.vxml.core.browser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptException;

import com.vxml.core.VxmlException;
import com.vxml.parser.event.EventHandler;
import com.vxml.tag.Tag;

public class VxmlExecutionContext {

    private ScriptExecutionContext scriptExecutionContext;
    private static boolean isTtsAllowed = true;
    private static String docBaseUrl;
    private EventHandler eventHandler;
    
    //mainly for form when referred from goto
    private Map<String, Tag> tagMap;
	private BufferedReader dtmfSource; 

    public VxmlExecutionContext() throws ScriptException {
        scriptExecutionContext = new ScriptExecutionContext();
        eventHandler = new EventHandler();
        //default
        dtmfSource = new BufferedReader(new InputStreamReader(System.in));
        tagMap = new HashMap<String, Tag>();
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
        tagMap.put(id, tag);
    }
    
    public Tag getTag(String id) {
        return tagMap.get(id);
    }

    public static boolean isTtsAllowed() {
        return isTtsAllowed;
    }

    public static void setTtsAllowed(boolean tts) {
        VxmlExecutionContext.isTtsAllowed = tts;
    }

	public BufferedReader getDtmfSource() {
		return dtmfSource;
	}

	public void setDtmfSource(BufferedReader dtmfSource) {
		this.dtmfSource = dtmfSource;
	}
}
