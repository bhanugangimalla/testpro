package com.proview.epmm.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.proview.api.RequestBase;

public class AffiliationAPI extends RequestBase {

	public AffiliationAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
		// TODO Auto-generated constructor stub
	}

	public Response sendPOSTAffiliationTokenRequest(String APIBody) {
		// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();
		// Setting API's body
		builder.setBody(APIBody);
		// Setting content type as application/json or application/xml
		builder.setContentType(ContentType.APPLICATION_JSON.getValue());
		RequestSpecification requestSpec = builder.build();
		RestAssured.useRelaxedHTTPSValidation();
		return RestAssured.given().spec(requestSpec).when().post(urlStr + "api/Affiliation/V1/Token");

	}

	public Response sendGETAffiliationRequest(String caqhid, String bearerToken) {
		// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();
		// Setting content type as application/json or application/xml
		builder.setContentType(ContentType.APPLICATION_JSON.getValue());
		RequestSpecification requestSpec = builder.build();
		return RestAssured.given().headers("Authorization", "Bearer " + bearerToken).spec(requestSpec).when()
				.get(urlStr + "api/Affiliation/V1/Affiliations?CAQHId=" + caqhid);

	}

	public Response sendPOSTAffiliationUpdateStatusRequest(String payload, String bearerToken, String affiliationId) {
		// Building request using requestSpecBuilder
		RequestSpecBuilder builder = new RequestSpecBuilder();
		// Setting API's body
		builder.setBody(payload);
		// Setting content type as application/json or application/xml
		builder.setContentType(ContentType.APPLICATION_JSON.getValue());
		RequestSpecification requestSpec = builder.build();

		return RestAssured.given().headers("Authorization", "Bearer " + bearerToken).spec(requestSpec).when()
				.post(urlStr + "api/Affiliation/V1/UpdateAffiliationStatus?AffiliationId=" + affiliationId);
	}

	public String verifyAffiliationAPIResonse(Response response, String responceVar) {
		String message = null;
		try {
			JSONObject actualResponseBody = new JSONObject(response.body().asString());
			message = actualResponseBody.getString(responceVar);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return message;

	}

	public String getBearerToken(Response response) {
		String token = null;
		try {
			JSONObject responseObj = new JSONObject(response.body().asString());
			JSONObject resultObj = new JSONObject(responseObj.getString("result"));
			token = resultObj.getString("token");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return token;

	}

	public String getAffilationDetails(Response affilationResponse, String valueOf) {
		String resultData = null;
		try {
			JSONObject responseObj = new JSONObject(affilationResponse.body().asString());
			JSONArray resultsArray = new JSONArray(responseObj.getString("result"));
			JSONObject result = resultsArray.getJSONObject(0);
			resultData = result.getString(valueOf);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultData;
	}

	public int getAffilationDetailsCount(Response affilationResponse) throws JSONException {

		JSONObject responseObj = new JSONObject(affilationResponse.body().asString());
		JSONArray resultsArray = new JSONArray(responseObj.getString("result"));

		return resultsArray.length();

	}
}
