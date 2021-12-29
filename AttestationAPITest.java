package com.proview.api.tests;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.proview.api.APITestBase;
import com.proview.api.AttestationAPI;

import com.proview.util.Constants;
import com.proview.util.Util;



public class AttestationAPITest extends APITestBase {

	AttestationAPI attestationAPI;
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");

	public AttestationAPITest() {
		super(ApiType.ATTESTATION);
	}

	@BeforeMethod(alwaysRun=true)
	public void init() {
		attestationAPI = new AttestationAPI(endPointUrl, authorizationKey);
	}

	@Test(groups={"attestationAPI", "PVAPIRegression","APIRegression", "getAttestationIds"},priority = 180)
	public void getAttestationIds() throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		 String response = attestationAPI.getAttestationUsers(Util.getStartDate(), Util.getEndDate(),false);
		Assert.assertEquals(attestationAPI.getResponseCode(), 200);
		String responseFileName = "FailedResponses\\AttestationAPIResponse\\Response_" + format.format(new Date()) + ".xml";
		File responseFile = new File(responseFileName);
		File sourceFile = new File("BaselineResponses\\"+Constants.environment+"\\AttestationAPIResponse\\AttestationAPIResponse.xml");
		//File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPISITResponse.xml");
		//response = response.substring(0, response.lastIndexOf('\n'));
		System.out.println("res"+response);
		FileUtils.writeStringToFile(responseFile, response, Charset.forName("UTF-8"));
		//Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);
	}
	
	@Test(groups = { "attestationAPI", "PVAPIRegression","APIRegression", "AttestationDataMissing" },priority = 181)
	public void verifyProviderAttestationDataMissing() throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = attestationAPI.getAttestationUsers(Util.getStartDate(), Util.getEndDate(),false);
		if (attestationAPI.getResponseCode() == 294){
			
			//Assert.assertEquals(attestationAPI.getResponseCode(), 294);

		String responseFileName = "FailedResponses\\AttestationAPIResponse\\Response_" + format.format(new Date()) + ".xml";
		File responseFile = new File(responseFileName);
		File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_AttestationDataMissing.xml");

		//File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_AttestationDataMissingSIT.xml");
		FileUtils.writeStringToFile(responseFile, response, Charset.forName("UTF-8"));

		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);	
		}
		else {
			System.out.println("Attestation API for the given start and end date has provider attestations");
		}
	}

	
	@Test(groups = { "attestationAPI", "PVAPIRegression","APIRegression", "InvalidRequest" },dataProvider="testDataProvider",priority = 182)
	public void verifyProviderInvalidRequest(String startDate, String endDate) throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = attestationAPI.getAttestationUsersBadReq(startDate, endDate);

		Assert.assertEquals(attestationAPI.getResponseCode(), 400);

		String responseFileName = "FailedResponses\\AttestationAPIResponse\\Response_" + format.format(new Date()) + ".xml";
		File responseFile = new File(responseFileName);
		File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_BadRequest.xml");
		//File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_BadRequestSIT.xml");
		FileUtils.writeStringToFile(responseFile, response, Charset.forName("UTF-8"));

		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);		


	}

	//@Test(groups = { "attestationAPI", "PVAPIRegression","APIRegression", "IncorrectAuthentication" },dataProvider="testDataProvider",priority = 183)
	public void verifyIncorrectAuthentication(String startDate, String endDate) throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = attestationAPI.getAttestationUsers(startDate, endDate,true);

		Assert.assertEquals(attestationAPI.getResponseCode(), 401);

		String responseFileName = "FailedResponses\\AttestationAPIResponse\\Response_" + format.format(new Date()) + ".xml";
		File responseFile = new File(responseFileName);
		File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_InvalidRequest.xml");
       // File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_InvalidRequestSIT.xml");
		FileUtils.writeStringToFile(responseFile, response, Charset.forName("UTF-8"));

		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);	

	}

			
	@Test(groups = { "attestationAPI", "PVAPIRegression","APIRegression", "InvalidfromDate" },dataProvider="testDataProvider",priority = 184)
	public void verifyResponseWhenInvalidfromDate(String startDate, String endDate) throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = attestationAPI.getAttestationUsers(startDate, endDate,true);
		System.out.println(attestationAPI.getResponseCode());
		Assert.assertEquals(attestationAPI.getResponseCode(), 422);
      
		String responseFileName = "FailedResponses\\AttestationAPIResponse\\Response_" + format.format(new Date()) + ".xml";
		File responseFile = new File(responseFileName);
		File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_InvalidFromDate.xml");
		//File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_InvalidFromDateSIT.xml");
		FileUtils.writeStringToFile(responseFile, response, Charset.forName("UTF-8"));

		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);	

	}
			
	@Test(groups = { "attestationAPI", "PVAPIRegression","APIRegression", "InvalidToDate" },dataProvider="testDataProvider",priority = 185)
	public void verifyResponseWhenInvalidtoDate(String startDate, String endDate) throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		String response = attestationAPI.getAttestationUsers(startDate, endDate,true);

		Assert.assertEquals(attestationAPI.getResponseCode(), 423);

		String responseFileName = "FailedResponses\\AttestationAPIResponse\\Response_" + format.format(new Date()) + ".xml";
		File responseFile = new File(responseFileName);
		File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_InvalidToDate.xml");
		//File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_InvalidToDateSIT.xml");
		FileUtils.writeStringToFile(responseFile, response, Charset.forName("UTF-8"));

		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);	

	}
	
	
	@Test(groups = { "attestationAPI", "PVAPIRegression","APIRegression", "PopulateDateRange" },dataProvider="testDataProvider",priority = 186)
	public void verifyPopulateDateRange(String startDate, String endDate) throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
		
		String response = attestationAPI.getAttestationUsers("", "", true);

		Assert.assertEquals(attestationAPI.getResponseCode(), 424);
		String responseFileName = "FailedResponses\\AttestationAPIResponse\\Response_" + format.format(new Date()) + ".xml";
		File responseFile = new File(responseFileName);
		File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_PopulateDateRange.xml");
		//File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_PopulateDateRangeSIT.xml");

		FileUtils.writeStringToFile(responseFile, response, Charset.forName("UTF-8"));

		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);		

	}
	
		
	@Test(groups = { "attestationAPI", "PVAPIRegression","APIRegression", "DateRangeExceeds" },dataProvider="testDataProvider",priority = 187)
	public void verifyResponseWhenDateRangeExceeds(String fromDate, String toDate)
			throws IOException {

		String response = attestationAPI.getAttestationUsers(fromDate, toDate, true);
		Assert.assertEquals(attestationAPI.getResponseCode(), 425);
		String responseFileName = "FailedResponses\\AttestationAPIResponse\\Response_" + format.format(new Date()) + ".xml";
		File responseFile = new File(responseFileName);
		File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_DateRangeExceeds.xml");
		//File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_DateRangeExceedsSIT.xml");

		FileUtils.writeStringToFile(responseFile, response, Charset.forName("UTF-8"));

		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);
	}
	
	@Test(groups = { "attestationAPI", "PVAPIRegression","APIRegression", "withinDateRange"},dataProvider="testDataProvider",priority = 188)
	public void verifyResponseWithinDateRange(String fromDate, String toDate)
			throws IOException {

		String response = attestationAPI.getAttestationUsers(fromDate, toDate, true);
		
		Assert.assertEquals(attestationAPI.getResponseCode(), 426);
		String responseFileName = "FailedResponses\\AttestationAPIResponse\\Response_" + format.format(new Date()) + ".xml";
		File responseFile = new File(responseFileName);
		File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_withinDateRange.xml");
		//File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_withinDateRangeSIT.xml");

		FileUtils.writeStringToFile(responseFile, response, Charset.forName("UTF-8"));

		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);
	}

	
	//@Test(groups = { "attestationAPI", "PVAPIRegression","APIRegression", "InternalError" },dataProvider="testDataProvider",priority = 189)
	public void verifyInternalError(String fromDate, String toDate)
			throws IOException {

		String response = attestationAPI.getAttestationUsers(fromDate, toDate, true);
		
		Assert.assertEquals(attestationAPI.getResponseCode(), 500);
		String responseFileName = "FailedResponses\\AttestationAPIResponse\\Response_" + format.format(new Date()) + ".xml";
		File responseFile = new File(responseFileName);
		//File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_InternalError.xml");
         File sourceFile = new File("BaselineResponses\\AttestationAPIResponse\\AttestationAPIResponse_InternalErrorSIT.xml");
		FileUtils.writeStringToFile(responseFile, response, Charset.forName("UTF-8"));

		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);
	}
	@Test(groups={"ProdSmokeSuite","ProdAPISmokeSuite","CICDSmokeSuite","CICDAPISmokeSuite"},priority = 3)
	public void verifyAttestationAPISmokeScenario() throws IOException, SAXException, ParserConfigurationException, URISyntaxException, JSONException {
		
		 String response = attestationAPI.getAttestationUsers(Util.getStartDate(), Util.getEndDate(),false);
		 logger.info("Attestation API Response Code :" +attestationAPI.getResponseCode() );
		Assert.assertEquals(attestationAPI.getResponseCode(), 200 ,"Attestation API Response Code is Not 200 ");
		 logger.info("Attestation API Response :"+ response);
		
		String jsonString = response;
		JSONArray jsonArray= new JSONArray(jsonString);
	
		int i,j;
		for (i = 0; i <= jsonArray.length() - 1; i++) {

			JSONObject jsonObjectProvider = jsonArray.getJSONObject(i);
			String caqhID=jsonObjectProvider.getString("caqh_provider_id");
			if(caqhID.equalsIgnoreCase(Constants.attestedProdCQAHID))
			{
				 logger.info("CAQH ID :"+caqhID+ " Attested On :"+jsonObjectProvider.getString("attest_timestamp") );
			}
		
		}
}
}
