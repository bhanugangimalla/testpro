/*
 * Author: Rahul Kannoju
 * Creation Date: 03/06/18.
 * This test will execute 3 ApI one for submitting input json and getting batchID
 * Other for getting batchID Status
 * And the last one verfying the extracts by hitting extracts API
 */package com.proview.api.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;

import com.proview.api.APITestBase;
import com.proview.api.AddPractiseLocationsAPI;
import com.proview.util.Constants;
import com.proview.util.Util;

public class AddPractiseLocationsAPITest extends APITestBase {

	public AddPractiseLocationsAPITest() {

		super(ApiType.AddPL);

	}

	AddPractiseLocationsAPI aAddPractiseLocationsAPi;

	String addRosterBaselineDir = "BaselineResponses\\RosterAPIResponse\\AddDARosterAPIResponse\\";

	@BeforeMethod(alwaysRun = true)
	public void init() {
		aAddPractiseLocationsAPi = new AddPractiseLocationsAPI(endPointUrl, authorizationKey);
	}

	@Test(groups = "DAproviderPLRosterAPI", dataProvider = "testDataProvider")

	public void verifyAddDARoster(String organization_id, String tax_id, String practice_name,
			String practice_location_address1, String practice_location_address2, String practice_location_city,
			String practice_location_state, String practice_location_zipcode, String practice_location_province,
			String practice_location_country, String npi_type_2, String po_location_id, String location_type,
			String caqh_provider_id, String po_provider_id, String provider_type,
			String provider_primary_practice_state, String provider_first_name, String provider_middle_name,
			String provider_last_name, String npi_type_1ax, String caqh_provider_location_id, String dea_number,
			String dea_state, String practice_location_address3, String practice_location_address4) throws Exception {
		String response = aAddPractiseLocationsAPi.addDARoster(organization_id, tax_id, practice_name,
				practice_location_address1, practice_location_address2, practice_location_city, practice_location_state,
				practice_location_zipcode, practice_location_province, practice_location_country, npi_type_2,
				po_location_id, location_type, caqh_provider_id, po_provider_id, provider_type,
				provider_primary_practice_state, provider_first_name, provider_middle_name, provider_last_name,
				npi_type_1ax, caqh_provider_location_id, dea_number, dea_state, practice_location_address3,
				practice_location_address4);
		System.out.println(response);
		String[] split2 = response.split(":");

		System.out.println(aAddPractiseLocationsAPi.getResponseCode());
		String strbatchID = split2[2].replace("\"", "");
		strbatchID = strbatchID.replace("}", "");
		System.out.println(strbatchID);
		Assert.assertEquals(aAddPractiseLocationsAPi.getResponseCode(), 200);
		TimeUnit.SECONDS.sleep(190);

		String rosterStatus = aAddPractiseLocationsAPi.getRosterStatus(strbatchID);
		System.out.println(rosterStatus);
		String rosterStatus1 = aAddPractiseLocationsAPi.getRosterStatus(strbatchID);
		System.out.println(rosterStatus1);

		String rosterResponse = aAddPractiseLocationsAPi.getRosterResponse();

		String[] split = rosterResponse.split(",");
		String replace = rosterResponse.replace(split[2], " ");
		System.out.println(replace);

		System.out.println(rosterResponse);
		System.out.println(aAddPractiseLocationsAPi.getResponseCode());

		File sourceFile = new File("BaselineResponses\\ADDPL\\AddPL.xml");
		File responseFile = writeResponseToAFile("AddPL", rosterResponse);
		assertEachLine(sourceFile, responseFile);
		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);

	}

	@DataProvider(name = "testDataProvider")
	public Iterator<Object[]> dataProvider(Method method) throws FileNotFoundException, IOException {
		System.out.println(method.getName());

		Iterator iter = Util.getTestData1(new File(System.getProperty("user.dir")+File.separator +"Data"+File.separator +"AddPLAPI.xlsx"), Constants.environment,
				method.getName());
		System.out.println();
		System.out.println("the iter" + iter);
		return iter;
	}

}
