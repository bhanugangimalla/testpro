package com.proview.api.tests;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.proview.api.APITestBase;
import com.proview.api.VerifideProviderSearchAPIRequest;

/**
 * This test case validates the Response of VeriFide search API with different
 * input parameters entered(PVT-766)
 *
 * @author mousumi.a.mohanty
 *
 */

public class VerifideProviderSearchAPITest extends APITestBase {

	public VerifideProviderSearchAPITest() {
		super(ApiType.VERIFIDEROSTER);
	}

	VerifideProviderSearchAPIRequest verifideProviderSearchAPIRequest;

	@BeforeMethod(alwaysRun = true)
	public void init() {

		verifideProviderSearchAPIRequest = new VerifideProviderSearchAPIRequest(endPointUrl, authorizationKey);

	}

	// @Parameters({"PO-ID", "CAQH-ID", "PO-ProviderId", "FirstName",
	// "LastName", "Provider Type", "Provider Practice State", "NPI Number",
	// "Max Return Count"})

	@Test(groups = { "Functional", "PVAPIRegression","APIRegression"})

	/**
	 * Below script verifies the VeriFide search API Response Body with all
	 * valid input parameters
	 **/

	public void verifyVerifideAPIResponse() throws Exception {

		String response = verifideProviderSearchAPIRequest.getAPIOutput("1042", "13613153", "PlanID431", "zgoeqmqa",
				"kciqgktw", "ND", "CO", "7303530114", 10);
		File sourceFile = new File("BaselineResponses\\VerifideAPIResponses\\VerifyProviderOutput.xml");
		File responseFile = writeResponseToAFile("VerifideAPIMatchSingle", response);
		System.out.println(responseFile);
		assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
		System.out.println("passed");
	}

	@Test(groups = { "Functional", "PVAPIRegression","APIRegression" })

	/**
	 * Below script verifies the API Response Code with a Provider which is not
	 * Rostered to ProView or DA
	 **/

	public void verifyDeRosteredProvider() throws Exception {

		String response1 = verifideProviderSearchAPIRequest.getAPIOutput("1042", "13612576", "", "", "", "", "", "",
				10);
		Assert.assertTrue(response1.contains("No providers are associated with this PO"),
				"Response code found is not as expected :");
		Assert.assertEquals(verifideProviderSearchAPIRequest.getResponseCode(), 295,
				"Response code found is not as expected :");
		System.out.println("passed");
	}

	@Test(groups = { "Functional" , "PVAPIRegression","APIRegression"})

	/**
	 * Below script verifies the API Response Code with a Provider which is
	 * Rostered to ProView / DA / Both
	 **/

