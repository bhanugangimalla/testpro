package graphQLFinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

import com.proview.Configuration;
import com.proview.api.DQRequestBase;
import com.proview.util.Constants;
import com.proview.util.DBUtil;
import com.proview.util.Util;

public class DQGRAPHQLAPIFinal extends DQRequestBase {

	public DQGRAPHQLAPIFinal(String urlStr, String authorizationKey, String subscriptionKey) {
		super(urlStr, authorizationKey, subscriptionKey);

	}

	public String DQGRAPHQLAPIgetPractitionerValidation(String CAQHID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		//filename for the related query placed in properties file 
		Constants.DQXmlMappingSheetName = (new Configuration().getDQByCaqhIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByCaqhIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR + queryFileName;

		String body = readFile(queryCompFilePath);

		body = body.replaceAll("#CAQHID#",CAQHID);
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}

	public String DQGRAPHQLAPIgetPractitionersValidation(String CAQHIDs) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		Constants.DQXmlMappingSheetName = (new Configuration().getDQByCaqhIDsQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByCaqhIDsQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR + queryFileName;


		String body = readFile(queryCompFilePath);
		body = body.replaceAll("#CAQHIDS#",CAQHIDs);

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}

	public String DQGRAPHQLAPIgetPractitionerAtLocationByLocationNpiValidation(String NpiID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		Constants.DQXmlMappingSheetName = (new Configuration().getDQByNpiIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByNpiIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR + queryFileName;

		String body = readFile(queryCompFilePath);
		body = body.replaceAll("#NPIID#",NpiID);

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
		String queryCompFilePath = Constants.RESOURCES_DIR + queryFileName;

		String body = readFile(queryCompFilePath);
		body = body.replaceAll("#TAXID#",TaxID);


		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}
	//GraphQL response for scoring

	public String DQGRAPHQLAPIgetScoringDataResponse(String CAQHID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		//filename for the related query placed in properties file 
		String queryFileName = (new Configuration().getDQByScoringCaqhIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR + queryFileName;

		String body = readFile(queryCompFilePath);

		body = body.replaceAll("#CAQHID#",CAQHID);
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}
	//SFTP FullRoster
	public String DQGRAPHQLAPISFTPFullRoster(String BatchSize) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		//filename for the related query placed in properties file 
		String queryFileName = (new Configuration().getDQBySFTPFullRosterQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR + queryFileName;

		String body = readFile(queryCompFilePath);

		body = body.replaceAll("#BATCHSIZE#",BatchSize);
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getInputStreamAsString();

	}

	public String DQGRAPHQLAPIgetErrorResponse(String CAQHID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		//filename for the related query placed in properties file 
		Constants.DQXmlMappingSheetName = (new Configuration().getDQByCaqhIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByCaqhIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR + queryFileName;

		String body = readFile(queryCompFilePath);

		body = body.replaceAll("#CAQHID#",CAQHID);
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getErrorStreamString();

	}

	public String DQGRAPHQLAPIgetErrorResponseNPI(String NpiID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		Constants.DQXmlMappingSheetName = (new Configuration().getDQByNpiIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByNpiIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR + queryFileName;

		String body = readFile(queryCompFilePath);
		body = body.replaceAll("#NPIID#",NpiID);

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getErrorStreamString();

	}

	public String DQGRAPHQLAPIgetErrorResponseTAX(String TaxID) throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		Constants.DQXmlMappingSheetName = (new Configuration().getDQByTaxIDQueryXmlMapping());
		String queryFileName = (new Configuration().getDQByTaxIDQueryFile());
		String queryCompFilePath = Constants.RESOURCES_DIR + queryFileName;

		String body = readFile(queryCompFilePath);
		body = body.replaceAll("#TAXID#",TaxID);


		sendRequest(RequestMethod.POST, ContentType.APPLICATION_GRAPHQL, params, body);

		return getErrorStreamString();

	}


	//Method for retriveing elgible Caqh IDs for scoring data

	public void DQGRAPHQLAPIgetEligibleIDsforScoring() throws Exception {

		new DBUtil().getdbDQAzureCaqhIDsAuthDB();
		System.out.println("Constants.DQCAQHIDs: "+Constants.DQCAQHIDs);

	}
	public void scoringDataComparisonFromResponse(String[] caqhIDs) throws Exception {

		String [] caqhIDss = {"13616293","13616294","13616295","13616296","13616297","13616298","13616302","13616303","13616311"};
		for(int i=0;i<caqhIDss.length;i++) {//caqhIDs.length--s
			String caqhID = caqhIDss[i];//--s
			//caqhID = "13616293";
			new DBUtil().getByteProviewAttestedJsonDataByQuery(caqhID);
			String response = DQGRAPHQLAPIgetScoringDataResponse(caqhID);
			File proviewJson = new File("resources\\" +Constants.DQProviewAzureJSONFileName);
			String str = FileUtils.readFileToString(proviewJson, "UTF-8");
			String eligibleScoringSpecialitiesFile = "Specialities_ScoringData.txt";
			Scanner scanner = new Scanner(new File(Constants.RESOURCES_DIR +eligibleScoringSpecialitiesFile));
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
				String strLocationMsg = "\"caqhLocationId\":\"#LOCATIONID#\"";
				String endLocationMsg = "}}";

				String[] locationIdsJson = getLocationIDsJson(str,wordJson,nxtWordJson);

				String[] locationIdsResponse = getLocationIDsResponse(response,wordRep,nxtWordRep);
				Assert.assertTrue(Arrays.equals(locationIdsJson, locationIdsResponse),"LocationIDs from Json and DB are not same");
				//Retrieving the scoring parameters through SP in Feature DB

				for(int j=0;j<locationIdsJson.length;j++) {
					
					String locationIdJson = locationIdsJson[j];
					getScoringDataDB(caqhID,locationIdJson);
					System.out.println(Constants.DQProviderLocationAssessment+Constants.DQIsProviderAtLocationIndicator+
							Constants.DQIsAddressPhoneAlignIndicator+Constants.DQProviderLocationAssessmentOn);
					strLocationMsg = strLocationMsg.replace("#LOCATIONID#", locationIdJson);
					String locationIDvalidationMsg = getLocationIDResponseMessage(response, strLocationMsg, endLocationMsg);
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
					Assert.assertTrue(locationIDvalidationMsg.contains(modifiedAt),"modifiedAt value mis-match CaqhID:"+caqhID+" LocationID:"+locationIdJson);

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

	//reading the query from a file to a string
	private String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader (file));
		String         line = null;
		StringBuilder  stringBuilder = new StringBuilder();
		String         ls = System.getProperty("line.separator");

		try {
			while((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			return stringBuilder.toString();
		} finally {
			reader.close();
		}
	}

	//converting comma seperated String in an Array
	public String[] stringToArray(String commaSepString) throws IOException{

		char delimitChar = ',';

		//to know the count of the commas so that to initialize the length of an array
		int count = numerOfOccurences(commaSepString,delimitChar);


		String[] caqhIds =  commaSepString.split(",");

		String eligileCaqhIDs []= new String[count+1];

		// converting the String separated by commas in array

		for(int i=0; i<caqhIds.length;i++){

			eligileCaqhIDs[i]=caqhIds[i];
		}
		return eligileCaqhIDs;

	}
	private int numerOfOccurences(String strValue,char delimitChar) {
		int count = 0;
		for (int i = 0; i < strValue.length(); i++) {
			if (strValue.charAt(i) == delimitChar) {
				count++;
			}
		}
		return count;

	}

	public String readEligibleSpecialitiesFile() {
		String specialities = "";
		try {
			//place in properties file
			String eligibleScoringSpecialitiesFile = "Specialities_ScoringData.txt";
			Scanner scanner = new Scanner(new File(Constants.RESOURCES_DIR +eligibleScoringSpecialitiesFile));
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
	private String[] getLocationIDsJson(String str, String word,String nxtWord)  
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
	private String[] getLocationIDsResponse(String str, String word,String nxtWord)  
	{ 
		int counter2 = 0;

		int index = 0;
		int indexNxt;
		int count = StringUtils.countMatches(str, word);
		String locationIDs []= new String[count+1];
		while(index >= 0) {
			index = str.indexOf(word, index+1);
			indexNxt = str.indexOf(nxtWord, index);
			locationIDs[counter2] = str.substring(index+word.length(), indexNxt).replaceAll("\"", "").replaceAll(",", "").replaceAll(":", "");
			counter2++;
		}
		return Arrays.copyOf(locationIDs, locationIDs.length-1);
	}
	private String getLocationIDResponseMessage(String str, String word,String nxtWord)  
	{ 
		int counter = 0;

		int index = 0;
		int indexNxt;
		int count = StringUtils.countMatches(str, word);
		String locationIDRepMessage = "";
		index = str.indexOf(word, index+1);
		indexNxt = str.indexOf(nxtWord, index);
		locationIDRepMessage = str.substring(index+word.length(), indexNxt);
		return locationIDRepMessage;
	}
	private void getScoringDataDB(String caqhID,String locationIdsJson) throws Exception {
		new DBUtil().getDBDQAzureScoringData(caqhID,locationIdsJson);


	}

}
