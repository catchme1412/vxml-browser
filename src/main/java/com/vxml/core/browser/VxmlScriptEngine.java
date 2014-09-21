package com.vxml.core.browser;

import java.io.InputStreamReader;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class VxmlScriptEngine {
	private ScriptEngine scriptEngine;
	private List<String> script;
	
	public static final String SCRIPT_EXECUTION_NAME_SPACE = "_vxmlExecutionContext";
	
	public VxmlScriptEngine() throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
		scriptEngine = engine;
		scriptEngine.eval("var application={};");
		scriptEngine.eval("application.lastresult$={}");
		scriptEngine.eval("application.ANI='19733689500';");
		scriptEngine.eval("application.UUID='684CB6BA3CCC11E4B810B0FAEB421300';");
		scriptEngine.eval("application.lastresult$.inputmode='dtmf'");
		scriptEngine.eval("var session={};session.telephone={};session.telephone.dnis=8886564546;");
		scriptEngine.eval("var " + SCRIPT_EXECUTION_NAME_SPACE + "={};");
		scriptEngine.eval(SCRIPT_EXECUTION_NAME_SPACE + ".dtmfInput=null;");
		scriptEngine.eval("var event;");
	}

	public Object eval(String script) throws ScriptException {
		if (!script.endsWith(";")) {
			script += ";";
		}
		return scriptEngine.eval(script);
	}

	public Object eval(InputStreamReader inputStreamReader) throws ScriptException {
		return scriptEngine.eval(inputStreamReader);
	}

	public void put(String key, Object val) {
		scriptEngine.put(key, val);
	}

	public Object get(String var) {
		return scriptEngine.get(var);
	}


}
