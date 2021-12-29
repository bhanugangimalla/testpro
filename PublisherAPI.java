package com.proview.ems.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.restassured.response.Response;
import com.proview.api.RequestBase;
import com.proview.api.beans.ems.Publisher;
import com.proview.util.Restassured;

public class PublisherAPI extends RequestBase{
	public PublisherAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
		// TODO Auto-generated constructor stub
	}

	private static Logger logger = LogManager.getLogger(PublisherAPI.class);
	Restassured restassured = new Restassured();

	public Response publishAPI(Publisher publisher) {
		
		String url = urlStr + "api/AttestationMessage/publish";
		logger.info("Call Publisher API");
		Formatter fmt = new Formatter();
	    Calendar cal = Calendar.getInstance();
	    // Display complete time and date information.
	   		  
        String requestBody =fmt.format(publisher.requestBody, cal,cal,cal,cal).toString();
	
		Response res = restassured.sendPost(url, requestBody, "application/json");
		return res;
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

}
