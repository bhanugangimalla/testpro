package com.proview.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.proview.api.RequestBase.ContentType;
import com.proview.api.RequestBase.RequestMethod;

public class SmartyStreetAPI extends RequestBase {

	public SmartyStreetAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
	}
		
	public String getLocationInfo(String Add1,String Add2,String City,String State,String Zip) 
	{

		String body = "{\n"+
				"\"addressLine1\":\"" + Add1 + "\",\n" +
				"\"addressLine2\":\"" + Add2 +"\",\n" +
				"\"city\":\"" + City + "\",\n" +
				"\"state\":\"" + State + "\",\n" +
				"\"zip\":\"" + Zip + "\"\n" +				
				"}\n";
		Map<String, String> params = new HashMap<String, String>();
		params.put("Ocp-Apim-Subscription-Key", "c022d470bef845689932db392a2855d6");
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);
		return getInputStreamAsString();
	}
	
	public String getMultipleLocationInfo(String Add1,String Add2,String City,String State,String Zip) 
	{

		String body = "[\n"+"{\n"+
				"\"addressLine1\":\"" + Add1 + "\",\n" +
				"\"addressLine2\":\"" + Add2 +"\",\n" +
				"\"city\":\"" + City + "\",\n" +
				"\"state\":\"" + State + "\",\n" +
				"\"zip\":\"" + Zip + "\"\n" +				
				"}\n"+"]\n";
		Map<String, String> params = new HashMap<String, String>();
		params.put("Ocp-Apim-Subscription-Key", "c022d470bef845689932db392a2855d6");
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);
		return getInputStreamAsString();
	}
	
	public String getMultipleReqLocationInfo(String firstAdd1,String firstAdd2,String firstCity,String firstState,String firstZip,String secondAdd1,String secondAdd2,String secondCity,String secondState,String secondZip,String thirdAdd1,String thirdAdd2,String thirdCity,String thirdState,String thirdZip,String fourthAdd1,String fourthAdd2,String fourthCity,String fourthState,String fourthZip,String fifthAdd1,String fifthAdd2,String fifthCity,String fifthState,String fifthZip) 
	{

		String body = "[\n"+"{\n"+
				"\"addressLine1\":\"" + firstAdd1 + "\",\n" +
				"\"addressLine2\":\"" + firstAdd2 +"\",\n" +
				"\"city\":\"" + firstCity + "\",\n" +
				"\"state\":\"" + firstState + "\",\n" +
				"\"zip\":\"" + firstZip + "\"\n" +				
				"}\n"+ ","+"{\n"+
				"\"addressLine1\":\"" + secondAdd1 + "\",\n" +
				"\"addressLine2\":\"" + secondAdd2 +"\",\n" +
				"\"city\":\"" + secondCity + "\",\n" +
				"\"state\":\"" + secondState + "\",\n" +
				"\"zip\":\"" + secondZip + "\"\n" +				
				"}\n"+ ","+"{\n"+
				"\"addressLine1\":\"" + thirdAdd1 + "\",\n" +
				"\"addressLine2\":\"" + thirdAdd2 +"\",\n" +
				"\"city\":\"" + thirdCity + "\",\n" +
				"\"state\":\"" + thirdState + "\",\n" +
				"\"zip\":\"" + thirdZip + "\"\n" +				
				"}\n"+ ","+"{\n"+
				"\"addressLine1\":\"" + fourthAdd1 + "\",\n" +
				"\"addressLine2\":\"" + fourthAdd2 +"\",\n" +
				"\"city\":\"" + fourthCity + "\",\n" +
				"\"state\":\"" + fourthState + "\",\n" +
				"\"zip\":\"" + fourthZip + "\"\n" +				
				"}\n"+ ","+"{\n"+
				"\"addressLine1\":\"" + fifthAdd1 + "\",\n" +
				"\"addressLine2\":\"" + fifthAdd2 +"\",\n" +
				"\"city\":\"" + fifthCity + "\",\n" +
				"\"state\":\"" + fifthState + "\",\n" +
				"\"zip\":\"" + fifthZip + "\"\n" +				
				"}\n"
				+"]\n";
		Map<String, String> params = new HashMap<String, String>();
		params.put("Ocp-Apim-Subscription-Key", "c022d470bef845689932db392a2855d6");
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);
		return getInputStreamAsString();
	}
	
	public int getMultipleReq1LocationInfo(String firstAdd1,String firstAdd2,String firstCity,String firstState,String firstZip,String secondAdd1,String secondAdd2,String secondCity,String secondState,String secondZip,String thirdAdd1,String thirdAdd2,String thirdCity,String thirdState,String thirdZip,String fourthAdd1,String fourthAdd2,String fourthCity,String fourthState,String fourthZip,String fifthAdd1,String fifthAdd2,String fifthCity,String fifthState,String fifthZip,String sixthAdd1,String sixthAdd2,String sixthCity,String sixthState,String sixthZip) 
	{

		String body = "[\n"+"{\n"+
				"\"addressLine1\":\"" + firstAdd1 + "\",\n" +
				"\"addressLine2\":\"" + firstAdd2 +"\",\n" +
				"\"city\":\"" + firstCity + "\",\n" +
				"\"state\":\"" + firstState + "\",\n" +
				"\"zip\":\"" + firstZip + "\"\n" +				
				"}\n"+ ","+"{\n"+
				"\"addressLine1\":\"" + secondAdd1 + "\",\n" +
				"\"addressLine2\":\"" + secondAdd2 +"\",\n" +
				"\"city\":\"" + secondCity + "\",\n" +
				"\"state\":\"" + secondState + "\",\n" +
				"\"zip\":\"" + secondZip + "\"\n" +				
				"}\n"+ ","+"{\n"+
				"\"addressLine1\":\"" + thirdAdd1 + "\",\n" +
				"\"addressLine2\":\"" + thirdAdd2 +"\",\n" +
				"\"city\":\"" + thirdCity + "\",\n" +
				"\"state\":\"" + thirdState + "\",\n" +
				"\"zip\":\"" + thirdZip + "\"\n" +				
				"}\n"+ ","+"{\n"+
				"\"addressLine1\":\"" + fourthAdd1 + "\",\n" +
				"\"addressLine2\":\"" + fourthAdd2 +"\",\n" +
				"\"city\":\"" + fourthCity + "\",\n" +
				"\"state\":\"" + fourthState + "\",\n" +
				"\"zip\":\"" + fourthZip + "\"\n" +				
				"}\n"+ ","+"{\n"+
				"\"addressLine1\":\"" + fifthAdd1 + "\",\n" +
				"\"addressLine2\":\"" + fifthAdd2 +"\",\n" +
				"\"city\":\"" + fifthCity + "\",\n" +
				"\"state\":\"" + fifthState + "\",\n" +
				"\"zip\":\"" + fifthZip + "\"\n" +				
				"}\n"+","+"{\n"+
				"\"addressLine1\":\"" + sixthAdd1 + "\",\n" +
				"\"addressLine2\":\"" + sixthAdd2 +"\",\n" +
				"\"city\":\"" + sixthCity + "\",\n" +
				"\"state\":\"" + sixthState + "\",\n" +
				"\"zip\":\"" + sixthZip + "\"\n" +				
				"}\n"
				+"]\n";
		Map<String, String> params = new HashMap<String, String>();
		params.put("Ocp-Apim-Subscription-Key", "c022d470bef845689932db392a2855d6");
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);
		return getResponseCode();
	}

}
