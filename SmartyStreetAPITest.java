package com.proview.api.tests;

import java.io.File;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.proview.api.APITestBase;
import com.proview.api.ProviewAPI;
import com.proview.api.SmartyStreetAPI;
import com.proview.util.Constants;
import com.proview.util.DBUtil;

/**
 * This test case validates the Response of Smarty Street single end point API
 * with different input parameters entered(PVT-1144)
 *
 * @author Mousumi
 *
 */

public class SmartyStreetAPITest extends APITestBase {
	protected DBUtil dbUtil;

	public SmartyStreetAPITest() {
		super(ApiType.SMARTYSTREETAPI);
	}

	private SmartyStreetAPI smartystreetAPI;

	@BeforeMethod(alwaysRun = true)
	public void init() {
		smartystreetAPI = new SmartyStreetAPI(endPointUrl, authorizationKey);
	}

	@Test(groups = {"DAAPIRegression", "APIRegression" })
	public void verifySmartyStreetAPI() throws Exception {
		dbUtil = new DBUtil();
		String response = smartystreetAPI.getLocationInfo("2020 k street, NW", "Suite 900 APItest", "Washington", "DC",
				"20006");
		System.out.println(smartystreetAPI.getResponseCode());
		Assert.assertEquals(smartystreetAPI.getResponseCode(), 200);
		File sourceFile = new File("BaselineResponses\\" + Constants.environment
				+ "\\SmartyStreetAPIResponse\\SmartyStreetAPIResponse.xml");
		File responseFile = writeResponseToAFile("SSAPIResponse", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);

		dbUtil.getStandardizedAddressByQuery();

		Assert.assertEquals(Constants.addressLine1, "2020 K St NW Ste 900",
				"Standardized address line 1 is not correct");
		Assert.assertEquals(Constants.addressLine2, "Apitest", "Standardized address line 2 is not correct");
		Assert.assertEquals(Constants.city, "Washington", "Standardized city is not correct");
		Assert.assertEquals(Constants.state, "DC", "Standardized state is not correct");
		Assert.assertEquals(Constants.zip, "20006-1872", "Standardized zip is not correct");
		Assert.assertEquals(Constants.dpbc, "200061872257", "Standardized zip is not correct");

		String dpbc1 = Constants.dpbc;

		smartystreetAPI.getLocationInfo("2020 k street, NW", "Suite 900 APItest XYZ", "Washington", "DC", "20006");
		dbUtil.getStandardizedAddressByQuery();
		String dpbc2 = Constants.dpbc;

		Assert.assertEquals(dpbc1, dpbc2, "Duplicate address is present in Standardized address table");
	}

	@Test(groups = { "DAAPIRegression","APIRegression" })
	public void verifySmartyStreetAPIIncorrectData() throws Exception {

		String response = smartystreetAPI.getLocationInfo("address1", "add2", "city", "state", "zip");
		System.out.println(smartystreetAPI.getResponseCode());
		Assert.assertEquals(smartystreetAPI.getResponseCode(), 206);
		File sourceFile = new File("BaselineResponses\\" + Constants.environment
				+ "\\SmartyStreetAPIResponse\\SmartyStreetnullAPIResponse.xml");
		File responseFile = writeResponseToAFile("SSNullAPIResponse", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
	}

	@Test(groups = { "DAAPIRegression","APIRegression" })
	public void verifySmartyStreetAPIwithCity() throws Exception {

		String response = smartystreetAPI.getLocationInfo("5001 pendleton way", "", "Cranberry Township", "PA",
				"16066");
		System.out.println(smartystreetAPI.getResponseCode());
		Assert.assertEquals(smartystreetAPI.getResponseCode(), 200);
		File sourceFile = new File("BaselineResponses\\" + Constants.environment
				+ "\\SmartyStreetAPIResponse\\SmartyStreetVerifyCityAPIResponse.xml");
		File responseFile = writeResponseToAFile("SSCityAPIResponse", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);

	}

}
