/*

 * Creation Date: 01/28/19.
 * This test will execute for Purging 
 */package graphQLFinal;

 import java.sql.Timestamp;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.GregorianCalendar;

 import org.testng.Assert;
 import org.testng.annotations.BeforeMethod;
 import org.testng.annotations.Test;

import graphQLFinal.DQGRAPHQLAPI;
import graphQLFinal.DQUtilities;

import com.proview.api.APITestBase;
import com.proview.util.Constants;
 import com.proview.util.DBUtil;



 public class DQGraphQLAPIPurgingTest extends APITestBase {
	 // protected TestEnvironment testEnv;

	 public DQGraphQLAPIPurgingTest() {


		 super(ApiType.DQGRAPHQLAPI);

	 }

	 private DQGRAPHQLAPI dqGRAPHQLAPI;

	 @BeforeMethod(alwaysRun = true)
	 public void init() throws Exception {
		 dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, "", subscriptionKey);
		 String token = dqGRAPHQLAPI.tokenGeneration();
		 dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, token, subscriptionKey);
	 }

	 ArrayList<DBUtil> attestData; 

	 @Test(groups = "Functional")

	 public void purgingTest() throws Exception {

		 String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Timestamp(System.currentTimeMillis()));

		 //getting all the caqhIDs from Ingest DB
		 Constants.DQIngestCAQHIDs = "";
		 new DBUtil().getDBDQAzureCaqhIDsIngestDB();
		 String caqhIDs = Constants.DQIngestCAQHIDs.substring(1);
		 String [] caqhIDsArray = new DQUtilities().stringToArray(caqhIDs);

		 //String [] caqhIDsArray = {"13614162"};//13611090
		 System.out.println("caqhIDs"+Arrays.asList(caqhIDsArray));

		 for(int i=0;i<caqhIDsArray.length;i++) {
			 String caqhID = caqhIDsArray[i];
			 //getting the AttestationID and AttestDate from local DB
			 String[] attestData = new DBUtil().getDBDQAttestCaqhIDs(caqhID);

			 System.out.println("CAQHID:"+caqhID);
			 String attestDate = "";
			 String attestId = "";
			 if(attestData[1].length()>0) {
			  attestDate = attestData[0].substring(1);
			  attestId = attestData[1].substring(1);
			 }
			 //loop for DA
			 String src = new DBUtil().getDbDQDAProviderCheckQuery(caqhID);

			 if(src.contains("2")) {
				 String attestIDDA = new DBUtil().getDbDQAttestCaqhIDsDAQuery(caqhID);
				 if(attestIDDA.length()>0) {
				 attestIDDA = attestIDDA.substring(1);
				 }
				 String[] attestDataDAIngest = new DBUtil().getDBDQIngestAttestDACaqhIDs(caqhID);
				 //String attestedDAOn = attestDataDAIngest[0].substring(1);
				 System.out.println("CAQHID:"+caqhID);
				 if(attestDataDAIngest[1].length()>0) {
				 String attestationDAId = attestDataDAIngest[1].substring(1);
				 softAssert.assertTrue(attestIDDA.contains(attestationDAId) ,"The Attest ID doesnt match for DA CaqhID:"+caqhID);
				 }
			 }

			 //getting the AttestationID and AttestDate from Ingest DB
			 String[] attestDataIngest = new DBUtil().getDBDQIngestAttestCaqhIDs(caqhID);
			 
			 String attestedOn = "";
			 String attestationId = "";
			 if(attestDataIngest[0].length()>0) {
			  attestedOn = attestDataIngest[0].substring(1);
			  attestationId = attestDataIngest[1].substring(1);

			 //softAssert.assertEquals(attestDate, attestedOn,"The Attest Date doesnt match for CaqhID:"+caqhID);
			 softAssert.assertTrue(attestId.contains(attestationId),"The Attest ID doesnt match for CaqhID:"+caqhID);
			 
			 String[] arrAttestedOns = attestedOn.split(",");

			 for(int j=0;j<arrAttestedOns.length;j++) {
				 arrAttestedOns[j] = "'"+arrAttestedOns[j]+"'";
			 }

			 String timeStamp = Arrays.toString(arrAttestedOns).replace("[", "").replace("]", "");


			 String npiResult = new DBUtil().getDBDQAttestedOnNPIIngestDBQuery(caqhID, timeStamp);
			 String taxResult = new DBUtil().getDBDQAttestedOnTaxIngestDBQuery(caqhID, timeStamp);
			 String phNoResult = new DBUtil().getDBDQAttestedOnPhNoIngestDBQuery(caqhID, timeStamp);
			 softAssert.assertEquals(npiResult, "","The AttestOn details in NPI Table is not null for CAQHID:"+caqhID);
			 softAssert.assertEquals(taxResult, "","The AttestOn details in Tax Table is not null for CAQHID:"+caqhID);
			 softAssert.assertEquals(phNoResult, "","The AttestOn details in PhoneNo Table is not null for CAQHID:"+caqhID);
			 System.out.println("++"+npiResult);
			 System.out.println("##");

		 } 
		  }
		 softAssert.assertAll();
	 }
 }


