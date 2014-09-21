package com.vxml.core.browser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptExecutionContext {

	private static final Logger log = Logger.getAnonymousLogger();
	private ScriptEngineManager manager;

	private VxmlScriptEngine scriptEngine;

	// /newCallController.htm?dnis=8886564546&ani=19733689500&uuid=684CB6BA3CCC11E4B810B0FAEB421300&newCallSuccess=true
	public ScriptExecutionContext() throws ScriptException {

		scriptEngine = new VxmlScriptEngine();

	}

	public Object executeScript(InputStream script) throws ScriptException {

		return scriptEngine.eval(new InputStreamReader(script));
	}

	public Object executeScriptNullIfUndefined(String script) {
		try {
			return scriptEngine.eval(script);
		} catch (Exception e) {
			System.err.println(script);
			System.err.println("SCRIPT FAILURE: " + e.getMessage());

		}
		return null;
	}

	public void put(String key, Object val) {
		scriptEngine.put(key, val);
	}

	public Object get(String var) {
		return scriptEngine.get(var);
	}

	public static void main(String[] args) throws ScriptException, FileNotFoundException, NoSuchMethodException {

		// Create ScriptEngineManager
		ScriptEngineManager engineManager = new ScriptEngineManager();

		// Create ScriptEngine
		ScriptEngine engine = engineManager.getEngineByName("ECMAScript");
		engine.eval("var a = {\\\"A\\\":true};");
		// Create file and reader instance for reading the script file
		File file = new File("/opt/orbitz/code/web-ivr/src/main/webapp/ivr/common/js/parseXmlWithAttrToObject.js");
		Reader reader = new FileReader(file);

		// Pass the script file to the engine
		engine.eval(reader);
		System.out.println("Java Program Output");
		// Create invocable instance
		Invocable invocable = (Invocable) engine;

		// Invoke the methods defined in the script file
		// invocable.invokeFunction("parseXmlWithAttrToObject",
		// "/opt/orbitz/code/web-ivr/src/main/webapp/ivr/common/js/parseXmlWithAttrToObject.js");

	}

}
