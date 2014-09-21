package com.vxml.parser.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vxml.browser.event.Event;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.tag.AbstractTag;
import com.vxml.tag.Tag;

public class EventHandler {

	private Map<String, Tag> eventMap = new HashMap<String, Tag>();

	private static Map<String, List<OutputListener>> outputListener = new HashMap<String, List<OutputListener>>();

	public void addEventHandle(String eventName, Tag node) {
		eventMap.put(eventName, node);
	}

	public void handleEvent(String eventType) throws Event {
		eventMap.get(eventType).execute();
		clearEvent();
	}

	public void fireEvent(String eventType) {
		VxmlBrowser.getContext().executeScript("event='" + eventType + "'");
	}

	public Object getLastEvent() {
		return VxmlBrowser.getContext().executeScript("event");
	}

	public void clearEvent() {
		VxmlBrowser.getContext().executeScript("event=null;");
	}

	// public boolean isEventFired(String eventType) {
	// return VxmlPlayer.context.isVarDefined("event");
	// }
	
	public void register(String eventType, OutputListener lister) {
		List<OutputListener> list = outputListener.get(eventType);
		if (list != null) {
			list.add(lister);
		} else {
			list = new ArrayList();
		}
	}
	
	public void invokeListeners(Tag tag) {
		List<OutputListener> l = outputListener.get(((AbstractTag)tag).getNode().getNodeName());
		if(l != null) {
			for (OutputListener o : l) {
				o.invoke(tag);
			}
		}
	}
}
