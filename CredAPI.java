package com.proview.api;

import java.io.IOException;

public class CredAPI extends RequestBase {

	public CredAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}

	public String getProviderInfo(String caqhId, String orgId, String attestationDate, String credentialSection, boolean isError) throws IOException {
		addParamToUrl("caqhProviderId", caqhId);
		addParamToUrl("organizationId", orgId);
		addParamToUrl("attestationDate", attestationDate);
		addParamToUrl("credentialSection", credentialSection);

		sendRequest(RequestMethod.GET, ContentType.APPLICATION_XML, null);
		//System.out.println(connection.getResponseCode());
		connection.getResponseCode();

		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}
	public String getProviderHospitalInfo(String caqhId, String orgId, String attestationDate,boolean isError) throws IOException {
		addParamToUrl("caqhProviderId", caqhId);
		addParamToUrl("organizationId", orgId);
		addParamToUrl("attestationDate", attestationDate);



		sendRequest(RequestMethod.GET, ContentType.APPLICATION_XML, null);
		//System.out.println(connection.getResponseCode());
		connection.getResponseCode();

		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}

	public String getPLIs(String caqhId, String orgId, String attestationDate,boolean isError) throws IOException {
		addParamToUrl("caqhProviderId", caqhId);
		addParamToUrl("organizationId", orgId);
		addParamToUrl("attestationDate", attestationDate);



		sendRequest(RequestMethod.GET, ContentType.APPLICATION_XML, null);
		//System.out.println(connection.getResponseCode());
		connection.getResponseCode();

		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}

	public String getProviderInfoCredV3(String caqhId, String orgId, String attestationDate, boolean isError) throws IOException {
		addParamToUrl("caqhProviderId", caqhId);
		addParamToUrl("organizationId", orgId);
		addParamToUrl("attestationDate", attestationDate);

		sendRequest(RequestMethod.GET, ContentType.APPLICATION_XML, null);
		//System.out.println(connection.getResponseCode());
		connection.getResponseCode();

		return isError ? getErrorStreamAsString() : getInputStreamAsString();

	}
	
	public String getProviderInfoCredV4(String caqhId, String orgId, String attestationDate, boolean isError) throws IOException {
		addParamToUrl("caqhProviderId", caqhId);
		addParamToUrl("organizationId", orgId);
		addParamToUrl("attestationDate", attestationDate);

		sendRequest(RequestMethod.GET, ContentType.APPLICATION_XML, null);
		//System.out.println(connection.getResponseCode());
		connection.getResponseCode();

		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}
	
	public String getProviderInfoCredV5(String caqhId, String orgId, String attestationDate, boolean isError) throws IOException {
		addParamToUrl("caqhProviderId", caqhId);
		addParamToUrl("organizationId", orgId);
		addParamToUrl("attestationDate", attestationDate);

		sendRequest(RequestMethod.GET, ContentType.APPLICATION_XML, null);
		//System.out.println(connection.getResponseCode());
		connection.getResponseCode();

		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}



}

