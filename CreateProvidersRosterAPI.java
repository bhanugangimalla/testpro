package com.proview.api.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.proview.api.APITestBase;
import com.proview.api.RosterAPIRequest;
import com.proview.util.Constants;
import com.proview.util.Util;

public class CreateProvidersRosterAPI extends APITestBase {

	public CreateProvidersRosterAPI() {
		super(ApiType.PVROSTER);
	}
	
	RosterAPIRequest rosterApiRequest;
		
	@BeforeMethod(alwaysRun=true)
	public void init() {
		
		rosterApiRequest = new RosterAPIRequest(endPointUrl, authorizationKey);
		
	}
	
	@Test(groups={"Functional", "providerRosterAPI", "addInitialRoster", "DBUtilIssues"})
	//@Parameters({"firstName", "lastName", "Provider_Address1", "Provider_Address_City", "Provider_Address_State", "Provider_Address_Zip", "Provider_Practice_State", "Provider_Birthdate", "Provider_Short_SSN", "Provider_Type", "Provider_License_State", "Provider_License_Number", "Application_Type", "Organization_ID"})
	
	//If email is added in the request body, Provider will be in Initial Provider Complete Status else it will be a New provider
	
	public void verifyAddRoster() throws Exception {
		for(int i=0; i<Integer.parseInt(PRGenCount);i++)
		{
	
		Constants objConstants =new Constants();
		
		String firstName = objConstants.firstName;
		String PRmiddleName = objConstants.PRmiddleName;
		String lastName = objConstants.lastName;
		String Provider_Address1 = objConstants.Provider_Address1;
		String Provider_Address_City =objConstants.Provider_Address_City;
		String Provider_Address_State=objConstants.practiceStates_api.get(objConstants.primaryPracticeState_api);
		String Provider_Address_Zip=objConstants.Provider_Address_Zip;
		String Provider_Practice_State = Provider_Address_State;
		String Provider_Birthdate =objConstants.Provider_Birthdate;
		String Provider_SSN = objConstants.Provider_SSN;
		String Provider_Type =objConstants.TypeName;
		String Provider_License_State = Provider_Address_State;
		String Provider_License_Number =objConstants.Provider_License_Number;
		String Application_Type = objConstants.Application_Type;
		String Organization_ID = objConstants.Organization_ID;
		String name_suffix = objConstants.name_suffix;
		String gender = objConstants.gender;
		String Provider_Address2 = objConstants.Provider_Address2;
		String Provider_Address_Zip_extn = objConstants.Provider_Address_Zip_extn; 
		String Phone = objConstants.Phone;
		String Fax = objConstants.Fax;
		String emailID = objConstants.emailID_api;
		String Provider_Short_SSN = objConstants.Provider_Short_SSN;
		String npi = objConstants.npi;
		String dea = objConstants.dea;
		String upin = objConstants.upin;
		String Tax_ID = objConstants.Tax_ID;
		String last_recredential_date = objConstants.last_recredential_date;
		String next_recredential_date =objConstants.next_recredential_date;
		String delegation_flag =objConstants.delegation_flag;
		String affiliation_flag = objConstants.affiliation_flag;
		String region_id =objConstants.region_id;
		String caqh_provider_id=objConstants.caqh_provider_id;
		String po_provider_id=objConstants.po_provider_id;
		String product=objConstants.product;
		
		
		String response = rosterApiRequest.addRoster(firstName, PRmiddleName, lastName, name_suffix, gender, Provider_Address1, Provider_Address2, Provider_Address_City, Provider_Address_State, Provider_Address_Zip, Provider_Address_Zip_extn, Phone, Fax,
				  emailID, Provider_Address_State, Provider_Birthdate, Provider_SSN, Provider_Short_SSN, dea, upin, Provider_Type, Tax_ID, npi, Provider_Address_State, Provider_License_Number, caqh_provider_id, po_provider_id, last_recredential_date, next_recredential_date, delegation_flag, Application_Type, affiliation_flag, Organization_ID,  region_id,  product);
		Assert.assertEquals(rosterApiRequest.getResponseCode(), 200);
		
		String[] split = response.split(":");
		String strBatchId = split[1].split("\"")[1];
		
		Thread.sleep(40000);
		
		/* To Change the API URL to get the JSON response from the batchId*/
		Properties properties = new Properties();
		String propertiesFile = System.getProperty("environment");
		properties.load(new FileInputStream(new File("Data" + File.separator + propertiesFile + ".properties")));
		endPointUrl = properties.getProperty("PVRosterStatusAPIURL", null);
		authorizationKey = encodeAuthorization(properties.getProperty("PVRosterStatusAPIAuthorizationKey", null));
		rosterApiRequest = new RosterAPIRequest(endPointUrl, authorizationKey);
		
		String response1 = rosterApiRequest.getRosterStatus(strBatchId);
		System.out.println(response1);
		
		/* To Change the API URL to execute the test for the next instance */
		endPointUrl = properties.getProperty("PVRosterAPIURL", null);
		authorizationKey = encodeAuthorization(properties.getProperty("PVRosterAPIAuthorization", null));
		rosterApiRequest = new RosterAPIRequest(endPointUrl, authorizationKey);
		
		String strCaqhPRID = response1.split("caqh_provider_id")[1].split("\"")[2];
		System.out.println("caqh_provider_id:"+strCaqhPRID);
		
		/* To write the values into the excel file present in 22 server*/
		writeIntoExcell(properties.getProperty("ProvidedDataExcelPath", null), "Data",firstName, lastName, strCaqhPRID, Provider_Type, Provider_Practice_State, emailID);
		Thread.sleep(10000);
		// Get CAQH GUID from CRM using SP
		/*String caqhGUID = new DBUtil().getRegistrationLink(firstName+" "+lastName,"%"+emailID+"%");
		System.out.println(caqhGUID);*/
				
		}
	}
	
	public void writeIntoExcell(String excelFileName, String sheetName, String firstName, String lastName, String strCaqhPRID, String Provider_Type,String Provider_Practice_State, String emailID ) throws IOException{
		XSSFWorkbook wb = null;
		File file = new File(excelFileName);
					
		wb = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = wb.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		XSSFRow row;
		 // Create a row and put some cells in it. Rows are 0 based.
	    row = sheet.createRow(rowCount+1);
	    row.createCell(0).setCellValue(endPointUrl);
	    row.createCell(1).setCellValue("ProvidersGenerated");
	    row.createCell(2).setCellValue(firstName+" "+lastName);
	    row.createCell(3).setCellValue(strCaqhPRID);
	    row.createCell(4).setCellValue(Provider_Practice_State);
	    row.createCell(5).setCellValue(Provider_Type);
	    row.createCell(6).setCellValue(authorizationKey);
	    row.createCell(7).setCellValue(Util.getCurrentDate("yyyy-MM-dd"));
	    row.createCell(8).setCellValue(emailID);
	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream(excelFileName);
	    wb.write(fileOut);
	    wb.close();
	    fileOut.close();
		
	}
}
