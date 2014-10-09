package com.vxml.tag;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.w3c.dom.Node;

import sun.org.mozilla.javascript.internal.Undefined;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;

public class ValueTag extends AbstractTag {

    public ValueTag(Node node) {
        super(node);
    }

    @Override
    public void execute() {
        if (getNode().getParentNode().getNodeName().equals("prompt")) {
            String expr = getAttribute("expr");
            String subDialog = (String)VxmlBrowser.getContext().getScriptVar(VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + SubdialogTag.SUBDIALOG_NAME);
            if (subDialog != null) {
                expr = subDialog + "." + expr;
            }
            Object value = VxmlBrowser.getContext().executeScript(expr);
            if (value == null || value instanceof Undefined) {
                value = VxmlBrowser.getContext().getScriptVar(expr);
            }
            try {
                VxmlBrowser.getContext().playTTS(value.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws ScriptException {
        // VxmlBrowser.getContext().executeScript("var POSCODE='ORB';");
        // // VxmlBrowser.getContext().assignScriptVar("POSCODE", "'ORB'");
        // Object value =
        // VxmlBrowser.getContext().executeScript("POSCODE == 'ORB';");
        // System.out.println(value);

        // Create a ScriptEngineManager that discovers all script engine
        // factories (and their associated script engines) that are visible to
        // the current thread's classloader.

        ScriptEngineManager manager = new ScriptEngineManager();

        // Obtain a ScriptEngine that supports the JavaScript short name.

        ScriptEngine engine = manager.getEngineByName("JavaScript");

        // Initialize the color and shape script variables.

        engine.put("color", "red");
        engine.put("shape", "rectangle");
        Object t = engine.eval("color == 'red'");
        System.out.println("FFFFFFFFF" + t);
        // Evaluate a script that outputs the values of these variables.

        engine.eval("println (color); println (shape);");

        // Save the current bindings object.

        Bindings oldBindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);

        // Replace the bindings with a new bindings that overrides color and
        // shape.

        Bindings newBindings = engine.createBindings();
        newBindings.put("color", "blue");
        engine.setBindings(newBindings, ScriptContext.ENGINE_SCOPE);
        engine.put("shape", "triangle");

        // Evaluate the script.

        engine.eval("println (color); println (shape);");

        // Restore the original bindings.

        engine.setBindings(oldBindings, ScriptContext.ENGINE_SCOPE);

        // Evaluate the script.

        engine.eval("println (color); println (shape);");
    }

}
