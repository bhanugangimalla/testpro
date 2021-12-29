/*

 * Creation Date: 04/01/19.
 * This test will execute for all the Sftp queries and 
 * check the responses have been generated for eligible caqhIDs in DB
 */package graphQLFinal;

 import java.io.File;
 import java.sql.Timestamp;
 import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Collections;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Set;

 import org.apache.commons.io.FileUtils;
 import org.testng.Assert;
 import org.testng.annotations.BeforeMethod;
 import org.testng.annotations.Test;

import graphQLFinal.DQGRAPHQLAPI;
import graphQLFinal.DQSftp;
import graphQLFinal.DQUtilities;
import com.jcraft.jsch.ChannelSftp;
 import com.jcraft.jsch.SftpATTRS;
 import com.proview.Configuration;
import com.proview.api.APITestBase;
import com.proview.util.Constants;
 import com.proview.util.DBUtil;
 import com.proview.util.Util;

 import ch.qos.logback.core.net.SyslogOutputStream;


 public class DQGraphQLAPISftpIncrementalTest extends APITestBase {
	 // protected TestEnvironment testEnv;
	 public DQGraphQLAPISftpIncrementalTest() {


		 super(ApiType.DQGRAPHQLAPI);

	 }

	 private DQGRAPHQLAPI dqGRAPHQLAPI;

	 @BeforeMethod(alwaysRun = true)
	 public void init() throws Exception {
		 
		 dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, "", subscriptionKey);
		 String token = dqGRAPHQLAPI.tokenGeneration();
		 dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, token, subscriptionKey);
	 }
	 
	 @Test(groups = "DQGRAPHQLAPI", priority = 1)

	 public void sftpQueryValidation() throws Exception {
		 
		 String startWord = "practitionerProfileDetail";
		 	
		 String caqhID = (new Configuration().getDQGraphQLCAQHID());

		 String getPractitionerResponse = dqGRAPHQLAPI.DQGRAPHQLAPIgetPractitionerValidation(caqhID);
		 String getPractitionersResponse = dqGRAPHQLAPI.DQGRAPHQLAPIgetPractitionersValidation(caqhID);
		 getPractitionerResponse = getPractitionerResponse.substring(getPractitionerResponse.indexOf(startWord), getPractitionerResponse.length());
		 getPractitionersResponse = getPractitionersResponse.substring(getPractitionersResponse.indexOf(startWord), getPractitionersResponse.length());
		 System.out.println("1:"+getPractitionerResponse);
		 System.out.println("2:"+getPractitionersResponse);
		 Thread.sleep(3000);
		 Assert.assertEquals(getPractitionersResponse, getPractitionerResponse,"output of getPractitioners &"
		 		+ " getPractitioner query for a single caqhID is not equal CaqhID:"+caqhID);
		 
		 //query to find  CaqhIDs from authdb
		 //running mutation Query
		 String batchSize = new Configuration().getDQSFTPBatchSize();
		 String batchType = new Configuration().getDQSFTPFullRosterBatchType();
		 String startDate = new Configuration().getDQSFTPStartDate();
		 String endDate = new Configuration().getDQSFTPEndDate();
		 String poID = new Configuration().getDQPOID();
		 String scheduleType = new Configuration().getDQSFTPMonthlyScheduleType();
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPScheduleMutation(batchSize,batchType,startDate,endDate,poID,scheduleType);
		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 String jobIDPattern = "jobId\":\"";
		 String jobID = response.substring(response.indexOf(jobIDPattern)+jobIDPattern.length(), response.indexOf(",")).replaceAll("\"", "");
		 System.out.println("jobID:"+jobID);
		 new DBUtil().getDbDQSFTPIncrementalFilesUpdateQuery(jobID);
		 /*String multipleVariationResponse = dqGRAPHQLAPI.DQGRAPHQLAPISFTPMultipleVarationMutation(batchSize,batchType,startDate,endDate);
		 String multipleQueryJobID = multipleVariationResponse.substring(multipleVariationResponse.indexOf(jobIDPattern)+jobIDPattern.length(), multipleVariationResponse.indexOf("}")).replaceAll("\"", "");*/
		 Constants.DQCAQHIDs = "";
		 Constants.DQScoringCAQHIDs = "";


		 //check the status of Job Status which needs to be completed
		 System.out.println("Wait Started");
		 Thread.sleep(90000);
		 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
		 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
		 String sftpJsonResponse = new DQSftp().captureJsonFromFileforCaqhID(allUnZippedDir,caqhID);
		 System.out.println("getprac:"+getPractitionerResponse);
		 System.out.println("sftp:"+sftpJsonResponse);
		 Assert.assertTrue(getPractitionerResponse.contains(sftpJsonResponse), "output of sftpJsonResponse &"
			 		+ " getPractitioner query Response for a single caqhID is not equal CaqhID:"+caqhID);
		 
		 //multiple Varation Query validation
		 
		 /*String filesMultipleDir = new DQSftp().sftpJobBatchFileDownload(multipleQueryJobID);
		 String allUnZippedMultipleDir = new DQUtilities().unZipFloders(filesMultipleDir);
		 String sftpJsonMultipleResponse = new DQSftp().captureJsonFromFileforCaqhID(allUnZippedMultipleDir,caqhID);
		 System.out.println("getprac:"+getPractitionerResponse);
		 System.out.println("sftp:"+sftpJsonMultipleResponse);
		 Assert.assertTrue(getPractitionerResponse.contains(sftpJsonMultipleResponse), "output of sftpJsonMultipleResponse &"
			 		+ " getPractitioner query Response for a single caqhID is not equal CaqhID:"+caqhID);*/

	 }
	 
	 @Test(groups = "DQGRAPHQLAPI", priority = 2)
	 
	 //monthly scenario
	 public void sftpFullRoster() throws Exception {

		 //query to find  CaqhIDs from authdb
		 //running mutation Query
		 String batchSize = new Configuration().getDQSFTPBatchSize();
		 String batchType = new Configuration().getDQSFTPFullRosterBatchType();
		 String startDate = new Configuration().getDQSFTPStartDate();
		 String endDate = new Configuration().getDQSFTPEndDate();
		 String poID = new Configuration().getDQPOID();
		 String scheduleType = new Configuration().getDQSFTPMonthlyScheduleType();
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPScheduleMutation(batchSize,batchType,startDate,endDate,poID,scheduleType);
		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 String jobID = "jobId\":\"";
		 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf(",")).replaceAll("\"", "");
		 System.out.println("jobID:"+jobID);
		 new DBUtil().getDbDQSFTPIncrementalFilesUpdateQuery(jobID);
		 Constants.DQCAQHIDs = "";
		 Constants.DQScoringCAQHIDs = "";

		 //to get all the CaqhIDs
		 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDB("sftp",null,null);
		 String CaqhIDs= Constants.DQCAQHIDs.substring(1);
		 System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);


		 //to filter out eligible Caqh IDs with the records present from Ingest DB
		 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(CaqhIDs);
		 String CaqhIDsScoring = Constants.DQScoringCAQHIDs.substring(1);
		 System.out.println("Constants.DQScoringCAQHIDs::"+CaqhIDsScoring);

		 String [] eligibleCaqhIDS = new DQUtilities().stringToArray(CaqhIDsScoring);
		 System.out.println("eligibleCaqhIDS"+Arrays.asList(eligibleCaqhIDS));

		 //check the status of Job Status which needs to be completed
		 // code has been commented as the functionality has changed
		 //String jobID = "1042_FullRoster_20181221065038";
		 /*boolean jobstatus = dqGRAPHQLAPI.getJobStatus(jobID);
		 System.out.println("JobStatus"+jobstatus);*/
		 Thread.sleep(90000);
		 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
		 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
		 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
		 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
		 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
		 Arrays.sort(sftpCaqhIDs);
		 Arrays.sort(eligibleCaqhIDS);
		 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
		 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
		 //System.out.println("JobID"+jobID);
		 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

	 }

	 //@Test(groups = "DQGRAPHQLAPI", priority = 3)
	 
	 //Daily scenario
	 public void attestedOnDailySftp() throws Exception {

		 //query to find  CaqhIDs from authdb
		 //running mutation Query
		 String batchSize = new Configuration().getDQSFTPBatchSize();
		 String batchType = new Configuration().getDQSFTPAttestedOnBatchType();
		 //LocalDate date = LocalDate.now().minusDays(1);
		 String startDate = LocalDate.now().minusDays(1).toString();
		 String endDate = LocalDate.now().minusDays(1).toString();
		 String poID = new Configuration().getDQPOID();
		 String scheduleType = new Configuration().getDQSFTPDailyScheduleType();
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPScheduleMutation(batchSize,batchType,startDate,endDate,poID,scheduleType);
		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 String jobID = "jobId\":\"";
		 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf(",")).replaceAll("\"", "");
		 System.out.println("jobID:"+jobID);
		 new DBUtil().getDbDQSFTPIncrementalFilesUpdateQuery(jobID);
		 Constants.DQCAQHIDs = "";
		 Constants.DQScoringCAQHIDs = "";


		 
		 //get all CAQHIDs from IngestDb
		 
		 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDBAttestedOn(startDate, endDate,null,"SFTP",poID);
		 String ingestCaqhIDs = Constants.DQCAQHIDs.substring(1);
		 System.out.println("ingestCaqhIDs: "+ingestCaqhIDs);
		 
		 //to filter all the IngestCAQHIDs in Auth DB
		 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBAttestedOn(ingestCaqhIDs,null,"SFTP",poID);
		 String eligibleAuthCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
		 System.out.println("eligibleAuthCaqhIDs: "+eligibleAuthCaqhIDs);
		 
		 String [] eligibleCaqhIDS = new DQUtilities().stringToArray(eligibleAuthCaqhIDs);
		 System.out.println("eligibleCaqhIDS"+Arrays.asList(eligibleCaqhIDS));
		 

		 Thread.sleep(70000);
		 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
		 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
		 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
		 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
		 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
		 Arrays.sort(sftpCaqhIDs);
		 Arrays.sort(eligibleCaqhIDS);
		 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
		 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
		 //System.out.println("JobID"+jobID);
		 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

	 }
	//@Test(groups = "DQGRAPHQLAPI", priority = 4)
	 
		 //Quarterly scenario
		 public void attestedOrRosteredOnQuarterlySftp() throws Exception {

			 //query to find  CaqhIDs from authdb
			 //running mutation Query
			 String batchSize = new Configuration().getDQSFTPBatchSize();
			 String batchType = new Configuration().getDQSFTPAttestedOrRosteredBatchType();
			 String startDate = LocalDate.now().minusMonths(3).minusDays(LocalDate.now().getDayOfMonth()-1).toString();
			 String endDate = LocalDate.now().minusDays(1).toString();
			 String poID = new Configuration().getDQPOID();
			 String dataSource = new Configuration().getDQSFTPALLDATASOURCE();
			 String scheduleType = new Configuration().getDQSFTPQuarterlyScheduleType();
			 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPScheduleMutation(batchSize,batchType,startDate,endDate,poID,scheduleType);
			 System.out.println(dqGRAPHQLAPI.getResponseCode());
			 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
			 String jobID = "jobId\":\"";
			 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf(",")).replaceAll("\"", "");
			 System.out.println("jobID:"+jobID);
			 new DBUtil().getDbDQSFTPIncrementalFilesUpdateQuery(jobID);
			 Constants.DQCAQHIDs = "";
			 Constants.DQScoringCAQHIDs = "";

			 //to get all the CaqhIDs
			 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBRostredOn(startDate, endDate,dataSource,"SFTP",poID);
			 String CaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);


			 //to filter out eligible Caqh IDs with the records present from Ingest DB
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(CaqhIDs);
			 String rosteredCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Constants.DQScoringCAQHIDs::"+rosteredCaqhIDs);
			 
			 Constants.DQCAQHIDs = "";
			 Constants.DQScoringCAQHIDs = "";
			 
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDBAttestedOn(startDate, endDate,null,"SFTP",poID);
			 String attestedCaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+attestedCaqhIDs);
			 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBAttestedOn(attestedCaqhIDs,null,"SFTP",poID);
			 String attestedIngestCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Attested Ingest CaqhIDs::"+attestedIngestCaqhIDs);

			 String [] eligibleRosteredCaqhIDS = new DQUtilities().stringToArray(rosteredCaqhIDs);
			 String [] eligibleAttestedCaqhIDS = new DQUtilities().stringToArray(attestedIngestCaqhIDs);
			 
			 String[] allCaqhIDs = Arrays.copyOf(eligibleRosteredCaqhIDS, eligibleRosteredCaqhIDS.length + eligibleAttestedCaqhIDS.length);
			    System.arraycopy(eligibleAttestedCaqhIDS, 0, allCaqhIDs, eligibleRosteredCaqhIDS.length, eligibleAttestedCaqhIDS.length);
			    
			    List<String> caqhList = Arrays.asList(allCaqhIDs);
			    Set<String> uniqueCaqhIds = new HashSet<String>(caqhList);
			    System.out.println("Unique gas count: " + uniqueCaqhIds.size());
			    //String[] eligibleCaqhIDS = Array.from(uniqueCaqhIds);
			    
			    int n = uniqueCaqhIds.size(); 
		        String eligibleCaqhIDS[] = new String[n]; 
		  
		        int i = 0; 
		        for (String x : uniqueCaqhIds) {
		        	eligibleCaqhIDS[i++] = x; 
		  
		    } 
		        System.out.println("Array:"+Arrays.toString(eligibleCaqhIDS));
	
			    //String[] eligibleCaqhIDS = (String[]) uniqueCaqhIds.toArray();
			 Thread.sleep(80000);
			 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
			 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
			 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
			 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
			 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
			 Arrays.sort(sftpCaqhIDs);
			 Arrays.sort(eligibleCaqhIDS);
			 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
			 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
			 //System.out.println("JobID"+jobID);
			 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

		 }
		@Test(groups = "DQGRAPHQLAPI", priority = 4)
		 
		 //Quarterly scenario
		 public void attestedOrRosteredOrAuthorizedOnQuarterlySftp() throws Exception {

			 //query to find  CaqhIDs from authdb
			 //running mutation Query
			 String batchSize = new Configuration().getDQSFTPBatchSize();
			 String batchType = new Configuration().getDQSFTPAttestedOrRosteredOrAuthorizedBatchType();
			 String startDate = LocalDate.now().minusMonths(3).minusDays(LocalDate.now().getDayOfMonth()-1).toString();
			 String endDate = LocalDate.now().minusDays(1).toString();
			 String poID = new Configuration().getDQPOID();
			 String dataSource = new Configuration().getDQSFTPALLDATASOURCE();
			 String scheduleType = new Configuration().getDQSFTPQuarterlyScheduleType();
			 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPScheduleMutation(batchSize,batchType,startDate,endDate,poID,scheduleType);
			 System.out.println(dqGRAPHQLAPI.getResponseCode());
			 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
			 String jobID = "jobId\":\"";
			 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf(",")).replaceAll("\"", "");
			 System.out.println("jobID:"+jobID);
			 new DBUtil().getDbDQSFTPIncrementalFilesUpdateQuery(jobID);
			 Constants.DQCAQHIDs = "";
			 Constants.DQScoringCAQHIDs = "";

			 //to get all the CaqhIDs
			 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBRostredOn(startDate, endDate,dataSource,"SFTP",poID);
			 String CaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);


			 //to filter out eligible Caqh IDs with the records present from Ingest DB
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(CaqhIDs);
			 String rosteredCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Constants.DQScoringCAQHIDs::"+rosteredCaqhIDs);
			 
			 Constants.DQCAQHIDs = "";
			 Constants.DQScoringCAQHIDs = "";
			 
			 new DBUtil().getdbDQAzureCaqhIDsAuthDBAuthorizedOn(startDate, endDate,dataSource,"SFTP",poID);
			 String authorizedCaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+authorizedCaqhIDs);
			 
			 //to filter out eligible Caqh IDs with the records present from Ingest DB
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(authorizedCaqhIDs);
			 String attestedauthorizedCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Constants.DQScoringCAQHIDs::"+attestedauthorizedCaqhIDs);
			 
			 Constants.DQCAQHIDs = "";
			 Constants.DQScoringCAQHIDs = "";
			 
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDBAttestedOn(startDate, endDate,null,"SFTP",poID);
			 String attestedCaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+attestedCaqhIDs);
			 
			 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBAttestedOn(attestedCaqhIDs,null,"SFTP",poID);
			 String attestedIngestCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Attested Ingest CaqhIDs::"+attestedIngestCaqhIDs);

			 String [] eligibleRosteredCaqhIDS = new DQUtilities().stringToArray(rosteredCaqhIDs);
			 String [] eligibleAttestedCaqhIDS = new DQUtilities().stringToArray(attestedIngestCaqhIDs);
			 String [] eligibleAuthorizedCaqhIDS = new DQUtilities().stringToArray(attestedauthorizedCaqhIDs);
			 
			 String[] rosteredNAttestedCaqhIDs = Arrays.copyOf(eligibleRosteredCaqhIDS, eligibleRosteredCaqhIDS.length + eligibleAttestedCaqhIDS.length);
			    System.arraycopy(eligibleAttestedCaqhIDS, 0, rosteredNAttestedCaqhIDs, eligibleRosteredCaqhIDS.length, eligibleAttestedCaqhIDS.length);
			    
			 String[] allCaqhIDs = Arrays.copyOf(eligibleAuthorizedCaqhIDS, eligibleAuthorizedCaqhIDS.length + rosteredNAttestedCaqhIDs.length);
			    System.arraycopy(rosteredNAttestedCaqhIDs, 0, allCaqhIDs, eligibleAuthorizedCaqhIDS.length, rosteredNAttestedCaqhIDs.length);
 			    
			    List<String> caqhList = Arrays.asList(allCaqhIDs);
			    Set<String> uniqueCaqhIds = new HashSet<String>(caqhList);
			    System.out.println("Unique gas count: " + uniqueCaqhIds.size());
			    //String[] eligibleCaqhIDS = Array.from(uniqueCaqhIds);
			    
			    int n = uniqueCaqhIds.size(); 
		        String eligibleCaqhIDS[] = new String[n]; 
		  
		        int i = 0; 
		        for (String x : uniqueCaqhIds) {
		        	eligibleCaqhIDS[i++] = x; 
		  
		    } 
		        System.out.println("Array:"+Arrays.toString(eligibleCaqhIDS));
	
			    //String[] eligibleCaqhIDS = (String[]) uniqueCaqhIds.toArray();
			 Thread.sleep(80000);
			 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
			 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
			 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
			 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
			 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
			 Arrays.sort(sftpCaqhIDs);
			 Arrays.sort(eligibleCaqhIDS);
			 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
			 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
			 //System.out.println("JobID"+jobID);
			 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

		 }
		 @Test(groups = "DQGRAPHQLAPI", priority = 5)
		 
		 //Weekly scenario
		 public void attestedOrRosteredOrAuthorizedOnWeeklySftp() throws Exception {

			 //query to find  CaqhIDs from authdb
			 //running mutation Query
			 String batchSize = new Configuration().getDQSFTPBatchSize();
			 String batchType = new Configuration().getDQSFTPAttestedOrRosteredOrAuthorizedBatchType();
			 String startDate = LocalDate.now().minusDays(8).toString();
			 String endDate = LocalDate.now().minusDays(1).toString();
			 String poID = new Configuration().getDQPOID();
			 String dataSource = new Configuration().getDQSFTPALLDATASOURCE();
			 String scheduleType = new Configuration().getDQSFTPWeeklyScheduleType();
			 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPScheduleMutation(batchSize,batchType,startDate,endDate,poID,scheduleType);
			 System.out.println(dqGRAPHQLAPI.getResponseCode());
			 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
			 String jobID = "jobId\":\"";
			 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf(",")).replaceAll("\"", "");
			 System.out.println("jobID:"+jobID);
			 new DBUtil().getDbDQSFTPIncrementalFilesUpdateQuery(jobID);
			 Constants.DQCAQHIDs = "";
			 Constants.DQScoringCAQHIDs = "";

			 //to get all the CaqhIDs
			 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBRostredOn(startDate, endDate,dataSource,"SFTP",poID);
			 String CaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);


			 //to filter out eligible Caqh IDs with the records present from Ingest DB
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(CaqhIDs);
			 String rosteredCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Constants.DQScoringCAQHIDs::"+rosteredCaqhIDs);
			 
			 Constants.DQCAQHIDs = "";
			 Constants.DQScoringCAQHIDs = "";
			 
			 new DBUtil().getdbDQAzureCaqhIDsAuthDBAuthorizedOn(startDate, endDate,dataSource,"SFTP",poID);
			 String authorizedCaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+authorizedCaqhIDs);
			 
			 //to filter out eligible Caqh IDs with the records present from Ingest DB
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(authorizedCaqhIDs);
			 String attestedauthorizedCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Constants.DQScoringCAQHIDs::"+attestedauthorizedCaqhIDs);
			 
			 Constants.DQCAQHIDs = "";
			 Constants.DQScoringCAQHIDs = "";
			 
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDBAttestedOn(startDate, endDate,null,"SFTP",poID);
			 String attestedCaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+attestedCaqhIDs);
			 
			 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBAttestedOn(attestedCaqhIDs,null,"SFTP",poID);
			 String attestedIngestCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Attested Ingest CaqhIDs::"+attestedIngestCaqhIDs);

			 String [] eligibleRosteredCaqhIDS = new DQUtilities().stringToArray(rosteredCaqhIDs);
			 String [] eligibleAttestedCaqhIDS = new DQUtilities().stringToArray(attestedIngestCaqhIDs);
			 String [] eligibleAuthorizedCaqhIDS = new DQUtilities().stringToArray(attestedauthorizedCaqhIDs);
			 
			 String[] rosteredNAttestedCaqhIDs = Arrays.copyOf(eligibleRosteredCaqhIDS, eligibleRosteredCaqhIDS.length + eligibleAttestedCaqhIDS.length);
			    System.arraycopy(eligibleAttestedCaqhIDS, 0, rosteredNAttestedCaqhIDs, eligibleRosteredCaqhIDS.length, eligibleAttestedCaqhIDS.length);
			    
			 String[] allCaqhIDs = Arrays.copyOf(eligibleAuthorizedCaqhIDS, eligibleAuthorizedCaqhIDS.length + rosteredNAttestedCaqhIDs.length);
			    System.arraycopy(rosteredNAttestedCaqhIDs, 0, allCaqhIDs, eligibleAuthorizedCaqhIDS.length, rosteredNAttestedCaqhIDs.length);
 			    
			    List<String> caqhList = Arrays.asList(allCaqhIDs);
			    Set<String> uniqueCaqhIds = new HashSet<String>(caqhList);
			    System.out.println("Unique gas count: " + uniqueCaqhIds.size());
			    //String[] eligibleCaqhIDS = Array.from(uniqueCaqhIds);
			    
			    int n = uniqueCaqhIds.size(); 
		        String eligibleCaqhIDS[] = new String[n]; 
		  
		        int i = 0; 
		        for (String x : uniqueCaqhIds) {
		        	eligibleCaqhIDS[i++] = x; 
		  
		    } 
		        System.out.println("Array:"+Arrays.toString(eligibleCaqhIDS));
	
			 Thread.sleep(70000);
			 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
			 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
			 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
			 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
			 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
			 Arrays.sort(sftpCaqhIDs);
			 Arrays.sort(eligibleCaqhIDS);
			 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
			 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
			 //System.out.println("JobID"+jobID);
			 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

		 }
