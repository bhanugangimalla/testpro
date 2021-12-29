package com.proview.api.tests;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.proview.api.APITestBase;
import com.proview.api.SmartyStreetAPI;
import com.proview.util.Constants;

/**
 * This test case validates the Response of Smarty Street multiple request API
 * with different input parameters entered(PVT-1144)
 *
 * @author Mousumi
 *
 */

public class SmartyStreetMultipleRequestAPITest extends APITestBase {

	public SmartyStreetMultipleRequestAPITest() {
		super(ApiType.SMARTYSTREETMULTIPLEAPI);
	}

	private SmartyStreetAPI smartystreetAPI;

	@BeforeMethod(alwaysRun = true)
	public void init() {
		smartystreetAPI = new SmartyStreetAPI(endPointUrl, authorizationKey);
	}

	@Test(groups = { "DAAPIRegression","APIRegression" })
	public void verifySmartyStreetDifferentReqAPI() throws Exception {
		String response = smartystreetAPI.getMultipleReqLocationInfo("920 MEDICAL PLAZA DR", "STE 260", "SHENANDOAH",
				"TX", "02476", "920 MEDICAL PLAZA DR", "Suite 900", "SHENANDOAH", "TX", "77380", "5001 pendleton way",
				"", "Cranberry Township", "PA", "16066", "1720 MURCHISON DR", "Suite 900", "EL PASO", "TX",
				"79902-2921", "2020 k street NW", "Suite 900", "Washington", "DC", "20006");
		System.out.println(smartystreetAPI.getResponseCode());
		Assert.assertEquals(smartystreetAPI.getResponseCode(), 200);
		File sourceFile = new File("BaselineResponses\\" + Constants.environment
				+ "\\SmartyStreetAPIResponse\\SmartyStreetcheckDifferentAPIResponse.xml");
		File responseFile = writeResponseToAFile("SSAPIDifferentResponse", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
	}

	@Test(groups = { "DAAPIRegression","APIRegression" })
	public void verifySmartyStreetRandomReqAPI() throws Exception {
		String response = smartystreetAPI.getMultipleReqLocationInfo("920 MEDICAL PLAZA DR", "STE 260", "SHENANDOAH",
				"TX", "02476", "920 MEDICAL PLAZA DR", "Suite 900", "SHENANDOAH", "TX", "77380", "5001 pendleton way",
				"", "Cranberry Township", "PA", "16066", "123 PLEASANT ST", "Suite 900", "ARLINGTON", "MA", "02476",
				"add1", "3453453", "random", "EW", "342");
		System.out.println(smartystreetAPI.getResponseCode());
		Assert.assertEquals(smartystreetAPI.getResponseCode(), 200);
		File sourceFile = new File("BaselineResponses\\" + Constants.environment
				+ "\\SmartyStreetAPIResponse\\SmartyStreetcheckRandomAPIResponse.xml");
		File responseFile = writeResponseToAFile("SSAPIDifferentResponse", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
	}

	@Test(groups = { "DAAPIRegression","APIRegression" })
	public void verifySmartyStreetInvalidReqAPI() throws Exception {
		smartystreetAPI.getMultipleReq1LocationInfo("920 MEDICAL PLAZA DR", "STE 260", "SHENANDOAH", "TX", "02476",
				"920 MEDICAL PLAZA DR", "Suite 900", "SHENANDOAH", "TX", "77380", "5001 pendleton way", "",
				"Cranberry Township", "PA", "16066", "123 PLEASANT ST", "Suite 900", "ARLINGTON", "MA", "02476", "add1",
				"3453453", "random", "EW", "342", "add1", "3453453", "random", "EW", "342");
		System.out.println(smartystreetAPI.getResponseCode());
		Assert.assertEquals(smartystreetAPI.getResponseCode(), 420);
	}

	@Test(groups = { "DAAPIRegression","APIRegression" })
	public void verifySmartyStreetMutipleReqwithSameAddAPI() throws Exception {
		String response = smartystreetAPI.getMultipleReqLocationInfo("2020 k street NW", "Suite 900", "Washington",
				"DC", "20006", "2020 k street NW", "Suite 900", "Washington", "DC", "20006", "2020 k street NW",
				"Suite 900", "Washington", "DC", "20006", "2020 k street NW", "Suite 900", "Washington", "DC", "20006",
				"2020 k street NW", "Suite 900", "Washington", "DC", "20006");
		System.out.println(smartystreetAPI.getResponseCode());
		Assert.assertEquals(smartystreetAPI.getResponseCode(), 200);
		File sourceFile = new File("BaselineResponses\\" + Constants.environment
				+ "\\SmartyStreetAPIResponse\\SmartyStreetcheckMultipleAPIResponse.xml");
		File responseFile = writeResponseToAFile("SSAPIMultipleResponse", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
	}

	@Test(groups = { "DAAPIRegression","APIRegression" })
	public void verifySmartyStreetSingleReqAPI() throws Exception {

		String response = smartystreetAPI.getMultipleLocationInfo("2020 k street NW", "Suite 900", "Washington", "DC",
				"20006");
		System.out.println(smartystreetAPI.getResponseCode());
		Assert.assertEquals(smartystreetAPI.getResponseCode(), 200);
		File sourceFile = new File("BaselineResponses\\" + Constants.environment
				+ "\\SmartyStreetAPIResponse\\SmartyStreetMultipleAPIResponse.xml");
		File responseFile = writeResponseToAFile("SSAPIResponse", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
	}

	@Test(groups = { "DAAPIRegression","APIRegression" })
	public void verifySmartyStreetAPIIncorrectData() throws Exception {

		String response = smartystreetAPI.getMultipleLocationInfo("address1", "add2", "city", "state", "zip");
		System.out.println(smartystreetAPI.getResponseCode());
		Assert.assertEquals(smartystreetAPI.getResponseCode(), 200);
		File sourceFile = new File("BaselineResponses\\" + Constants.environment
				+ "\\SmartyStreetAPIResponse\\SmartyStreetnullMultipleAPIResponse.xml");
		File responseFile = writeResponseToAFile("SSNullAPIResponse", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
	}

}
