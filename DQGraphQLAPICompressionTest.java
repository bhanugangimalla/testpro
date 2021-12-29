/*

 * Creation Date: 02/27/19.
 * This test will execute to check the integrity of Json remains constant post compression 
 */package graphQLFinal;

 import java.sql.Timestamp;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.GregorianCalendar;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
 import org.testng.annotations.Test;

import graphQLFinal.DQGRAPHQLAPI;
import com.proview.Configuration;
import com.proview.api.APITestBase;
import com.proview.util.Constants;
 import com.proview.util.DBUtil;



 public class DQGraphQLAPICompressionTest extends APITestBase {
	 // protected TestEnvironment testEnv;

	 public DQGraphQLAPICompressionTest() {


		 super(ApiType.DQGRAPHQLAPI);

	 }

	 private DQGRAPHQLAPI dqGRAPHQLAPI;

	 @BeforeMethod(alwaysRun = true)
	 public void init() throws Exception {
		 /*dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, "", subscriptionKey);
		 String token = dqGRAPHQLAPI.tokenGeneration();
		 dqGRAPHQLAPI = new DQGRAPHQLAPI(endPointUrl, token, subscriptionKey);*/
	 }

	 ArrayList<DBUtil> attestData; 

	 @Test(groups = "DQGRAPHQLAPI")

	 public void compressionTest() throws Exception {

		 //String caqhID = (new Configuration().getDQGraphQLCAQHID());
		 String [] caqhIDs = {"13611371","13611387","13612608","13611372","13611373","13611375","13611377","13611383","13611384","13611385","13611386","13611388","13611389","13612581","13612576","13612582","13612575","13612744","13612751"};
			 //SIT3{"13616293","13616294","13616295","13616296","13616297","13616298","13616303","13616311","13616292","13614162"};
		 String caqhID = null;
		 for(int i=0;i<caqhIDs.length;i++) {
			 caqhID = caqhIDs[i];
		 new DBUtil().getCompressionByteProviewAttestedJsonDataByQuery(caqhID);
		 String src = new DBUtil().getDbDQDAProviderCheckQuery(caqhID);
		 if(src.contains("2")) {
		 new DBUtil().getCompressionByteDAAttestedJsonDataByQuery(caqhID);
		 }
	 }
	 }
 }


