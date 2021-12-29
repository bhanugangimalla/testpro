/*

 * Creation Date: 02/27/19.
 * This Negative test will execute with Authorization implementation before OAuth  
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
import com.proview.Configuration;
import com.proview.api.APITestBase;
import com.proview.util.Constants;
 import com.proview.util.DBUtil;



 public class DQGraphQLAPIOAuthNegTest extends APITestBase {
	 // protected TestEnvironment testEnv;

	 public DQGraphQLAPIOAuthNegTest() {


		 super(ApiType.DQGRAPHQLAPI);

	 }

	 private DQGRAPHQLAPI dqGRAPHQLAPI;

	 @BeforeMethod(alwaysRun = true)
	 public void init() {
		 dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, authorizationKey, subscriptionKey);
	 }

	 ArrayList<DBUtil> attestData; 

	 @Test(groups = "DQGRAPHQLAPI")

	 public void oAuthNegTest() throws Exception {

		 String caqhID = (new Configuration().getDQGraphQLCAQHID());

		 String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetPractitionerValidation(caqhID);

		 // System.out.println(response);

		 System.out.println(dqGRAPHQLAPI.getResponseCode());
		 Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);
		 String errorMessage = "\"errors\":[{\"message\":\"GraphQL.ExecutionError: Not Authorized\",\"extensions\":{\"code\":\"401\"}}";
		 Assert.assertTrue(response.contains(errorMessage),"The expecetd error message is not displayed");
		 
	 }
 }


