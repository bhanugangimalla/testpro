package com.proview.api;


public class VerifideProviderSearchAPIRequest extends RequestBase {


	public VerifideProviderSearchAPIRequest(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}
	
	/**
	 * Below method returns Response Body 
	 **/
	
	public String getAPIOutput(String PO_ID,String CAQH_ID,String PO_ProviderId,String FirstName,String LastName,String Provider_Type,String Provider_Practice_State,String NPI_Number,int Max_Return_Count) 
	{

		String body = "{\n"+
				"\"Po_Id\":\"" + PO_ID + "\",\n" +
				"\"Caqh_Id\":\"" + CAQH_ID +"\",\n" +
				"\"Po_Provider_Id\":\"" + PO_ProviderId + "\",\n" +
				"\"FirstName\":\"" + FirstName + "\",\n" +
				"\"LastName\":\"" + LastName + "\",\n" +
				"\"ProviderType\":\"" + Provider_Type + "\",\n" +
				"\"PrimaryPracticeState\":\"" + Provider_Practice_State + "\",\n" +
				"\"NpiNumber\":\"" + NPI_Number + "\",\n" +
				"\"MaxReturnCount\":\"" + Max_Return_Count + "\",\n" +
				"}\n";
				
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, null, body);
		return getInputStreamAsString();
	}
	
	/**
	 * Below method returns response code 
	 **/
	
	public int getAPIResponseCode(String PO_ID,String CAQH_ID,String PO_ProviderId,String FirstName,String LastName,String Provider_Type,String Provider_Practice_State,String NPI_Number,int Max_Return_Count) 
	{

		String body = "{\n"+
				"\"Po_Id\":\"" + PO_ID + "\",\n" +
				"\"Caqh_Id\":\"" + CAQH_ID +"\",\n" +
				"\"Po_Provider_Id\":\"" + PO_ProviderId + "\",\n" +
				"\"FirstName\":\"" + FirstName + "\",\n" +
				"\"LastName\":\"" + LastName + "\",\n" +
				"\"ProviderType\":\"" + Provider_Type + "\",\n" +
				"\"PrimaryPracticeState\":\"" + Provider_Practice_State + "\",\n" +
				"\"NpiNumber\":\"" + NPI_Number + "\",\n" +
				"\"MaxReturnCount\":\"" + Max_Return_Count + "\",\n" +
				"}\n";
				
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, null, body);
		return getResponseCode();
	}
	
	/**
	 * Below method returns Error response code 
	 **/
	public String getProviderErrorResponseCode(String PO_ID,String CAQH_ID,String PO_ProviderId,String FirstName,String LastName,String Provider_Type,String Provider_Practice_State,String NPI_Number,int Max_Return_Count) 
	{
		String body = "{\n"+
				"\"Po_Id\":\"" + PO_ID + "\",\n" +
				"\"Caqh_Id\":\"" + CAQH_ID +"\",\n" +
				"\"Po_Provider_Id\":\"" + PO_ProviderId + "\",\n" +
				"\"FirstName\":\"" + FirstName + "\",\n" +
				"\"LastName\":\"" + LastName + "\",\n" +
				"\"ProviderType\":\"" + Provider_Type + "\",\n" +
				"\"PrimaryPracticeState\":\"" + Provider_Practice_State + "\",\n" +
				"\"NpiNumber\":\"" + NPI_Number + "\",\n" +
				"\"MaxReturnCount\":\"" + Max_Return_Count + "\",\n" +
				"}\n";

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, null,  body);
		
		return getErrorStreamAsString();
	}
	
	/**
	 * Below method returns response code with empty API body
	 **/
	
	public int getProviderResponseCodewithoutBody() 
	{
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, null, "");
		return getResponseCode();
	}
	

	/**
	 * Below method returns Error response code message
	 **/

	public String searchProvider(String PO_ID,String CAQH_ID,String PO_ProviderId,String FirstName,String LastName,String Provider_Type,String Provider_Practice_State,String NPI_Number,int Max_Return_Count) 
	{

		String body = "{\n"+
				"\"Po_Id\":\"" + PO_ID + "\",\n" +
				"\"Caqh_Id\":\"" + CAQH_ID +"\",\n" +
				"\"Po_Provider_Id\":\"" + PO_ProviderId + "\",\n" +
				"\"FirstName\":\"" + FirstName + "\",\n" +
				"\"LastName\":\"" + LastName + "\",\n" +
				"\"ProviderType\":\"" + Provider_Type + "\",\n" +
				"\"PrimaryPracticeState\":\"" + Provider_Practice_State + "\",\n" +
				"\"NpiNumber\":\"" + NPI_Number + "\",\n" +
				"\"MaxReturnCount\":\"" + Max_Return_Count + "\",\n" +
				"}\n";
				
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, null, body);
		return connectionResponse();
	}
	
	

}
