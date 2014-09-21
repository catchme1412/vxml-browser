package com.vxml.core;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.vxml.parser.event.EventHandler;

public class VxmlContext {

    private ScriptEngineManager manager;
    private ScriptEngine engine;
    private String docBase = "http://localhost:8080/javascript/";

    private Map inputMapping = new HashMap<String, String>();
    
    private EventHandler eventHandler = new EventHandler();

    public VxmlContext() {
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("js");
        try {
            engine.eval("var application={};application.ANI='8773366233'; application.UUID='1ECA9F03DD8E11E28505B0FAEB421300';");
            engine.eval("var C1;var C2;var C3;var C4;var C5;var C6;var C7;var C8;var C9;var C10;");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public Object executeScript(String script) {
        try {
            return engine.eval(script);
        } catch (ScriptException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public boolean isVarDefined(String varName) {
    	Boolean isDefined = (Boolean) executeScript( "typeof " + varName +" === 'undefined'");
    	return !isDefined;
    }

    public Object executeScript(File file) {
        try {
            return engine.eval(new FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDocBase() {
        return docBase;
    }

    public void setDocBase(String docBase) {
        this.docBase = docBase;
    }

    public Map getInputMapping() {
        return inputMapping;
    }

    public void addInputMapping(String input, String replacement) {
        this.inputMapping.put(input, replacement);
    }

	public EventHandler getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}
	
	

    
}
