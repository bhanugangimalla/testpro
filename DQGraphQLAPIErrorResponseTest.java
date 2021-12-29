/*
 
 * Creation Date: 11/20/18.
 * This test will execute for submitting input json and getting batchID status
 * And the also verfying the extracts by hitting extracts API
 */package graphQLFinal;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import graphQLFinal.DQGRAPHQLAPI;
import com.proview.Configuration;
import com.proview.api.APITestBase;
import com.proview.util.Constants;
import com.proview.util.DBUtil;


public class DQGraphQLAPIErrorResponseTest extends APITestBase {
	// protected TestEnvironment testEnv;

	public DQGraphQLAPIErrorResponseTest() {

		super(ApiType.DQGRAPHQLAPI);

	}

	private DQGRAPHQLAPI dqGRAPHQLAPI;

	@BeforeMethod(alwaysRun = true)
	public void init() {
		dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, authorizationKey, subscriptionKey);
	}

	@Test(groups = "DQGRAPHQLAPI", priority = 1)

	// Error Response Test for Practitioner not in health plan roster
	public void verifyPractitionerNotInHealthPlanResponse() throws Exception {
		
		new DBUtil().getDbDQHealthPlanRosterErrorQuery();
		String caqhID = Constants.DQCAQHIDs.replace(",", "");
		
		String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetErrorResponse(caqhID);

		 System.out.println(response);

		System.out.println(dqGRAPHQLAPI.getResponseCode());
		Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 400);
		Assert.assertTrue(response.contains("NOT_IN_PLAN_ROSTER"),"The Expected response is not matched with the expected");
	}
	
	@Test(groups = "DQGRAPHQLAPI", priority = 1)
	// Error Response Test for Practitioner has not authorized the health plan
		public void verifyPractitionerNotAuthInHealthPlanResponse() throws Exception {
		
		
		new DBUtil().getDbDQAuthorizeErrorQuery();
			String caqhID = Constants.DQCAQHIDs.replace(",", "");
			
			String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetErrorResponse(caqhID);

			// System.out.println(response);

			System.out.println(dqGRAPHQLAPI.getResponseCode());
			Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 400);
			Assert.assertTrue(response.contains("NOT_AUTHORIZED_BY_PRACTITIONER"),"The Expected response is not matched with the expected");
		}
		
		@Test(groups = "DQGRAPHQLAPI", priority = 1)
		//Error Response Test for Practitioner has no attestation data
		public void verifyPractitionerNoAttestationDataResponse() throws Exception {

			String caqhID = (new Configuration().getDQGraphQLNoAttestationDataCAQHID());
			
			String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetErrorResponse(caqhID);

			// System.out.println(response);

			System.out.println(dqGRAPHQLAPI.getResponseCode());
			Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 400);
			Assert.assertEquals(response, "","The Expected response is not matched with the expected");
		}
		
		@Test(groups = "DQGRAPHQLAPI", priority = 1)
		//Error Response Test for Missing required parameter CAQHID
				public void verifyPractitionerMissingCaqhIDResponse() throws Exception {

					String caqhID = "";
					
					String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetErrorResponse(caqhID);

					 System.out.println("response:"+response);

					System.out.println(dqGRAPHQLAPI.getResponseCode());
					Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 400);
				Assert.assertTrue(response.contains("SYNTAX_ERROR"),"The Expected response is not matched with the expected");
				}
		
		@Test(groups = "DQGRAPHQLAPI", priority = 2)
		//Error Response Test for Missing required parameter CAQHID
				public void verifyPractitionerMissingNpiIDResponse() throws Exception {

					String npiID = "";
					
					String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetErrorResponseNPI(npiID);

					 System.out.println("response:"+response);

					System.out.println(dqGRAPHQLAPI.getResponseCode());
					Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 400);
				Assert.assertTrue(response.contains("SYNTAX_ERROR"),"The Expected response is not matched with the expected");
				}
		
		@Test(groups = "DQGRAPHQLAPI", priority = 3)
		//Error Response Test for Missing required parameter TAX ID
				public void verifyPractitionerMissingTaxIDResponse() throws Exception {

					String taxID = "";
					
					String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetErrorResponseTAX(taxID);

					 System.out.println("response:"+response);

					System.out.println(dqGRAPHQLAPI.getResponseCode());
					Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 400);
				Assert.assertTrue(response.contains("SYNTAX_ERROR"),"The Expected response is not matched with the expected");
				}
		@Test(groups = "DQGRAPHQLAPI", priority = 4)
		//Error Response Test for No data available for the Location NPI
				public void verifyPractitionerNoDataNpiIDResponse() throws Exception {
			
					//providing a non existing Npi ID
					String npiID = "000000000";
					
					String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetErrorResponseNPI(npiID);

					 System.out.println("response:"+response);

					System.out.println(dqGRAPHQLAPI.getResponseCode());
					Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 400);
				Assert.assertTrue(response.contains("NO_DATA_NPITYPE2"),"The Expected response is not matched with the expected");
				}
		
		@Test(groups = "DQGRAPHQLAPI", priority = 5)
		//Error Response Test for No data available for the Location TaxID2
				public void verifyPractitionerNoDataTaxIDResponse() throws Exception {
					
					//providing a non existing Tax ID
					String taxID = "000000000";
					
					String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetErrorResponseTAX(taxID);

					 System.out.println("response:"+response);

					System.out.println(dqGRAPHQLAPI.getResponseCode());
					Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 400);
				Assert.assertTrue(response.contains("NO_DATA_TAXID"),"The Expected response is not matched with the expected");
				}
}


