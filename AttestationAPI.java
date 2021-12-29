package com.proview.api;

import java.io.IOException;

public class AttestationAPI extends RequestBase {

	public AttestationAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}	
	public String getAttestationUsers(String startDate, String endDate,boolean isError)throws IOException {
		addParamToUrl("attestationBeginDate", startDate);
		addParamToUrl("attestationEndDate", endDate);
		
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		
		return isError ? connectionResponse():getInputStreamAsString();
	}
	public String getAttestationUsersBadReq(String startDate, String endDate)throws IOException {
		addParamToUrl("attestationBeginDate", startDate);
		addParamToUrl("attestationEndDate", endDate);
		removeSeperatorfromURL("?");
		
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		
		return connectionResponse();
	}
}
