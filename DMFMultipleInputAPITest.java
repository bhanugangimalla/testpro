package com.proview.api.tests;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.proview.api.APITestBase;
import com.proview.api.DMFApi;

public class DMFMultipleInputAPITest extends APITestBase {
	public DMFMultipleInputAPITest() {
		super(ApiType.DMF);
	}
	
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
	private DMFApi dmfapi;

	@BeforeMethod(alwaysRun = true)
	public void init() {
		dmfapi = new DMFApi(endPointUrl1, authorizationKey);
	}
	
	@Test(groups =  "DMFApi")
	public void verifyDMFMultipleProviderInfo()
			throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = dmfapi.getDMFMultipleProviderInfo();
		File sourceFile = new File("BaselineResponses\\DMFAPIResponses\\verifyDMFInfo.xml");
        File responseFile =writeResponseToAFile("DMFAPIMatchSingle", response);
		System.out.println(responseFile);
		System.out.println(dmfapi.getResponseCode());
		Assert.assertEquals(dmfapi.getResponseCode(), 200);
        assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
		System.out.println("passed");
	}
}
