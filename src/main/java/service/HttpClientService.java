package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class HttpClientService {
	private HttpClient httpclient = HttpClients.createDefault();

	public HttpClient getHttpclient() {
		return httpclient;
	}

	public void setHttpclient(HttpClient httpclient) {
		this.httpclient = httpclient;
	}
	
	public HttpGet getHttpGet(String uri) {
		return new HttpGet(uri);
	}
	
	public HttpEntity getContent(HttpClient httpClient, HttpGet httpGet) {
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return response.getEntity();
			}
		} catch (ClientProtocolException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}
		return null;
	}
}
