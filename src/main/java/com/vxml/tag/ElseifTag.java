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

			boolean isAllParentIfAreTrue = checkParentIfs();
			
			if (elseIfCondition != null && elseIfCondition && isAllParentIfAreTrue) {
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


	private boolean checkParentIfs() {
        for (int i = 0; i < ifConditionLevel - 1 ; i++) {
            Boolean t = (Boolean) VxmlBrowser.getContext().getScriptVar(
                    VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + IfTag.IF_CONDITION_LEVEL_PREFIX + i);
            if (t != null && t == false) {
                return false;
            }
        }
        return true;
    }

    @Override
	public void execute() {
	}

	private void markElseIfCondition(boolean isTrue) {
		VxmlBrowser.getContext().assignScriptVar(
				VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + IfTag.IF_CONDITION_LEVEL_PREFIX + ifConditionLevel, isTrue);
	}

	private Boolean isIfConditionTrue() {
		return (Boolean) VxmlBrowser.getContext().getScriptVar(
				VxmlScriptEngine.SCRIPT_EXECUTION_NAME_SPACE + IfTag.IF_CONDITION_LEVEL_PREFIX + ifConditionLevel);
	}

	@Override
	public void endTag() {
	}
}
