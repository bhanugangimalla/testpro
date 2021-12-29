package com.proview.api.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.proview.api.APITestBase;
import com.proview.api.AddPracticeRandomLocationAPI;
import com.proview.api.AddPractiseLocationsAPI;

import com.proview.util.Constants;
import com.proview.util.Util;

public class AddRndomPracticeLocationsAPITest extends APITestBase {

	public AddRndomPracticeLocationsAPITest() {

		super(ApiType.AddPL);

	}
	File file;
	AddPracticeRandomLocationAPI aAddPractiseLocationsAPi;

	String addRosterBaselineDir = "BaselineResponses\\RosterAPIResponse\\AddDARosterAPIResponse\\";

	@BeforeMethod(alwaysRun = true)
	public void init() {
		aAddPractiseLocationsAPi = new AddPracticeRandomLocationAPI(endPointUrl, authorizationKey);
	}

	//@Test(groups = "DAproviderRndomPracticeLocAPI", "DAAPIRegression","APIRegression", dataProvider = "testDataProvider")
	@Test(groups={"DAproviderRndomPracticeLocAP", "DAAPIRegression","APIRegression"}, dataProvider="testDataProvider")
	public void verifyAddDARoster(String organization_id, String tax_id, String practice_name,
			String practice_location_address1, String practice_location_address2, String practice_location_city,
			String practice_location_state, String practice_location_zipcode, String practice_location_province,
			String practice_location_country, String npi_type_2, String po_location_id, String location_type,
			String caqh_provider_id, String po_provider_id, String provider_type,
			String provider_primary_practice_state, String provider_first_name, String provider_middle_name,
			String provider_last_name, String npi_type_1ax, String caqh_provider_location_id, String dea_number,
			String dea_state, String practice_location_address3, String practice_location_address4,String practice_location_address5,String practice_location_address6) throws Exception {
		String response = aAddPractiseLocationsAPi.addDARoster(organization_id, tax_id, practice_name,
				practice_location_address1, practice_location_address2, practice_location_city, practice_location_state,
				practice_location_zipcode, practice_location_province, practice_location_country, npi_type_2,
				po_location_id, location_type, caqh_provider_id, po_provider_id, provider_type,
				provider_primary_practice_state, provider_first_name, provider_middle_name, provider_last_name,
				npi_type_1ax, caqh_provider_location_id, dea_number, dea_state, practice_location_address3,
				practice_location_address4,practice_location_address5,practice_location_address6);
		System.out.println(response);
		String[] split2 = response.split(":");

		System.out.println(aAddPractiseLocationsAPi.getResponseCode());
		String strbatchID = split2[2].replace("\"", "");
		strbatchID = strbatchID.replace("}", "");
		System.out.println(strbatchID);
		Assert.assertEquals(aAddPractiseLocationsAPi.getResponseCode(), 200);
		TimeUnit.SECONDS.sleep(300);

		/*String rosterStatus = aAddPractiseLocationsAPi.getRosterStatus(strbatchID);
		System.out.println(rosterStatus);*/

		/* To Change the API URL to get the JSON response from the batchId*/
		/*Properties properties = new Properties();
		String propertiesFile = System.getProperty("environment");
		if (propertiesFile == null) {
			file = new File("Data//SIT2" + ".properties");
			//throw new RuntimeException("environment variable is not set");
		}
		else{
		properties.load(new FileInputStream(new File("Data\\"+propertiesFile+".properties")));
		}
		endPointUrl = properties.getProperty("DARosterAddPLAPIStatusURL",null);
		
		authorizationKey = encodeAuthorization(properties.getProperty("DARosterAddPLAPIAuthorizationKey",null ));
		aAddPractiseLocationsAPi = new AddPracticeRandomLocationAPI(endPointUrl, authorizationKey);
*/
		
		String rosterStatus = aAddPractiseLocationsAPi.getRosterStatus(strbatchID);
		System.out.println(rosterStatus);
		System.out.println(aAddPractiseLocationsAPi.getResponseCode());
		Assert.assertEquals( aAddPractiseLocationsAPi.getResponseCode(), 202);
		




	}

	@DataProvider(name = "testDataProvider")
	public Iterator<Object[]> dataProvider(Method method) throws FileNotFoundException, IOException {
		System.out.println(method.getName());

		Iterator iter = Util.getTestData1(new File(System.getProperty("user.dir")+File.separator +"Data"+File.separator +"AddPracticeLoc.xlsx"), Constants.environment,
				method.getName());
		System.out.println();
		System.out.println("the iter" + iter);
		return iter;
	}

}
