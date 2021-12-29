package com.proview.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AddPractiseLocationsAPI extends RequestBase {

	public AddPractiseLocationsAPI(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);

	}

	public String getRosterStatus(String batchId) {
		
		urlStr = "http://10.164.28.23:801/DirectAssure/api/POPracticeLocation/" + batchId;
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		return getInputStreamAsString();
	}

	public String getRosterResponse() {
		urlStr = "http://10.164.28.23:801/directassureextractapi/api/v5/entities?organizationId=1042&fromDate=03/06/2018&toDate=03/06/2018";
		sendRequest(RequestMethod.GET, ContentType.APPLICATION_JSON, null);
		return getInputStreamAsString();
	}

	public String addDARoster(String organization_id, String tax_id, String practice_name,
			String practice_location_address1, String practice_location_address2, String practice_location_city,
			String practice_location_state, String practice_location_zipcode, String practice_location_province,
			String practice_location_country, String npi_type_2, String po_location_id, String location_type,
			String caqh_provider_id, String po_provider_id, String provider_type,
			String provider_primary_practice_state, String provider_first_name, String provider_middle_name,
			String provider_last_name, String npi_type_1ax, String caqh_provider_location_id, String dea_number,
			String dea_state, String practice_location_address3, String practice_location_address4)
			throws FileNotFoundException, IOException {

		Map<String, String> params = new HashMap<String, String>();

		String body = "{\n" + "\"organization_id\":\"" + organization_id + "\",\n" + "\"practice_location\":[\n" + "{"
				+ "\"tax_id\":\"" + tax_id + "\",\n" + "\"practice_name\":\"" + practice_name + "\",\n"
				+ "\"practice_location_address1\":\"" + practice_location_address1 + "\",\n"
				+ "\"practice_location_address2\":\"" + practice_location_address2 + "\",\n"
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
				+ "\"tax_id\":\"" + tax_id + "\",\n" + "\"practice_name\":\"" + practice_name + "\",\n"
				+ "\"practice_location_address1\":\"" + practice_location_address3 + "\",\n"
				+ "\"practice_location_address2\":\"" + practice_location_address4 + "\",\n"
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
				+ "\",\n" + "\"dea_state\":\"" + dea_state + "\"\n" + "}\n" + "]\n" + "}\n" + "]\n" + "}\n" + "]\n"
				+ "}\n";

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);

		return getInputStreamAsString();
	}

	public String addRoster(String caqhId, String orgId) {
		Map<String, String> params = new HashMap<String, String>();

		String body = "[{\n" + "\"caqh_provider_id\": \"" + caqhId + "\",\n" + "\"organization_id\": \"" + orgId
				+ "\",\n" + "}]";

		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);

		return getInputStreamAsString();
	}

	public String DADeRoster(String caqhId, String orgId) {
		Map<String, String> params = new HashMap<String, String>();

		String body = "[{\n" + "\"caqh_provider_id\": \"" + caqhId + "\",\n" + "\"organization_id\": \"" + orgId
				+ "\",\n" + "}]";

		sendRequest(RequestMethod.PUT, ContentType.APPLICATION_JSON, params, body);

		return getInputStreamAsString();
	}
}
