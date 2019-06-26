package com.csod.edge.api.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class EdgeApiClient {
	
	private final String clientId;
	private final String clientSecret;
	private static final String SCOPE = "all";	
	private static final String GRANT_TYPE = "client_credentials";
	private static final String SCOPE_KEY = "scope";	
	private static final String GRANT_TYPE_KEY = "grantType";
	private static final String CLIENT_ID_KEY = "clientId";	
	private static final String CLIENT_SECRET_KEY = "clientSecret";	
	private final URL baseUrl;

	private boolean initialized = false;	
	private String accessToken;	
	

	public EdgeApiClient(URL baseUrl, String clientId, String clientSecret) {
		this.baseUrl = baseUrl;		
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}


	public String getString(String relativeAddress) throws Exception {
		initialize();

		URL url = new URL(this.baseUrl, relativeAddress);
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

		// Set timeout to infinite because Edge API service has its own timeout.
		httpConnection.setReadTimeout(0);

		httpConnection.setUseCaches(false);
		httpConnection.setRequestMethod("GET");
		httpConnection.setRequestProperty(HttpHeaders.ACCEPT, "application/json");
		httpConnection.setDoOutput(false);
		httpConnection.setDoInput(true);

		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header(HttpHeaders.AUTHORIZATION_KEY, getAuthorizationHeaderValue()));


		addHeadersToRequest(headers, httpConnection);

		if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return readConnectionInput(httpConnection);
		}

		String exception=readErrorStream(httpConnection); 
		
		throw new Exception(String.format("Response by URL '%s' is '%s' '%s' Exception Details is '%s'.", url, httpConnection.getResponseCode(),
				httpConnection.getResponseMessage(),exception));
	}

	private void initialize() throws Exception {
		if (this.initialized) {
			return;
		}

		
		String relativeAddress = "/services/api/oauth2/token";
		URL url = new URL(this.baseUrl, relativeAddress);
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

		httpConnection.setUseCaches(false);
		httpConnection.setRequestMethod("POST");		
		httpConnection.setRequestProperty(HttpHeaders.ACCEPT, "application/json");		
		httpConnection.setDoOutput(true);
		httpConnection.setDoInput(true);
		
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("cache-control", "no-cache"));		

		List<RequestParameter> requestParameters = new ArrayList<RequestParameter>();
		requestParameters.add(new RequestParameter(CLIENT_ID_KEY, this.clientId));	
		requestParameters.add(new RequestParameter(CLIENT_SECRET_KEY, this.clientSecret));
		requestParameters.add(new RequestParameter(GRANT_TYPE_KEY, GRANT_TYPE));
		requestParameters.add(new RequestParameter(SCOPE_KEY, SCOPE));
		
				
		addHeadersToRequest(headers, httpConnection);
		
		String requestBody=buildRequestBody(requestParameters);

		OutputStream outputStream = httpConnection.getOutputStream();
		outputStream.write(requestBody.getBytes());
		outputStream.flush();
		outputStream.close();
		
		if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			String content = readConnectionInput(httpConnection);
			JsonObject contentObject = new JsonParser().parse(content).getAsJsonObject();
			this.accessToken=contentObject.get("access_token").getAsString();			
			this.initialized = true;
			return;
		}

		String exception=readErrorStream(httpConnection); 
		
		throw new Exception(String.format("Initialization failed with '%s' '%s' Exception Details is '%s'.", httpConnection.getResponseCode(),
				httpConnection.getResponseMessage(),exception));
	}

	private static String readConnectionInput(HttpURLConnection httpConnection) throws IOException {
		try (InputStreamReader inputStream = new InputStreamReader(httpConnection.getInputStream())) {
			BufferedReader reader = new BufferedReader(inputStream);

			String responseString;
			StringWriter writer = new StringWriter();
			while ((responseString = reader.readLine()) != null) {
				writer.write(responseString);
			}

			return writer.toString();
		}
	}

	private static String readErrorStream(HttpURLConnection httpConnection) throws IOException {
		try (InputStreamReader inputStream = new InputStreamReader(httpConnection.getErrorStream())) {
			BufferedReader reader = new BufferedReader(inputStream);

			String responseString;
			StringWriter writer = new StringWriter();
			while ((responseString = reader.readLine()) != null) {
				writer.write(responseString);
			}

			return writer.toString();
		}
	}

	private  String getAuthorizationHeaderValue() {
		
		return "Bearer " + this.accessToken;
	}



	private static void addHeadersToRequest(List<Header> headers, HttpURLConnection httpConnection) {
		for (Header header : headers) {
			httpConnection.setRequestProperty(header.name, header.value);
		}
	}

	private static String buildRequestBody(List<RequestParameter> requestParameters) {
		StringBuilder reqbody =new StringBuilder();
		Integer max=0;
		reqbody.append("{");
		for (RequestParameter requestParameter : requestParameters) {
			reqbody.append('"');
			reqbody.append(requestParameter.name);
			reqbody.append('"');
			reqbody.append(':');
			reqbody.append('"');
			reqbody.append(requestParameter.value);
			reqbody.append('"');
			if(max < requestParameters.size()-1)
			{	
				reqbody.append(",");			
			}			
			max++;
		}
		reqbody.append("}");		
		return reqbody.toString();
	}

	
}

class Header {
	public String name;
	public String value;

	public Header(String name, String value) {
		this.name = name;
		this.value = value;
	}
}

class RequestParameter {
	public String name;
	public String value;

	public RequestParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}
}