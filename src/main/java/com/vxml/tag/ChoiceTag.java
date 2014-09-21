package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.browser.event.Event;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;
import com.vxml.dtmf.DtmfInput;
import com.vxml.parser.VxmlDoc;

public class ChoiceTag extends AbstractTag {

	public ChoiceTag(Node node) {
		super(node);
	}

	@Override
	public void execute() throws Event {
		String dtmf = getAttribute("dtmf");
		String dtmfInput = (String) VxmlBrowser.getContext().getScriptVar(VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + ".dtmfInput");
		if (dtmfInput == null) {
		    dtmfInput = new DtmfInput(VxmlBrowser.getContext().getDtmfSource()).read();
		    if (dtmfInput != null) {
		        VxmlBrowser.getContext().assignScriptVar(VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + ".dtmfInput" , dtmfInput);
		    }
		}
		if (dtmf.equals(dtmfInput)) {
		    String expr = getAttribute("expr");
		    Object target = VxmlBrowser.getContext().executeScript(expr);
		    new VxmlDoc(target.toString()).play();
		}
	}
}
