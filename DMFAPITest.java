package com.proview.api.tests;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.proview.api.APITestBase;
import com.proview.api.DMFApi;
import com.proview.util.Constants;


public class DMFAPITest extends APITestBase{

	public DMFAPITest() {
		super(ApiType.DMF);
	}
	
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
	private DMFApi dmfapi;

	@BeforeMethod(alwaysRun = true)
	public void init() {
		dmfapi = new DMFApi(endPointUrl, authorizationKey);
	}
	
	@Test(groups = { "DMFApi"}, dataProvider="testDataProvider",priority = 1)
	public void verifyDMFProviderInfo(String provider_Birthdate, String provider_SSN)
			throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = dmfapi.getDMFProviderInfo(provider_Birthdate, provider_SSN);
		File sourceFile = new File("BaselineResponses\\DMFAPIResponses\\verifyDMFInfo.xml");
        File responseFile =writeResponseToAFile("DMFAPIMatchSingle", response);
		System.out.println(responseFile);
		System.out.println(dmfapi.getResponseCode());
		Assert.assertEquals(dmfapi.getResponseCode(), 200);
        assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
		System.out.println("passed");
	}
	
	@Test(groups = { "DMFApi"}, dataProvider="testDataProvider",priority = 2)
	public void verifyDMFNoSSNDOB(String provider_Birthdate, String provider_SSN)
			throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = dmfapi.getDMFProviderInfo(provider_Birthdate, provider_SSN);
		File sourceFile = new File("BaselineResponses\\DMFAPIResponses\\NoSSNDOB.xml");
        File responseFile =writeResponseToAFile("DMFAPIMatchSingle", response);
		System.out.println(responseFile);
		System.out.println(dmfapi.getResponseCode());
		Assert.assertEquals(dmfapi.getResponseCode(), 291);
        assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
		System.out.println("passed");
	}
	@Test(groups = { "DMFApi"}, dataProvider="testDataProvider" ,priority = 3)
	public void verifyDMFInvalidDOB(String provider_Birthdate, String provider_SSN)
			throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = dmfapi.getDMFProviderInfo(provider_Birthdate, provider_SSN);
		File sourceFile = new File("BaselineResponses\\DMFAPIResponses\\InvalidDOB.xml");
        File responseFile =writeResponseToAFile("DMFAPIMatchSingle", response);
		System.out.println(responseFile);
		System.out.println(dmfapi.getResponseCode());
		Assert.assertEquals(dmfapi.getResponseCode(), 293);
        assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
		System.out.println("passed");
	}
	@Test(groups = { "DMFApi"}, dataProvider="testDataProvider",priority = 4)
	public void verifyDMFFutureDOB(String provider_Birthdate, String provider_SSN)
			throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = dmfapi.getDMFProviderInfo(provider_Birthdate, provider_SSN);
		File sourceFile = new File("BaselineResponses\\DMFAPIResponses\\FutureDOB.xml");
        File responseFile =writeResponseToAFile("DMFAPIMatchSingle", response);
		System.out.println(responseFile);
		System.out.println(dmfapi.getResponseCode());
		Assert.assertEquals(dmfapi.getResponseCode(), 294);
        assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
		System.out.println("passed");
	}	
	@Test(groups = { "DMFApi"}, dataProvider="testDataProvider",priority = 5)
	public void verifyDMFMissingSSN(String provider_Birthdate, String provider_SSN)
			throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = dmfapi.getDMFProviderInfo(provider_Birthdate, provider_SSN);
		File sourceFile = new File("BaselineResponses\\DMFAPIResponses\\MissingSSN.xml");
        File responseFile =writeResponseToAFile("DMFAPIMatchSingle", response);
		System.out.println(responseFile);
		System.out.println(dmfapi.getResponseCode());
		Assert.assertEquals(dmfapi.getResponseCode(), 296);
        assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
		System.out.println("passed");
	}	
	@Test(groups = { "DMFApi"}, dataProvider="testDataProvider",priority = 6)
	public void verifyDMFMissingDOB(String provider_Birthdate, String provider_SSN)
			throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = dmfapi.getDMFProviderInfo(provider_Birthdate, provider_SSN);
		File sourceFile = new File("BaselineResponses\\DMFAPIResponses\\MissingDOB.xml");
        File responseFile =writeResponseToAFile("DMFAPIMatchSingle", response);
		System.out.println(responseFile);
		System.out.println(dmfapi.getResponseCode());
		Assert.assertEquals(dmfapi.getResponseCode(), 297);
        assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
		System.out.println("passed");
	}	
	
	@Test(groups = { "DMFApi"}, dataProvider="testDataProvider",priority = 7)
	public void verifyDMFInvalidSSN(String provider_Birthdate, String provider_SSN)
			throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = dmfapi.getDMFProviderInfo(provider_Birthdate, provider_SSN);
		File sourceFile = new File("BaselineResponses\\DMFAPIResponses\\InvalidSSN.xml");
        File responseFile =writeResponseToAFile("DMFAPIMatchSingle", response);
		System.out.println(responseFile);
		System.out.println(dmfapi.getResponseCode());
		Assert.assertEquals(dmfapi.getResponseCode(), 210);
        assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
		System.out.println("passed");
	}
	@Test(groups = { "DMFApi"}, dataProvider="testDataProvider",priority = 8)
	public void verifyDMFNoData(String provider_Birthdate, String provider_SSN)
			throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = dmfapi.getDMFNoData(provider_Birthdate, provider_SSN);
		File sourceFile = new File("BaselineResponses\\DMFAPIResponses\\NoData.xml");
        File responseFile =writeResponseToAFile("DMFAPIMatchSingle", response);
		System.out.println(responseFile);
		System.out.println(dmfapi.getResponseCode());
		Assert.assertEquals(dmfapi.getResponseCode(), 400);
        assertEachLine(sourceFile, responseFile);
		FileUtils.deleteQuietly(responseFile);
		System.out.println("passed");
	}	
}
