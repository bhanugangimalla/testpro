package com.proview.epmm.api.tests;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;
import com.proview.api.APITestBase;
import com.proview.epmm.api.AffiliationAPI;
import com.proview.util.DBUtil;

/**
 * Test is about EPMM home page test navigating
 * 
 * @author sanjeev.kumar
 *
 */
public class AffiliationAPITest extends APITestBase {

	public AffiliationAPITest() {
		super(ApiType.EPMMAFFILIATION);
	}

	AffiliationAPI apiValidation;
	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

	@BeforeClass(alwaysRun = true)
	public void dataReset() throws Exception {
		apiValidation = new AffiliationAPI(endPointUrl, authorizationKey);
		new DBUtil().resetAffiliationStatus(super.caqhid);
	}

	@Test(description = "Verifying the Get Affiliation Token API resopnce", groups = "PMBATAPI",priority = 1)
	public void verifyGetAffiliationTokenAPI() {
		Response response = apiValidation.sendPOSTAffiliationTokenRequest("{'caqhId': '" + super.caqhid + "'}");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "message"),
				"POST Request successful.");
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "isError"), "false");
	}

	@Test(description = "Verifying the Get Affiliation Token API resopnce when request body variable is incorrect ", groups = "PMBATAPI",priority = 2)
	public void verifyGetAffiliationTokenAPIWithIncorrectVariableName() {
		Response response = apiValidation.sendPOSTAffiliationTokenRequest("{'caqhIdddd': '" + super.caqhid + "'}");
		
		Assert.assertEquals(response.getStatusCode(), 404);
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "isError"), "true");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "type"),
				"https://httpstatuses.com/404");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "title"),
				"Record with Id:  does not exist .");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "instance"),
				"/api/Affiliation/V1/Token");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "status"), "404");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "extensions"), "{}");
	}

	@Test(description = "Verifying the Get Affiliation Token API resopnce when request body has incorrect CAQHID", groups = "PMBATAPI",priority = 3)
	public void verifyGetAffiliationTokenAPIWithIncorrectCAQHID() {
		Response response = apiValidation.sendPOSTAffiliationTokenRequest("{'caqhId': '01010101'}");
		
		Assert.assertEquals(response.getStatusCode(), 404);
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "isError"), "true");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "type"),
				"https://httpstatuses.com/404");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "title"),
				"Record with Id: 01010101 does not exist .");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "instance"),
				"/api/Affiliation/V1/Token");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "status"), "404");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(response, "extensions"), "{}");
	}

	@Test(description = "Verifying the Get Affiliation of a provider", groups = "PMBATAPI", priority = 4)
	public void verifyGetAffiliation() {
		Response affilationTokenResponse = apiValidation
				.sendPOSTAffiliationTokenRequest("{'caqhId': '" + super.caqhid + "'}");
		
		Assert.assertEquals(affilationTokenResponse.getStatusCode(), 200);
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationTokenResponse, "message"),
				"POST Request successful.");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationTokenResponse, "isError"),
				"false");

		String bearerToken = apiValidation.getBearerToken(affilationTokenResponse);

		Response affilationResponse = apiValidation.sendGETAffiliationRequest(super.caqhid, bearerToken);
		
		Assert.assertEquals(affilationResponse.getStatusCode(), 200);
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationResponse, "message"),
				"GET Request successful.");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationResponse, "isError"), "false");
		
		Assert.assertEquals(apiValidation.getAffilationDetails(affilationResponse, "affiliationStatus"), "PENDING");
		
		Assert.assertEquals(apiValidation.getAffilationDetails(affilationResponse, "caqhId"), super.caqhid);
	}
	
	@Test(description = "Verifying the Get Affiliation of a provider for incorrect bearerToken", groups = "PMBATAPI",priority = 5)
	public void verifyGetAffiliationUnAuthorized() {
		

		Response affilationResponse = apiValidation.sendGETAffiliationRequest(super.caqhid, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFk");
		
		Assert.assertEquals(affilationResponse.getStatusCode(), 401);
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationResponse, "type"), "https://httpstatuses.com/401");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationResponse, "title"), "Unauthorized");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationResponse, "status"), "401");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationResponse, "instance"), "/api/Affiliation/V1/Affiliations");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationResponse, "extensions"), "{}");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationResponse, "detail"), "");
	}
	
	@Test(description = "Verifying the Get Affiliation of a provider for incorrect caqhid", groups = "PMBATAPI",priority = 6)
	public void verifyGetAffiliationInValidCaqhid() throws JSONException {
		
		Response affilationTokenResponse = apiValidation
				.sendPOSTAffiliationTokenRequest("{'caqhId': '" + super.caqhid + "'}");
		
		Assert.assertEquals(affilationTokenResponse.getStatusCode(), 200);
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationTokenResponse, "message"),
				"POST Request successful.");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationTokenResponse, "isError"),
				"false");

		String bearerToken = apiValidation.getBearerToken(affilationTokenResponse);
		

		Response affilationResponse = apiValidation.sendGETAffiliationRequest("11111111", bearerToken);
		
		Assert.assertEquals(affilationResponse.getStatusCode(), 200);
				
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationResponse, "message"),
				"GET Request successful.");
		
		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(affilationResponse, "isError"), "false");
		
		Assert.assertEquals(apiValidation.getAffilationDetailsCount(affilationResponse), 0);
	}
	
	@Test(description = "Verifying the update Affiliation of a provider form Pending to Accept", groups = "PMBATAPI",priority = 7)
	public void verifyPostUpdateAffiliationStatusAccept() throws JSONException {

		Response affilationTokenResponse = apiValidation
				.sendPOSTAffiliationTokenRequest("{'caqhId': '" + super.caqhid + "'}");

		Assert.assertEquals(affilationTokenResponse.getStatusCode(), 200);

		String bearerToken = apiValidation.getBearerToken(affilationTokenResponse);

		Response affilationResponse = apiValidation.sendGETAffiliationRequest(super.caqhid, bearerToken);

		Assert.assertEquals(affilationResponse.getStatusCode(), 200);

		String affiliationID = apiValidation.getAffilationDetails(affilationResponse, "affiliationId");

		Response updateAffilationResponse = apiValidation
				.sendPOSTAffiliationUpdateStatusRequest("{'affiliationStatus': 'ACCEPT'}", bearerToken, affiliationID);

		Assert.assertEquals(updateAffilationResponse.getStatusCode(), 200);
		
		// Verification of provider affiliation status from pending to Accept

		Response affilationResponseAfterUpdate = apiValidation.sendGETAffiliationRequest(super.caqhid, bearerToken);

		Assert.assertEquals(affilationResponseAfterUpdate.getStatusCode(), 200);
		
		Assert.assertEquals(apiValidation.getAffilationDetails(affilationResponseAfterUpdate, "affiliationStatus"), "ACCEPT");
		
		String expectedDate =apiValidation.getAffilationDetails(affilationResponseAfterUpdate, "affiliationAcceptDate");
		
		Assert.assertEquals(expectedDate.substring(0,expectedDate.lastIndexOf("T")),date);
		
		// Verification of provider affiliation status from Accept to Remove

	}
	
	@Test(description = "Verifying the update Affiliation of a provider form Accept to remove", groups = "PMBATAPI", priority = 8)
	public void verifyPutUpdateAffiliationStatusRemove() throws JSONException {

		Response affilationTokenResponse = apiValidation
				.sendPOSTAffiliationTokenRequest("{'caqhId': '" + super.caqhid + "'}");

		Assert.assertEquals(affilationTokenResponse.getStatusCode(), 200);

		String bearerToken = apiValidation.getBearerToken(affilationTokenResponse);

		Response affilationResponse = apiValidation.sendGETAffiliationRequest(super.caqhid, bearerToken);

		Assert.assertEquals(affilationResponse.getStatusCode(), 200);

		String affiliationID = apiValidation.getAffilationDetails(affilationResponse, "affiliationId");

		Response updateAffilationResponse = apiValidation
				.sendPOSTAffiliationUpdateStatusRequest("{'affiliationStatus': 'REMOVE'}", bearerToken, affiliationID);

		Assert.assertEquals(updateAffilationResponse.getStatusCode(), 200);
		
		// Verification of provider affiliation status from Accept to Remove

		Response affilationResponseAfterUpdate = apiValidation.sendGETAffiliationRequest(super.caqhid, bearerToken);

		Assert.assertEquals(affilationResponseAfterUpdate.getStatusCode(), 200);
		
		Assert.assertEquals(apiValidation.getAffilationDetails(affilationResponseAfterUpdate, "affiliationStatus"), "REMOVE");
		
		String expectedDate =apiValidation.getAffilationDetails(affilationResponseAfterUpdate, "affiliationRemoveDate");
		
		Assert.assertEquals(expectedDate.substring(0,expectedDate.lastIndexOf("T")),date);
	}
	
	@Test(description = "Verifying the update Affiliation of a provider form Pending to Reject", groups = "PMBATAPI",priority = 9)
	public void verifyPutUpdateAffiliationStatusReject() throws Exception {
		new DBUtil().resetAffiliationStatus(super.caqhid);
		Response affilationTokenResponse = apiValidation
				.sendPOSTAffiliationTokenRequest("{'caqhId': '" + super.caqhid + "'}");

		Assert.assertEquals(affilationTokenResponse.getStatusCode(), 200);

		String bearerToken = apiValidation.getBearerToken(affilationTokenResponse);

		Response affilationResponse = apiValidation.sendGETAffiliationRequest(super.caqhid, bearerToken);

		Assert.assertEquals(affilationResponse.getStatusCode(), 200);

		String affiliationID = apiValidation.getAffilationDetails(affilationResponse, "affiliationId");

		Response updateAffilationResponse = apiValidation
				.sendPOSTAffiliationUpdateStatusRequest("{'affiliationStatus': 'REJECT'}", bearerToken, affiliationID);

		Assert.assertEquals(updateAffilationResponse.getStatusCode(), 200);
		
		// Verification of provider affiliation status from Accept to Remove

		Response affilationResponseAfterUpdate = apiValidation.sendGETAffiliationRequest(super.caqhid, bearerToken);

		Assert.assertEquals(affilationResponseAfterUpdate.getStatusCode(), 200);
		
		Assert.assertEquals(apiValidation.getAffilationDetails(affilationResponseAfterUpdate, "affiliationStatus"), "REJECT");
		
		String expectedDate =apiValidation.getAffilationDetails(affilationResponseAfterUpdate, "affiliationRemoveDate");
		
		Assert.assertEquals(expectedDate.substring(0,expectedDate.lastIndexOf("T")),date);
	}
	
	@Test(description = "Verifying the user should not update Affiliation status form Reject to PENDING , ACCEPT REMOVE ", groups = "PMBATAPI",priority = 10)
	public void verifyPutUpdateAffiliationStatusRejectToAccept() throws Exception {
		Response affilationTokenResponse = apiValidation
				.sendPOSTAffiliationTokenRequest("{'caqhId': '" + super.caqhid + "'}");

		Assert.assertEquals(affilationTokenResponse.getStatusCode(), 200);

		String bearerToken = apiValidation.getBearerToken(affilationTokenResponse);

		Response affilationResponse = apiValidation.sendGETAffiliationRequest(super.caqhid, bearerToken);

		Assert.assertEquals(affilationResponse.getStatusCode(), 200);

		String affiliationID = apiValidation.getAffilationDetails(affilationResponse, "affiliationId");

		// Verification the user should not update Affiliation status form Reject to
		// Accept

		Response updateAffilationResponse = apiValidation
				.sendPOSTAffiliationUpdateStatusRequest("{'affiliationStatus': 'PENDING'}", bearerToken, affiliationID);

		Assert.assertEquals(updateAffilationResponse.getStatusCode(), 400);

		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(updateAffilationResponse, "title"),
				"Record with Id: " + affiliationID + " does not exist or provided status is not valid.");

		Response affilationResponseAfterUpdate = apiValidation.sendGETAffiliationRequest(super.caqhid, bearerToken);

		Assert.assertEquals(affilationResponseAfterUpdate.getStatusCode(), 200);

		Assert.assertEquals(apiValidation.getAffilationDetails(affilationResponseAfterUpdate, "affiliationStatus"),
				"REJECT");

		// Verification the user should not update Affiliation status form Reject to
		// Accept

		Response updateAcceptAffilationResponse = apiValidation
				.sendPOSTAffiliationUpdateStatusRequest("{'affiliationStatus': 'ACCEPT'}", bearerToken, affiliationID);

		Assert.assertEquals(updateAcceptAffilationResponse.getStatusCode(), 400);

		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(updateAcceptAffilationResponse, "title"),
				"Record with Id: " + affiliationID + " does not exist or provided status is not valid.");

		Response affilationResponseAfterUpdate2 = apiValidation.sendGETAffiliationRequest(super.caqhid, bearerToken);

		Assert.assertEquals(affilationResponseAfterUpdate2.getStatusCode(), 200);

		Assert.assertEquals(apiValidation.getAffilationDetails(affilationResponseAfterUpdate2, "affiliationStatus"),
				"REJECT");

		// Verification the user should not update Affiliation status form Reject to
		// Accept

		Response updateRemoveAffilationResponse = apiValidation
				.sendPOSTAffiliationUpdateStatusRequest("{'affiliationStatus': 'REMOVE'}", bearerToken, affiliationID);

		Assert.assertEquals(updateRemoveAffilationResponse.getStatusCode(), 400);

		Assert.assertEquals(apiValidation.verifyAffiliationAPIResonse(updateRemoveAffilationResponse, "title"),
				"Record with Id: " + affiliationID + " does not exist or provided status is not valid.");

		Response affilationResponseAfterUpdate3 = apiValidation.sendGETAffiliationRequest(super.caqhid, bearerToken);

		Assert.assertEquals(affilationResponseAfterUpdate3.getStatusCode(), 200);

		Assert.assertEquals(apiValidation.getAffilationDetails(affilationResponseAfterUpdate3, "affiliationStatus"),
				"REJECT");

	}

}