/* //@Test(groups = "DQGRAPHQLAPI", priority = 5)
		 
		 //Weekly scenario
		 public void attestedOrRosteredWeeklySftp() throws Exception {

			 //query to find  CaqhIDs from authdb
			 //running mutation Query
			 String batchSize = new Configuration().getDQSFTPBatchSize();
			 String batchType = new Configuration().getDQSFTPAttestedOrRosteredBatchType();
			 String startDate = LocalDate.now().minusDays(8).toString();
			 String endDate = LocalDate.now().minusDays(1).toString();
			 String poID = new Configuration().getDQPOID();
			 String scheduleType = new Configuration().getDQSFTPWeeklyScheduleType();
			 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPScheduleMutation(batchSize,batchType,startDate,endDate,poID,scheduleType);
			 System.out.println(dqGRAPHQLAPI.getResponseCode());
			 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
			 String jobID = "jobId\":\"";
			 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf(",")).replaceAll("\"", "");
			 System.out.println("jobID:"+jobID);
			 new DBUtil().getDbDQSFTPIncrementalFilesUpdateQuery(jobID);
			 Constants.DQCAQHIDs = "";
			 Constants.DQScoringCAQHIDs = "";

			 //to get all the CaqhIDs
			 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBRostredOn(startDate, endDate);
			 String CaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);


			 //to filter out eligible Caqh IDs with the records present from Ingest DB
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(CaqhIDs);
			 String rosteredCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Constants.DQScoringCAQHIDs::"+rosteredCaqhIDs);
			 
			 Constants.DQCAQHIDs = "";
			 Constants.DQScoringCAQHIDs = "";
			 
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDBAttestedOn(startDate, endDate);
			 String attestedCaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+attestedCaqhIDs);
			 
			 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBAttestedOn(attestedCaqhIDs);
			 String attestedIngestCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Attested Ingest CaqhIDs::"+attestedIngestCaqhIDs);

			 String [] eligibleRosteredCaqhIDS = new DQUtilities().stringToArray(rosteredCaqhIDs);
			 String [] eligibleAttestedCaqhIDS = new DQUtilities().stringToArray(attestedIngestCaqhIDs);
			 
			 String[] allCaqhIDs = Arrays.copyOf(eligibleRosteredCaqhIDS, eligibleRosteredCaqhIDS.length + eligibleAttestedCaqhIDS.length);
			    System.arraycopy(eligibleAttestedCaqhIDS, 0, allCaqhIDs, eligibleRosteredCaqhIDS.length, eligibleAttestedCaqhIDS.length);
			    
			    List<String> caqhList = Arrays.asList(allCaqhIDs);
			    Set<String> uniqueCaqhIds = new HashSet<String>(caqhList);
			    System.out.println("Unique gas count: " + uniqueCaqhIds.size());
			    //String[] eligibleCaqhIDS = Array.from(uniqueCaqhIds);
			    
			    int n = uniqueCaqhIds.size(); 
		        String eligibleCaqhIDS[] = new String[n]; 
		  
		        int i = 0; 
		        for (String x : uniqueCaqhIds) {
		        	eligibleCaqhIDS[i++] = x; 
		  
		    } 
		        System.out.println("Array:"+Arrays.toString(eligibleCaqhIDS));
	
			 Thread.sleep(70000);
			 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
			 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
			 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
			 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
			 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
			 Arrays.sort(sftpCaqhIDs);
			 Arrays.sort(eligibleCaqhIDS);
			 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
			 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
			 //System.out.println("JobID"+jobID);
			 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

		 }
		 */
