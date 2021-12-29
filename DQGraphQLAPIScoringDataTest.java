/*
 
 * Creation Date: 01/20/19.
 * This test will execute for validating the scoring data from response to DB
 */package graphQLFinal;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import graphQLFinal.DQGRAPHQLAPI;
import graphQLFinal.DQUtilities;
import com.proview.Configuration;
import com.proview.api.APITestBase;
import com.proview.util.Constants;
import com.proview.util.DBUtil;


public class DQGraphQLAPIScoringDataTest extends APITestBase {
	// protected TestEnvironment testEnv;

	public DQGraphQLAPIScoringDataTest() {


			super(ApiType.DQGRAPHQLAPI);

		}

		private DQGRAPHQLAPI dqGRAPHQLAPI;

		@BeforeMethod(alwaysRun = true)
		public void init() throws Exception {
			dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, "", subscriptionKey);
			 String token = dqGRAPHQLAPI.tokenGeneration();
			 dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, token, subscriptionKey);
		}
	
	@Test(groups = "DQGRAPHQLAPI")
	
	public void scoringData() throws Exception {
		
		//query to find  CaqhIDs from authdb
		
		//to get all the CaqhIDs
		new DBUtil().getdbDQAzureCaqhIDsAuthDB();
		String CaqhIDs= Constants.DQCAQHIDs.substring(1);
		System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);
		
		//to get all the specialities eligible Caqh IDs from local DB
		/*String specialities = dqGRAPHQLAPI.readEligibleSpecialitiesFile();
		new DBUtil().getdbDQCaqhIDsSpecialitiesQuery(CaqhIDs, specialities);
		String eligibleSpecialitiesCaqhIDs = Constants.DQEligibleSpecialitiesCAQHIDs.substring(1);
		System.out.println("Constants.DQEligibleSpecialitiesCAQHIDs::"+eligibleSpecialitiesCaqhIDs);*/
		
		//to filter out specialities eligible Caqh IDs with the records present from Ingest DB
		new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(CaqhIDs);
		String CaqhIDsScoring = Constants.DQScoringCAQHIDs.substring(1);
		System.out.println("Constants.DQScoringCAQHIDs::"+CaqhIDsScoring);
		
		String [] eligibleCaqhIDS = new DQUtilities().stringToArray(CaqhIDsScoring);
		System.out.println("eligibleCaqhIDS"+Arrays.asList(eligibleCaqhIDS));
		
		//downloading the Json files for the eligible CaqhIDs and comparision with the expected Scoring Data
		dqGRAPHQLAPI.scoringDataComparisonFromResponse(eligibleCaqhIDS);
		
	}


	}


