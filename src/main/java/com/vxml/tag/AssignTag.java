package com.vxml.tag;

import org.w3c.dom.Node;

import sun.org.mozilla.javascript.internal.Undefined;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;

public class AssignTag extends AbstractTag {

	private String name;

	public AssignTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
		name = getAttribute("name");
		VxmlBrowser.getContext().assignScriptVar(name, null);
	}

	private String getSubdialogNameIfAny() {
		return (String) VxmlBrowser.getContext().getScriptVar(
				VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + SubdialogTag.SUBDIALOG_NAME);
	}

	@Override
	public void execute() {
		name = getAttribute("name");
		String expr = getAttribute("expr");
		String varName = name;
		
		// // expr = cleanup(expr);
		// if (expr != null) {
		//
		// // TODO clean up the logic
		// Object exprResult = expr;
		 
		// if (!(val instanceof Undefined || val == null)) {
		// exprResult = val;
		// } else if (val == null) {
		 Object val = VxmlBrowser.getContext().executeScript(expr);
		// if (val != null) {
		// exprResult = val;
		// }
		// }
		//
		VxmlBrowser.getContext().assignScriptVar(name, val);
		// }
	}

	private String cleanup(String expr) {
		if (expr.startsWith("'") && expr.endsWith("'")) {
			return expr.substring(1, expr.length() - 1);
		}
		return expr;
	}
}
