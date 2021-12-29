package com.proview.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.proview.api.RequestBase.ContentType;
import com.proview.api.RequestBase.RequestMethod;

public class PSVBatchServiceAPI extends RequestBase{

	public PSVBatchServiceAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}

	
	public String addPsvBatch(String providerid,  String FORMAT, String ATTACHMENTS, String DISPOSITION,String SECTIONS, String ATTESTATIONDATE
			 ) throws IOException 
	{

		Map<String, String> params = new HashMap<String, String> ();
		String body = "{\n" + 
				"\"PROVIDER_ID\":\"" + providerid +"\",\n" +
				"\"FORMAT\":\"" + FORMAT +"\",\n" + 
				"\"ATTACHMENTS\":\"" + ATTACHMENTS + "\",\n" +
				"\"DISPOSITION\":\"" +  DISPOSITION + "\",\n" +
				"\"SECTIONS\":\"" + SECTIONS + "\",\n" +
				"\"ATTESTATION_DATE\":\"" + ATTESTATIONDATE + "\"\n" +
				"}";
	

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);
		connection.getResponseCode();
		//return getErrorStreamAsString();
		return getInputStreamAsString();

}

}