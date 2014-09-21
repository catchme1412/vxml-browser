package com.vxml.store;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class DocumentStore {

	private static HttpCaller httpCaller;

	public DocumentStore() {
		if (httpCaller == null) {
			httpCaller = new HttpCaller();
			httpCaller.startSession();
		}
	}

	public Document getDoc(URI uri) {
		// System.out.println("Fetch:" + uri);
		InputStream is = null;
		Document doc = null;
		try {
			is = getInputStream(uri);
			// is = new UrlFetchService().fetchInputStream(uri);
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(false);
			domFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			doc = builder.parse(is);
		} catch (Exception e) {
			System.err.println("FAILED TO FETCH:" + uri);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		doc.setDocumentURI(uri.toString());
		return doc;
	}

	public InputStream getInputStream(URI uri) throws ClientProtocolException, IOException {
		InputStream is;
		String result = httpCaller.doGet(uri.toString());
		is = new ByteArrayInputStream(result.getBytes());
		return is;
	}

	public StringBuilder getData(URI uri) {
		StringBuilder builder = new StringBuilder();
		try {
			InputStream in = getInputStream(uri);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder;
	}

	public static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
