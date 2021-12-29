package com.proview.api;

import java.io.IOException;

import com.proview.api.RequestBase.ContentType;
import com.proview.api.RequestBase.RequestMethod;

public class PSVRealTimeAPI extends RequestBase {

	public PSVRealTimeAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}
	
	public String getPSVOutput(String providerId, String disposition, String sections, String attestationDate) throws IOException {
		
		addParamToUrl("PROVIDER_ID", providerId);
		addParamToUrl("DISPOSITION", disposition);
		addParamToUrl("SECTIONS", sections);
		addParamToUrl("ATTESTATION_DATE", attestationDate);
		
		//sendRequest(RequestMethod.GET, null, null); // create an overloaded method
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_XML, null);
		connection.getResponseCode();
		return getInputStreamAsString();
		
	}

}