@Test(groups = "DQGRAPHQLAPI", priority = 6)
		 
		 //Weekly scenario
		 public void authorizedOnWeeklySftp() throws Exception {

			 //query to find  CaqhIDs from authdb
			 //running mutation Query
			 String batchSize = new Configuration().getDQSFTPBatchSize();
			 String batchType = new Configuration().getDQSFTPAuthorizedOnBatchType();
			 String startDate = LocalDate.now().minusDays(8).toString();
			 String endDate = LocalDate.now().minusDays(1).toString();
			 String poID = new Configuration().getDQPOID();
			 String dataSource = new Configuration().getDQSFTPALLDATASOURCE();
			 String scheduleType = new Configuration().getDQSFTPWeeklyScheduleType();
			 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPScheduleMutation(batchSize,batchType,startDate,endDate,poID,scheduleType);
			 System.out.println(dqGRAPHQLAPI.getResponseCode());
			 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
			 String jobID = "jobId\":\"";
			 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf(",")).replaceAll("\"", "");
			 System.out.println("jobID:"+jobID);
			 new DBUtil().getDbDQSFTPIncrementalFilesUpdateQuery(jobID);
			 Constants.DQCAQHIDs = "";
			 Constants.DQScoringCAQHIDs = "";

			 //to get all the CaqhIDs
			 new DBUtil().getdbDQAzureCaqhIDsAuthDBAuthorizedOn(startDate, endDate,dataSource,"SFTP",poID);
			 String authorizedCaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+authorizedCaqhIDs);


			 //to filter out eligible Caqh IDs with the records present from Ingest DB
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(authorizedCaqhIDs);
			 String attestedauthorizedCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Constants.DQScoringCAQHIDs::"+attestedauthorizedCaqhIDs);
			 String [] eligibleCaqhIDS = new DQUtilities().stringToArray(attestedauthorizedCaqhIDs);
			 
			 Thread.sleep(70000);
			 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
			 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
			 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
			 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
			 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
			 Arrays.sort(sftpCaqhIDs);
			 Arrays.sort(eligibleCaqhIDS);
			 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
			 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
			 //System.out.println("JobID"+jobID);
			 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

		 }


 }


