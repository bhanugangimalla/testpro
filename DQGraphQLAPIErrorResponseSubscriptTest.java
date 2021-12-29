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


public class DQGraphQLAPIErrorResponseSubscriptTest extends APITestBase {
	// protected TestEnvironment testEnv;

	public DQGraphQLAPIErrorResponseSubscriptTest() {

		super(ApiType.DQGRAPHQLAPIINVSUB);

	}

	private DQGRAPHQLAPI dqGRAPHQLAPI;

	@BeforeMethod(alwaysRun = true)
	public void init() {
		dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, authorizationKey, subscriptionKey);
	}

	@Test(groups = "DQGRAPHQLAPI", priority = 1)

	// Error Response Test for Invalid Subscription Key
	public void verifyPractitionerInvalidSubscriptionKeynResponse() throws Exception {

		String caqhID = (new Configuration().getDQGraphQLCAQHID());
		
		String response = dqGRAPHQLAPI.DQGRAPHQLAPIgetErrorResponse(caqhID);

		System.out.println(response);

		System.out.println(dqGRAPHQLAPI.getResponseCode());
		Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 401);
		String errorResponseMessage = "Access denied due to invalid subscription key. Make sure to provide a valid key for an active subscription.";
		Assert.assertTrue(response.contains(errorResponseMessage),"The Expected response is not matched with the expected");
	}
	
}


