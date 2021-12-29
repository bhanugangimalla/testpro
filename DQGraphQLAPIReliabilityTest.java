/*
 
 * Creation Date: 04/23/18.
 * This test will execute for submitting input json and getting batchID status
 * And the also verfying the extracts by hitting extracts API
 */package graphQLFinal;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.proview.Configuration;
import com.proview.api.APITestBase;
import com.proview.api.DQGRAPHQLAPI;
import com.proview.util.Constants;
import com.proview.util.DBUtil;

public class DQGraphQLAPIReliabilityTest extends APITestBase {
	// protected TestEnvironment testEnv;

	public DQGraphQLAPIReliabilityTest() {

		super(ApiType.DQGRAPHQLAPI);

	}

	private DQGRAPHQLAPI dqGRAPHQLAPI;

	@BeforeMethod(alwaysRun = true)
	public void init() {
		dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, authorizationKey, subscriptionKey);
		// testEnv = new TestEnvironment();
	}

	@Test(groups = "DQGRAPHQLAPIReliablity")

	public void verifyDQGraphQLReliablity() throws Exception {

		String caqhID = (new Configuration().getDQGraphQLCAQHID());

		//String response = dqGRAPHQLAPI.DQGRAPHQLAPIReliablityTest(caqhID);

		// System.out.println(response);

		System.out.println(dqGRAPHQLAPI.getResponseCode());
		Assert.assertEquals(dqGRAPHQLAPI.getResponseCode(), 200);

		File responseFile = new File("resources\\GraphQL_Output.json");
		/*FileUtils.writeStringToFile(responseFile, response, "UTF-8");*/
		
		/*Assert.assertTrue(response.contains("\"caqhProviderId\":" +"\""+caqhID+"\""),"CaqhID is not present in the GraphQL Json response,check if response is valid");
		File ValidateResponseFile = new File("resources\\" + "ValidResponse.txt");
		String validationString = FileUtils.readFileToString(ValidateResponseFile, Charset.forName("UTF-8"));
		Assert.assertTrue(response.contains(validationString),"GraphQL response is not properly structured");*/

	}
	
	
}


