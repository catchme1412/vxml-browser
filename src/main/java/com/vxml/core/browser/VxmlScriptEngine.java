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

        // defaults
        scriptEngine.eval("application={};");
        scriptEngine.eval("application.lastresult$={}");
        scriptEngine.eval("application.ANI='19733689500';");
        scriptEngine.eval("application.UUID='684CB6BA3CCC11E4B810B0FAEB421300';");
        scriptEngine.eval("application.lastresult$.inputmode='dtmf'");
        scriptEngine.eval("var session={};session.telephone={};session.telephone.dnis=8886564546;");
        scriptEngine.eval("var " + SCRIPT_EXECUTION_NAME_SPACE + "={};");
        scriptEngine.eval(SCRIPT_EXECUTION_NAME_SPACE + ".dtmfInput=null;");
        scriptEngine.eval("var event;");
        scriptEngine
                .eval("function simpleJsonParse(j){var ar = j.toString().substring(1,j.length-1);var arr=ar.split(':');var m={};m[arr[0]]=m[arr[1]];return m;}");
    }

    public Object eval(String script) throws ScriptException {
        String scriptModified = script;
        if (!script.endsWith(";")) {
            scriptModified += ";";
        }
        try {
            Object eval = scriptEngine.eval(scriptModified);
            //TODO dirty logic
            if (eval.equals("'" + script + "'")) {
                eval = script;
            }
            return eval;
        } catch (Exception e) {
            if (script.startsWith("{") && script.endsWith("}")) {
                return scriptEngine.eval("simpleJsonParse(" + script + ");");
            }
        }
        return get(script);
    }

    public Object eval(InputStreamReader inputStreamReader) throws ScriptException {
        return scriptEngine.eval(inputStreamReader);
    }

    public void put(String key, Object val) {
        if (val instanceof String && ((String) val).startsWith("'") && ((String) val).endsWith("'")) {
            scriptEngine.put(key, ((String) val).substring(1, val.toString().length() - 1));
        }
        scriptEngine.put(key, val);
    }

    public Object get(String var) {
        return scriptEngine.get(var);
    }

    public static void main(String[] args) throws ScriptException {
        VxmlScriptEngine e = new VxmlScriptEngine();
        e.put("a", "true");
        String originalCondition = "a=='true'";
        System.out.println(e.eval(originalCondition));
        String cond = originalCondition.replaceAll("'true'", "true");
        System.out.println(cond);
        System.out.println(e.eval(cond));
    }

}