	public void verifyDAorProviewRosteredProvider() throws Exception {

		int daresponse = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "13613446", "", "", "", "", "", "",
				10);
		Assert.assertEquals(daresponse, 200, "Response code found is not as expected :");
		int proViewresponse = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "13613447", "", "", "", "",
				"", "", 10);
		Assert.assertEquals(proViewresponse, 200, "Response code found is not as expected :");
		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "13611810", "", "", "", "", "", "",
				10);
		Assert.assertEquals(response, 200, "Response code found is not as expected :");
		System.out.println("passed");
	}

	@Test(groups = { "Functional" , "PVAPIRegression","APIRegression"})

	/**
	 * Below script verifies the API Response Code without any optional
	 * parameter entered
	 **/

	public void searchProviderWithoutOptionalParameter() throws Exception {

		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "", "", "", "", "", "", "", 10);
		Assert.assertEquals(response, 436, "Response code found is not as expected :");
		System.out.println("passed");
	}

	@Test(groups = { "Functional", "PVAPIRegression","APIRegression" })

	/**
	 * Below script verifies the API Response Code with Max count less than -1
	 **/

	public void searchProviderWithIncorrectMaxcount() throws Exception {

		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "13613153", "", "", "", "", "",
				"1234567890", -20);

		Assert.assertEquals(response, 433, "Response code found is not as expected :");

		System.out.println("passed");
	}

	@Test(groups = { "Functional" , "PVAPIRegression","APIRegression"})

	/**
	 * Below script verifies the API Response Code without entering PO-ID
	 **/

	public void searchProviderWithoutPOID() throws Exception {

		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("", "13613153", "", "", "", "", "", "", 10);

		Assert.assertEquals(response, 432, "Response code found is not as expected :");

		System.out.println("passed");
	}

	@Test(groups = { "Functional" , "PVAPIRegression","APIRegression"})

	/**
	 * Below script verifies the API Response Code with Incorrect Provider type
	 * and Primary practice state entered
	 **/

	public void searchProviderWithIncorrectProviderType() throws Exception {

		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "13613153", "", "", "", "ABC", "ABC",
				"", 10);

		Assert.assertEquals(response, 435, "Response code found is not as expected :");

		System.out.println("passed");
	}

	@Test(groups = { "Functional", "PVAPIRegression","APIRegression" })

	/**
	 * Below script verifies the API Response Code with Incorrect length CAQH-ID
	 * ,NPI number and PO-ID entered
	 **/

	public void searchProviderWithIncorrectlength() throws Exception {

		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("10421", "136131531", "", "", "", "", "",
				"11111", 10);

		Assert.assertEquals(response, 437, "Response code found is not as expected :");

		System.out.println("passed");
	}

	@Test(groups = { "Functional", "PVAPIRegression","APIRegression" })

	/**
	 * Below script verifies the API Response Code with Non-Numeric PO-ID,
	 * CAQH-ID and NPI number entered
	 **/

	public void searchProviderWithnonNumericDetails() throws Exception {

		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("abc", "abc", "", "", "", "", "", "abc", 10);

		Assert.assertEquals(response, 434, "Response code found is not as expected :");

		System.out.println("passed");
	}

	@Test(groups = { "Functional", "PVAPIRegression","APIRegression" })

	/**
	 * Below script verifies the API Response Code with PO-ID which does not
	 * exist in ProView system
	 **/

	public void searchProviderWithNonexistPO() throws Exception {

		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("6004", "13613153", "", "", "", "", "", "",
				10);

		Assert.assertEquals(response, 296, "Response code found is not as expected :");

		System.out.println("passed");

	}

	@Test(groups = { "Functional", "PVAPIRegression","APIRegression" })

	/**
	 * Below script verifies the API Response Code with CAQH-ID which does not
	 * exist in ProView system
	 **/

	public void searchProviderWithNonexistPR() throws Exception {

		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "13613153", "", "Mousumi", "Mohanty",
				"DDS", "", "", 10);

		Assert.assertEquals(response, 295, "Response code found is not as expected :");

		System.out.println("passed");

	}

	@Test(groups = { "Functional", "PVAPIRegression","APIRegression" })

	/**
	 * Below script verifies the API Response Code with Max count as 0
	 **/

	public void searchProviderWithZeroMaxCount() throws Exception {

		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "", "", "Mousumi", "Mohanty", "DDS",
				"", "", 0);

		Assert.assertEquals(response, 200, "Response code found is not as expected :");

		System.out.println("passed");

	}

	@Test(groups = { "Functional" , "PVAPIRegression","APIRegression"})

	/**
	 * Below script verifies the API Response Code with Max count as -1
	 **/

	public void searchProviderWithNegativeMaxCount() throws Exception {

		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "", "", "", "", "", "", "1234567890",
				-1);

		Assert.assertEquals(response, 200, "Response code found is not as expected :");

		System.out.println("passed");

	}

	@Test(groups = { "Functional", "PVAPIRegression","APIRegression" })

	/**
	 * Below script verifies the API Response Code with Row count exceeding Max
	 * Return count
	 **/

	public void searchProviderWithtMaxCountExceedingRow() throws Exception {

		int response = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "", "", "", "mohanty", "", "", "",
				1);

		Assert.assertEquals(response, 210, "Response code found is not as expected :");

		System.out.println("passed");

	}

	@Test(groups = { "Functional" , "PVAPIRegression","APIRegression"})

	/**
	 * Below script verifies the API Response Code without Body entered
	 **/

	public void searchProviderWithoutBody() throws Exception {

		int response = verifideProviderSearchAPIRequest.getProviderResponseCodewithoutBody();
		Assert.assertEquals(response, 400, "Response code found is not as expected :");

		System.out.println("passed");

	}

	@Test(groups = { "Functional", "PVAPIRegression","APIRegression" })

	/**
	 * Below script verifies the API Response Code with Mandatory parameters and
	 * LastName/CAQH-ID/NPI entered
	 **/

	public void searchProviderWithMandateParam() throws Exception {

		int responsewithCaqh = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "13613153", "", "", "", "",
				"", "", 10);
		Assert.assertEquals(responsewithCaqh, 200, "Response code found is not as expected :");
		int responsewithLastname = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "", "", "", "kciqgktw",
				"", "", "", 10);
		Assert.assertEquals(responsewithLastname, 200, "Response code found is not as expected :");
		int responsewithNPI = verifideProviderSearchAPIRequest.getAPIResponseCode("1042", "", "", "", "", "", "",
				"7303530114", 10);
		Assert.assertEquals(responsewithNPI, 200, "Response code found is not as expected :");

		System.out.println("passed");

	}
	@Test(groups = { "Functional"})
	/**
	 * Below script verifies the API Response Code with a Provider 
	 * Rostered to ProView 
	 **/

	public void verifyRosteredProvider() throws Exception {

		verifideProviderSearchAPIRequest.getAPIOutput("1001", "14259413", "", "", "", "", "", "",
				10);
		Assert.assertEquals(verifideProviderSearchAPIRequest.getResponseCode(), 200,
				"Response code found is not as expected :");
		System.out.println("passed");
	}

}
