package com.proview.api.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.proview.api.APITestBase;
//import com.proview.api.CVOServiceAPI;
import com.proview.api.PSVBatchServiceAPI;
import com.proview.api.cvoserviceapi;
import com.proview.util.Constants;
import com.proview.util.Util;

public class CVOAPITest  extends APITestBase {
	public CVOAPITest() {
		super(ApiType.CVOAPI);
	}
	
	cvoserviceapi cvoservice;
	String CVOBaselineDir="BaselineResponses\\CVOAPIResponse\\";

	@BeforeMethod(alwaysRun=true)
	public void init() {
		cvoservice = new cvoserviceapi(endPointUrl, authorizationKey);
	}
	
	@Test(groups = {  "PVAPIRegression","APIRegression","CVO"}, dataProvider = "testDataProvider")
	
	public void verifyCvoBridge(String caqhid,  String format, String includeDocuments, String cvopo)
		   throws Exception{
	
	
		String response = cvoservice.addCvoBatch(caqhid, format, includeDocuments,cvopo,false);
	
		System.out.println("replaced body: " +  response);
		System.out.println(cvoservice.getResponseCode());
		
		Assert.assertEquals(cvoservice.getResponseCode(), 202);
		
		File sourceFile = new File("BaselineResponses\\"+System.getenv("environment")+"\\CVOBridgeServiceAPIResponse\\CVOService.xml");
		
		File responseFile = writeResponseToAFile("CVOBridgeServiceAPIResponse", response);
		
		//JSON Compare 
		JSONCompare.compareJSON(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")),  response, JSONCompareMode.LENIENT);
	
		FileUtils.deleteQuietly(responseFile);
		
		
	}
	@Test(groups={"ProdSmokeSuite","ProdAPISmokeSuite"}, dataProvider = "testDataProvider")	
	public void verifyCvoBridgeSmokeScenario(String caqhid,  String format, String includeDocuments, String cvopo)
		   throws Exception{	
	
		String response = cvoservice.addCvoBatch(caqhid, format, includeDocuments,cvopo,false);	
		System.out.println("CVO Response Body: " +  response);
		System.out.println(cvoservice.getResponseCode());		
		Assert.assertEquals(cvoservice.getResponseCode(), 202);			
		
	}
	}



