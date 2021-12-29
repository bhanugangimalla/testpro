package com.proview.api.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.proview.api.APITestBase;
import com.proview.api.DAExtractAPI;
import com.proview.util.Constants;
//import com.proview.util.DBUtil;
import com.proview.util.Util;


public class ZDAExtractAPITestE2E extends APITestBase {
//	protected DBUtil dbUtil;
	public ZDAExtractAPITestE2E() {
		super(ApiType.DAEXTRACTE2E);
	}

	private DAExtractAPI daExtractAPI;

	@BeforeMethod(alwaysRun = true)
	public void init() {
		daExtractAPI = new DAExtractAPI(endPointUrl, authorizationKey);
	}
	
	@Test(groups = "DAAPIPracticeE2E", dataProvider = "testDataProvider",priority = 4)//, dependsOnMethods = {
			//"com.proview.provider.tests.practiceLocations.DAProviderParticipationE2ETest.verifyParticipationTest" })
	public void verifyDAAPIProviderE2EExtract(String orgId, String fromDate, String toDate)
			throws Exception {
		String currentDate = Util.getCurrentDate("yyyyMMddHHmmss");
		System.out.println(currentDate);
		String year = currentDate.substring(0, 4);
		String 	month = currentDate.substring(4, 6);
		String day = currentDate.substring(6, 8);
		fromDate =month+"/"+day+"/"+year;
		toDate =month+"/"+day+"/"+year;
		String response = daExtractAPI.getDAExtractInfo(orgId, fromDate, toDate, false);

		// Used to mask fields in Json
		// response(Extract_StartDate,Extract_EndDate,Extract_timeStamp)
		String[] split = response.split(",");
		response = response.replaceAll(split[1] + ",", "");
		response = response.replaceAll(split[2] + ",", "");
		response = response.replaceAll(split[3] + ",", "");
		Assert.assertEquals(daExtractAPI.getResponseCode(), 200);
		File sourceFile = new File("BaselineResponses\\" + Constants.environment
				+ "\\DAExtractAPIE2EResponse\\DAExtractAPIE2EResponse.xml");
		// File sourceFile = new
		// File("BaselineResponses\\"+"HotFix"+"\\DAExtractAPIE2EResponse\\DAExtractAPIE2EResponse.xml");

		File responseFile = writeResponseToAFile("DAExtractAPIResponse", response);

		//Code to validate the Last Modified time stamps under Practice Location section in DA Extract against CRM
//		String caqhID="";
//		String practiceLocationName = "";
//		dbUtil.getLastUpdatedTimestampsfromPLDAExtract(practiceLocationName, caqhID);
		
		// JSONCompare
		JSONCompare.compareJSON(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response,
				JSONCompareMode.LENIENT);

		JSONAssert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response, false);
		
		// Assert.assertEquals(FileUtils.readFileToString(sourceFile,
		// Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);

	}

	@DataProvider(name = "testDataProvider")
	public Iterator<Object[]> dataProvider(Method method) throws FileNotFoundException, IOException {
		System.out.println(method.getName());

		Iterator iter = Util.getTestData1(
				new File(System.getProperty("user.dir") + File.separator + "Data" + File.separator + "DAapi.xlsx"),
				"DAExtractInfo", method.getName());
		System.out.println();
		System.out.println("the iter" + iter);
		return iter;
	}

}
