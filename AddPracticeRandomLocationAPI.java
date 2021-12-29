package com.proview.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.proview.api.RequestBase.ContentType;
import com.proview.api.RequestBase.RequestMethod;
import com.proview.util.Util;

public class AddPracticeRandomLocationAPI  extends RequestBase {
	String practicename = Util.getRandomNo(4);
	String address1 = Util.getRandomNo(3);
	String address2 = Util.getRandomNo(5);

	public AddPracticeRandomLocationAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);

	}

	public String getRosterStatus1(String batchId, boolean isError)throws IOException {


		urlStr = urlStr.substring(0, urlStr.indexOf('I')) + batchId;
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		connection.getResponseCode();

		connection.getResponseMessage();

		return isError ? getErrorStreamAsString() : getInputStreamAsString();


		//return getErrorStreamAsString();


	}

	public String getRosterStatus(String batchId)throws IOException {


		urlStr = urlStr.substring(0, urlStr.indexOf('I')) + batchId;
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		connection.getResponseCode();

		connection.getResponseMessage();

		return  getInputStreamAsString();


		//return getErrorStreamAsString();


	}


	public String addDARoster(String organization_id, String tax_id, String practice_name,
			String practice_location_address1, String practice_location_address2, String practice_location_city,
			String practice_location_state, String practice_location_zipcode, String practice_location_province,
			String practice_location_country, String npi_type_2, String po_location_id, String location_type,
			String caqh_provider_id, String po_provider_id, String provider_type,
			String provider_primary_practice_state, String provider_first_name, String provider_middle_name,
			String provider_last_name, String npi_type_1ax, String caqh_provider_location_id, String dea_number,
			String dea_state, String practice_location_address3, String practice_location_address4, String practice_location_address5, String practice_location_address6)
					throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		String body = "{\n" + "\"organization_id\":\"" + organization_id + "\",\n" + "\"practice_location\":[\n" + "{"
				+ "\"tax_id\":\"" + tax_id + "\",\n" + "\"practice_name\":\"" + practice_name + practicename +"\",\n"
				+ "\"practice_location_address1\":\"" + practice_location_address1 + address1 +"\",\n"
				+ "\"practice_location_address2\":\"" + practice_location_address2 + address2 + "\",\n"
				+ "\"practice_location_city\":\"" + practice_location_city + "\",\n" + "\"practice_location_state\":\""
				+ practice_location_state + "\",\n" + "\"practice_location_zipcode\":\"" + practice_location_zipcode
				+ "\",\n" + "\"practice_location_province\":\"" + practice_location_province + "\",\n"
				+ "\"practice_location_country\":\"" + practice_location_country + "\",\n" + "\"npi_type_2\":\""
				+ npi_type_2 + "\",\n" + "\"providers\":[\n" + "{\n" + "\"caqh_provider_id\":\"" + caqh_provider_id
				+ "\",\n" + "\"po_provider_id\":\"" + po_provider_id + "\",\n" + "\"provider_type\":\"" + provider_type
				+ "\",\n" + "\"provider_primary_practice_state\":\"" + provider_primary_practice_state + "\",\n"
				+ "\"provider_first_name\":\"" + provider_first_name + "\",\n" + "\"provider_middle_name\":\""
				+ provider_middle_name + "\",\n" + "\"provider_last_name\":\"" + provider_last_name + "\",\n"
				+ "\"npi_type_1ax\":\"" + npi_type_1ax + "\",\n" + "\"caqh_provider_location_id\":\""
				+ caqh_provider_location_id + "\",\n" + "\"provider_dea\":[\n" + "{" + "\"dea_number\":\"" + dea_number
				+ "\",\n" + "\"dea_state\":\"" + dea_state + "\"\n" + "}\n" + "]\n" + "}\n" + "]\n" + "},\n" + "{\n"
				+ "\"tax_id\":\"" + tax_id + "\",\n" + "\"practice_name\":\"" + practice_name +practicename + "\",\n"
				+ "\"practice_location_address1\":\"" + practice_location_address3 + address1 +"\",\n"
				+ "\"practice_location_address2\":\"" + practice_location_address4 + address2 + "\",\n"
				+ "\"practice_location_city\":\"" + practice_location_city + "\",\n" + "\"practice_location_state\":\""
				+ practice_location_state + "\",\n" + "\"practice_location_zipcode\":\"" + practice_location_zipcode
				+ "\",\n" + "\"practice_location_province\":\"" + practice_location_province + "\",\n"
				+ "\"practice_location_country\":\"" + practice_location_country + "\",\n" + "\"npi_type_2\":\""
				+ npi_type_2 + "\",\n" + "\"providers\":[\n" + "{\n" + "\"caqh_provider_id\":\"" + caqh_provider_id
				+ "\",\n" + "\"po_provider_id\":\"" + po_provider_id + "\",\n" + "\"provider_type\":\"" + provider_type
				+ "\",\n" + "\"provider_primary_practice_state\":\"" + provider_primary_practice_state + "\",\n"
				+ "\"provider_first_name\":\"" + provider_first_name + "\",\n" + "\"provider_middle_name\":\""
				+ provider_middle_name + "\",\n" + "\"provider_last_name\":\"" + provider_last_name + "\",\n"
				+ "\"npi_type_1ax\":\"" + npi_type_1ax + "\",\n" + "\"caqh_provider_location_id\":\""
				+ caqh_provider_location_id + "\",\n" + "\"provider_dea\":[\n" + "{" + "\"dea_number\":\"" + dea_number
				+ "\",\n" + "\"dea_state\":\"" + dea_state + "\"\n" + "}\n" + "]\n" + "}\n" + "]\n" + "},\n" + "{\n"
				+"\"tax_id\":\"" + tax_id + "\",\n" + "\"practice_name\":\"" + practice_name + practicename +"\",\n"
				+ "\"practice_location_address1\":\"" + practice_location_address5 + address1 +"\",\n"
				+ "\"practice_location_address2\":\"" + practice_location_address6 + address2 + "\",\n"
				+ "\"practice_location_city\":\"" + practice_location_city + "\",\n" + "\"practice_location_state\":\""
				+ practice_location_state + "\",\n" + "\"practice_location_zipcode\":\"" + practice_location_zipcode
				+ "\",\n" + "\"practice_location_province\":\"" + practice_location_province + "\",\n"
				+ "\"practice_location_country\":\"" + practice_location_country + "\",\n" + "\"npi_type_2\":\""
				+ npi_type_2 + "\",\n" + "\"providers\":[\n" + "{\n" + "\"caqh_provider_id\":\"" + caqh_provider_id
				+ "\",\n" + "\"po_provider_id\":\"" + po_provider_id + "\",\n" + "\"provider_type\":\"" + provider_type
				+ "\",\n" + "\"provider_primary_practice_state\":\"" + provider_primary_practice_state + "\",\n"
				+ "\"provider_first_name\":\"" + provider_first_name + "\",\n" + "\"provider_middle_name\":\""
				+ provider_middle_name + "\",\n" + "\"provider_last_name\":\"" + provider_last_name + "\",\n"
				+ "\"npi_type_1ax\":\"" + npi_type_1ax + "\",\n" + "\"caqh_provider_location_id\":\""
				+ caqh_provider_location_id + "\",\n" + "\"provider_dea\":[\n" + "{" + "\"dea_number\":\"" + dea_number
				+ "\",\n" + "\"dea_state\":\"" + dea_state + "\"\n" + "}\n" + "]\n" + "}\n" + "]\n" + "}\n" +  "]\n"
				+ "}\n";

		String urlstr1=urlStr.substring(0, urlStr.lastIndexOf("_"));
		System.out.println(urlstr1);
		//String urlstr2=urlStr.substring(88, urlStr.indexOf("&"));

		String urlstr2=urlStr.substring(urlStr.lastIndexOf("_"));

		System.out.println(urlstr2);


		//urlStr = urlStr.replace("yyyymmdd", Util.getCurrentDate("yyyyMMdd"))+urlstr1.replace("hhmmss", Util.getCurrentDate("hhmmss"));
		urlStr=urlstr1.replace("yyyymmdd", Util.getCurrentDate("yyyyMMdd"))+urlstr2.replace("hhmmss", Util.getCurrentDate("hhmmss"))+"eof=true";

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);


		return getInputStreamAsString();
	}




} 


