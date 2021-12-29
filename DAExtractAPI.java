package com.proview.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.proview.util.Constants;
import com.proview.util.DBUtil;


public class DAExtractAPI extends RequestBase {
	public DAExtractAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}

	public String getDAExtractInfo(String orgId,String fromDate,String toDate,boolean isError) throws IOException {

		addParamToUrl("organizationId", orgId);
		addParamToUrl("fromDate", fromDate);
		addParamToUrl("toDate", toDate);


		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);		
		connection.getResponseCode();

		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}
	public String getDAAttestInfo(String orgId,String attestDate,boolean isError) throws IOException {

		addParamToUrl("organizationId", orgId);
		addParamToUrl("attestationdate", attestDate);
		
		

		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);		
		connection.getResponseCode();

		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}
	public String getDAAttestInvalidInfo(String orgId,String attestDate,boolean isError) throws IOException {

		addParamToUrl("organizationId", orgId);
		addParamToUrl("attestationdate", attestDate);
		removeSeperatorfromURL("?");
		

		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);		
		connection.getResponseCode();

		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}
	public String getDAExtractInfoBadResponse(String orgId,String fromDate,String toDate,boolean isError) throws IOException {

		addParamToUrl("organizationId", orgId);
		addParamToUrl("fromDate", fromDate);
		addParamToUrl("toDate", toDate);
		removeSeperatorfromURL("?");


		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		System.out.println(connection.getResponseCode());

		connection.getResponseCode();

		return connectionResponse();
	}
	
	public String getDAExtractInfowithFilter(String orgId,String fromDate,String toDate,boolean isError) throws IOException {

		addParamToUrl("organizationId", orgId);
		addParamToUrl("fromDate", fromDate);
		addParamToUrl("toDate", toDate);
		
		Map<String, String> params = new HashMap<String, String>();		
		params.put("FilterArchiveLocations", "true");
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, params);
	
		connection.getResponseCode();

		return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}
	

	
	
	public void validateCRMModifiedFields(String response) throws Exception  {
	//String jsonResponse = response;
	String jsonString = response;
	JSONObject jsonObject = new JSONObject(jsonString);
	JSONArray jsonArrayProviders = jsonObject.getJSONArray("providers");
	// validation for Multiple Providers for a PO
	int i;
	for (i = 0; i <= jsonArrayProviders.length() - 1; i++) {

		JSONObject jsonObjectProvider = jsonArrayProviders.getJSONObject(i);
		String providerCAQHID = jsonObjectProvider.getString("caqh_provider_id");
		JSONArray jsonarray = jsonObjectProvider.getJSONArray("practice_location");

		//code for multiple Practice locations for a provider
		int j;
		for (j = 0; j <= jsonarray.length() - 1; j++) {

			JSONObject jsonObjectPL = jsonarray.getJSONObject(j);
			JSONObject jsonObjectdpl = jsonObjectPL.getJSONObject("displayed_practice_location");
			String practiceName = jsonObjectdpl.getString("practice_name");
			System.out
					.println("Provider CAQH ID : " + providerCAQHID + "  Provider Practice_name: " + practiceName);

			new DBUtil().getLastUpdatedTimestampsfromPLDAExtract(providerCAQHID, practiceName);

			String generalInfolastmodifiedon = jsonObjectdpl.getString("last_modified_date");
			generalInfolastmodifiedon = generalInfolastmodifiedon.replace(" ", "");
			System.out.println("General Information Last ModifiedOn : " + generalInfolastmodifiedon);

			String officemanagerlastmodifiedon = jsonObjectdpl.getString("services_last_modified_date");
			officemanagerlastmodifiedon = officemanagerlastmodifiedon.replace(" ", "");
			System.out.println("Office Manager Last ModifiedOn : " + officemanagerlastmodifiedon);

			JSONArray jsonArrayTax = jsonObjectdpl.getJSONArray("tax");
			JSONObject jsonObjectTax = jsonArrayTax.getJSONObject(0);
			String taxidlastmodifiedon = jsonObjectTax.getString("last_modified_date");
			taxidlastmodifiedon = taxidlastmodifiedon.replace(" ", "");
			System.out.println("Tax ID Last ModifiedOn : " + taxidlastmodifiedon);

			JSONObject jsonObjectOfficehours = jsonObjectdpl.getJSONObject("office_hours");
			String officehourslastmodifiedon = jsonObjectOfficehours.getString("last_modified_date");
			officehourslastmodifiedon = officehourslastmodifiedon.replace(" ", "");
			System.out.println("Office Hours Last ModifiedOn : " + officehourslastmodifiedon);

			JSONObject jsonObjectPracticeLimitation = jsonObjectdpl.getJSONObject("practice_limitation");
			String practiceLimitationlastmodifiedon = jsonObjectPracticeLimitation.getString("last_modified_date");
			practiceLimitationlastmodifiedon = practiceLimitationlastmodifiedon.replace(" ", "");
			System.out.println("Practice Limitation Last ModifiedOn : " + practiceLimitationlastmodifiedon);

			JSONObject jsonObjectAccessiblity = jsonObjectdpl.getJSONObject("accessibility");
			String Accessiblitylastmodifiedon = jsonObjectAccessiblity.getString("last_modified_date");
			Accessiblitylastmodifiedon = Accessiblitylastmodifiedon.replace(" ", "");
			System.out.println("Accessibility Last ModifiedOn : " + Accessiblitylastmodifiedon);

			JSONObject jsonObjectPracticeServices = jsonObjectdpl.getJSONObject("practice_services");
			String serviceslastmodifiedon = jsonObjectPracticeServices.getString("last_modified_date");
			serviceslastmodifiedon = serviceslastmodifiedon.replace(" ", "");
			System.out.println("Services Last ModifiedOn : " + serviceslastmodifiedon);

			String PracticeLastModifiedDate = jsonObjectdpl.getString("displayedPL_last_modified_date");
			PracticeLastModifiedDate = PracticeLastModifiedDate.replace(" ", "");
			System.out.println("Practice Last ModifiedOn : " + PracticeLastModifiedDate);

			// Validation of Modified Dates from CRM with JSON Response file

			if (!(Constants.generalinformationlastmodifiedon.equalsIgnoreCase("null")
					|| Constants.hourslastmodifiedon.equalsIgnoreCase("null")
					|| Constants.taxidlastmodifiedon.equalsIgnoreCase("null")
					|| Constants.accessibilitylastmodifiedon.equalsIgnoreCase("null")
					|| Constants.serviceslastmodifiedon.equalsIgnoreCase("null")
					|| Constants.limitationlastmodifiedon.equalsIgnoreCase("null")
					|| Constants.officemanagerlastmodifiedon.equalsIgnoreCase("null"))) {
				Constants.officemanagerlastmodifiedon = String
						.valueOf(Math.max(Long.parseLong(Constants.officemanagerlastmodifiedon),
								Long.parseLong(Constants.generalinformationlastmodifiedon)));
				Constants.practicelastmodifiedon = String.valueOf(Math.max(
						Math.max(
								Math.max(
										Math.max(Math.max(
												Math.max(
														Long.parseLong(Constants.officemanagerlastmodifiedon),
														Long.parseLong(Constants.generalinformationlastmodifiedon)),
												Long.parseLong(Constants.hourslastmodifiedon)),
												Long.parseLong(Constants.taxidlastmodifiedon)),
										Long.parseLong(Constants.accessibilitylastmodifiedon)),
								Long.parseLong(Constants.serviceslastmodifiedon)),
						Long.parseLong(Constants.limitationlastmodifiedon)));
			} else {
				Constants.practicelastmodifiedon = "null";
			}
			Assert.assertTrue(generalInfolastmodifiedon.contains(Constants.generalinformationlastmodifiedon),
					"General Information Last ModifiedOn in Extract is not matching with CRM");
			Assert.assertTrue(officemanagerlastmodifiedon.contains(Constants.officemanagerlastmodifiedon),
					"Office Manager Last ModifiedOn in Extract is not matching with CRM");
			Assert.assertTrue(taxidlastmodifiedon.contains(Constants.taxidlastmodifiedon),
					"Tax ID Last ModifiedOn in Extract is not matching with CRM");
			Assert.assertTrue(officehourslastmodifiedon.contains(Constants.hourslastmodifiedon),
					"Office Hours Last ModifiedOn in Extract is not matching with CRM");
			Assert.assertTrue(practiceLimitationlastmodifiedon.contains(Constants.limitationlastmodifiedon),
					"Practice Limitation Last ModifiedOn in Extract is not matching with CRM");
			Assert.assertTrue(Accessiblitylastmodifiedon.contains(Constants.accessibilitylastmodifiedon),
					"Accessibility Last ModifiedOn in Extract is not matching with CRM");
			Assert.assertTrue(serviceslastmodifiedon.contains(Constants.serviceslastmodifiedon),
					"Services Last ModifiedOn in Extract is not matching with CRM");
			if (Constants.practicelastmodifiedon != "null"){
				Assert.assertTrue(PracticeLastModifiedDate.contains(Constants.practicelastmodifiedon),
						"Pratice Location Last ModifiedOn in Extract is not matching with CRM");
			}
		}

	}
	
	}
}
	 



