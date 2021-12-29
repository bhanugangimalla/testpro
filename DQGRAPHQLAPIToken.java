/*This class hold all the methods which are been used in DQ Project*/
package graphQLFinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import graphQLFinal.DQUtilities;
import com.proview.Configuration;
import com.proview.api.DQRequestBase;
import com.proview.api.DQRequestBase.ContentType;
import com.proview.api.DQRequestBase.RequestMethod;
import com.proview.util.Constants;
import com.proview.util.DBUtil;
import com.proview.util.Util;

public class DQGRAPHQLAPIToken extends DQRequestBase {

	public DQGRAPHQLAPIToken(String urlStr, String authorizationKey, String subscriptionKey) {
		super(urlStr, authorizationKey, subscriptionKey);

	}

	//Get Access Token
	public String DQGRAPHQLAPIGetAccessToken(String clientId,String clientSecret) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		//filename for the related query placed in properties file 
		String queryFileName = (new Configuration().getDQDGetAccessTokenQuery());
		String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

		String body = new DQUtilities().readQueryFile(queryCompFilePath);

		body = body.replaceAll("#CLIENTID#",clientId);
		body = body.replaceAll("#CLIENTSECRET#",clientSecret);
		sendRequestToken(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}

	public String tokenGeneration() throws Exception {

		//running Query to generate token
		String clientId = new Configuration().getDQOAuthApplicationClientId();
		String clientSecret = new Configuration().getDQOAuthApplicationClientSecret();
		String response = DQGRAPHQLAPIGetAccessToken(clientId,clientSecret);
		System.out.println(getResponseCode());
		Assert.assertEquals(getResponseCode(), 200);
		String tokenPattern = "\"accessToken\":\"";
		String endPattern = ",\"expiresOn";
		String token = response.substring(response.indexOf(tokenPattern)+tokenPattern.length(), response.indexOf(endPattern)).replaceAll("\"", "");
		System.out.println("Token:"+token);
		return token;
	}
}
