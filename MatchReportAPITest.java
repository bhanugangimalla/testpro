package com.proview.api.tests;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.proview.api.APITestBase;
import com.proview.api.MatchReportAPI;

import com.proview.util.Constants;
import com.proview.util.TestBase;

public class MatchReportAPITest extends APITestBase {
	
	public MatchReportAPITest() {
		super(ApiType.MATCHREPORT);
	}

	MatchReportAPI matchReportAPI;
	String matchReportBaselineDir = "BaselineResponses\\MatchReportAPIResponse\\";

	@BeforeMethod(alwaysRun = true)
	public void init() {
		matchReportAPI = new MatchReportAPI(endPointUrl, authorizationKey);
	}

	@Test(groups = { "matchReportAPI", "DAAPIRegression","APIRegression", "MatchReportRequest" }, dataProvider="testDataProvider",priority = 190)
	public void verifyMatchReport(String SubmissionbatchId) throws IOException {
		String response = matchReportAPI.getMatchReport(SubmissionbatchId, true);
		File sourceMessageFile = new File("BaselineResponses\\"+Constants.environment+ "\\MatchReport_Baseline.txt");
		String responseMessage = matchReportAPI.getInputStreamAsString();
		System.out.println(response);
		System.out.println(matchReportAPI.getResponseCode());
		Assert.assertEquals( responseMessage,FileUtils.readFileToString(sourceMessageFile, Charset.forName("UTF-8")));
		Assert.assertEquals(matchReportAPI.getResponseCode(), 200);
		File sourceFile = new File("BaselineResponses\\MatchReportAPIResponse\\MatchReportResponse.xml");
		File responseFile = writeResponseToAFile("MatchReportResponse", response);
		assertEachLine(sourceFile, responseFile);
		Assert.assertEquals(FileUtils.readFileToString(sourceFile, Charset.forName("UTF-8")), response);
		FileUtils.deleteQuietly(responseFile);

	}

	@Test(groups = { "matchReportAPI", "DAAPIRegression","APIRegression", "InvalidRequest" }, dataProvider="testDataProvider",priority = 191)
	public void verifyMRInvalidRequest(String SubmissionbatchId) throws IOException {
		String response = matchReportAPI.getMatchReport(SubmissionbatchId, true);
		System.out.println(response);
		System.out.println(matchReportAPI.getResponseCode());
		Assert.assertEquals(matchReportAPI.getResponseCode(), 400);
		Assert.assertEquals(response,
				FileUtils.readFileToString(
						new File(matchReportBaselineDir + "MatchReportAPIInvalidRequestResponse.xml"), "UTF-8"),
				"Incorrect response");

	}

	//@Test(groups = { "matchReportAPI", "DAAPIRegression","APIRegression", "verifyAuthentication" }, dataProvider="testDataProvider",priority = 36)
	public void verifyMRAuthentication(String SubmissionbatchId) throws IOException {
		String response = matchReportAPI.getMatchReport(SubmissionbatchId, false);
		System.out.println(response);
		System.out.println(matchReportAPI.getResponseCode());
		Assert.assertEquals(matchReportAPI.getResponseCode(), 401);
		Assert.assertEquals(response,
				FileUtils.readFileToString(
						new File(matchReportBaselineDir + "MatchReportAPIVerifyAuthenticationResponse.xml"), "UTF-8"),
				"Incorrect response");

	}

	@Test(groups = { "matchReportAPI", "DAAPIRegression","APIRegression", "InvalidBatchID" }, dataProvider="testDataProvider",priority = 192)
	public void verifyMRBatchIDStatus(String SubmissionbatchId) throws IOException {
		String response = matchReportAPI.getMatchReport(SubmissionbatchId, false);
		System.out.println(response);
		System.out.println(matchReportAPI.getResponseCode());
		Assert.assertEquals(matchReportAPI.getResponseCode(), 420);
		Assert.assertEquals(response,
				FileUtils.readFileToString(
						new File(matchReportBaselineDir + "MatchReportAPIInvalidBatchIDResponse.xml"), "UTF-8"),
				"Incorrect response");

	}

