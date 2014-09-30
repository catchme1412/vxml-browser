package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;

public class ElseifTag extends AbstractTag {

	public ElseifTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
		Boolean isIfCondition = isIfConditionTrue();
		if (isIfCondition ==null || (isIfCondition != null && !isIfCondition)) {
			String cond = getAttribute("cond");
			Boolean elseIfCondition = (Boolean) VxmlBrowser.getContext().executeScript(cond);

			if (elseIfCondition != null && elseIfCondition) {
				setSkipExecute(false);
				markElseIfCondition(true);
			} else {
				markElseIfCondition(false);
				setSkipExecute(true);
			}
		} else {
			setSkipExecute(true);
		}
	}


	@Override
	public void execute() {
	}

	private void markElseIfCondition(boolean isTrue) {
		VxmlBrowser.getContext().assignScriptVar(
				VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + ".elseIfCondition_" + ifConditionLevel, isTrue);
	}

	private Boolean isIfConditionTrue() {
		return (Boolean) VxmlBrowser.getContext().getScriptVar(
				VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + ".ifConditionLevel_" + ifConditionLevel);
	}

	@Override
	public void endTag() {
	}
}
