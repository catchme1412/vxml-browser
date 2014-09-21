package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class ElseIfTag extends AbstractTag {

	public ElseIfTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
	    String cond = getAttribute("cond"); 
        Boolean elseIfCondition = (Boolean) VxmlBrowser.getContext().executeScript(cond);
        boolean isIfCondition = (Boolean) VxmlBrowser.getContext().executeScript("_vxmlExecutionContext.ifCondition");
       
        if(elseIfCondition && !isIfCondition) {
            setSkipExecute(false);
            //Just to skip else tag
            VxmlBrowser.getContext().executeScript("_vxmlExecutionContext.ifCondition=true");
        } else  if(!elseIfCondition && !isIfCondition){
            setSkipExecute(true);
        }
	}
	@Override
	public void execute() {
	    
	}

}
