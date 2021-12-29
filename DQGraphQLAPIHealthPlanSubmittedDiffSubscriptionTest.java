/*

 * Creation Date: 11/20/18.
 * This test will execute for submitting input json and getting batchID status
 * And the also verfying the extracts by hitting extracts API
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


 public class DQGraphQLAPIHealthPlanSubmittedDiffSubscriptionTest extends APITestBase {
	 // protected TestEnvironment testEnv;

	 public DQGraphQLAPIHealthPlanSubmittedDiffSubscriptionTest() {


		 super(ApiType.DQGRAPHQLAPI1041AUT);

	 }

	 private DQGRAPHQLAPI dqGRAPHQLAPI;

	 @BeforeMethod(alwaysRun = true)
	 public void init() {
		 dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, authorizationKey, subscriptionKey);
	 }

	 @Test(groups = "DQGRAPHQLAPI")

	 public void healthPlanSubmittedTest() throws Exception {

		 String emptyDataResp = "\"getPractitioner\":[]";
		 String caqhIDs = (new Configuration().getDQGraphQLHealtPlanSubmittedCAQHIDs());
		 String[] caqhIDArray = new DQUtilities().stringToArray(caqhIDs);

		 for (int i=0;i<caqhIDArray.length;i++) {
			 String caqhID = caqhIDArray[i];

			 String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetDQByHealthPlanSubmittedValidation(caqhID);
			
				 softAssert.assertTrue(response.contains(emptyDataResp),"Response is displayed for provider which is not rosted by current POID"+caqhID);
				 
			 }
		 softAssert.assertAll();
		 }
	 



 }


