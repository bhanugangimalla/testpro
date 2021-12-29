/*

 * Creation Date: 02/07/19.
 * This test will execute for Aggregate Functionality
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

import com.google.common.collect.ImmutableList;
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


 public class DQGraphQLAPIAggregateTest extends APITestBase {
	 // protected TestEnvironment testEnv;

	 public DQGraphQLAPIAggregateTest() {


		 super(ApiType.DQGRAPHQLAPI);

	 }

	 private DQGRAPHQLAPI dqGRAPHQLAPI;

	 @BeforeMethod(alwaysRun = true)
	 public void init() {
		 dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, authorizationKey, subscriptionKey);
	 }


	 @Test(groups = "DQGRAPHQLAPI", priority = 1)

	 public void aggregateFunctionNPIID() throws Exception {
		 List<String> allNpis = new ArrayList<String>();
		 Constants.DQIngestCAQHIDs = "";
		 new DBUtil().getDBDQAzureCaqhIDsIngestDB();
		 String caqhIDs = Constants.DQIngestCAQHIDs.substring(1);
		 System.out.println("Constants.DQIngestCAQHIDs:: "+caqhIDs);
		 String [] caqhIDsArray = new DQUtilities().stringToArray(caqhIDs);
		 //String [] caqhIDsArray = {"13616293","13616294","13616295","13616296","13616297","13616298","13616302","13616303","13616311"};
		 String [] npiIDsArray = null;
		 //getting all the caqhIDs from Ingest DB  getDBDQAzureAttestedOnIngestDB
		 for(int i=0;i<caqhIDsArray.length;i++) {
			 Constants.DQIngestAttestedOns = "";
			 Constants.DQIngestProviewNpi = "";
			 String npiIDs = "";
			 
			 String caqhID =  caqhIDsArray[i];
			 //System.out.println("CaqhID:"+caqhID);
			 new DBUtil().getDBDQAzureAttestedOnIngestDB(caqhID);
			 
			 String attestedOn = Constants.DQIngestAttestedOns.substring(1);
			 new DBUtil().getDBDQNPIType2IngestDBQuery(attestedOn);
			 System.out.println(Constants.DQIngestProviewNpi);
			 if(Constants.DQIngestProviewNpi.equals("")) {
				 System.out.println("Provider:"+caqhID+"Dosent have NPI ID");
			 }else {
			 npiIDs = Constants.DQIngestProviewNpi.substring(1);
			 npiIDsArray = new DQUtilities().stringToArray(npiIDs);
			 List<String> currentNpis = ImmutableList.copyOf(npiIDsArray);
			 allNpis.addAll(currentNpis);
			 }

		 }
		 System.out.println("All Npis:"+allNpis);
		 Set<String> unique = new HashSet<String>(allNpis);
		 for (String npi : unique) {
			 Constants.DQNPIAggregateCount = "";
			 String expctedaggregateCount= Integer.toString(Collections.frequency(allNpis, npi));
		     System.out.println(npi + ": " + expctedaggregateCount);
		     new DBUtil().getDBDQNPIType2FeatureDB(npi);
		     String actualaggregateCount = Constants.DQNPIAggregateCount;
		     softAssert.assertEquals(actualaggregateCount,expctedaggregateCount,"Aggregate Count mis-match for NPI:"+npi);
		     
		 }

		 softAssert.assertAll();



	 }
	 //@Test(groups = "DQGRAPHQLAPI", priority = 3)

	 public void aggregateFunctionTAXID() throws Exception {
		 List<String> allTaxs = new ArrayList<String>();
		 new DBUtil().getDBDQAzureCaqhIDsIngestDB();
		 String caqhIDs = Constants.DQIngestCAQHIDs.substring(1);
		 System.out.println("Constants.DQIngestCAQHIDs:: "+caqhIDs);
		 String [] caqhIDsArray = new DQUtilities().stringToArray(caqhIDs);
		 String [] taxIDsArray = null;
		 //getting all the caqhIDs from Ingest DB  getDBDQAzureAttestedOnIngestDB
		 for(int i=0;i<caqhIDsArray.length;i++) {
			 Constants.DQIngestAttestedOns = "";
			 Constants.DQIngestProviewTax = "";
			 String taxIDs = "";
			 
			 String caqhID =  caqhIDsArray[i];
			 //System.out.println("CaqhID:"+caqhID);
			 new DBUtil().getDBDQAzureAttestedOnIngestDB(caqhID);
			 
			 String attestedOn = Constants.DQIngestAttestedOns.substring(1);
			 new DBUtil().getDBDQTaxIngestDBQuery(attestedOn);
			 System.out.println(Constants.DQIngestProviewTax);
			 if(Constants.DQIngestProviewTax.equals("")) {
				 System.out.println("Provider:"+caqhID+"Dosent have TAX ID");
			 }else {
				 taxIDs = Constants.DQIngestProviewTax.substring(1);
				 taxIDsArray = new DQUtilities().stringToArray(taxIDs);
			 List<String> currentTaxs = ImmutableList.copyOf(taxIDsArray);
			 allTaxs.addAll(currentTaxs);
			 }

		 }
		 System.out.println("All Taxs:"+allTaxs);
		 Set<String> unique = new HashSet<String>(allTaxs);
		 for (String tax : unique) {
			 Constants.DQTAXAggregateCount = "";
			 String expctedaggregateCount= Integer.toString(Collections.frequency(allTaxs, tax));
		     System.out.println(tax + ": " + expctedaggregateCount);
		     new DBUtil().getDBDQTaxFeatureDB (tax);
		     String actualaggregateCount = Constants.DQTAXAggregateCount;
		     softAssert.assertEquals(actualaggregateCount,expctedaggregateCount,"Aggregate Count mis-match for TAX:"+tax);
		     
		 }

		 softAssert.assertAll();



	 }
	 
	 //@Test(groups = "DQGRAPHQLAPI", priority = 2)

	 public void aggregateFunctionPhoneNumber() throws Exception {
		 List<String> allPhNos = new ArrayList<String>();
		 new DBUtil().getDBDQAzureCaqhIDsIngestDB();
		 String caqhIDs = Constants.DQIngestCAQHIDs.substring(1);
		 System.out.println("Constants.DQIngestCAQHIDs:: "+caqhIDs);
		 String [] caqhIDsArray = new DQUtilities().stringToArray(caqhIDs);
		 String [] phoneNosArray = null;
		 //getting all the caqhIDs from Ingest DB  getDBDQAzureAttestedOnIngestDB
		 for(int i=0;i<caqhIDsArray.length;i++) {
			 Constants.DQIngestAttestedOns = "";
			 Constants.DQIngestProviewPhNo = "";
			 String phNums = "";
			 
			 String caqhID =  caqhIDsArray[i];
			 //System.out.println("CaqhID:"+caqhID);
			 new DBUtil().getDBDQAzureAttestedOnIngestDB(caqhID);
			 
			 String attestedOn = Constants.DQIngestAttestedOns.substring(1);
			 new DBUtil().getDBDQPhoneNoIngestDBQuery(attestedOn);
			 System.out.println(Constants.DQIngestProviewPhNo);
			 if(Constants.DQIngestProviewPhNo.equals("")) {
				 System.out.println("Provider:"+caqhID+"Dosent have PhoneNumber");
			 }else {
				 phNums = Constants.DQIngestProviewPhNo.substring(1);
				 phoneNosArray = new DQUtilities().stringToArray(phNums);
			 List<String> currentPhNos = ImmutableList.copyOf(phoneNosArray);
			 allPhNos.addAll(currentPhNos);
			 }

		 }
		 System.out.println("All PhoneNumber:"+allPhNos);
		 Set<String> unique = new HashSet<String>(allPhNos);
		 for (String phNum : unique) {
			 Constants.DQPhNoAggregateCount = "";
			 String expctedaggregateCount= Integer.toString(Collections.frequency(allPhNos, phNum));
		     System.out.println(phNum + ": " + expctedaggregateCount);
		     new DBUtil().getDBDQPhoneNoFeatureDB(phNum);
		     String actualaggregateCount = Constants.DQPhNoAggregateCount;
		     softAssert.assertEquals(actualaggregateCount,expctedaggregateCount,"Aggregate Count mis-match for PhoneNumber:"+phNum);
		     
		 }

		 softAssert.assertAll();



	 }
 }


