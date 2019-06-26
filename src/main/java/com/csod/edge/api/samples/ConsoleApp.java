package com.csod.edge.api.samples;

import java.net.MalformedURLException;
import java.net.URL;


public class ConsoleApp {
	private static final int PRINT_LENGTH = 500;

	private static final String API_BASE_ADDRESS = "http://DOMAIN.csod.com:81";

	private static final String CLIENT_ID = "";
	
	private static final String CLIENT_SECRET = "";
	
	private static final String API_PATH = "/services/api/Recruiting/JobRequisitionDetails";
	
	private static EdgeApiClient client;

	public static void main(String[] args) throws Exception {
		init();
		System.out.println("Edge REST API sample");		
		executeService();
		
	}

	private static void init() throws MalformedURLException {
		client = new EdgeApiClient(new URL(API_BASE_ADDRESS), CLIENT_ID, CLIENT_SECRET);
	}

	private static void executeService() throws Exception {
		System.out.println("Calling... " + API_PATH);
		String stringContent = client.getString(API_PATH);
		if(stringContent.length()>PRINT_LENGTH)
		{
			System.out.println(String.format("Response length is %s. First %s characters: %s", stringContent.length(),
					PRINT_LENGTH, stringContent.substring(0, PRINT_LENGTH - 1)));
		}
		else{
			System.out.println(String.format("Response length is %s. Response : %s", stringContent.length(), stringContent));
		}
	}

	

	
}
