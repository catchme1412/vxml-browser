package com.vxml.tag;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.vxml.browser.event.Event;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.dtmf.DtmfInput;
import com.vxml.utils.XmlUtils;

public class FilledTag extends AbstractTag {

	private NoinputTag noinputTag;
	private NomatchTag nomatchTag;
	
	public FilledTag(Node node) {
		super(node);
	}

	@Override
	public void startTag() {
		NodeList nodes = XmlUtils.getNodesByXPath(getNode(), ".//noinput");
		if(nodes != null && nodes.getLength() > 0) {
			Node t = nodes.item(0);
			noinputTag = (NoinputTag) TagFactory.get(t);
		} else {
			nodes = XmlUtils.getNodesByXPath(getNode(), ".//../noinput");
			if(nodes != null && nodes.getLength() > 0) {
				Node t = nodes.item(0);
				noinputTag = (NoinputTag) TagFactory.get(t);
			}
		}
		
		nodes = XmlUtils.getNodesByXPath(getNode(), ".//nomatch");
		if(nodes != null && nodes.getLength() > 0) {
			Node t = nodes.item(0);
			nomatchTag = (NomatchTag) TagFactory.get(t);
		} else {
			nodes = XmlUtils.getNodesByXPath(getNode(), ".//../nomatch");
			if(nodes != null && nodes.getLength() > 0) {
				Node t = nodes.item(0);
				nomatchTag = (NomatchTag) TagFactory.get(t);
			}
		}
	}
	
	@Override
	public void execute() throws Event {
		try {
		    
			Object value = new DtmfInput(VxmlBrowser.getContext().getDtmfSource()).read();
			if (value != null && !"-".equals(value)) {
				String fieldName = getFieldName();
				//TODO check whether this can be avoided
				if (VxmlBrowser.getContext().getScriptVar(fieldName) != null) {
				    Object t = VxmlBrowser.getContext().getScriptVar(fieldName);
				    if (!(t instanceof sun.org.mozilla.javascript.internal.Undefined)) {
				        value = t;
				    }
				}
				if (Boolean.parseBoolean(value.toString())) {
					value = true;
				}
				VxmlBrowser.getContext().assignScriptVar(fieldName, value);

			} else {
				// no input
				VxmlBrowser.getContext().getEventHandler().fireEvent("noinput");
				if (noinputTag != null) {
				    VxmlBrowser.getContext().getEventHandler().clearEvent();
					executeChildTree(noinputTag.getNode());
				} else {
				    VxmlBrowser.getContext().getEventHandler().clearEvent();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getFieldName() {
		return getNode().getParentNode().getAttributes().getNamedItem("name").getNodeValue();
	}

}
