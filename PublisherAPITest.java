package com.proview.ems.api.tests;

import org.json.JSONException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.jayway.restassured.response.Response;
import com.proview.api.APITestBase;
import com.proview.api.beans.ems.Publisher;
import com.proview.api.dataprovider.ems.EmsDataProvider;
import com.proview.ems.api.PublisherAPI;

public class PublisherAPITest extends APITestBase{
	public PublisherAPITest() {
		super(ApiType.EMS);
		// TODO Auto-generated constructor stub
	}

	private PublisherAPI publisherValidate;
	private Assertion publisher_assert = new Assertion();

	/** This method runs before the first test from the class runs */
	@BeforeClass
	public void initClass() {
		publisherValidate = new PublisherAPI(endPointUrl, authorizationKey);

	}

	@Test(description = " Verify that publisher api should publis message in azue bus topic", dataProvider = "publishMessageDataprovider", dataProviderClass = EmsDataProvider.class)
	public void verifyPublishMessage(Publisher publisher) throws JSONException {
		System.out.println(publisher.requestBody);
		Response response = publisherValidate.publishAPI(publisher);
		publisher_assert.assertTrue(publisherValidate.verifyPublisherResponse(response, publisher),
				"Verify that publisher api");
	}
}
