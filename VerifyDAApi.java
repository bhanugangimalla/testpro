package com.proview.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.proview.api.RequestBase.ContentType;
import com.proview.api.RequestBase.RequestMethod;
import com.proview.util.Util;

public class VerifyDAApi extends RequestBase {

	public VerifyDAApi(String urlStr, String authorizationKey) {
		super(urlStr, authorizationKey);

	}
	
	
	public String  DAApiValidation(String organization_id, String tax_id, String practice_name,
			String practice_location_address1, String practice_location_address2, String practice_location_city,
			String practice_location_state, String practice_location_zipcode, String practice_location_province,
			String practice_location_country, String npi_type_2, String po_location_id, String location_type,
			String caqh_provider_id, String po_provider_id, String provider_type,
			String provider_primary_practice_state, String provider_first_name, String provider_middle_name,
			String provider_last_name, String npi_type_1, String po_provider_location_id, String dea_number,
			String dea_state, String license_number, String license_state, String license_expiration_date,
			String specialty_type, String specialty_taxonomy_code ,String specialty_name)
			throws FileNotFoundException, IOException {
		
		Map<String, String> params = new HashMap<String, String>();

		
		String body = "{\n" + "\"organization_id\":\"" + organization_id + "\",\n" + "\"practice_location\":[\n" + "{"
				+ "\"tax_id\":\"" + tax_id + "\",\n" + "\"practice_name\":\"" + practice_name+Util.getRandomNo(2) + "\",\n"
				+ "\"practice_location_address1\":\"" + practice_location_address1+Util.getRandomNo(3) + "\",\n"
				+ "\"practice_location_address2\":\"" + practice_location_address2+Util.getRandomizedString(3) + "\",\n"
				+ "\"practice_location_city\":\"" + practice_location_city + "\",\n" + "\"practice_location_state\":\""
				+ practice_location_state + "\",\n" + "\"practice_location_zipcode\":\"" + practice_location_zipcode
				+ "\",\n" + "\"practice_location_province\":\"" + practice_location_province + "\",\n"
				+ "\"practice_location_country\":\"" + practice_location_country + "\",\n" + "\"npi_type_2\":\""
				+ npi_type_2 + "\",\n" + "\"po_location_id\":\"" + po_location_id + "\",\n"  + "\"location_type\":\"" 
				+ location_type + "\",\n"+ "\"providers\":[\n" + "{\n" + "\"caqh_provider_id\":\"" + caqh_provider_id
				+ "\",\n" + "\"po_provider_id\":\"" + po_provider_id+ "\",\n" + "\"provider_type\":\"" + provider_type
				+ "\",\n" + "\"provider_primary_practice_state\":\"" + provider_primary_practice_state + "\",\n"
				+ "\"provider_first_name\":\"" + provider_first_name + "\",\n" + "\"provider_middle_name\":\""
				+ provider_middle_name + "\",\n" + "\"provider_last_name\":\"" + provider_last_name + "\",\n"
				+ "\"npi_type_1\":\"" + npi_type_1 + "\",\n" + "\"po_provider_location_id\":\""
				+ po_provider_location_id + "\",\n" + "\"provider_dea\":[\n" + "{" + "\"dea_number\":\"" + dea_number
				+ "\",\n" + "\"dea_state\":\"" + dea_state + "\"\n" + "}\n" + "],\n"  + "\"provider_license\":[\n" + "{" + "\"license_number\":\"" + license_number + "\",\n" + "\"license_state\":\"" + license_state + "\",\n"
				+ "\"license_expiration_date\":\"" + license_expiration_date + "\"\n"+ "}\n" + "],\n" + "\"provider_practice_specialty\":[\n" + "{" 
				+ "\"specialty_type\":\"" + specialty_type + "\",\n"
				+ "\"specialty_taxonomy_code\":\"" + specialty_taxonomy_code + "\",\n" + "\"specialty_name\":\""
				+ specialty_name +"\"" + "\n "+ "}, \n{" + "\"specialty_type\":\"" + specialty_type
				+ "\",\n" + "\"specialty_taxonomy_code\":\"" + specialty_taxonomy_code + "\",\n"
				+ "\"specialty_name\":\"" + specialty_name + "\" \n"  + "}\n" + "]\n"+ "}\n" + "]\n" + "}\n" + "]\n" + "}\n"  ;
		
		String currentDate = Util.getCurrentDate("yyyyMMddHHmmss");
		System.out.println(currentDate);
		String year = currentDate.substring(0, 4);
		String 	month = currentDate.substring(4, 6);
		String day = currentDate.substring(6, 8);
		String hours=currentDate.substring(8, 10);
		String minutes =currentDate.substring(10, 12);
		String seconds  =currentDate.substring(12, 14);
		
		String timestamp = year+month+day +"_"+ hours + minutes + seconds + "&eof=true" ;
		System.out.println(timestamp);
		addParamToUrl(timestamp);
		
		sendRequest(RequestMethod.POST, ContentType.APPLICATION_JSON, params, body);
		
		    return getInputStreamAsString();

	}
	
	
}
