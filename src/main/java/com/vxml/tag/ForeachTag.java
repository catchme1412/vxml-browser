package com.vxml.tag;

import java.util.List;

import org.w3c.dom.Node;

import sun.org.mozilla.javascript.internal.NativeArray;

import com.vxml.browser.event.Event;
import com.vxml.core.browser.VxmlBrowser;

//<foreach item="flavor" array="arrayFlavors">
// <prompt>
//  <value expr="flavor" />
//  <break />
// </prompt>
//</foreach>
public class ForeachTag extends AbstractTag {

	private boolean isSkipTagBackup;
    private String item;

	public ForeachTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
		isSkipTagBackup = isSkipExecute();
		item = getAttribute("item");
		VxmlBrowser.getContext().executeScript("var " + item);
	}

	@Override
	public void execute() throws Event {

		setSkipExecute(false);
		String arrayVar = getAttribute("array");
		
		Object array = VxmlBrowser.getContext().executeScript(arrayVar);
		if (array instanceof List) {
			for (Object o : (List) array) {
				VxmlBrowser.getContext().assignScriptVar(item, o);
				executeChildTree(getNode());
			}
		}
		if (array instanceof NativeArray) {
		    NativeArray arr = (NativeArray) array;
		    Object [] a = new Object[(int) arr.getLength()];
		    for (Object o : arr.getIds()) {
		        int index = (Integer) o;
		        a[index] = arr.get(index, null);
		        Object val = a[index];
		        
		        VxmlBrowser.getContext().assignScriptVar(item, val);
                System.out.println("LOOOP:" + o);
                executeChildTree(getNode());
		    }
		    
		}
		setSkipExecute(true);
	}

	@Override
	public void endTag() {
		setSkipExecute(isSkipTagBackup);
	}

}
