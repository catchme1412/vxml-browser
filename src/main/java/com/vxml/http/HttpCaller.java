package com.vxml.http;

import java.io.Closeable;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * An implementation of HTTP_Caller using the Apache Commons of HttpClient
 * 
 */
public class HttpCaller implements Closeable {

	private CloseableHttpClient httpclient;

	public void startSession() {
		httpclient = HttpClients.createDefault();
	}

	public String doGet(String url) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		// Create a custom response handler
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			public String handleResponse(final HttpResponse response)
					throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException(
							"Unexpected response status: " + status);
				}
			}

		};
		String responseBody = httpclient.execute(httpGet, responseHandler);
//		System.out.println("----------------------------------------");
//		System.out.println(responseBody);
		return responseBody;
	}

	@Override
	public void close() throws IOException {
		httpclient.close();
	}

}
