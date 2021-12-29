package com.proview.ems.api.tests;

import org.json.JSONException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.jayway.restassured.response.Response;
import com.proview.api.APITestBase;
import com.proview.api.beans.ems.GenericPublisher;
import com.proview.api.dataprovider.ems.EmsDataProvider;
import com.proview.ems.api.GenericPublisherAPI;

public class GenericPublisherAPITest extends APITestBase {
	public GenericPublisherAPITest() {
		super(ApiType.GENRICEMS);
		// TODO Auto-generated constructor stub
	}

	private GenericPublisherAPI genricPublisherAPIValidate;
	private Assertion genricPublisher_assert = new Assertion();

	/** This method runs before the first test from the class runs */
	@BeforeClass(alwaysRun = true)
	public void initClass() {
		genricPublisherAPIValidate = new GenericPublisherAPI(endPointUrl, authorizationKey);

	}

	@Test(description = " Verify that get pulisher API token", groups = "PMBATAPI", dataProvider = "getTokenDataprovider", dataProviderClass = EmsDataProvider.class)
	public void verifyGETTokenAPI(GenericPublisher publisher) throws JSONException {

		Response response = genricPublisherAPIValidate.reqGETTokenAPI(publisher);

		genricPublisher_assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(publisher.tokenAPIResponceStatusCode),
				"Actual status is :- " + response.getStatusCode() + " And expected status is:-"
						+ publisher.tokenAPIResponceStatusCode);
		genricPublisher_assert.assertTrue(
				genricPublisherAPIValidate.verifyGETTokenAPIResponce(response, publisher.tokenAPIExpectedResponse));
	}

	@Test(description = " Verify that get pulisher API token", groups = "PMBATAPI", dataProvider = "genricPublishMessageDataprovider", dataProviderClass = EmsDataProvider.class)
	public void verifyPublishMessage(GenericPublisher publisher) throws JSONException {
		// send request for get bearer token
		Response response = genricPublisherAPIValidate.reqGETTokenAPI(publisher);

		genricPublisher_assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(publisher.tokenAPIResponceStatusCode),
				"Actual status is :- " + response.getStatusCode() + " And expected status is:-"
						+ publisher.tokenAPIResponceStatusCode);
		String bearerToken = genricPublisherAPIValidate.getAccessToken(response);
		// for incorrect bearer token
		if (!publisher.bearerToken.isEmpty()) {
			bearerToken = publisher.bearerToken;
		}
		// send request for publish message
		Response publisherResponse = genricPublisherAPIValidate.publishAPI(publisher, bearerToken);

		genricPublisher_assert.assertEquals(publisherResponse.getStatusCode(), Integer.parseInt(publisher.statusCode),
				"Actual status is :- " + publisherResponse.getStatusCode() + " And expected status is:-"
						+ publisher.statusCode);
		genricPublisher_assert.assertTrue(genricPublisherAPIValidate.verifyPublishAPIResponce(publisherResponse,
				publisher.expectedResponseStatus));
	}
}
