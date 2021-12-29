package com.proview.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.proview.api.RequestBase.ContentType;
import com.proview.api.RequestBase.RequestMethod;

public class cvoserviceapi extends RequestBase{

	public cvoserviceapi(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}
	public String addCvoBatch(String caqhid,  String format, String includeDocuments, String cvopo,boolean isError
			 ) throws IOException 
	{

		Map<String, String> params = new HashMap<String, String> ();
		String body = "{" + 
				"\"caqhid\":\"" + caqhid +"\"," +
				"\"format\":\"" + format +"\"," + 
				"\"includeDocuments\":\"" + includeDocuments + "\"," +
				"\"cvopo\":\"" + cvopo + "\"" +
				"}";
	

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);
		connection.getResponseCode();
		//return getErrorStreamAsString();
		//return getInputStreamAsString();
		return isError ? getErrorStreamAsString() : getInputStreamAsString();

}

}