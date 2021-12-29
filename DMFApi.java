package com.proview.api;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;


import com.proview.Configuration;
import com.proview.util.Constants;
import com.proview.util.Util;

import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class DMFApi extends RequestBase {

	public DMFApi(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}
	
	public String getDMFProviderInfo(String provider_Birthdate, String provider_SSN) throws IOException {
		
		String body = "{\n" + 
				"\"SSN\":\"" + provider_SSN + "\",\n" +
				"\"BIRTH_DT\":\"" + provider_Birthdate + "\"\n" +
				
				"}";

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, null,  body);
		//return getInputStreamAsString();
        return connectionResponse();
	}
	
public String getDMFNoData(String provider_Birthdate, String provider_SSN) throws IOException {
		
		String body = "";

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, null,  body);
		//return getInputStreamAsString();
        return connectionResponse();
	}
public String getDMFMultipleProviderInfo() throws IOException {
	
	String queryFileName = (new Configuration().getDMFMultipleSourceFile());
	String queryCompFilePath = Constants.RESOURCES_DIR+ "DMF-Resources\\" + queryFileName;
	String body = new Util().fileContentToString(queryCompFilePath);
	sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, null,  body);
	
	return connectionResponse();
}

}
