package com.proview.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.proview.api.RequestBase.ContentType;
import com.proview.api.RequestBase.RequestMethod;

public class MatchReportAPI extends RequestBase {
	
	public MatchReportAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}
	
	public String getMatchReport(String SubmissionbatchId,  boolean isError)throws IOException {
		urlStr = urlStr + "/" + SubmissionbatchId;
		System.out.println("API :" +urlStr);
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON,  null);
		
		return connectionResponse();
		//return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}
	
	public String getMatchReportPost(String SubmissionbatchId, String eof) {
		
		Map<String, String> params = new HashMap<String, String> ();
		
		String body = "[{\n" + 
			"\"batchid\": \"" + SubmissionbatchId + "\",\n" +
			"\"eof\": \"" + eof + "\",\n" + 
			"}]";
						
		addParamToUrl("batchid", SubmissionbatchId);
		addParamToUrl("eof", eof);
		System.out.println("API :" +urlStr);
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);
		return connectionResponse();
		/*
		return getInputStreamAsString();
		return isError ? getErrorStreamAsString() : getInputStreamAsString();
		*/
	}
	
}
