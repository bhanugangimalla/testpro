package com.proview.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.proview.api.RequestBase.ContentType;
import com.proview.api.RequestBase.RequestMethod;

public class DARosterAPI extends RequestBase{
	public DARosterAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);
		
		

}
	public String getRosterStatus(String batchId) {
		urlStr = urlStr + "?batch_id=" + batchId;
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null); // create an overloaded method
		return getInputStreamAsString();
	}


	/*public String addDARoster(String firstName, String lastName, String Provider_Address2, String provider_Address_City,
			String provider_Address_State, String provider_Address_Zip,String  Provider_Address_Zip_extn, String Phone,String Fax,String emailID,String provider_Practice_State,
			String provider_Birthdate, String  provider_SSN ,String provider_Short_SSN,String dea , String upin,String provider_Type, String Tax_ID ,String npi ,String provider_License_State,
			String provider_License_Number, String caqh_provider_id ,String po_provider_id ,String last_recredential_date,String next_recredential_date,String  delegation_flag,String application_Type,String affiliation_flag, String organization_ID,String  region_id ) throws IOException {
		
		Map<String, String> params = new HashMap<String, String> ();
		
		String body = "[{\n"+
				"\"provider\":{\n" + 
				"\"first_name\":\"" + firstName + "\",\n" +
				"\"middle_name\":\"" + "" +"\",\n" +
				"\"last_name\":\"" + lastName + "\",\n" +
				"\"name_suffix\":\"" + "" + "\",\n" +
				"\"gender\":\"" + "" + "\",\n" +
				"\"address1\":\"" + ""+ "\",\n" +
				"\"address2\":\"" + Provider_Address2 + "\",\n" +
				"\"city\":\"" + provider_Address_City + "\",\n" +
				"\"state\":\"" + provider_Address_State + "\",\n" +
				"\"zip\":\"" + provider_Address_Zip + "\",\n" +
				"\"zip_extn\":\"" + Provider_Address_Zip_extn + "\",\n" +
				"\"phone\":\"" + Phone + "\",\n" +
				"\"fax\":\"" + Fax + "\",\n" +
				"\"email\":\"" + emailID + "\",\n" +
				"\"practice_state\":\"" + provider_Practice_State + "\",\n" +
				"\"birthdate\":\"" + provider_Birthdate + "\",\n" +
				"\"ssn\":\"" + provider_SSN + "\",\n" +
				"\"provider_Short_SSN\":\"" + provider_Short_SSN + "\",\n" +
				"\"dea\":\"" + dea + "\",\n" +
				"\"upin\":\"" + upin + "\",\n" +
				"\"type\":\"" + provider_Type + "\",\n" +
				"\"tax_id\":\"" + Tax_ID + "\",\n" +
				"\"npi\":\"" + npi + "\",\n" +
				"\"license_state\":\"" + provider_License_State + "\",\n" +
				"\"license_number\":\"" + provider_License_Number + "\"\n" +
				"},\n" +
				"\"caqh_provider_id\":\"" + caqh_provider_id + "\",\n" +
				"\"po_provider_id\":\"" + po_provider_id + "\",\n" +
				"\"last_recredential_date\":\"" + last_recredential_date + "\",\n" +
				"\"next_recredential_date\":\"" + next_recredential_date + "\",\n" +
				"\"delegation_flag\":\"" + delegation_flag + "\",\n" +
				"\"application_Type\":\"" + application_Type + "\",\n" +
				"\"affiliation_flag\":\"" + affiliation_flag + "\",\n" +
				"\"organization_ID\":\"" + organization_ID + "\",\n" +
				"\"region_id\":\"" + region_id + "\"\n" +
				"}\n" +
				"]";
		*/
	public String addDARoster(String firstName, String PRmiddleName, String lastName, String name_suffix,String gender ,String provider_Address1,
			String Provider_Address2, String provider_Address_City, String provider_Address_State,
			String provider_Address_Zip, String Provider_Address_Zip_extn, String Phone, String Fax,
			String emailID, String provider_Practice_State, String provider_Birthdate, String provider_SSN, 
			String Provider_Short_SSN, String dea, String upin, String provider_Type, String Tax_ID, 
			String npi, String provider_License_State, String provider_License_Number, String caqh_provider_id, String po_provider_id, String last_recredential_date, String next_recredential_date, String delegation_flag, String application_Type, String affiliation_flag, String organization_ID,String region_id) 
	{
		
		Map<String, String> params = new HashMap<String, String> ();
		
		String body = "[{\n"+
				"\"provider\":{\n" + 
				"\"first_name\":\"" + firstName + "\",\n" +
				"\"middle_name\":\"" + PRmiddleName +"\",\n" +
				"\"last_name\":\"" + lastName + "\",\n" +
				"\"name_suffix\":\"" + name_suffix + "\",\n" +
				"\"gender\":\"" + gender + "\",\n" +
				"\"address1\":\"" + provider_Address1 + "\",\n" +
				"\"address2\":\"" + Provider_Address2 + "\",\n" +
				"\"city\":\"" + provider_Address_City + "\",\n" +
				"\"state\":\"" + provider_Address_State + "\",\n" +
				"\"zip\":\"" + provider_Address_Zip + "\",\n" +
				"\"zip_extn\":\"" + Provider_Address_Zip_extn + "\",\n" +
				"\"phone\":\"" + Phone + "\",\n" +
				"\"fax\":\"" + Fax + "\",\n" +
				"\"email\":\"" + emailID + "\",\n" +
				"\"practice_state\":\"" + provider_Practice_State + "\",\n" +
				"\"birthdate\":\"" + provider_Birthdate + "\",\n" +
				"\"ssn\":\"" + provider_SSN + "\",\n" +
				"\"provider_Short_SSN\":\"" + Provider_Short_SSN + "\",\n" +
				"\"dea\":\"" + dea + "\",\n" +
				"\"upin\":\"" + upin + "\",\n" +
				"\"type\":\"" + provider_Type + "\",\n" +
				"\"tax_id\":\"" + Tax_ID + "\",\n" +
				"\"npi\":\"" + npi + "\",\n" +
				"\"license_state\":\"" + provider_License_State + "\",\n" +
				"\"license_number\":\"" + provider_License_Number + "\"\n" +
				"},\n" +
				"\"caqh_provider_id\":\"" + caqh_provider_id + "\",\n" +
				"\"po_provider_id\":\"" + po_provider_id + "\",\n" +
				"\"last_recredential_date\":\"" + last_recredential_date + "\",\n" +
				"\"next_recredential_date\":\"" + next_recredential_date + "\",\n" +
				"\"delegation_flag\":\"" + delegation_flag + "\",\n" +
				"\"application_Type\":\"" + application_Type + "\",\n" +
				"\"affiliation_flag\":\"" + affiliation_flag + "\",\n" +
				"\"organization_ID\":\"" + organization_ID + "\",\n" +
				"\"region_id\":\"" + region_id + "\"\n" +
				"}\n" +
				"]";
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);
		
		//String body =FileUtils.readFileToString(new File("BaselineResponses\\RosterAPIResponse\\json.txt"), "UTF-8");
		
		//sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, null, body);
		return getInputStreamAsString();
	}
	public String addIndividualDARoster(String firstName, String PRmiddleName, String lastName, String name_suffix,String gender ,String provider_Address1,
			String Provider_Address2, String provider_Address_City, String provider_Address_State,
			String provider_Address_Zip, String Provider_Address_Zip_extn, String Phone, String Fax,
			String emailID, String provider_Practice_State, String provider_Birthdate, String provider_SSN, 
			String Provider_Short_SSN, String dea, String upin, String provider_Type, String Tax_ID, 
			String npi, String provider_License_State, String provider_License_Number, String caqh_provider_id, String po_provider_id, String last_recredential_date, String next_recredential_date, String delegation_flag, String application_Type, String affiliation_flag, String organization_ID,String region_id) 
	{
		
		Map<String, String> params = new HashMap<String, String> ();
		
		String body = "{\n"+
				"\"provider\":{\n" + 
				"\"first_name\":\"" + firstName + "\",\n" +
				"\"middle_name\":\"" + PRmiddleName +"\",\n" +
				"\"last_name\":\"" + lastName + "\",\n" +
				"\"name_suffix\":\"" + name_suffix + "\",\n" +
				"\"gender\":\"" + gender + "\",\n" +
				"\"address1\":\"" + provider_Address1 + "\",\n" +
				"\"address2\":\"" + Provider_Address2 + "\",\n" +
				"\"city\":\"" + provider_Address_City + "\",\n" +
				"\"state\":\"" + provider_Address_State + "\",\n" +
				"\"zip\":\"" + provider_Address_Zip + "\",\n" +
				"\"zip_extn\":\"" + Provider_Address_Zip_extn + "\",\n" +
				"\"phone\":\"" + Phone + "\",\n" +
				"\"fax\":\"" + Fax + "\",\n" +
				"\"email\":\"" + emailID + "\",\n" +
				"\"practice_state\":\"" + provider_Practice_State + "\",\n" +
				"\"birthdate\":\"" + provider_Birthdate + "\",\n" +
				"\"ssn\":\"" + provider_SSN + "\",\n" +
				"\"short_ssn\":\"" + Provider_Short_SSN + "\",\n" +
				"\"dea\":\"" + dea + "\",\n" +
				"\"upin\":\"" + upin + "\",\n" +
				"\"type\":\"" + provider_Type + "\",\n" +
				"\"tax_id\":\"" + Tax_ID + "\",\n" +
				"\"npi\":\"" + npi + "\",\n" +
				"\"license_state\":\"" + provider_License_State + "\",\n" +
				"\"license_number\":\"" + provider_License_Number + "\"\n" +
				"},\n" +
				"\"caqh_provider_id\":\"" + caqh_provider_id + "\",\n" +
				"\"po_provider_id\":\"" + po_provider_id + "\",\n" +
				"\"last_recredential_date\":\"" + last_recredential_date + "\",\n" +
				"\"next_recredential_date\":\"" + next_recredential_date + "\",\n" +
				"\"delegation_flag\":\"" + delegation_flag + "\",\n" +
				"\"application_Type\":\"" + application_Type + "\",\n" +
				"\"affiliation_flag\":\"" + affiliation_flag + "\",\n" +
				"\"organization_ID\":\"" + organization_ID + "\",\n" +
				"\"region_id\":\"" + region_id + "\"\n" +
				"}\n" ;
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);
		
		//String body =FileUtils.readFileToString(new File("BaselineResponses\\RosterAPIResponse\\json.txt"), "UTF-8");
		
		//sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, null, body);
		return getInputStreamAsString();
	}
	public String addIndividualDARosterPayload(String firstName, String PRmiddleName, String lastName, String name_suffix,String gender ,String provider_Address1,
			String Provider_Address2, String provider_Address_City, String provider_Address_State,
			String provider_Address_Zip, String Provider_Address_Zip_extn, String Phone, String Fax,
			String emailID, String provider_Practice_State, String provider_Birthdate, String provider_SSN, 
			String Provider_Short_SSN, String dea, String upin, String provider_Type, String Tax_ID, 
			String npi, String provider_License_State, String provider_License_Number, String caqh_provider_id, String po_provider_id, String last_recredential_date, String next_recredential_date, String delegation_flag, String application_Type, String affiliation_flag, String organization_ID,String region_id) 
	{
		
		String body = "{\n"+
				"\"provider\":{\n" + 
				"\"first_name\":\"" + firstName + "\",\n" +
				"\"middle_name\":\"" + PRmiddleName +"\",\n" +
				"\"last_name\":\"" + lastName + "\",\n" +
				"\"name_suffix\":\"" + name_suffix + "\",\n" +
				"\"gender\":\"" + gender + "\",\n" +
				"\"address1\":\"" + provider_Address1 + "\",\n" +
				"\"address2\":\"" + Provider_Address2 + "\",\n" +
				"\"city\":\"" + provider_Address_City + "\",\n" +
				"\"state\":\"" + provider_Address_State + "\",\n" +
				"\"zip\":\"" + provider_Address_Zip + "\",\n" +
				"\"zip_extn\":\"" + Provider_Address_Zip_extn + "\",\n" +
				"\"phone\":\"" + Phone + "\",\n" +
				"\"fax\":\"" + Fax + "\",\n" +
				"\"email\":\"" + emailID + "\",\n" +
				"\"practice_state\":\"" + provider_Practice_State + "\",\n" +
				"\"birthdate\":\"" + provider_Birthdate + "\",\n" +
				"\"ssn\":\"" + provider_SSN + "\",\n" +
				"\"short_ssn\":\"" + Provider_Short_SSN + "\",\n" +
				"\"dea\":\"" + dea + "\",\n" +
				"\"upin\":\"" + upin + "\",\n" +
				"\"type\":\"" + provider_Type + "\",\n" +
				"\"tax_id\":\"" + Tax_ID + "\",\n" +
				"\"npi\":\"" + npi + "\",\n" +
				"\"license_state\":\"" + provider_License_State + "\",\n" +
				"\"license_number\":\"" + provider_License_Number + "\"\n" +
				"},\n" +
				"\"caqh_provider_id\":\"" + caqh_provider_id + "\",\n" +
				"\"po_provider_id\":\"" + po_provider_id + "\",\n" +
				"\"last_recredential_date\":\"" + last_recredential_date + "\",\n" +
				"\"next_recredential_date\":\"" + next_recredential_date + "\",\n" +
				"\"delegation_flag\":\"" + delegation_flag + "\",\n" +
				"\"application_Type\":\"" + application_Type + "\",\n" +
				"\"affiliation_flag\":\"" + affiliation_flag + "\",\n" +
				"\"organization_ID\":\"" + organization_ID + "\",\n" +
				"\"region_id\":\"" + region_id + "\"\n" +
				"}\n" ;
		
		return body;
	}
	public String addRoster(String caqhId, String orgId) {
		Map<String, String> params = new HashMap<String, String> ();
				
		String body = "[{\n" + 
			"\"caqh_provider_id\": \"" + caqhId + "\",\n" +
			"\"organization_id\": \"" + orgId + "\",\n" + 
			"}]";
		
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);
		
		return getInputStreamAsString();
	}
	
	public String DADeRoster(String caqhId, String orgId) {
		Map<String, String> params = new HashMap<String, String> ();
				
		String body = "[{\n" + 
			"\"caqh_provider_id\": \"" + caqhId + "\",\n" +
			"\"organization_id\": \"" + orgId + "\",\n" + 
			"}]";
		
		sendRequest(RequestMethod.PUT, ContentType.APPLICATION_JSON, params, body);
		
		return getInputStreamAsString();
}
	public String DADeIndividualRoster(String caqhId, String orgId) {
		Map<String, String> params = new HashMap<String, String> ();
				
		String body = "{\n" + 
			"\"organization_id\": \"" + orgId + "\",\n" +
			"\"caqh_provider_id\": \"" + caqhId + "\"\n" + 
			"}";
		
		sendRequest(RequestMethod.PUT, ContentType.APPLICATION_JSON, params, body);
		
		return getInputStreamAsString();
}
	public String DADeIndividualRosterPayload(String caqhId, String orgId) {
					
		String body = "{\n" + 
			"\"organization_id\": \"" + orgId + "\",\n" +
			"\"caqh_provider_id\": \"" + caqhId + "\"\n" + 
			"}";
	
		return body;
}
	public String updateDaIndividualRoster(String caqh_provider_id,String po_provider_id,String organization_ID) 
	{
		Map<String, String> params = new HashMap<String, String> ();

		String body = "{\n"+
		"\"caqh_provider_id\":\"" + caqh_provider_id + "\",\n" +
		"\"po_provider_id\":\"" + po_provider_id + "\",\n" +
		"\"organization_ID\":\"" + organization_ID + "\"\n" +
	   "}\n";

		sendRequest(RequestMethod.PUT, ContentType.APPLICATION_JSON, params, body);

		return getInputStreamAsString();
	}
	public String updateDaIndividualRosterPayload(String caqh_provider_id,String po_provider_id,String organization_ID) 
	{
		String body = "{\n"+
		"\"caqh_provider_id\":\"" + caqh_provider_id + "\",\n" +
		"\"po_provider_id\":\"" + po_provider_id + "\",\n" +
		"\"organization_ID\":\"" + organization_ID + "\"\n" +
	   "}\n";

		return body;
	}
	
	public String getRosterStatusReplace(String batchId) {
		urlStr=urlStr.replaceAll("product=DA","batch_id=");
		urlStr =urlStr+batchId;
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		return getInputStreamAsString();		
		//return isError ? getErrorStreamAsString() : getInputStreamAsString();
	}
	
	public String payLoadAdd(String firstName, String PRmiddleName, String lastName, String name_suffix,String gender, String provider_Address1,
			String Provider_Address2, String provider_Address_City, String provider_Address_State,
			String provider_Address_Zip, String Provider_Address_Zip_extn, String Phone, String Fax,
			String emailID, String provider_Practice_State, String provider_Birthdate, String provider_SSN, 
			String Provider_Short_SSN, String dea, String upin, String provider_Type, String Tax_ID, 
			String npi, String provider_License_State, String provider_License_Number, String caqh_provider_id, String po_provider_id, String last_recredential_date, String next_recredential_date, String delegation_flag, String application_Type, String affiliation_flag, String organization_ID,String region_id,String product) 
	{

		Map<String, String> params = new HashMap<String, String> ();
		String body = "[{\n"+
				"\"provider\":{\n" + 
				"\"first_name\":\"" + firstName + "\",\n" +
				"\"middle_name\":\"" + PRmiddleName +"\",\n" +
				"\"last_name\":\"" + lastName + "\",\n" +
				"\"name_suffix\":\"" + name_suffix + "\",\n" +
				"\"gender\":\"" + gender + "\",\n" +
				"\"address1\":\"" + provider_Address1 + "\",\n" +
				"\"address2\":\"" + Provider_Address2 + "\",\n" +
				"\"city\":\"" + provider_Address_City + "\",\n" +
				"\"state\":\"" + provider_Address_State + "\",\n" +
				"\"zip\":\"" + provider_Address_Zip + "\",\n" +
				"\"zip_extn\":\"" + Provider_Address_Zip_extn + "\",\n" +
				"\"phone\":\"" + Phone + "\",\n" +
				"\"fax\":\"" + Fax + "\",\n" +
				"\"email\":\"" + emailID + "\",\n" +
				"\"practice_state\":\"" + provider_Practice_State + "\",\n" +
				"\"birthdate\":\"" + provider_Birthdate + "\",\n" +
				"\"ssn\":\"" + provider_SSN + "\",\n" +
				"\"provider_Short_SSN\":\"" + Provider_Short_SSN + "\",\n" +
				"\"dea\":\"" + dea + "\",\n" +
				"\"upin\":\"" + upin + "\",\n" +
				"\"type\":\"" + provider_Type + "\",\n" +
				"\"tax_id\":\"" + Tax_ID + "\",\n" +
				"\"npi\":\"" + npi + "\",\n" +
				"\"license_state\":\"" + provider_License_State + "\",\n" +
				"\"license_number\":\"" + provider_License_Number + "\"\n" +
				"},\n" +
				"\"caqh_provider_id\":\"" + caqh_provider_id + "\",\n" +
				"\"po_provider_id\":\"" + po_provider_id + "\",\n" +
				"\"last_recredential_date\":\"" + last_recredential_date + "\",\n" +
				"\"next_recredential_date\":\"" + next_recredential_date + "\",\n" +
				"\"delegation_flag\":\"" + delegation_flag + "\",\n" +
				"\"application_Type\":\"" + application_Type + "\",\n" +
				"\"affiliation_flag\":\"" + affiliation_flag + "\",\n" +
				"\"organization_ID\":\"" + organization_ID + "\",\n" +
				"\"region_id\":\"" + region_id + "\"\n" +
				"}\n" +
				"]";		
		
		return body;
	}
	
	public String payLoadUpdateRoster(String caqhId, String orgId,String po_provider_id,String last_recredential_date,String next_recredential_date,String delegation_flag,String application_Type,String affiliation_flag,String product) {
		Map<String, String> params = new HashMap<String, String> ();

		String body = "[{\n"+
				"\"organization_ID\":\"" + orgId + "\",\n" +
				"\"caqh_provider_id\":\"" + caqhId + "\",\n" +
				"\"po_provider_id\":\"" + po_provider_id + "\",\n" +
				"\"last_recredential_date\":\"" + last_recredential_date + "\",\n" +
				"\"next_recredential_date\":\"" + next_recredential_date + "\",\n" +
				"\"delegation_flag\":\"" + delegation_flag + "\",\n" +
				"\"application_Type\":\"" + application_Type + "\",\n" +
				"\"affiliation_flag\":\"" + affiliation_flag + "\",\n" +
				"},\n"+
				"]";
		
		return body;
	}
	
	public String updateRoster(String caqhId, String orgId,String po_provider_id,String last_recredential_date,String next_recredential_date,String delegation_flag,String application_Type,String affiliation_flag) {
		Map<String, String> params = new HashMap<String, String> ();

		String body = "[{\n"+
				"\"organization_ID\":\"" + orgId + "\",\n" +
				"\"caqh_provider_id\":\"" + caqhId + "\",\n" +
				"\"po_provider_id\":\"" + po_provider_id + "\",\n" +
				"\"last_recredential_date\":\"" + last_recredential_date + "\",\n" +
				"\"next_recredential_date\":\"" + next_recredential_date + "\",\n" +
				"\"delegation_flag\":\"" + delegation_flag + "\",\n" +
				"\"application_Type\":\"" + application_Type + "\",\n" +
				"\"affiliation_flag\":\"" + affiliation_flag + "\",\n" +
				"},\n"+
				"]";		

		sendRequest(RequestMethod.PUT, ContentType.APPLICATION_JSON, params, body);

		return getInputStreamAsString();
	}
	public String getDeRosterStatusReplace(String batchId) {
		urlStr=urlStr.replace("DeRoster?product=DA","");
		urlStr = urlStr + "Roster?batch_id=" + batchId;
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		return getInputStreamAsString();
		}
}
