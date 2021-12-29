/*

 * Creation Date: 01/27/19.
 * This Test is used to validate Health Plan Submitted Address from Json to DB
 */package graphQLFinal;

 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileReader;
 import java.nio.file.Files;
 import java.nio.file.Paths;
 import java.sql.Timestamp;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Scanner;

 import org.apache.commons.io.FileUtils;
 import org.apache.poi.util.SystemOutLogger;
 import org.json.JSONObject;
 import org.testng.Assert;
 import org.testng.annotations.BeforeMethod;
 import org.testng.annotations.Test;

import graphQLFinal.DQGRAPHQLAPI;
import graphQLFinal.DQUtilities;
import com.jcraft.jsch.ChannelSftp;
 import com.jcraft.jsch.SftpATTRS;
 import com.proview.Configuration;
import com.proview.api.APITestBase;
import com.proview.util.Constants;
 import com.proview.util.DBUtil;
 import com.proview.util.Util;

 import ch.qos.logback.core.net.SyslogOutputStream;
 import net.minidev.json.parser.JSONParser;


 public class DQGraphQLAPIHealthPlanSubmittedTest extends APITestBase {
	 // protected TestEnvironment testEnv;

	 public DQGraphQLAPIHealthPlanSubmittedTest() {


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

	 public void healthPlanSubmittedTest() throws Exception {


		 String caqhIDs = (new Configuration().getDQGraphQLHealtPlanSubmittedCAQHIDs());
		 String[] caqhIDArray = new DQUtilities().stringToArray(caqhIDs);

		 for (int i=0;i<caqhIDArray.length;i++) {
			 String caqhID = caqhIDArray[i];

			 String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetDQByHealthPlanSubmittedValidation(caqhID);
			 System.out.println("response:"+response);
			 new DBUtil().getByteDAAttestedJsonDataByQuery(caqhID);
			 String DQDAAzureJSONFileSt = "resources\\DQ-Resources\\" + Constants.DQDAAzureJSONFileName;
			 String daFileContents = FileUtils.readFileToString(new File(DQDAAzureJSONFileSt));


			 String locationIDJson = "\"CAQH_Practice_Location_ID\":\"";
			 String locationStatusJson = "\"Provider_Practice_Response_Code\":\"";
			 String standardizationSourceJson = "\"Standardization_Source\":\"";
			 String endWordJson = "\",";
			 String endWord = ",";
			 String endWrd = "\"}";
			 String taxIdNumberResp = "\"taxIdNumber\":\"";
			 String npiNumberResp = "\"npiNumber\":\"";
			 String address1Resp = "\"address1\":\"";
			 String address2Resp = "\"address2\":\"";
			 String cityResp = "\"city\":\"";
			 String stateResp = "\"state\":\"";
			 String zipCodeResp = "\"zipCode\":\"";
			 String countryResp = "\"country\":\"";
			 String addressStandardizedFlagResp = "\"addressStandardizedFlag\":";
			 String endLocationSecRep = "addressStandardizationSource";
			 String[] locationIds = dqGRAPHQLAPI.getTagValues(daFileContents,locationIDJson,endWordJson);
			 String[] locationStatus = dqGRAPHQLAPI.getTagValues(daFileContents,locationStatusJson,endWordJson);
			 String[] standardizationSources = dqGRAPHQLAPI.getTagValues(daFileContents,standardizationSourceJson,endWordJson);
			 

			 for(int j =0;j<locationIds.length;j++) {
				 String locationID = locationIds[j];
				 System.out.println("locationID:"+locationID+"CaqhID:"+caqhID);
				 String standardizedAddressKey = new DBUtil().dbDQPOStandardizedAddressKeyQuery(locationID);
				 String[] addressArray =  new DBUtil().dbDQPOStandardizedAddressQuery(standardizedAddressKey);
				 String Address_Line_1 = addressArray[0];
				 String Address_Line_2 =	addressArray[1];
				 String City = addressArray[2];
				 String State_Key =	addressArray[3];
				 String Zip = addressArray[4];
				 String Country_Key = addressArray[5];
				 String stateDb = new DBUtil().dbDQAddressStateCodeQuery(State_Key);
				 String countryDb = new DBUtil().dbDQAddressCountryCodeQuery(Country_Key);
				 String poSubAddKey = addressArray[6];
				 String[] idDetailsArray =  new DBUtil().dbDQPraticeLocationNPITAXIDsKeyQuery(poSubAddKey);
				 String practiceName = idDetailsArray[1];
				 String taxID = idDetailsArray[2];
				 String npiID = idDetailsArray[3];
				 if(countryDb.equals("United States")) {
					 countryDb = "UNITED_STATES";
				 }else if(countryDb.equals("Default")) {
					 countryDb = "INVALID_REFERENCE_VALUE";
				 }
					 
				 
				 //getting the location section from response message and getting the below required fields
				 String locationIDSection = dqGRAPHQLAPI.getMessageBetweenTags(response,locationID,endLocationSecRep);
				 
				 String[] respTaxID = dqGRAPHQLAPI.getTagValues(locationIDSection,taxIdNumberResp,endWrd);
				 String[] respNpiID = dqGRAPHQLAPI.getTagValues(locationIDSection,npiNumberResp,endWrd);
				 String[] respAdd1 = dqGRAPHQLAPI.getTagValues(locationIDSection,address1Resp,endWordJson);
				 String[] respAdd2 = dqGRAPHQLAPI.getTagValues(locationIDSection,address2Resp,endWordJson);
				 String[] respCity = dqGRAPHQLAPI.getTagValues(locationIDSection,cityResp,endWordJson);
				 String[] respState = dqGRAPHQLAPI.getTagValues(locationIDSection,stateResp,endWordJson);
				 String[] respZip = dqGRAPHQLAPI.getTagValues(locationIDSection,zipCodeResp,endWordJson);
				 String[] respCountry = dqGRAPHQLAPI.getTagValues(locationIDSection,countryResp,endWordJson);
				 String[] respAddressStandardizedFlag = dqGRAPHQLAPI.getTagValues(locationIDSection,addressStandardizedFlagResp,endWord);
				 
				 
				 softAssert.assertEquals(respTaxID[0], taxID,"The Tax ID doesnt match for CaqhID:"+caqhID+"LocationID:"+locationID);
				 softAssert.assertEquals(respNpiID[0], npiID,"The Tax ID doesnt match for CaqhID:"+caqhID+"LocationID:"+locationID);
				 softAssert.assertEquals(respAdd1[0], Address_Line_1,"The Address Line 1 doesnt match for CaqhID:"+caqhID+"LocationID:"+locationID);
				 softAssert.assertEquals(respAdd2[0], Address_Line_2,"The Address Line 2 doesnt match for CaqhID:"+caqhID+"LocationID:"+locationID);
				 softAssert.assertEquals(respCity[0], City,"The City doesnt match for CaqhID:"+caqhID+"LocationID:"+locationID);
				 softAssert.assertEquals(respState[0], stateDb,"The State doesnt match for CaqhID:"+caqhID+"LocationID:"+locationID);
				 softAssert.assertEquals(respZip[0], Zip,"The Zip doesnt match for CaqhID:"+caqhID+"LocationID:"+locationID);
				 softAssert.assertEquals(respCountry[0], countryDb,"The Country doesnt match for CaqhID:"+caqhID+"LocationID:"+locationID);
				 //softAssert.assertEquals(respAddressStandardizedFlag, taxID,"The Tax ID doesnt match for CaqhID:"+caqhID+"LocationID:"+locationID);
				 
				 
				 System.out.println(countryDb);

			 }
		 }softAssert.assertAll();
		 }
	 



 }