	//@Test(groups = { "matchReportAPI", "DAAPIRegression","APIRegression", "InvalidOrgId" }, dataProvider="testDataProvider",priority = 193)
	public void verifyMRInvalidOrgId(String SubmissionbatchId, String eof) throws IOException {
		String response = matchReportAPI.getMatchReportPost(SubmissionbatchId, eof);
		// writeResponseToAFile("MatchReportAPIResponses", response);
		System.out.println(response);
		System.out.println(matchReportAPI.getResponseCode());
		Assert.assertEquals(matchReportAPI.getResponseCode(), 421);
		Assert.assertEquals(response, FileUtils
				.readFileToString(new File(matchReportBaselineDir + "MatchReportAPIInvalidOrgIDResponse.xml"), "UTF-8"),
				"Incorrect response");

	}

	@Test(groups = { "matchReportAPI", "DAAPIRegression","APIRegression", "UnmatchedOrgID" }, dataProvider="testDataProvider",priority = 194)
	public void MRUnmatchedOrgId(String SubmissionbatchId) throws IOException {
		String response = matchReportAPI.getMatchReport(SubmissionbatchId, true);
		System.out.println(response);
		System.out.println(matchReportAPI.getResponseCode());
		Assert.assertEquals(matchReportAPI.getResponseCode(), 423);
		Assert.assertEquals(response,
				FileUtils.readFileToString(
						new File(matchReportBaselineDir + "MatchReportAPIUnmatchedOrgIDResponse.xml"), "UTF-8"),
				"Incorrect response");

	}

	@Test(groups = { "matchReportAPI", "DAAPIRegression","APIRegression", "InvalidJson" }, dataProvider="testDataProvider",priority = 195)
	public void verifyMRInvalidJSON(String SubmissionbatchId) throws IOException {
		String response = matchReportAPI.getMatchReport(SubmissionbatchId, true);
		System.out.println(response);
		System.out.println(matchReportAPI.getResponseCode());
		Assert.assertEquals(matchReportAPI.getResponseCode(), 425);
		Assert.assertEquals(response, FileUtils
				.readFileToString(new File(matchReportBaselineDir + "MatchReportAPIInvalidJsonResponse.xml"), "UTF-8"),
				"Incorrect response");
	}

	//@Test(groups = { "matchReportAPI", "DAAPIRegression","APIRegression", "verifyInternalError" }, dataProvider="testDataProvider",priority = 196)
	public void verifyMRInternalError(String SubmissionbatchId) throws IOException {
		String status = matchReportAPI.getMatchReport(SubmissionbatchId, true);
		Assert.assertEquals(matchReportAPI.getResponseCode(), 500);
		Assert.assertEquals(status,
				FileUtils.readFileToString(new File(matchReportBaselineDir + "MatchReportAPIInternalErrorResponse.xml"),
						"UTF-8"),
				"Incorrect response");
	}

	//@Test(groups = {"matchReportAPI", "DAAPIRegression","APIRegression", "ExpiredBatchID"}, dataProvider="testDataProvider",priority = 197)
	public void verifyMRExpiredBatchID(String SubmissionbatchId) throws IOException {
		String response = matchReportAPI.getMatchReport(SubmissionbatchId, true);
		System.out.println(response);
		System.out.println(matchReportAPI.getResponseCode());
		Assert.assertEquals(matchReportAPI.getResponseCode(), 422);
		Assert.assertEquals(response,
				FileUtils.readFileToString(
						new File(matchReportBaselineDir + "MatchReportAPIExpiredBatchIDResponse.xml"), "UTF-8"),
				"Incorrect response");
	}
	
	 //Verifies successful response 
	@Test(groups = {"ProdSmokeSuite", "ProdAPISmokeSuite"}, dataProvider="testDataProvider",priority = 190)
	public void verifyMatchReportForSmokeTest(String SubmissionbatchId) throws IOException {
		String response = matchReportAPI.getMatchReport(SubmissionbatchId, true);
		logger.info(response);
		logger.info(matchReportAPI.getResponseCode());
		Assert.assertEquals(matchReportAPI.getResponseCode(), 200);
		
	}

}
