package com.proview.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.proview.util.Constants;
import com.proview.util.DBUtil;
import com.proview.util.TestBase;
import com.proview.util.Util;

public class APITestBase extends TestBase {

	protected String endPointUrl;
	protected String endPointUrl1;
	protected String authorizationKey;
	protected String subscriptionKey;
	protected ApiType apiType;
	protected Logger logger;
	protected String PRGenCount;
	protected DBUtil dbUtil;
	protected SoftAssert softAssert;
	protected String caqhid;

	public enum ApiType {
		
		DQGRAPHQLAPIINVAUT,DQGRAPHQLAPI1041AUT, DQGRAPHQLAPIINVSUB, DQGRAPHQLAPI, DOCAPI,DOCAPIDocList,DOCAPIDocDownload,DOCAPIAsyncReq, PVROSTER, PVROSTERSTATUS,ROSTERSTATUS, PSV, CRED, CREDV3,CREDV4,CREDV5, ATTESTATION, MATCHREPORT, DAROSTER, DAROSTERSTATUS, DADEROSTER, PVDEROSTER, DAExtract, AddPL, DAApi, DAEXTRACTE2E,PVINDIVIDUALROASTER,PVINDIVIDUALDEROSTER,DAINDIVIDUALROSTER,DAINDIVIDUALDEROSTER,PROVIEWAPI,PSVBatchService,
		CVOAPI,DAExtractV2,DAExtractV3,DAExtractV4,DAExtractV5,DAExtractV6,DAExtractV7,DAExtractV7_1,DAExtractV8,DAExtractV9,DAExtractV10,DAExtractV11,DAExtractV12,DAExtractV13,DAExtractV14,DAExtractV15,DAExtractV16,DAExtractV17,DMF,DAATTESTAPI, EPMMAFFILIATION, EMS, GENRICEMS,VERIFIDEROSTER,SMARTYSTREETAPI,SMARTYSTREETMULTIPLEAPI;
	}

	public APITestBase(ApiType apiType) {
		this.apiType = apiType;
		getConfiguration();
		softAssert = new SoftAssert();
		logger = getLogger();
	}

