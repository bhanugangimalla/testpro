package com.proview.api.tests;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.proview.api.APITestBase;
import com.proview.api.AddPracticeRandomLocationAPI;
import com.proview.util.Constants;

public class ProviderStatusAPITest  extends APITestBase {

	public ProviderStatusAPITest() {

		super(ApiType.AddPL);

	} 
	File file;
	AddPracticeRandomLocationAPI aAddPractiseLocationsAPi;

	String addRosterBaselineDir = "BaselineResponses\\ProviderStatus";

	@BeforeMethod(alwaysRun = true)
	public void init() {
		aAddPractiseLocationsAPi = new AddPracticeRandomLocationAPI(endPointUrl, authorizationKey);
	}
	@Test(groups = { "ProviderStatusAPI", "APIRegression","InvalidJson"}, dataProvider="testDataProvider")
	public void verifyProviderInvalidJsonStatus(String batchId) throws IOException {
		String response = aAddPractiseLocationsAPi.getRosterStatus1(batchId, true);
		System.out.println(response);
		System.out.println(aAddPractiseLocationsAPi.getResponseCode());
		Assert.assertEquals(aAddPractiseLocationsAPi.getResponseCode(), 423);
		File sourceFile = new File("BaselineResponses\\"+Constants.environment+"\\ProviderStatusInvalidJsonAPIResponse\\InvalidJsonResponse.xml");

		File responseFile = writeResponseToAFile("ProviderStatusInvalidJsonAPIResponse", response);

		assertEachLine(sourceFile, responseFile);
		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
	}
}
