package com.proview.api;

import java.io.IOException;

import com.proview.api.RequestBase.ContentType;
import com.proview.api.RequestBase.RequestMethod;

public class RosterAPIStatus extends RequestBase {


public RosterAPIStatus(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}

public String getRosterStatusInfo(String product,String caqhId, String orgId,  boolean isError) throws IOException {
	addParamToUrl("Product", product);
	addParamToUrl("Caqh_Provider_Id", caqhId);
	addParamToUrl("Organization_Id", orgId);
	

	sendRequest(RequestMethod.GET, ContentType.APPLICATION_XML, null);
	//System.out.println(connection.getResponseCode());
	connection.getResponseCode();

	return isError ? getErrorStreamAsString() : getInputStreamAsString();
}
}
