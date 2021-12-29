package com.proview.api;

import java.io.IOException;

public class ProviewAPI extends RequestBase {

	public ProviewAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}
	
	public String getProviderInfo(String caqhId, boolean isError) throws IOException {
		addParameterToUrl("CaqhId", caqhId);
			
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		//System.out.println(connection.getResponseCode());
		connection.getResponseCode();
		
		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}
	
	public String getProviderInfoWithAttestationDates(String attestFromDate, String attestToDate, boolean isError) throws IOException {
		addParamToUrl("request.attestationBeginDate", attestFromDate);
		addParamToUrl("request.attestationEndDate", attestToDate);
			
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		//System.out.println(connection.getResponseCode());
		connection.getResponseCode();
		
		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}
	
	public String getProviderInfoWithoutAttestationDates(boolean isError) throws IOException {
				
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		//System.out.println(connection.getResponseCode());
		connection.getResponseCode();
		
		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}
}
