package com.proview.api.tests;

import java.io.File;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.proview.api.APITestBase;
import com.proview.api.ProviewAPI;
import com.proview.util.Constants;

public class ProviewAPITest extends APITestBase {

	public ProviewAPITest() {
		super(ApiType.PROVIEWAPI);
	}
	
	private ProviewAPI ProviewAPI;

	@BeforeMethod(alwaysRun = true)
	public void init() {
		ProviewAPI = new ProviewAPI(endPointUrl, authorizationKey);
	}
	
	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 283)
    public void verifyProviewAPIExtract(String caqhId) throws Exception {
		
		String response = ProviewAPI.getProviderInfo(caqhId, false);		
		System.out.println(ProviewAPI.getResponseCode());
		Assert.assertEquals(ProviewAPI.getResponseCode(), 200);
		File sourceFile = new File("BaselineResponses\\"+Constants.environment+"\\ProviewAPIResponse\\ProviewAPIResponse.xml");
		File responseFile = writeResponseToAFile("ProviewAPIResponse", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
	    FileUtils.deleteQuietly(responseFile);			
	}
    

	//@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 284)
    public void verifyProviewAPIWithAttestationDates(String attestFromDate, String attestToDate) throws Exception {
		
		String response = ProviewAPI.getProviderInfoWithAttestationDates(attestFromDate, attestToDate, true);		
		System.out.println(ProviewAPI.getResponseCode());
		Assert.assertEquals(ProviewAPI.getResponseCode(), 200);
		File sourceFile = new File("BaselineResponses\\"+Constants.environment+"\\ProviewAPIResponse\\ProviewAPIResponse.xml");
//		File sourceFile = new File("BaselineResponses\\"+"SIT"+"\\ProviewAPIResponse\\ProviewAPIAttestDatesResponse.xml");
		File responseFile = writeResponseToAFile("ProviewAPIResponse_AttestDates", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
	    FileUtils.deleteQuietly(responseFile);			
	}
    
  
   	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 285)
       public void verifyProviewAPINotActiveOnRoster(String caqhId) throws Exception {
   		
   		String response = ProviewAPI.getProviderInfo(caqhId, false);		
   		System.out.println(ProviewAPI.getResponseCode());
   		Assert.assertEquals(ProviewAPI.getResponseCode(), 292);
   		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPINotActiveOnRoster.xml");
   		File responseFile = writeResponseToAFile("ProviewAPIResponse", response);
   		System.out.println(response);
   		assertEachLine(sourceFile, responseFile);
   	    FileUtils.deleteQuietly(responseFile);			
   	}
    
 	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 286)
     public void verifyProviewAPINotAuthorized(String caqhId) throws Exception {
 		
 		String response = ProviewAPI.getProviderInfo(caqhId, false);		
 		System.out.println(ProviewAPI.getResponseCode());
 		Assert.assertEquals(ProviewAPI.getResponseCode(), 293);
 		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPINotAuthorizede.xml");
 		File responseFile = writeResponseToAFile("ProviewAPIResponse", response);
 		System.out.println(response);
 		assertEachLine(sourceFile, responseFile);
 	    FileUtils.deleteQuietly(responseFile);			
 	}
   
   
 	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 287)
     public void verifyProviewAPINoAttestationData(String caqhId) throws Exception {
 		
 		String response = ProviewAPI.getProviderInfo(caqhId, false);		
 		System.out.println(ProviewAPI.getResponseCode());
 		if (ProviewAPI.getResponseCode() == 294){
 		Assert.assertEquals(ProviewAPI.getResponseCode(), 294);
 		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPINoAttestationData.xml");
 		File responseFile = writeResponseToAFile("ProviewAPIResponse", response);
 		System.out.println(response);
 		assertEachLine(sourceFile, responseFile);
 	    FileUtils.deleteQuietly(responseFile);
 		}
 		else {
 			System.out.println("API contains attested data for the given Start and End dates");
 		}
 	}
   
   
 	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 288)
     public void verifyProviewAPINotCurrentAndComplete(String caqhId) throws Exception {
 		
 		String response = ProviewAPI.getProviderInfo(caqhId, false);		
 		System.out.println(ProviewAPI.getResponseCode());
 		Assert.assertEquals(ProviewAPI.getResponseCode(), 295);
 		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPINotCurrentAndComplete.xml");
 		File responseFile = writeResponseToAFile("ProviewAPIResponse", response);
 		System.out.println(response);
 		assertEachLine(sourceFile, responseFile);
 	    FileUtils.deleteQuietly(responseFile);			
 	}
	
	
	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 289)
    public void verifyProviewAPIBadRequest(String caqhId) throws Exception {
		
		String response = ProviewAPI.getProviderInfo(caqhId, true);		
		System.out.println(ProviewAPI.getResponseCode());
		Assert.assertEquals(ProviewAPI.getResponseCode(), 400);
		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPIBadRequest.xml");
		File responseFile = writeResponseToAFile("ProviewAPIResponse_BadRequest", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
	    FileUtils.deleteQuietly(responseFile);			
	}
	
	//Authentication details must be changed
	//@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 290)
    public void verifyProviewAPIInvalidRequest(String attestFromDate, String attestToDate) throws Exception {
		
		String response = ProviewAPI.getProviderInfoWithAttestationDates(attestFromDate, attestToDate, true);		
		System.out.println(ProviewAPI.getResponseCode());
		Assert.assertEquals(ProviewAPI.getResponseCode(), 401);
		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPIInvalidRequest.xml");
		File responseFile = writeResponseToAFile("ProviewAPIResponse_InvalidRequest", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
	    FileUtils.deleteQuietly(responseFile);			
	}
	

	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 291)
    public void verifyProviewAPIWithInvalidAttestBeginDate(String attestFromDate, String attestToDate) throws Exception {
		
		String response = ProviewAPI.getProviderInfoWithAttestationDates(attestFromDate, attestToDate, true);		
		System.out.println(ProviewAPI.getResponseCode());
		Assert.assertEquals(ProviewAPI.getResponseCode(), 422);
		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPIInvalidBeginAttestDateResponse.xml");
		File responseFile = writeResponseToAFile("ProviewAPIResponse_InvalidAttestBeginDate", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
	    FileUtils.deleteQuietly(responseFile);			
	}
	
	
	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 292)
    public void verifyProviewAPIWithInvalidAttestEndDate(String attestFromDate, String attestToDate) throws Exception {
		
		String response = ProviewAPI.getProviderInfoWithAttestationDates(attestFromDate, attestToDate, true);		
		System.out.println(ProviewAPI.getResponseCode());
		Assert.assertEquals(ProviewAPI.getResponseCode(), 423);
		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPIInvalidEndAttestDateResponse.xml");
		File responseFile = writeResponseToAFile("ProviewAPIResponse_InvalidAttestEndDate", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
	    FileUtils.deleteQuietly(responseFile);			
	}
    

	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 293)
    public void verifyProviewAPIWithAttestDatesParamPopulated(String attestFromDate, String attestToDate) throws Exception {
		
		String response = ProviewAPI.getProviderInfoWithAttestationDates(attestFromDate, attestToDate, true);		
		System.out.println(ProviewAPI.getResponseCode());
		Assert.assertEquals(ProviewAPI.getResponseCode(), 424);
		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPIAttestDatePopulated.xml");
		File responseFile = writeResponseToAFile("ProviewAPIResponse_LastSevenDays", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
	    FileUtils.deleteQuietly(responseFile);			
	}
	

	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 294)
    public void verifyProviewAPIAttestDateRange(String attestFromDate, String attestToDate) throws Exception {
		
		String response = ProviewAPI.getProviderInfoWithAttestationDates(attestFromDate, attestToDate, true);		
		System.out.println(ProviewAPI.getResponseCode());
		Assert.assertEquals(ProviewAPI.getResponseCode(), 425);
		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPIAttestDateRangeResponse.xml");
		File responseFile = writeResponseToAFile("ProviewAPIResponse_AttestDateRange", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
	    FileUtils.deleteQuietly(responseFile);			
	}
    

	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 295)
    public void verifyProviewAPIWithAttestationDatesInLastSevenDays(String attestFromDate, String attestToDate) throws Exception {
		
		String response = ProviewAPI.getProviderInfoWithAttestationDates(attestFromDate, attestToDate, true);		
		System.out.println(ProviewAPI.getResponseCode());
		Assert.assertEquals(ProviewAPI.getResponseCode(), 426);
		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPIAttestDateWithin7Days.xml");
		File responseFile = writeResponseToAFile("ProviewAPIResponse_LastSevenDays", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
	    FileUtils.deleteQuietly(responseFile);			
	}

    
//	@Test(groups={"ProviewAPI", "PVAPIRegression","APIRegression", "DBUtilIssues"},dataProvider="testDataProvider",priority = 296)
    public void verifyProviewAPIServerError() throws Exception {
		
		String response = ProviewAPI.getProviderInfoWithoutAttestationDates(true);		
		System.out.println(ProviewAPI.getResponseCode());
		Assert.assertEquals(ProviewAPI.getResponseCode(), 500);
		File sourceFile = new File("BaselineResponses\\"+"ProviewAPIResponse\\ProviewAPIServerError.xml");
		File responseFile = writeResponseToAFile("ProviewAPIResponse_ServerError", response);
		System.out.println(response);
		assertEachLine(sourceFile, responseFile);
	    FileUtils.deleteQuietly(responseFile);			
	}
     
    @Test(groups={"ProdSmokeSuite","ProdAPISmokeSuite"},dataProvider="testDataProvider",priority = 283)
    public void verifyProviewAPISmokeScenario(String caqhID) throws Exception {
		
		String response = ProviewAPI.getProviderInfo(caqhID, false);		
		logger.info(ProviewAPI.getResponseCode());
		Assert.assertEquals(ProviewAPI.getResponseCode(), 200);				
	}
}
