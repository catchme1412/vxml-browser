package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;

public class IfTag extends AbstractTag {

	private static final String IF_CONDITION_LEVEL_PREFIX = ".ifConditionLevel_";
	private Boolean isIfConditionTrue;
	private boolean isSkipBackup;

	public IfTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
		isSkipBackup = isSkipExecute();
		ifConditionLevel++;
	}

	@Override
	public void execute() {
		String cond = getAttribute("cond");
		isIfConditionTrue = (Boolean) VxmlBrowser.getContext().executeScript(cond);
		isIfConditionTrue = isIfConditionTrue != null && isIfConditionTrue;
		markIfCondition(isIfConditionTrue);
		if (isIfConditionTrue) {
			setSkipExecute(false);
		} else {
			setSkipExecute(true);
		}
	}

	private void markIfCondition(boolean isTrue) {
		VxmlBrowser.getContext().assignScriptVar(
				VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + IF_CONDITION_LEVEL_PREFIX + ifConditionLevel, isTrue);
	}

	@Override
	public void endTag() {
		// markIfCondition(false);
		// decrement only after setting calling markIfCondition method
		ifConditionLevel--;
		setSkipExecute(isSkipBackup);
	}

}
