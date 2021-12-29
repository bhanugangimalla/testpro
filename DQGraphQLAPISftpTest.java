/*

 * Creation Date: 01/23/18.
 * This test will execute for all the Sftp queries and 
 * check the responses have been generated for eligible caqhIDs in DB
 */package graphQLFinal;

 import java.io.File;
 import java.sql.Timestamp;
 import java.text.SimpleDateFormat;
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


 public class DQGraphQLAPISftpTest extends APITestBase {
	 // protected TestEnvironment testEnv;
	 public DQGraphQLAPISftpTest() {


		 super(ApiType.DQGRAPHQLAPI);

	 }

	 private DQGRAPHQLAPI dqGRAPHQLAPI;

	 @BeforeMethod(alwaysRun = true)
	 
	 public void init() throws Exception {
		 dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, "", subscriptionKey);
		 String token = dqGRAPHQLAPI.tokenGeneration();
		 dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, token, subscriptionKey);
	 }
	 
	 @Test(groups = "Functional", priority = 1)

	 public void sftpQueryValidation() throws Exception {
		 
		 String startWord = "practitionerProfileDetail";
		 	
		 String caqhID = (new Configuration().getDQGraphQLCAQHID());

		 String getPractitionerResponse = dqGRAPHQLAPI.DQGRAPHQLAPIgetPractitionerValidation(caqhID);
		 String getPractitionersResponse = dqGRAPHQLAPI.DQGRAPHQLAPIgetPractitionersValidation(caqhID);
		 getPractitionerResponse = getPractitionerResponse.substring(getPractitionerResponse.indexOf(startWord), getPractitionerResponse.length());
		 getPractitionersResponse = getPractitionersResponse.substring(getPractitionersResponse.indexOf(startWord), getPractitionersResponse.length());
		 System.out.println(getPractitionerResponse);
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
		 new DBUtil().getDbDQSFTPIncrementalFilesUpdateQuery(jobID);
		 Constants.DQCAQHIDs = "";
		 Constants.DQScoringCAQHIDs = "";


		 //check the status of Job Status which needs to be completed
		 System.out.println("Wait Started");
		 Thread.sleep(240000);
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
	 
	 //@Test(groups = "Functional", priority = 2)

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
		 Thread.sleep(240000);
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

	 //@Test(groups = "Functional", priority = 3)
	 //AttestedOn mutation Query for date Range
	 public void sftpAttestedOnDateRange() throws Exception {

		 //query to find  CaqhIDs from authdb
		 //running mutation Query
		 String batchSize = new Configuration().getDQSFTPBatchSize();
		 String batchType = new Configuration().getDQSFTPAttestedOnBatchType();
		 String startDate = new Configuration().getDQSFTPStartDate();
		 String endDate = new Configuration().getDQSFTPEndDate();
		 String poID = new Configuration().getDQPOID();
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPMutation(batchSize,batchType,startDate,endDate);
		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 String jobID = "jobId\":\"";
		 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf("}")).replaceAll("\"", "");
		 System.out.println("jobID:"+jobID);
		 Constants.DQCAQHIDs = "";
		 Constants.DQScoringCAQHIDs = "";

		 //to get all the CaqhIDs
		 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDBAttestedOn(startDate,endDate,null,"SFTP",poID);
		 String CaqhIDs= Constants.DQCAQHIDs.substring(1);
		 System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);


		 //to filter out specialities eligible Caqh IDs with the records present from Auth DB
		 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBAttestedOn(CaqhIDs,null,"SFTP",poID);
		 String CaqhIDsScoring = Constants.DQScoringCAQHIDs.substring(1);
		 System.out.println("Constants.DQScoringCAQHIDs::"+CaqhIDsScoring);

		 String [] eligibleCaqhIDS = new DQUtilities().stringToArray(CaqhIDsScoring);
		 System.out.println("eligibleCaqhIDS"+Arrays.asList(eligibleCaqhIDS));

		 Thread.sleep(150000);
		 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
		 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
		 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
		 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
		 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
		 Arrays.sort(sftpCaqhIDs);
		 Arrays.sort(eligibleCaqhIDS);
		 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
		 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
		 System.out.println("JobID"+jobID);
		 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

	 }

	 //@Test(groups = "Functional", priority = 4)
	 //AttestedOn mutation Query for particular date
	 public void sftpAttestedOnParticularDate() throws Exception {

		 //query to find  CaqhIDs from authdb
		 //running mutation Query
		 String batchSize = new Configuration().getDQSFTPBatchSize();
		 String batchType = new Configuration().getDQSFTPAttestedOnBatchType();
		 String startDate = new Configuration().getDQSFTPStartDate();
		 String poID = new Configuration().getDQPOID();
		 //placing the start date and end date same
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPMutation(batchSize,batchType,startDate,startDate);
		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 String jobID = "jobId\":\"";
		 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf("}")).replaceAll("\"", "");
		 System.out.println("jobID:"+jobID);
		 Constants.DQCAQHIDs = "";
		 Constants.DQScoringCAQHIDs = "";

		 //to get all the CaqhIDs
		 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDBAttestedOnDate(startDate);
		 String CaqhIDs= Constants.DQCAQHIDs.substring(1);
		 System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);


		 //to filter out specialities eligible Caqh IDs with the records present from Auth DB
		 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBAttestedOn(CaqhIDs,null,"SFTP",poID);
		 String CaqhIDsScoring = Constants.DQScoringCAQHIDs.substring(1);
		 System.out.println("Constants.DQScoringCAQHIDs::"+CaqhIDsScoring);

		 String [] eligibleCaqhIDS = new DQUtilities().stringToArray(CaqhIDsScoring);
		 System.out.println("eligibleCaqhIDS"+Arrays.asList(eligibleCaqhIDS));

		 Thread.sleep(120000);
		 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
		 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
		 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
		 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
		 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
		 Arrays.sort(sftpCaqhIDs);
		 Arrays.sort(eligibleCaqhIDS);
		 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
		 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
		 System.out.println("JobID"+jobID);
		 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

	 }

	 //@Test(groups = "Functional", priority = 5)
	 //AttestedOn mutation Query for Current date
	 public void sftpAttestedOnCurrentDate() throws Exception {

		 //query to find  CaqhIDs from authdb
		 //running mutation Query
		 String batchSize = new Configuration().getDQSFTPBatchSize();
		 String batchType = new Configuration().getDQSFTPAttestedOnBatchType();
		 String poID = new Configuration().getDQPOID();
		 //placing the start date and end date same
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPMutationCurrentDate(batchSize,batchType);
		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 String jobID = "jobId\":\"";
		 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf("}")).replaceAll("\"", "");
		 System.out.println("jobID:"+jobID);
		 Constants.DQCAQHIDs = "";
		 Constants.DQScoringCAQHIDs = "";

		 //to get all the CaqhIDs
		 String currentDate = Util.getCurrentDate("yyyy-MM-dd");
		 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDBAttestedOnDate(currentDate);
		 if(Constants.DQCAQHIDs.equals("")) {
			 System.out.println("None Rosted for current date:"+currentDate);
		 }else {
			 String CaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);


			 //to filter out specialities eligible Caqh IDs with the records present from Auth DB
			 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBAttestedOn(CaqhIDs,null,"SFTP",poID);
			 String CaqhIDsScoring = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Constants.DQScoringCAQHIDs::"+CaqhIDsScoring);

			 String [] eligibleCaqhIDS = new DQUtilities().stringToArray(CaqhIDsScoring);
			 System.out.println("eligibleCaqhIDS"+Arrays.asList(eligibleCaqhIDS));

			 //String jobID = "";
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
			 System.out.println("JobID"+jobID);
			 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

		 }}
	 //@Test(groups = "Functional", priority = 6)
	 //RostedOn date Range
	 public void sftpRostedOnDateRange() throws Exception {

		 //query to find  CaqhIDs from authdb
		 //running mutation Query
		 String batchSize = new Configuration().getDQSFTPBatchSize();
		 String batchType = new Configuration().getDQSFTPRosteredOnBatchType();
		 String startDate = new Configuration().getDQSFTPStartDate();
		 String endDate = new Configuration().getDQSFTPEndDate();
		 String dataSource = new Configuration().getDQSFTPALLDATASOURCE();
		 String poID = new Configuration().getDQPOID();
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPMutation(batchSize,batchType,startDate,endDate);
		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 String jobID = "jobId\":\"";
		 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf("}")).replaceAll("\"", "");
		 System.out.println("jobID:"+jobID);
		 Constants.DQCAQHIDs = "";
		 Constants.DQScoringCAQHIDs = "";
		 
		 //to get all the CaqhIDs
		 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBRostredOn(startDate, endDate,dataSource,"SFTP",poID);
		 String CaqhIDs= Constants.DQCAQHIDs.substring(1);
		 System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);


		 //to filter out specialities eligible Caqh IDs with the records present from Ingest DB
		 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(CaqhIDs);
		 String CaqhIDsScoring = Constants.DQScoringCAQHIDs.substring(1);
		 System.out.println("Constants.DQScoringCAQHIDs::"+CaqhIDsScoring);

		 String [] eligibleCaqhIDS = new DQUtilities().stringToArray(CaqhIDsScoring);
		 System.out.println("eligibleCaqhIDS"+Arrays.asList(eligibleCaqhIDS));

		 Thread.sleep(120000);
		 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
		 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
		 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
		 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
		 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
		 Arrays.sort(sftpCaqhIDs);
		 Arrays.sort(eligibleCaqhIDS);
		 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
		 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
		 System.out.println("JobID"+jobID);
		 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

	 }

	 //@Test(groups = "Functional", priority = 7)
	 //RostedOn Particular date
	 public void sftpRostedOnParticularDate() throws Exception {

		 //query to find  CaqhIDs from authdb
		 //running mutation Query
		 String batchSize = new Configuration().getDQSFTPBatchSize();
		 String batchType = new Configuration().getDQSFTPRosteredOnBatchType();
		 String startDate = new Configuration().getDQSFTPEndDate();
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPMutation(batchSize,batchType,startDate,startDate);
		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 String jobID = "jobId\":\"";
		 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf("}")).replaceAll("\"", "");
		 System.out.println("jobID:"+jobID);
		 Constants.DQCAQHIDs = "";
		 Constants.DQScoringCAQHIDs = "";
		 
		 System.out.println("################"+Constants.DQCAQHIDs);
		 //to get all the CaqhIDs
		 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBRostredOnDate(startDate);
		 String CaqhIDs= Constants.DQCAQHIDs.substring(1);
		 System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);


		 //to filter out specialities eligible Caqh IDs with the records present from Ingest DB
		 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(CaqhIDs);
		 String CaqhIDsScoring = Constants.DQScoringCAQHIDs.substring(1);
		 System.out.println("Constants.DQScoringCAQHIDs::"+CaqhIDsScoring);

		 String [] eligibleCaqhIDS = new DQUtilities().stringToArray(CaqhIDsScoring);
		 System.out.println("eligibleCaqhIDS"+Arrays.asList(eligibleCaqhIDS));

		 Thread.sleep(100000);
		 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
		 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
		 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
		 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
		 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
		 Arrays.sort(sftpCaqhIDs);
		 Arrays.sort(eligibleCaqhIDS);
		 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
		 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
		 System.out.println("JobID"+jobID);
		 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

	 }

	 //@Test(groups = "Functional", priority = 8)
	 public void sftpRostedOnCurrentDate() throws Exception {

		 //query to find  CaqhIDs from authdb
		 //running mutation Query
		 String batchSize = new Configuration().getDQSFTPBatchSize();
		 String batchType = new Configuration().getDQSFTPRosteredOnBatchType();
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPMutationCurrentDate(batchSize,batchType);
		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 String jobID = "jobId\":\"";
		 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf("}")).replaceAll("\"", "");
		 System.out.println("jobID:"+jobID);
		 Constants.DQCAQHIDs = "";
		 Constants.DQScoringCAQHIDs = "";

		 //to get all the CaqhIDs
		 String currentDate = Util.getCurrentDate("yyyy-MM-dd");
		 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBRostredOnDate(currentDate);
		 if(Constants.DQCAQHIDs.equals("")) {
			 System.out.println("None Rosted for current date:"+currentDate);
		 }else {
			 String CaqhIDs= Constants.DQCAQHIDs.substring(1);
			 System.out.println("Constants.DQCAQHIDs:: "+CaqhIDs);


			 //to filter out specialities eligible Caqh IDs with the records present from Ingest DB
			 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(CaqhIDs);
			 String CaqhIDsScoring = Constants.DQScoringCAQHIDs.substring(1);
			 System.out.println("Constants.DQScoringCAQHIDs::"+CaqhIDsScoring);

			 String [] eligibleCaqhIDS = new DQUtilities().stringToArray(CaqhIDsScoring);
			 System.out.println("eligibleCaqhIDS"+Arrays.asList(eligibleCaqhIDS));

			 Thread.sleep(100000);
			 String filesDir = new DQSftp().sftpJobBatchFileDownload(jobID);
			 String allUnZippedDir = new DQUtilities().unZipFloders(filesDir);
			 ArrayList<String> sftpFileCaqhIDs = new DQSftp().readFilesCaptureCaqh(allUnZippedDir);
			 String[] sftpCaqhIDs = new String[sftpFileCaqhIDs.size()];
			 sftpCaqhIDs = sftpFileCaqhIDs.toArray(sftpCaqhIDs);
			 Arrays.sort(sftpCaqhIDs);
			 Arrays.sort(eligibleCaqhIDS);
			 System.out.println("SFTPIDS"+Arrays.asList(sftpCaqhIDs));
			 System.out.println("DBIDS"+Arrays.asList(eligibleCaqhIDS));
			 System.out.println("JobID"+jobID);
			 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");
		 }


	 }
	 //AttestedORRosted
	 //@Test(groups = "Functional", priority = 9)
	 public void sftpAttestedOrRostedDateRange() throws Exception {

		 //query to find  CaqhIDs from authdb
		 //running mutation Query
		 String batchSize = new Configuration().getDQSFTPBatchSize();
		 String batchType = new Configuration().getDQSFTPAttestedOrRosteredBatchType();
		 String startDate = new Configuration().getDQSFTPStartDate();
		 String endDate = new Configuration().getDQSFTPEndDate();
		 String poID = new Configuration().getDQPOID();
		 String dataSource = new Configuration().getDQSFTPALLDATASOURCE();
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPISFTPMutation(batchSize,batchType,startDate,endDate);
		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 String jobID = "jobId\":\"";
		 jobID = response.substring(response.indexOf(jobID)+jobID.length(), response.indexOf("}")).replaceAll("\"", "");
		 System.out.println("jobID:"+jobID);
		 Constants.DQCAQHIDs = "";
		 Constants.DQScoringCAQHIDs = "";

		 //to get all the CaqhIDs
		 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBRostredOn(startDate, endDate,dataSource,"SFTP",poID);
		 String rostedCaqhIDs= Constants.DQCAQHIDs.substring(1);
		 System.out.println("Constants.DQCAQHIDs:: "+rostedCaqhIDs);


		 //to filter out specialities eligible Caqh IDs with the records present from Ingest DB
		 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDB(rostedCaqhIDs);
		 String rostedIngestCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
		 System.out.println("Constants.DQScoringCAQHIDs::"+rostedIngestCaqhIDs);

		 String [] rostedEligibleCaqhIDS = new DQUtilities().stringToArray(rostedIngestCaqhIDs);
		 System.out.println("eligibleCaqhIDS"+Arrays.asList(rostedEligibleCaqhIDS));

		 new DBUtil().getDBDQAzureeligibleCaqhIDsIngestDBAttestedOn(startDate,endDate,null,"SFTP",poID);
		 String attestedCaqhIDs= Constants.DQCAQHIDs.substring(1);
		 System.out.println("Constants.DQCAQHIDs:: "+attestedCaqhIDs);


		 //to filter out specialities eligible Caqh IDs with the records present from Auth DB
		 new DBUtil().getdbDQAzureSFTPCaqhIDsAuthDBAttestedOn(attestedCaqhIDs,null,"SFTP",poID);
		 String attestedIngestCaqhIDs = Constants.DQScoringCAQHIDs.substring(1);
		 System.out.println("Constants.DQScoringCAQHIDs::"+attestedIngestCaqhIDs);

		 String [] attestedEligibleCaqhIDS = new DQUtilities().stringToArray(attestedIngestCaqhIDs);
		 System.out.println("eligibleCaqhIDS"+Arrays.asList(attestedEligibleCaqhIDS));


		 String [] duplicateEligibleCaqhID = new DQUtilities().arraysMerge(rostedEligibleCaqhIDS, attestedEligibleCaqhIDS);
		 Set <String> myset  = new HashSet<String>();
		 Collections.addAll(myset,duplicateEligibleCaqhID);
		 String[] eligibleCaqhIDS = new String[myset.size()] ;
		 eligibleCaqhIDS = myset.toArray(eligibleCaqhIDS);

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
		 System.out.println("JobID"+jobID);
		 Assert.assertTrue(Arrays.equals(sftpCaqhIDs, eligibleCaqhIDS),"The CaqhIDs from IngestDb and CaqhIDs from SFTP files arent same");

	 }

 }


