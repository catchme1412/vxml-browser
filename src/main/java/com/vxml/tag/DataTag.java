package com.vxml.tag;

import java.net.URI;
import java.net.URISyntaxException;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;
import com.vxml.store.DocumentStore;

public class DataTag extends AbstractTag {

	public DataTag(Node node) {
		super(node);
	}

	@Override
	public void execute() {
		String name = getAttribute("name");
		StringBuilder queryParams = new StringBuilder();
		String src = getAttribute("src");
		URI uri = VxmlBrowser.getContext().getFullUri(src);

		queryParams.append(uri.toString());
		queryParams.append("?");
		String namelist = getAttribute("namelist");
		String nameListArray[] = namelist.split(" ");
		for (int i = 0; i < nameListArray.length; i++) {
			queryParams.append(nameListArray[i]);
			queryParams.append("=");
			queryParams.append(VxmlBrowser.getContext().executeScript(nameListArray[i]));
			queryParams.append("&");
		}
		StringBuilder result = null;
		try {
			result = new DocumentStore().getData(new URI(queryParams.toString()));
			VxmlBrowser.getContext().executeScript("var " + name + "='" + result.toString().replaceAll("'", "\\\\'") + "'");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
