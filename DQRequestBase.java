package com.proview.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.IOUtils;
import org.testng.asserts.SoftAssert;

public class DQRequestBase {

	protected HttpURLConnection connection;
	protected String urlStr;
	protected String authorizationKey;
	protected String subscriptionKey;
	protected SoftAssert softAssert;

	public DQRequestBase(String urlStr, String authorizationKey, String subscriptionKey) {
		this.urlStr = urlStr;
		this.authorizationKey = authorizationKey;
		this.subscriptionKey = subscriptionKey;
		softAssert = new SoftAssert();
	}

	public enum RequestMethod {
		GET, POST, PUT;
	}

	public enum ContentType {
		APPLICATION_XML("application/xml"), APPLICATION_JSON("application/json"), APPLICATION_GRAPHQL(
				"application/graphql");

		private String value;

		private ContentType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum ContentLength {
		CONTENT_LENGTH("99");

		private String val;

		private ContentLength(String val) {
			this.val = val;
		}

		public String getVal() {
			return val;

		}
	}

	public HttpURLConnection sendRequest(RequestMethod method, ContentType contentType, ContentLength contentLength,
			Map<String, String> params, String body) {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});

		try {
			URL url = new URL(urlStr);
			System.out.println("URL: " + url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method.name());
			connection.setDoInput(true);
			connection.setReadTimeout(60000);
			System.out.println("authorizationKey : " + authorizationKey);
			connection.addRequestProperty("Authorization", authorizationKey);
			connection.addRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
			if (contentType != null) {
				connection.addRequestProperty("Content-Type", contentType.getValue());
				connection.addRequestProperty("Accept", contentType.getValue());
			}

			if (params != null && params.size() > 0) {
				Set<Entry<String, String>> entries = params.entrySet();
				for (Entry<String, String> entry : entries) {
					connection.addRequestProperty(entry.getKey(), entry.getValue());
				}
			}

			if (body != null) {
				//System.out.println(body);
				connection.setDoOutput(true);
				OutputStream outputStream = connection.getOutputStream();
				OutputStreamWriter writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
				writer.write(body);
				writer.flush();
				writer.close();
			}

			connection.connect();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return connection;

	}
	

	public HttpURLConnection sendRequest(RequestMethod method, ContentType contentType, ContentLength contentLength,
			Map<String, String> params) {
		return sendRequest(method, contentType, contentLength, params, null);
	}

	public HttpURLConnection sendRequest(RequestMethod method, ContentType contentType, Map<String, String> params) {
		return sendRequest(method, contentType, null, params, null);
	}

	public HttpURLConnection sendRequest(RequestMethod method, ContentType contentType, Map<String, String> params,
			String body) {
		return sendRequest(method, contentType, null, params, body);
	}
	
	// Creating a connection for generating the token
	public HttpURLConnection sendRequestToken(RequestMethod method, ContentType contentType, ContentLength contentLength,
			Map<String, String> params, String body) {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});

		try {
			URL url = new URL(urlStr);
			System.out.println("URL: " + url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method.name());
			connection.setDoInput(true);
			connection.setReadTimeout(60000);
			System.out.println("authorizationKey : " + authorizationKey);
			connection.addRequestProperty("Authorization", authorizationKey);
			connection.addRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
			if (contentType != null) {
				connection.addRequestProperty("Content-Type", contentType.getValue());
				connection.addRequestProperty("Accept", contentType.getValue());
			}

			if (params != null && params.size() > 0) {
				Set<Entry<String, String>> entries = params.entrySet();
				for (Entry<String, String> entry : entries) {
					connection.addRequestProperty(entry.getKey(), entry.getValue());
				}
			}

			if (body != null) {
				System.out.println(body);
				connection.setDoOutput(true);
				OutputStream outputStream = connection.getOutputStream();
				OutputStreamWriter writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
				writer.write(body);
				writer.flush();
				writer.close();
			}

			connection.connect();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return connection;

	}
	

	public HttpURLConnection sendRequestToken(RequestMethod method, ContentType contentType, ContentLength contentLength,
			Map<String, String> params) {
		return sendRequestToken(method, contentType, contentLength, params, null);
	}

	public HttpURLConnection sendRequestToken(RequestMethod method, ContentType contentType, Map<String, String> params) {
		return sendRequestToken(method, contentType, null, params, null);
	}

	public HttpURLConnection sendRequestToken(RequestMethod method, ContentType contentType, Map<String, String> params,
			String body) {
		return sendRequestToken(method, contentType, null, params, body);
	}

	public InputStream getErrorStream() {
		return connection.getErrorStream();
	}

	public InputStream getInputStream() {
		try {
			return connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public int getResponseCode() {
		try {
			return connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String getInputStreamAsString() {
		try {
			return IOUtils.toString(connection.getInputStream(), Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String getErrorStreamAsString() {
		try {
			return IOUtils.toString(connection.getErrorStream(), Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String getErrorStreamString() throws IOException {
		InputStream _is = null;
		try {
			if (connection.getResponseCode() >= 400) {
			    _is = connection.getErrorStream();
			   
			} else {
			      //error from server 
			    _is = connection.getErrorStream();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return IOUtils.toString(connection.getErrorStream(), Charset.forName("UTF-8"));
	}
	public void addParamToUrl(String key, String value) {
		String paramSeparator = !urlStr.contains("?") ? "?" : "&";
		urlStr = urlStr + paramSeparator + key + "=" + value;
	}

	public void addParamToUrl(String value) {
		String paramSeparator = !urlStr.contains("?") ? "?" : "";
		urlStr = urlStr + paramSeparator + value;
	}

	public void addParameterToUrl(String key, String value) {
		String paramSeparator = !urlStr.contains("?") ? "?" : "";
		urlStr = urlStr + paramSeparator + key + "=" + value;
	}

	public void removeSeperatorfromURL(String Seperator) {
		urlStr = urlStr.replace(Seperator, "");
	}

	public String connectionResponse() {
		try {
			return connection.getResponseMessage();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
