package com.proview.ems.api;

import java.util.Calendar;
import java.util.Formatter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.proview.api.RequestBase;
import com.proview.api.beans.ems.GenericPublisher;
import com.proview.api.beans.ems.Publisher;
import com.proview.util.Restassured;

public class GenericPublisherAPI extends RequestBase{
	public GenericPublisherAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
		// TODO Auto-generated constructor stub
	}

	private static Logger logger = LogManager.getLogger(GenericPublisherAPI.class);
	Restassured restassured = new Restassured();

public Response reqGETTokenAPI(GenericPublisher publisher) {
		
		String url = urlStr + "/api/Publisher/V1/Token";
		logger.info("Call GET Token API");	
		Response res = restassured.sendPost(url, publisher.tokenAPIRequestBody, "application/json");
		return res;
	}
	
	public Response publishAPI(GenericPublisher publisher, String bearerToken) {
		
		String url = urlStr + "/api/Publisher/V1/Message";
		logger.info("Call genric Publisher API");
		Formatter fmt = new Formatter();
	    Calendar cal = Calendar.getInstance();
	    // Display complete time and date information.
	   		  
        String requestBody =fmt.format(publisher.requestBody, cal,cal,cal,cal).toString();
        
    	// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();
		// Setting API's body
				builder.setBody(requestBody);
		// Setting content type as application/json or application/xml
		builder.setContentType(ContentType.APPLICATION_JSON.getValue());
		RequestSpecification requestSpec = builder.build();
		return RestAssured.given().headers("Authorization", "Bearer " + bearerToken).spec(requestSpec).when()
				.post(url);
	}
	
	public String getAccessToken (Response response) throws JSONException {
		
		JSONObject responceObj = new JSONObject(response.body().asString());
		JSONObject resultObj = new JSONObject(responceObj.getString("result"));
		
		return resultObj.getString("token");
	}
	
	public boolean verifyGetTokenAPIResponse(Response response, GenericPublisher publisher) throws JSONException {
		if (response.getStatusCode() == 500) {
			logger.error("Actual status is " + response.getStatusCode() + "\n and Response is "
					+ response.body().asString());
			return false;
		}

		if (!publisher.tokenAPIResponceStatusCode.isEmpty()) {
			if (!(response.getStatusCode() == Integer.parseInt(publisher.tokenAPIResponceStatusCode))) {
				logger.error("Actual status is " + response.getStatusCode() + " And expected status is "
						+ publisher.tokenAPIResponceStatusCode);
				return false;
			}
			logger.info("Sataus code is verified ");
		}
		JSONObject actualResponseBody = new JSONObject(response.body().asString());

		if (!publisher.tokenAPIExpectedResponse.isEmpty()) {
			JSONObject expectedResponseBody = new JSONObject(publisher.tokenAPIExpectedResponse);
			if (!actualResponseBody.getString("message").equals(expectedResponseBody.getString("message"))) {
				logger.error("Actual message is " + actualResponseBody.getString("message")
						+ " And expected message is " + expectedResponseBody.getString("message"));
				return false;
			}
			if (!actualResponseBody.getString("isError").equals(expectedResponseBody.getString("isError"))) {
				logger.error("Actual Error is " + actualResponseBody.getString("isError") + " And expected Error is "
						+ expectedResponseBody.getString("isError"));
				return false;
			}

			if (!getAccessToken(response).isEmpty()) {
				logger.error("Actual Message is " + getAccessToken(response));
				return false;
			}
			logger.info("Publisher responce Status is verified");
		}

		return true;
	}

	public boolean verifyPublisherResponse(Response response, Publisher publisher) throws JSONException {
		if (response.getStatusCode() == 500) {
			logger.error("Actual status is " + response.getStatusCode() + "\n and Response is "
					+ response.body().asString());
			return false;
		}

		if (!publisher.statusCode.isEmpty()) {
			if (!(response.getStatusCode() == Integer.parseInt(publisher.statusCode))) {
				logger.error("Actual status is " + response.getStatusCode() + " And expected status is "
						+ publisher.statusCode);
				return false;
			}
			logger.info("Sataus code is verified ");
		}
		JSONObject actualResponseBody = new JSONObject(response.body().asString());

		if (!publisher.expectedResponseStatus.isEmpty()) {
			JSONObject expectedResponseBody = new JSONObject(publisher.expectedResponseStatus);
			if (!actualResponseBody.getString("Status").equals(expectedResponseBody.getString("Status"))) {
				logger.error("Actual status is " + actualResponseBody.getString("status") + " And expected status is "
						+ expectedResponseBody.getString("status"));
				return false;
			}
			if (!actualResponseBody.getString("Error").equals(expectedResponseBody.getString("Error"))) {
				logger.error("Actual Error is " + actualResponseBody.getString("Error") + " And expected Error is "
						+ expectedResponseBody.getString("Error"));
				return false;
			}

			if (!actualResponseBody.getString("Message").equals(expectedResponseBody.getString("Message"))) {
				logger.error("Actual Message is " + actualResponseBody.getString("Message") + " And expected Message is "
						+ expectedResponseBody.getString("Message"));
				return false;
			}
			logger.info("Publisher responce Status is verified");
		}

		return true;
	}

	public boolean verifyGETTokenAPIResponce(Response response, String tokenAPIExpectedResponse) throws JSONException {
		if (!tokenAPIExpectedResponse.isEmpty()) {
		String actualResponceString = StringUtils.substringAfter(
				StringUtils.substringBeforeLast(new String(Base64.decodeBase64(getAccessToken(response))), "}"), "}")+"}";
		System.out.println(actualResponceString);
		JSONObject responseObj = new JSONObject(response.body().asString());
		JSONObject decodedTokenObj = new JSONObject(actualResponceString);
		JSONObject expectedresponceObj = new JSONObject(tokenAPIExpectedResponse);
		
			if (!(responseObj.getString("message").equalsIgnoreCase(expectedresponceObj.getString("message")))) {
				logger.error("Actual message is " + responseObj.getString("message") + " And expected Message is "
						+ expectedresponceObj.getString("message"));
				return false;
			}

			if (!responseObj.getString("isError").equalsIgnoreCase(expectedresponceObj.getString("isError"))) {
				logger.error("Actual isError is " + responseObj.getString("isError") + " And expected isError is "
						+ expectedresponceObj.getString("isError"));
				return false;
			}

			if (!decodedTokenObj.getString("ResourceName")
					.equalsIgnoreCase(expectedresponceObj.getString("ResourceName"))) {
				logger.error("Actual Resource Name is " + decodedTokenObj.getString("ResourceName")
						+ " And expected resource Name is " + expectedresponceObj.getString("ResourceName"));
				return false;
			}
			
			if (!decodedTokenObj.getString("ResourceType")
					.equalsIgnoreCase(expectedresponceObj.getString("ResourceType"))) {
				logger.error("Actual Resource Name is " + decodedTokenObj.getString("ResourceType")
						+ " And expected resource Name is " + expectedresponceObj.getString("ResourceType"));
				return false;
			}
		}
		return true;
	}

	public boolean verifyPublishAPIResponce(Response publisherResponse, String expectedResponseStatus)
			throws JSONException {
		if (!expectedResponseStatus.isEmpty()) {

			JSONObject responseObj = new JSONObject(publisherResponse.body().asString());

			JSONObject expectedresponceObj = new JSONObject(expectedResponseStatus);

			if (!(responseObj.getString("message").equalsIgnoreCase(expectedresponceObj.getString("message")))) {
				logger.error("Actual message is " + responseObj.getString("message") + " And expected Message is "
						+ expectedresponceObj.getString("message"));
				return false;
			}

			if (!responseObj.getString("isError").equalsIgnoreCase(expectedresponceObj.getString("isError"))) {
				logger.error("Actual isError is " + responseObj.getString("isError") + " And expected isError is "
						+ expectedresponceObj.getString("isError"));
				return false;
			}
		}

		return true;
	}

}
