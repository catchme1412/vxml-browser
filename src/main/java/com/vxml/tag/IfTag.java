package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlScriptEngine;

public class IfTag extends AbstractTag {

	public static final String IF_CONDITION_LEVEL_PREFIX = ".ifConditionLevel_";
	private Boolean isIfConditionTrue;
	private boolean isSkipBackup;
	private String cond;

	public IfTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
		isSkipBackup = isSkipExecute();
		ifConditionLevel++;
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + ifConditionLevel + " :::" + this + "::" + getNode().getBaseURI());
		ifConditionLevelStack.add(ifConditionLevel);

		cond = getAttribute("cond");
	}

	@Override
	public void execute() {
	    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + ifConditionLevel + " :::" + this);
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
//	    System.out.print("IF"+ this);
		// markIfCondition(false);
		// decrement only after setting calling markIfCondition method
	    System.out.println("<<<<<<<<<<<<" + ifConditionLevel + " :::" + this);
		ifConditionLevel--;
		ifConditionLevelStack.pop();
		setSkipExecute(isSkipBackup);
	}

}
