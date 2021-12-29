/*This class hold all the methods which are been used in DQ Project*/
package graphQLFinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import com.proview.util.Constants;
import com.proview.util.DBUtil;
import com.proview.util.Util;

public class DQGRAPHQLAPI extends DQRequestBase {

	public DQGRAPHQLAPI(String urlStr, String authorizationKey, String subscriptionKey) {
		super(urlStr, authorizationKey, subscriptionKey);

	}

	public String DQGRAPHQLAPIgetPractitionerValidation(String CAQHID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();
		//filename for the related query placed in properties file 
		Constants.DQXmlMappingSheetName = (new Configuration().getDQByCaqhIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByCaqhIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

		String body = new DQUtilities().readQueryFile(queryCompFilePath);

		body = body.replaceAll("#CAQHID#",CAQHID);
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}

	public String DQGRAPHQLAPIgetPractitionersValidation(String CAQHIDs) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		Constants.DQXmlMappingSheetName = (new Configuration().getDQByCaqhIDsQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByCaqhIDsQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;


		String body = new DQUtilities().readQueryFile(queryCompFilePath);
		body = body.replaceAll("#CAQHIDS#",CAQHIDs);

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}

	public String DQGRAPHQLAPINpiIDValidation(String npiID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		Constants.DQXmlMappingSheetName = (new Configuration().getDQByNpiIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByNpiIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

		String body = new DQUtilities().readQueryFile(queryCompFilePath);
		body = body.replaceAll("#NPIID#",npiID);

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}
	public String DQGRAPHQLAPITaxIDValidation(String taxID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		Constants.DQXmlMappingSheetName = (new Configuration().getDQByTaxIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByTaxIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

		String body = new DQUtilities().readQueryFile(queryCompFilePath);
		body = body.replaceAll("#TAXID#",taxID);

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}
	public String DQGRAPHQLAPIgetPractitionerBatches() throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		String body = 	"mutation{\r\n" + 
				" prepareAllRosteredProviders(jobInput:{\r\n" + 
				" pOID: 1042\r\n" + 
				" batchCount:15\r\n" + 
				" cAQHIdSortOrder: ASC\r\n" + 
				" batchType :ALL\r\n" + 
				" })\r\n" + 
				" {\r\n" + 
				" jobId\r\n" + 
				" }\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"";

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}

	public String DQGRAPHQLAPIgetPractitionerAtLocationByTaxIdValidation(String TaxID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		Constants.DQXmlMappingSheetName = (new Configuration().getDQByTaxIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByTaxIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

		String body = new DQUtilities().readQueryFile(queryCompFilePath);
		body = body.replaceAll("#TAXID#",TaxID);


		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}
	//GraphQL response for scoring

	public String DQGRAPHQLAPIgetScoringDataResponse(String CAQHID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		//filename for the related query placed in properties file 
		String queryFileName = (new Configuration().getDQByScoringCaqhIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

		String body = new DQUtilities().readQueryFile(queryCompFilePath);

		body = body.replaceAll("#CAQHID#",CAQHID);
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}
	//SFTP Mutation
	public String DQGRAPHQLAPISFTPMutation(String batchSize,String batchType,String startDate,String endDate) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		//filename for the related query placed in properties file 
		String queryFileName = (new Configuration().getDQBySFTPQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

		String body = new DQUtilities().readQueryFile(queryCompFilePath);

		body = body.replaceAll("#BATCHSIZE#",batchSize);
		body = body.replaceAll("#BATCHTYPE#",batchType);
		body = body.replaceAll("#STARTDATE#",startDate);
		body = body.replaceAll("#ENDDATE#",endDate);
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}
	//SFTP Mutation New
		public String DQGRAPHQLAPISFTPScheduleMutation(String batchSize,String batchType,String startDate,String endDate,String poID,String scheduleType) throws FileNotFoundException, IOException {

			Map<String, String> params = new HashMap<String, String>();

			//filename for the related query placed in properties file 
			String queryFileName = (new Configuration().getDQBySFTPScheduleQueryFile());
			String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

			String body = new DQUtilities().readQueryFile(queryCompFilePath);

			body = body.replaceAll("#BATCHSIZE#",batchSize);
			body = body.replaceAll("#BATCHTYPE#",batchType);
			body = body.replaceAll("#STARTDATE#",startDate);
			body = body.replaceAll("#ENDDATE#",endDate);
			body = body.replaceAll("#POID#",poID);
			body = body.replaceAll("#SCHEDULETYPE#",scheduleType);
			sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

			return getInputStreamAsString();

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
		
	//token Generation
		public String tokenGeneration() throws Exception {

			//running Query to generate token
			String clientId = new Configuration().getDQOAuthApplicationClientId();
			String clientSecret = new Configuration().getDQOAuthApplicationClientSecret();
			String response = DQGRAPHQLAPIGetAccessToken(clientId,clientSecret);
			System.out.println(getResponseCode());
			Assert.assertEquals(getResponseCode(), 200);
			String tokenPattern = "\"accessToken\":\"";
			String endPattern = ",\"expiresOn";
			String token = response.substring(response.indexOf(tokenPattern)+tokenPattern.length(), 
					response.indexOf(endPattern)).replaceAll("\"", "");
			System.out.println("Token:"+token);
			return token;
		}
	//SFTP Multiple Variation Mutation
		public String DQGRAPHQLAPISFTPMultipleVarationMutation(String batchSize,String batchType,String startDate,String endDate) throws FileNotFoundException, IOException {

			Map<String, String> params = new HashMap<String, String>();

			//filename for the related query placed in properties file 
			String queryFileName = (new Configuration().getDQBySFTPMultipleVariationQuery());
			String getPractionierFileName = (new Configuration().getDQBySFTPGetPractitionerQuery());
			String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;
			String queryGetPractionierFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + getPractionierFileName;

			String body = new DQUtilities().readQueryFile(queryCompFilePath);
			String getPractionierBody = new DQUtilities().readQueryFile(queryGetPractionierFilePath);

			body = body.replaceAll("#BATCHSIZE#",batchSize);
			body = body.replaceAll("#BATCHTYPE#",batchType);
			body = body.replaceAll("#STARTDATE#",startDate);
			body = body.replaceAll("#ENDDATE#",endDate);
			body = body.replaceAll("#QUERY#",getPractionierBody);
			sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

			return getInputStreamAsString();

		}
	
	//SFTP Mutation Current date
		public String DQGRAPHQLAPISFTPMutationCurrentDate(String batchSize,String batchType) throws FileNotFoundException, IOException {

			Map<String, String> params = new HashMap<String, String>();

			//filename for the related query placed in properties file 
			String queryFileName = (new Configuration().getDQBySFTPCurrentDateQueryFile());
			String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

			String body = new DQUtilities().readQueryFile(queryCompFilePath);

			body = body.replaceAll("#BATCHSIZE#",batchSize);
			body = body.replaceAll("#BATCHTYPE#",batchType);
			sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

			return getInputStreamAsString();

		}
		public String DQGRAPHQLAPIgetDQByHealthPlanSubmittedValidation(String CAQHID) throws FileNotFoundException, IOException {

			Map<String, String> params = new HashMap<String, String>();
			String poID = new Configuration().getDQPOID();

			String queryFileName = (new Configuration().getDQByHealthPlanSubmittedQueryFile());
			String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

			String body = new DQUtilities().readQueryFile(queryCompFilePath);

			body = body.replaceAll("#CAQHID#",CAQHID);
			body = body.replaceAll("#POID#",poID);
			sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

			return getInputStreamAsString();

		}

	public String DQGRAPHQLAPIgetErrorResponse(String CAQHID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		//filename for the related query placed in properties file 
		Constants.DQXmlMappingSheetName = (new Configuration().getDQByCaqhIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByCaqhIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

		String body = new DQUtilities().readQueryFile(queryCompFilePath);

		body = body.replaceAll("#CAQHID#",CAQHID);
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getErrorStreamString();

	}

	public String DQGRAPHQLAPIgetErrorResponseNPI(String NpiID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		Constants.DQXmlMappingSheetName = (new Configuration().getDQByNpiIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByNpiIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

		String body = new DQUtilities().readQueryFile(queryCompFilePath);
		body = body.replaceAll("#NPIID#",NpiID);

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getErrorStreamString();

	}

	public String DQGRAPHQLAPIgetErrorResponseTAX(String TaxID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		Constants.DQXmlMappingSheetName = (new Configuration().getDQByTaxIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByTaxIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR+ "DQ-Resources\\" + queryFileName;

		String body = new DQUtilities().readQueryFile(queryCompFilePath);
		body = body.replaceAll("#TAXID#",TaxID);


		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getErrorStreamString();

	}


	//Method for retriveing elgible Caqh IDs for scoring data

	public void DQGRAPHQLAPIgetEligibleIDsforScoring() throws Exception {

		new DBUtil().getdbDQAzureCaqhIDsAuthDB();
		System.out.println("Constants.DQCAQHIDs: "+Constants.DQCAQHIDs);

	}
	public String[] npiIDResponses(String fileName) throws FileNotFoundException {
		
		String str = new DQUtilities().fileContentToString(fileName);
		String npiStartTag = "\"npi_type_2\":\"";
		String npiEndTag = "\",";
		String[] npiIdsJson = getTagValues(str,npiStartTag,npiEndTag);
		return npiIdsJson;
	}
	public String[] taxIDResponses(String fileName) throws FileNotFoundException {
		
		String str = new DQUtilities().fileContentToString(fileName);
		String taxStartTag = "\"tax_id\":\"";
		String taxEndTag = "\",";
		String[] taxIdsJson = getTagValues(str,taxStartTag,taxEndTag);
		return taxIdsJson;
	}
	//retrive NPI & Tax IDs for Reviewed, Rejected and Archived Pratice location
	public String specificIDsExtract(String respStr,String addressStatusKeyword,String startOfPraticeLocation,String tag,String endChar) throws FileNotFoundException {
		
		int indexOfaddress = respStr.indexOf(addressStatusKeyword);
		String subStringTillAddress = respStr.substring(0,indexOfaddress);
		int lastIndexOfPraticeLocatio = subStringTillAddress.lastIndexOf(startOfPraticeLocation);
		String praticeLocationDetails = subStringTillAddress.substring(lastIndexOfPraticeLocatio,indexOfaddress);
		String[] NpiID = getTagValues(praticeLocationDetails, tag, endChar);
		
		return NpiID[0];
	}
	
	public void scoringDataComparisonFromResponse(String[] caqhIDs) throws Exception {

		String [] caqhIDss = {"13611371","13611387","13612608","13611372","13611375","13611377","13611383","13611384","13611386","13611388","13611389","13612581","13612576","13612582","13612575","13612744","13612751"};;
			//sit1{"13612742","13611373""13611385",}
			//SIT3{"13616293","13616294","13616295","13616296","13616297","13616298","13616303","13616311"};
		for(int i=0;i<caqhIDss.length;i++) {//caqhIDs.length--s
			String caqhID = caqhIDss[i];//--s
			//caqhID = "13616293";
			new DBUtil().getByteProviewAttestedJsonDataByQuery(caqhID);
			String response = DQGRAPHQLAPIgetScoringDataResponse(caqhID);
			File proviewJson = new File("resources\\DQ-Resources\\" +Constants.DQProviewAzureJSONFileName);
			String str = FileUtils.readFileToString(proviewJson, "UTF-8");
			String eligibleScoringSpecialitiesFile = "Specialities_ScoringData.txt";
			Scanner scanner = new Scanner(new File(Constants.RESOURCES_DIR+ "DQ-Resources\\" +eligibleScoringSpecialitiesFile));
			boolean flag = false;
			while (scanner.hasNextLine()) {
				String specialities = scanner.nextLine();
				if(str.contains("SpecialtyNameIdName\":\""+specialities)) {
					flag = true;
					System.out.println("Speciality available in Json:"+specialities);
				}
			}
			System.out.println("flag:"+flag);
			if(flag==true) {
				System.out.println("in flag loop");
				//get the active location IDs from Json
				String wordJson = "\"CurrentlyPracticingInThisAddressName\":\"Yes\"";
				String nxtWordJson = "\"CaqhUniqueLocationId\":";
				String wordRep = "caqhLocationId";
				String nxtWordRep = ",";
				//String strLocationMsg = "\"caqhLocationId\":\"#LOCATIONID#\"";
				String endLocationMsg = "}}";

				String[] locationIdsJson = getLocationIDsJson(str,wordJson,nxtWordJson);

				String[] locationIdsResponse = getTagValues(response,wordRep,nxtWordRep);
				Assert.assertTrue(Arrays.equals(locationIdsJson, locationIdsResponse),"LocationIDs from Json and DB are not same");
				//Retrieving the scoring parameters through SP in Feature DB

				for(int j=0;j<locationIdsJson.length;j++) {

					String locationIdJson = locationIdsJson[j];
					getScoringDataDB(caqhID,locationIdJson);
					System.out.println(Constants.DQProviderLocationAssessment+Constants.DQIsProviderAtLocationIndicator+
							Constants.DQIsAddressPhoneAlignIndicator+Constants.DQProviderLocationAssessmentOn);
					String strLocationMsg = "\"caqhLocationId\":\"#LOCATIONID#\"";
					strLocationMsg = strLocationMsg.replace("#LOCATIONID#", locationIdJson);
					String locationIDvalidationMsg = getMessageBetweenTags(response, strLocationMsg, endLocationMsg);
					System.out.println("locationIDvalidationMsg:"+locationIDvalidationMsg);

					String practitionerLocationAssessment = "practitionerLocationAssessment\":{\"name\":\""+Constants.DQProviderLocationAssessment;
					String practitionerAtLocationIndicator = "practitionerAtLocationIndicator\":{\"name\":\""+Constants.DQIsProviderAtLocationIndicator;
					String addressPhoneAlignIndicator = "addressPhoneAlignIndicator\":"+Constants.DQIsAddressPhoneAlignIndicator;
					String modifiedAt = "\"modifiedAt\":\""+Constants.DQProviderLocationAssessmentOn;

					// slight varition in output time format 
					modifiedAt = modifiedAt.replaceAll(" ", "T");
					modifiedAt = modifiedAt.substring(0, modifiedAt.indexOf("."));
					System.out.println("compare:"+modifiedAt);
					Assert.assertTrue(locationIDvalidationMsg.contains(practitionerLocationAssessment),"practitionerLocationAssessment value mis-match CaqhID:"+caqhID+" LocationID:"+locationIdJson);
					//to uncomment this once I in indeterminate is clariffied if caps or small
					//softAssert.assertTrue(locationIDvalidationMsg.contains(practitionerAtLocationIndicator),"practitionerAtLocationIndicator value mis-match CaqhID:"+caqhID+" LocationID:"+locationIdJson);
					Assert.assertTrue(locationIDvalidationMsg.contains(addressPhoneAlignIndicator),"addressPhoneAlignIndicator value mis-match CaqhID:"+caqhID+" LocationID:"+locationIdJson);
					//Assert.assertTrue(locationIDvalidationMsg.contains(modifiedAt),"modifiedAt value mis-match CaqhID:"+caqhID+" LocationID:"+locationIdJson);

				}

			}
			else if(flag==false) {
				System.out.println("No eligible speciality has been found in CaqhID:"+caqhID);
				String nonSpecialityResponse = DQGRAPHQLAPIgetScoringDataResponse(caqhID);
				Assert.assertTrue(nonSpecialityResponse.contains("practitionerLocationDirectoryAssessment\":null"), "The Json with CaqhID:"+caqhID+
						"doesnot contain elgible Speciality but scoring data is seen in GraphQL Response" );

			}


			scanner.close();
			softAssert.assertAll();

		}

	}

	//to check if all the jobStatus have been moved to completed status

	public boolean getJobStatus(String jobID) throws Exception {
		boolean status = false;
		int loop =20;
		for(int i=0;i<=loop;i++) {
			Thread.sleep(130000);
			new DBUtil().getdbCaqhIDsJobBatchStatus(jobID);
			String jobStatus = Constants.DQJOBSTATUS.substring(1);
			String [] jobStatusArray = new DQUtilities().stringToArray(jobStatus);
			String [] arrayValueEquals = new String[jobStatusArray.length];
			System.out.println("loop"+loop);
			for(int j=0;j<arrayValueEquals.length;j++) {
				arrayValueEquals[j] = "COMPLETED";			
				}
			System.out.println("jobStatusArray"+Arrays.asList(jobStatusArray));
			System.out.println("arrayValueEquals"+Arrays.asList(arrayValueEquals));
			if(jobStatusArray==arrayValueEquals) 
				status = true;
				break;
			/*else {
				continue;
			}*/
			
			/*for(int j=0;j<jobStatusArray.length;j++) {
				if(jobStatusArray[i].equals("COMPLETED")) {
					status = true;
				}else {
					continue;
				}
				if(status==true) {
					break;
				}
			}*/

		}
		return status;

	}

	public static String jobID = null;
	public String getJobID(String response) {
		String textFrom = "\"jobId\":\"";
		String textTo = "\"}";

		String result = "";

		result =
				response.substring(
						response.indexOf(textFrom) + textFrom.length(),
						response.length());

		// Cut the excessive ending of the text:
		result =
				result.substring(
						0,
						result.indexOf(textTo));

		return result;

	}

	

	
	

	public String readEligibleSpecialitiesFile() {
		String specialities = "";
		try {
			//place in properties file
			String eligibleScoringSpecialitiesFile = "Specialities_ScoringData.txt";
			Scanner scanner = new Scanner(new File(Constants.RESOURCES_DIR+ "DQ-Resources\\" +eligibleScoringSpecialitiesFile));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				specialities = specialities+"'" + line +"'"+ ",";
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return specialities.substring(0,specialities.length()-1);

	}
	public String[] getLocationIDsJson(String str, String word,String nxtWord)  
	{ 
		int counter = 0;

		int index = 0;
		int indexNxt;

		int count = StringUtils.countMatches(str, word);
		String locationIDs []= new String[count+1];
		while(index >= 0) {

			index = str.indexOf(word, index+1);
			indexNxt = str.indexOf(nxtWord, index);
			System.out.println("index:"+index);
			locationIDs[counter] = str.substring(indexNxt+nxtWord.length(),str.indexOf(",", indexNxt)).replaceAll("\"", "");
			counter++;
		}
		return Arrays.copyOf(locationIDs, locationIDs.length-1);
	}
	public String[] getTagValues(String str, String word,String nxtWord)  
	{ 
		int counter2 = 0;

		int index = 0;
		int indexNxt;
		int count = StringUtils.countMatches(str, word);
		//String locationIDs []= new String[count+1];
		String tags []= new String[count+1];
		while(index >= 0) {
			index = str.indexOf(word, index+1);
			indexNxt = str.indexOf(nxtWord, index);
			if(index>=0)
			tags[counter2] = str.substring(index+word.length(), indexNxt).replaceAll("\"", "").replaceAll(",", "").replaceAll(":", "");
			counter2++;
		}
		return Arrays.copyOf(tags, tags.length-1);
		//return tags;
	}
	public String getMessageBetweenTags(String str, String word,String nxtWord)  
	{ 
		int counter = 0;

		int index = 0;
		int indexNxt;
		int count = StringUtils.countMatches(str, word);
		String inBwtMessage = "";
		index = str.indexOf(word, index+1);
		indexNxt = str.indexOf(nxtWord, index);
		inBwtMessage = str.substring(index+word.length(), indexNxt);
		return inBwtMessage;
	}
	private void getScoringDataDB(String caqhID,String locationIdsJson) throws Exception {
		new DBUtil().getDBDQAzureScoringData(caqhID,locationIdsJson);


	}
	
	public static String getSHA(String input) 
    {
	try { 
		input = input.replaceAll("[a-zA-Z]", "");
	    String salt_Val = "MDhzMc5gQjpgkKnZGaiIF7";
	    String valueString = input + salt_Val; 
		
        // Static getInstance method is called with hashing SHA 
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 

        // digest() method called 
        // to calculate message digest of an input 
        // and return array of byte 
        byte[] messageDigest = md.digest(valueString.getBytes()); 

        // Convert byte array into signum representation 
        BigInteger no = new BigInteger(1, messageDigest); 

        // Convert message digest into hex value 
        String hashtext = no.toString(16); 

        while (hashtext.length() < 32) { 
            hashtext = "0" + hashtext; 
        } 

        return hashtext; 
    } 

    // For specifying wrong message digest algorithms 
    catch (NoSuchAlgorithmException e) { 
        System.out.println("Exception thrown"
                           + " for incorrect algorithm: " + e); 

        return null; 
    } 
	
}
	public String stringUntilSpecialChar(String word){
		int i;
		for(i=0;i<word.length();i++) {
			char c = word.charAt(i);
			if(!Character.isLetterOrDigit(c)) {
				if(i==0) {
					//return "ERROR";
					break;
				}else {
				
				break;
				}
			}
			
		}
		return word.substring(0, i);
		
	}

}