	public void getConfiguration() {

		String propertiesFile = System.getenv("environment");
		File file;
		if (propertiesFile == null) {
			file = new File("Data//SIT4" + ".properties");
			Constants.environment = "SIT4";
			// throw new RuntimeException("environment variable is not set");
		} else {

			file = new File("Data" + File.separator + propertiesFile + ".properties");
			Constants.environment = propertiesFile;
		}

		if (!file.exists()) {
			throw new RuntimeException("Properties file doesn't exist at given path: " + file.getAbsolutePath());
		}

		try {

			Properties properties = new Properties();
			properties.load(new FileInputStream(file));
			PRGenCount = properties.getProperty("NoOfProvidersRequired");

			switch (apiType) {
			case PVROSTER:
				endPointUrl = properties.getProperty("PVRosterAPIURL", null);
				authorizationKey = properties.getProperty("PVRosterAPIAuthorizationKey", null);
				break;

			case PVROSTERSTATUS:
				endPointUrl = properties.getProperty("PVRosterStatusAPIURL", null);
				authorizationKey = properties.getProperty("PVRosterStatusAPIAuthorizationKey", null);
				break;
			
			case ROSTERSTATUS:
				endPointUrl = properties.getProperty("RosterStatusAPIURL", null);
				authorizationKey = properties.getProperty("RosterStatusAPIAuthorizationKey", null);
				break;

			case PSV:
				endPointUrl = properties.getProperty("PSVRealTimeAPIURL", null);
				authorizationKey = properties.getProperty("PSVRealTimeAPIAuthorizationKey", null);
				break;

			case CRED:
				endPointUrl = properties.getProperty("CredAPIURL", null);
				authorizationKey = properties.getProperty("CredAPIAuthorizationKey", null);
				break;

			case CREDV3:
				endPointUrl = properties.getProperty("CredV3APIURL", null);
				authorizationKey = properties.getProperty("CredAPIAuthorizationKey", null);
				break;	
			
			case CREDV4:
				endPointUrl = properties.getProperty("CredV4APIURL", null);
				authorizationKey = properties.getProperty("CredAPIAuthorizationKey", null);
				break;	
				
			case CREDV5:
				endPointUrl = properties.getProperty("CredV5APIURL", null);
				authorizationKey = properties.getProperty("CredAPIAuthorizationKey", null);
				break;
				
			case ATTESTATION:
				endPointUrl = properties.getProperty("AttestationAPIURL", null);
				authorizationKey = properties.getProperty("AttestationAPIAuthorizationKey", null);
				break;

			case MATCHREPORT:
				endPointUrl = properties.getProperty("MatchReportAPIURL", null);
				// authorizationKey =
				// properties.getProperty("MatchReportAPIAuthorizationKey",
				// null);
				authorizationKey = encodeAuthorization(properties.getProperty("MatchReportAPIAuthorizationKey", null));
				break;

			case DAROSTER:
				endPointUrl = properties.getProperty("DARosterAPIURL", null);
				// authorizationKey =
				// properties.getProperty("DARosterAPIAuthorizationKey", null);
				authorizationKey = properties.getProperty("DARosterAPIAuthorizationKey", null);
				break;

			case DAROSTERSTATUS:
				endPointUrl = properties.getProperty("DARosterStatusAPIURL", null);
				// authorizationKey =
				// properties.getProperty("DARosterAPIAuthorizationKey", null);
				authorizationKey = properties.getProperty("DARosterAPIAuthorizationKey", null);
				break;

			case DADEROSTER:
				endPointUrl = properties.getProperty("DADeRosterAPIURL", null);
				authorizationKey = properties.getProperty("DARosterAPIAuthorizationKey", null);
				// authorizationKey =
				// encodeAuthorization(properties.getProperty("DARosterAPIAuthorizationKey1",
				// null));
				break;

			case PVDEROSTER:
				endPointUrl = properties.getProperty("PVDeRosterAPIURL", null);
				// authorizationKey =
				// properties.getProperty("PVRosterAPIAuthorizationKey", null);
				authorizationKey = encodeAuthorization(properties.getProperty("PVRosterAPIAuthorizationKey1", null));
				break;

			case DAExtract:
				endPointUrl = properties.getProperty("DAExtractAPIURL", null);
				// authorizationKey =
				// properties.getProperty("CredAPIAuthorizationKey", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV2:
				endPointUrl = properties.getProperty("DAExtractV2APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV3:
				endPointUrl = properties.getProperty("DAExtractV3APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV4:
				endPointUrl = properties.getProperty("DAExtractV4APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV5:
				endPointUrl = properties.getProperty("DAExtractV5APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV6:
				endPointUrl = properties.getProperty("DAExtractV6APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV7:
				endPointUrl = properties.getProperty("DAExtractV7APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV7_1:
				endPointUrl = properties.getProperty("DAExtractV7_1APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV8:
				endPointUrl = properties.getProperty("DAExtractV8APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV9:
				endPointUrl = properties.getProperty("DAExtractV9APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV10:
				endPointUrl = properties.getProperty("DAExtractV10APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV11:
				endPointUrl = properties.getProperty("DAExtractV11APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV12:
				endPointUrl = properties.getProperty("DAExtractV12APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV13:
				endPointUrl = properties.getProperty("DAExtractV13APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV14:
				endPointUrl = properties.getProperty("DAExtractV14APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAExtractV15:
				endPointUrl = properties.getProperty("DAExtractV15APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
				
			case DAExtractV16:
				endPointUrl = properties.getProperty("DAExtractV16APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
				
			case DAExtractV17:
				endPointUrl = properties.getProperty("DAExtractV17APIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
				
			case SMARTYSTREETAPI:
				endPointUrl=properties.getProperty("SmartyStreetURL",null);
				authorizationKey = "";
				break;
				
			case SMARTYSTREETMULTIPLEAPI:
				endPointUrl=properties.getProperty("SmartyStreetMultipleURL",null);
				authorizationKey = "";
				break;		
								
			case DAATTESTAPI:
				endPointUrl = properties.getProperty("DAAttestAPIURL", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAAttestAuthorization", null));
				break;
				
			case AddPL:
				endPointUrl = properties.getProperty("DARosterAddPLAPIURL", null);
				// authorizationKey =
				// properties.getProperty("PSVRealTimeAPIAuthorizationKey",
				// null);
				authorizationKey = encodeAuthorization(
						properties.getProperty("DARosterAddPLAPIAuthorizationKey1", null));
				break;
			case DAApi:
				endPointUrl = properties.getProperty("DARosterAPIURL2O", null);

				authorizationKey = encodeAuthorization(properties.getProperty("DARosterAPIAuthorizationKey2O", null));
				break;
			case DAEXTRACTE2E:
				endPointUrl = properties.getProperty("DAExtractAPIURL", null);

				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization2O", null));
				break;
			case PVINDIVIDUALROASTER:
				endPointUrl = properties.getProperty("PVIndividualRosterAPIURL", null);
				authorizationKey = properties.getProperty("PVRosterAPIAuthorizationKey", null);
				break;
			case PVINDIVIDUALDEROSTER:
				endPointUrl = properties.getProperty("PVIndividualDeRosterAPIURL", null);
				//authorizationKey = properties.getProperty("PVRosterAPIAuthorizationKey", null);
				authorizationKey = encodeAuthorization(properties.getProperty("PVRosterAPIAuthorizationKey1", null));
				break;
			case DAINDIVIDUALROSTER:
				endPointUrl = properties.getProperty("DAIndividualRoasterAPIURL", null);
				//authorizationKey = properties.getProperty("CredAPIAuthorizationKey", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;
			case DAINDIVIDUALDEROSTER:
				endPointUrl = properties.getProperty("DAIndividualDeRoasterAPIURL", null);
				//authorizationKey = properties.getProperty("CredAPIAuthorizationKey", null);
				authorizationKey = encodeAuthorization(properties.getProperty("DAExtractAuthorization", null));
				break;

			case DOCAPI:
				endPointUrl = properties.getProperty("DocAPIURL", null);
				authorizationKey = properties.getProperty("DocAPIAuthorizationKey", null);
				break;
			case DOCAPIDocList:
				endPointUrl = properties.getProperty("DocAPIGetDocListURL", null);
				authorizationKey = properties.getProperty("DocAPIAuthorizationKey", null);
				break;
			case DOCAPIDocDownload:
				endPointUrl = properties.getProperty("DocAPIDownloadDocURL", null);
				authorizationKey = properties.getProperty("DocAPIAuthorizationKey", null);
				break;
			case DOCAPIAsyncReq:
				endPointUrl = properties.getProperty("DocAPIAsyncReqURL", null);
				authorizationKey = properties.getProperty("DocAPIAuthorizationKey", null);
				break;

			case PROVIEWAPI:
				endPointUrl = properties.getProperty("ProviewAPIURL", null);
				authorizationKey = properties.getProperty("ProviewAPIAuthorizationKey", null);
				break;
			case PSVBatchService:

				endPointUrl = properties.getProperty("PSVBatchAPIURL", null);

				authorizationKey = encodeAuthorization(properties.getProperty("PSVBatchAPIAuthorizationKey1", null));

				break;
			case CVOAPI:

				endPointUrl = properties.getProperty("CVOAPIURL", null);

				authorizationKey = encodeAuthorization(properties.getProperty("CVOAPIAuthorization", null));

				break;
			case DQGRAPHQLAPI1041AUT:		
				endPointUrl = properties.getProperty("DQGRAPHQLAPIURL", null);		

				authorizationKey = encodeAuthorization(properties.getProperty("DQGRAPHQLAPI1041Authorization", null));		
				subscriptionKey = properties.getProperty("DQsubscription1041Key", null);		
				break;	
			case DQGRAPHQLAPIINVSUB:
				endPointUrl = properties.getProperty("DQGRAPHQLAPIURL", null);

				authorizationKey = encodeAuthorization(properties.getProperty("DQGRAPHQLAPIAuthorization", null));
				subscriptionKey = properties.getProperty("DQsubscriptionErrorKey", null);
				break;
			case DQGRAPHQLAPIINVAUT:
				endPointUrl = properties.getProperty("DQGRAPHQLAPIURL", null);

				authorizationKey = encodeAuthorization(properties.getProperty("DQGRAPHQLAPIErrorAuthorization", null));
				subscriptionKey = properties.getProperty("DQsubscriptionKey", null);
				break;

			case DQGRAPHQLAPI:
				endPointUrl = properties.getProperty("DQGRAPHQLAPIURL", null);

				authorizationKey = encodeAuthorization(properties.getProperty("DQGRAPHQLAPIAuthorization", null));
				subscriptionKey = properties.getProperty("DQsubscriptionKey", null);
				break;

			case DMF:
				endPointUrl = properties.getProperty("DMFMatchsingleAPIURL", null);
				endPointUrl1 = properties.getProperty("DMFMatchMultipleAPIURL", null);
				//authorizationKey = encodeAuthorization(properties.getProperty("DMFMatchSingleAPIAuthorization", null));
				authorizationKey = properties.getProperty("DMFMatchSingleAPIAuthorization", null);
				break;

			case EPMMAFFILIATION:
				endPointUrl = properties.getProperty("AffiliationAPIURL", null);
				caqhid= properties.getProperty("EPMMcaqhId", null);
				authorizationKey = "";
				break;
				
			case EMS:
				endPointUrl=properties.getProperty("EMSURL",null);
				authorizationKey = "";
				break;
				
			case VERIFIDEROSTER:
				endPointUrl = properties.getProperty("VerifideRosterAPIURL", null);
				authorizationKey = properties.getProperty("VerifideRosterAPIAuthorizationKey", null);
				break;
				
			case GENRICEMS:
				endPointUrl=properties.getProperty("GENRICEMSURL",null);
				authorizationKey = "";
				break;
			default:
				break;
			}

			if (endPointUrl == null || authorizationKey == null) {
				throw new RuntimeException(
						"URL or authorization key is not set in " + propertiesFile + " for API: " + apiType);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void assertEachLine(File expectedFile, File actualFile) {
		SoftAssert softassert = new SoftAssert();
		BufferedReader expectedFileReader = null;
		BufferedReader actualFileReader = null;
		try {
			expectedFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(expectedFile)));
			actualFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(actualFile)));

			String line1 = null, line2 = null;

			while ((line1 = actualFileReader.readLine()) != null) {
				if ((line2 = expectedFileReader.readLine()) != null) {
					softassert.assertEquals(line1, line2);
				} else {
					Assert.fail("End of file reached in source file");
				}
			}

			softassert.assertAll();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

			try {
				if (actualFileReader != null)
					actualFileReader.close();
				if (expectedFileReader != null)
					expectedFileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public File writeResponseToAFile(String dir, String response) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss");
		File responseFile;

		if (dir.contains("Data")) {
			File responseDir = new File(dir);
			if (!responseDir.exists()) {
				responseDir.mkdirs();
			}

			responseFile = new File(responseDir, "Response_" + format.format(new Date()) + ".txt");

		} else {
			File responseDir = new File("FailedResponses" + File.separator + dir);

			if (!responseDir.exists()) {
				responseDir.mkdirs();
			}
			/*
			 * else { responseDir.mkdirs(); }
			 */

			responseFile = new File(responseDir, "Response_" + format.format(new Date()) + ".xml");

		}

		try {
			FileUtils.writeStringToFile(responseFile, response, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		return responseFile;
	}

	@DataProvider(name = "testDataProvider")
	public Iterator<Object[]> dataProvider(Method method) throws FileNotFoundException, IOException {
		return Util.getTestData(new File(Constants.DATA_DIR + File.separator + "APITestData.xlsx"),
				Constants.environment, method.getName());

	}

	public String encodeAuthorization(String credentials) {
		String encodedKey = null;

		if (credentials.contains("Basic")) {
			return credentials;
		} else {
			byte[] bytesEncoded = Base64.encodeBase64(credentials.getBytes());
			// System.out.println("ecncoded value is " + new String(bytesEncoded
			// ));
			encodedKey = "Basic " + new String(bytesEncoded);
		}
		return encodedKey;
	}
	
	public File writeResponseToAXMLFile(String dir, String response) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss");
		File responseFile = null;

		if (dir.contains("Data")) {
			File responseDir = new File(dir);
			if (!responseDir.exists()) {
				responseDir.mkdirs();
			}

			responseFile = new File(responseDir, "CredV5API_Response_Test" + format.format(new Date()) + ".xml");

		} else {
			File responseDir = new File("FailedResponses" + File.separator + dir);
	
		}
		try {
			FileUtils.writeStringToFile(responseFile, response, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return responseFile;
	}
	public Logger getLogger(){
		/*if(logger == null){
			logger = new ProViewLogger();
		}
		return logger;*/
		if(logger == null){
			logger = Logger.getRootLogger();
		}
		return logger;
	}
}
