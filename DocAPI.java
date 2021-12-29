package com.proview.api;

import java.io.IOException;

public class DocAPI extends RequestBase {

	public DocAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}
	
	public String getDocumentInfo(String orgId,String caqhId, String docType, boolean isError) throws IOException {
		addParamToUrl("organizationID", orgId);
		addParamToUrl("caqhProviderID", caqhId);
		addParamToUrl("docType", docType);
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_XML, null);
		//System.out.println(connection.getResponseCode());
		connection.getResponseCode();
		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}

	public String getApprovedDocumentInfo(String caqhId,String orgId, boolean isError) throws IOException {
		addParamToUrl("caqhProviderID", caqhId);
		addParamToUrl("organizationID", orgId);
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_XML, null);
		connection.getResponseCode();
		return isError ? getErrorStreamAsString() : getInputStreamAsString();
		
	}
	
	public String getApprovedDocForDownload(String caqhId,String orgId,String docUrl, boolean isError) throws IOException {
		addParamToUrl("caqhProviderID", caqhId);
		addParamToUrl("organizationID", orgId);
		addParamToUrl("docURL", docUrl);
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_XML, null);
		connection.getResponseCode();
		return isError ? getErrorStreamAsString() : getInputStreamAsString();
		
	}
	
	public String getAsyncReqMessgaeId(String caqhId,String orgId,String docType, boolean isError) throws IOException {
		addParameterToUrl("caqhProviderID", caqhId);
		addParamToUrl("organizationID", orgId);
		addParamToUrl("docType", docType);
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, null, "");
		connection.getResponseCode();
		return isError ? getErrorStreamAsString() : getInputStreamAsString();
		
	}
}

