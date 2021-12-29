/*

 * Creation Date: 12/20/18.
 * This test will execute for field level validation of queries
 */package graphQLFinal;

 import java.io.File;
 import java.nio.charset.Charset;
 import java.sql.Timestamp;
 import java.text.SimpleDateFormat;

 import org.apache.commons.io.FileUtils;
 import org.json.JSONArray;
 import org.json.JSONObject;
 import org.testng.Assert;
 import org.testng.annotations.BeforeMethod;
 import org.testng.annotations.Test;
 import org.testng.internal.junit.ArrayAsserts;

import graphQLFinal.DQGRAPHQLAPI;
import graphQLFinal.DQUtilities;
import graphQLFinal.JsontoXmlConvert;
import graphQLFinal.compareAndReport;
import com.proview.Configuration;
import com.proview.api.APITestBase;
import com.proview.util.Constants;
 import com.proview.util.DBUtil;


 public class DQGraphQLAPITest extends APITestBase {
	 // protected TestEnvironment testEnv;

	 public DQGraphQLAPITest() {

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

	 public void verifyDQGraphQLgetPractitionerResponse() throws Exception {

		 String query = "getPractitioner";

		 String caqhID = (new Configuration().getDQGraphQLCAQHID());

		 String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetPractitionerValidation(caqhID);

		 // System.out.println(response);

		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 System.out.println("response:"+response);
		 // Call DB methods to get the DA and Proview JSON files from Azure blob

		 new DBUtil().getByteDAAttestedJsonDataByQuery(caqhID);
		 new DBUtil().getByteProviewAttestedJsonDataByQuery(caqhID);
		 int count = 0;

		 String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Timestamp(System.currentTimeMillis()));

		 String responseFileSt = "resources\\DQ-Resources\\"+timeStamp+"GraphQL_Output.json";
		 String DQProviewAzureJSONFileSt = "resources\\DQ-Resources\\" + Constants.DQProviewAzureJSONFileName;
		 String DQDAAzureJSONFileSt = "resources\\DQ-Resources\\" + Constants.DQDAAzureJSONFileName;

		 File responseFile = new File(responseFileSt);
		 FileUtils.writeStringToFile(responseFile, response, "UTF-8");

		 Constants.DQProviewJSONXMLFile = "resources\\DQ-Resources\\"+timeStamp+"Proview_Output.xml";;
		 Constants.DQDAJSONXMLFile = "resources\\DQ-Resources\\" +timeStamp+"DA_Output.xml";
		 Constants.DQGraphQLResponseJSONXMLFile = "resources\\DQ-Resources\\"+timeStamp+"GraphQL_Output.xml";

		 //converting Json files to XML
		 new JsontoXmlConvert().jsonToXML(responseFileSt, Constants.DQGraphQLResponseJSONXMLFile);
		 Thread.sleep(5000);
		 new JsontoXmlConvert().jsonToXML(DQProviewAzureJSONFileSt, Constants.DQProviewJSONXMLFile);
		 new JsontoXmlConvert().jsonToXML(DQDAAzureJSONFileSt, Constants.DQDAJSONXMLFile);

		 //to compare the values fetched through mapping and comparing it,finally produce excel report
		 new compareAndReport().DQGraphQLAPITestJSON(query,caqhID,count);

	 }
	 @Test(groups = "DQGRAPHQLAPI", priority = 2)

	 public void verifyDQGraphQLgetPractitionersResponse() throws Exception {

		 String query = "getPractitioners";
		 String multipleCAQHs = (new Configuration().getDQGraphQLMultipleCAQHIDs());
		 //String caqhIDs = "\""+multipleCAQHs.replaceAll(",", "\",\"")+"\"";
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetPractitionersValidation(multipleCAQHs);

		 System.out.println(response);

		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 File responseFile = new File("resources\\DQ-Resources\\GraphQL_Output.json");
		 FileUtils.writeStringToFile(responseFile, response, "UTF-8");

		 // Call DB methods to get the DA and Proview JSON files from Azure blob

		 String[] arrCAQHIDs = multipleCAQHs.split(",");
		 String jsonResponse = FileUtils.readFileToString(responseFile, Charset.forName("UTF-8"));
		 int i = 0;
		 int count = 1;


		 for (int ii = 0; ii <=arrCAQHIDs.length - 1; ii++) {

			 new DBUtil().getByteDAAttestedJsonDataByQuery(arrCAQHIDs[ii]);
			 new DBUtil().getByteProviewAttestedJsonDataByQuery(arrCAQHIDs[ii]);

			 String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Timestamp(System.currentTimeMillis()));

			 String responseFileSt = "resources\\DQ-Resources\\"+timeStamp+"GraphQL_Output.json";
			 String DQProviewAzureJSONFileSt = "resources\\DQ-Resources\\" + Constants.DQProviewAzureJSONFileName;
			 String DQDAAzureJSONFileSt = "resources\\DQ-Resources\\" + Constants.DQDAAzureJSONFileName;

			 File responseFileNew = new File(responseFileSt);
			 FileUtils.writeStringToFile(responseFileNew, response, "UTF-8");

			 Constants.DQProviewJSONXMLFile = "resources\\DQ-Resources\\"+timeStamp+"Proview_Output.xml";
			 Constants.DQDAJSONXMLFile = "resources\\DQ-Resources\\" +timeStamp+"DA_Output.xml";
			 Constants.DQGraphQLResponseJSONXMLFile = "resources\\DQ-Resources\\"+timeStamp+"GraphQL_Output.xml";

			 //converting Json files to XML
			 new JsontoXmlConvert().jsonToXML(responseFileSt, Constants.DQGraphQLResponseJSONXMLFile);
			 Thread.sleep(5000);
			 new JsontoXmlConvert().jsonToXML(DQProviewAzureJSONFileSt, Constants.DQProviewJSONXMLFile);
			 new JsontoXmlConvert().jsonToXML(DQDAAzureJSONFileSt, Constants.DQDAJSONXMLFile);

			 //to compare the values fetched through mapping and comparing it,finally produce excel report
			 new compareAndReport().DQGraphQLAPITestJSON(query,arrCAQHIDs[ii],count);
			 count++;

		 }
	 }
	 //Query not supported currently
	 //@Test(groups = "DQGRAPHQLAPI", priority = 3)

	 public void verifyDQGraphQLNPIIDResponse() throws Exception {

		 String query = "NPIID";

		 String caqhID = (new Configuration().getDQGraphQLCAQHID());
		 String npiID = (new Configuration().getDQGraphQLNpiID());

		 String response = dqGRAPHQLAPI.DQGRAPHQLAPINpiIDValidation(npiID);

		 // System.out.println(response);

		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);

		 // Call DB methods to get the DA and Proview JSON files from Azure blob

		 new DBUtil().getByteDAAttestedJsonDataByQuery(caqhID);
		 new DBUtil().getByteProviewAttestedJsonDataByQuery(caqhID);
		 int count = 0;

		 String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Timestamp(System.currentTimeMillis()));

		 String responseFileSt = "resources\\DQ-Resources\\"+timeStamp+"GraphQL_Output.json";
		 String DQProviewAzureJSONFileSt = "resources\\DQ-Resources\\" + Constants.DQProviewAzureJSONFileName;
		 String DQDAAzureJSONFileSt = "resources\\DQ-Resources\\" + Constants.DQDAAzureJSONFileName;

		 File responseFile = new File(responseFileSt);
		 FileUtils.writeStringToFile(responseFile, response, "UTF-8");

		 Constants.DQProviewJSONXMLFile = "resources\\DQ-Resources\\"+timeStamp+"Proview_Output.xml";
		 Constants.DQDAJSONXMLFile = "resources\\DQ-Resources\\" +timeStamp+"DA_Output.xml";
		 Constants.DQGraphQLResponseJSONXMLFile = "resources\\DQ-Resources\\"+timeStamp+"GraphQL_Output.xml";

		 //to retrive all the NPI IDs from the DA Json and place each in the NPI query to check if all the responses are same
		 String[] npiIDs = dqGRAPHQLAPI.npiIDResponses(DQDAAzureJSONFileSt);
		 String npiResponses = "";
		 String actualNpiResponse = "";
		 String responseEndChars = "}]}";
		 for(int i=0;i<npiIDs.length;i++) {

			 npiResponses = dqGRAPHQLAPI.DQGRAPHQLAPINpiIDValidation(npiIDs[i]);
			 //multiple providers could have the same npiIDs so getting the Response message for the required provider 
			 actualNpiResponse = dqGRAPHQLAPI.getMessageBetweenTags(npiResponses,caqhID,responseEndChars);
			 Assert.assertTrue(response.contains(actualNpiResponse),"The response is not same for npiID:"+npiIDs[i]);

		 }

		 //converting Json files to XML
		 new JsontoXmlConvert().jsonToXML(responseFileSt, Constants.DQGraphQLResponseJSONXMLFile);
		 Thread.sleep(5000);
		 new JsontoXmlConvert().jsonToXML(DQProviewAzureJSONFileSt, Constants.DQProviewJSONXMLFile);
		 new JsontoXmlConvert().jsonToXML(DQDAAzureJSONFileSt, Constants.DQDAJSONXMLFile);

		 //to compare the values fetched through mapping and comparing it,finally produce excel report
		 new compareAndReport().DQGraphQLAPITestJSON(query,npiID,count);

	 }
	 //Query not supported currently
	 //@Test(groups = "DQGRAPHQLAPI", priority = 4)

	 public void verifyDQGraphQLTAXIDResponse() throws Exception {

		 String query = "TAXID";

		 String caqhID = (new Configuration().getDQGraphQLCAQHID());
		 String taxID = (new Configuration().getDQGraphQLTaxID());

		 String response = dqGRAPHQLAPI.DQGRAPHQLAPITaxIDValidation(taxID);

		 // System.out.println(response);

		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);

		 // Call DB methods to get the DA and Proview JSON files from Azure blob

		 new DBUtil().getByteDAAttestedJsonDataByQuery(caqhID);
		 new DBUtil().getByteProviewAttestedJsonDataByQuery(caqhID);
		 int count = 0;

		 String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Timestamp(System.currentTimeMillis()));

		 String responseFileSt = "resources\\DQ-Resources\\"+timeStamp+"GraphQL_Output.json";
		 String DQProviewAzureJSONFileSt = "resources\\DQ-Resources\\" + Constants.DQProviewAzureJSONFileName;
		 String DQDAAzureJSONFileSt = "resources\\DQ-Resources\\" + Constants.DQDAAzureJSONFileName;

		 File responseFile = new File(responseFileSt);
		 FileUtils.writeStringToFile(responseFile, response, "UTF-8");

		 Constants.DQProviewJSONXMLFile = "resources\\DQ-Resources\\"+timeStamp+"Proview_Output.xml";;
		 Constants.DQDAJSONXMLFile = "resources\\DQ-Resources\\" +timeStamp+"DA_Output.xml";
		 Constants.DQGraphQLResponseJSONXMLFile = "resources\\DQ-Resources\\"+timeStamp+"GraphQL_Output.xml";

		 //to retrive all the Tax IDs from the DA Json and place each in the Tax query to check if all the responses are same
		 String[] taxIDs = dqGRAPHQLAPI.taxIDResponses(DQDAAzureJSONFileSt);
		 String taxResponses = "";
		 String actualTaxResponse = "";
		 String responseEndChars = "}]}";
		 for(int i=0;i<taxIDs.length;i++) {

			 taxResponses = dqGRAPHQLAPI.DQGRAPHQLAPITaxIDValidation(taxIDs[i]);
			 //multiple providers could have the same taxIDs so getting the Response message for the required provider 
			 actualTaxResponse = dqGRAPHQLAPI.getMessageBetweenTags(taxResponses,caqhID,responseEndChars);
			 Assert.assertTrue(response.contains(actualTaxResponse),"The response is not same for taxID:"+taxIDs[i]);

		 }

		 //converting Json files to XML
		 new JsontoXmlConvert().jsonToXML(responseFileSt, Constants.DQGraphQLResponseJSONXMLFile);
		 Thread.sleep(5000);
		 new JsontoXmlConvert().jsonToXML(DQProviewAzureJSONFileSt, Constants.DQProviewJSONXMLFile);
		 new JsontoXmlConvert().jsonToXML(DQDAAzureJSONFileSt, Constants.DQDAJSONXMLFile);

		 //to compare the values fetched through mapping and comparing it,finally produce excel report
		 new compareAndReport().DQGraphQLAPITestJSON(query,taxID,count);

	 }

	 //Query not supported currently
	 //NPI Query Scenario

	 //@Test(groups = "DQGRAPHQLAPI", priority = 5)


	 public void verifyDQGraphQLNPIIDResponseScenario() throws Exception {


		 String caqhID = (new Configuration().getDQGraphQLCAQHIDNPI());

		 // Change it to Automation Caqh ID
		 String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetPractitionerValidation(caqhID);

		 // System.out.println(response);

		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);

		 // Call DB methods to get the DA and Proview JSON files from Azure blob

		 new DBUtil().getByteDAAttestedJsonDataByQuery(caqhID);
		 String DQDAAzureJSONFileSt = "resources\\DQ-Resources\\" + Constants.DQDAAzureJSONFileName;
		 String daFileContents = new DQUtilities().fileContentToString(DQDAAzureJSONFileSt);

		 String[] addressStatus = {"Rejected Address","Archived Address","Reviewed Address"};
		 String addressStatusKeyword = "\"Provider_Practice_Response_Code\":\"#AddressStatus#\"";
		 String startOfPraticeLocation= "\"Practice_Name\":\"";
		 String tag= "\"NPI_Type_2\":\"";
		 String endChar = "\",";
		 String expectedResponseMsg = "{\"data\":{\"getPractitionerAtLocationByLocationNpi\":[]}}";
		 for(int i=0;i<addressStatus.length;i++) {

			 String addressStat = addressStatusKeyword.replace("#AddressStatus#", addressStatus[i]);

			 String NpiID = dqGRAPHQLAPI.specificIDsExtract(daFileContents, addressStat, startOfPraticeLocation,tag,endChar);
			 String npiResponse = dqGRAPHQLAPI.DQGRAPHQLAPINpiIDValidation(NpiID.toString());
			 Assert.assertEquals(npiResponse,expectedResponseMsg,
					 "Data is showing up for NPIID:"+NpiID.toString());

		 }

	 }

 }



