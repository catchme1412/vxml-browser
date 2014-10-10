package com.vxml.core.browser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.http.client.ClientProtocolException;

import sun.org.mozilla.javascript.internal.NativeArray;

import com.vxml.store.DocumentStore;

public class ScriptExecutionContext {

    private static final Logger log = Logger.getAnonymousLogger();

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

    public Object executeScript(String script) throws ScriptException {
        return scriptEngine.eval(script);

    }

    public void put(String key, Object val) {
        // val = "true".equals(val) || "'true'".equals(val)? true : val;
        scriptEngine.put(key, val);
    }

    public Object get(String var) {
        return scriptEngine.get(var);
    }

    public static void main(String[] args) throws ScriptException, NoSuchMethodException,
            URISyntaxException, ClientProtocolException, IOException {

        // Create ScriptEngineManager
        ScriptEngineManager engineManager = new ScriptEngineManager();

        // Create ScriptEngine
        ScriptEngine engine = engineManager.getEngineByName("ECMAScript");
        engine.put("k", "'sales'");
        Object r = engine.eval("k");
        Object r2 = engine.eval("'sales'");
        System.out.println(r);
        // Create file and reader instance for reading the script file
        // Pass the script file to the engine
//        URI uri = new URI("http://localhost:8585/ivr/service/wl2/js/audio_functions.js");
//        InputStream string = new DocumentStore().getInputStream(uri);
//        engine.eval(new InputStreamReader(string));
//        engine.put("phoneNumber", "234");
//        Object t = engine.eval("phNumDigitsAudio(phoneNumber,'http://audio.en-US.tellme.com/en-us/sys/')");
//        System.out.println(t);
//        if (t instanceof NativeArray) {
//            NativeArray arr = (NativeArray) t;
//            Object [] a = new Object[(int) arr.getLength()];
//            for (Object o : arr.getIds()) {
//                int index = (Integer) o;
//                a[index] = arr.get(index, null);
//                Object val = a[index];
//                System.out.println("LOOOP:" + o);
//                System.out.println("LOOOP:" + val);
//            }
//
//        }
        // System.out.println("Java Program Output");
        // // Create invocable instance
        // Invocable invocable = (Invocable) engine;

        // Invoke the methods defined in the script file
        // invocable.invokeFunction("parseXmlWithAttrToObject",
        // "/opt/orbitz/code/web-ivr/src/main/webapp/ivr/common/js/parseXmlWithAttrToObject.js");

    }

}
