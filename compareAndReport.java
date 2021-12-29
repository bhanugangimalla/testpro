/*This Class is used for comparing and Reporting for field level validations of Queries*/
package graphQLFinal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import graphQLFinal.ConversionKeywords;
import graphQLFinal.excelReport;
import com.proview.Configuration;
import com.proview.api.APITestBase;
import com.proview.util.Constants;

import net.minidev.json.parser.ParseException;

public class compareAndReport extends APITestBase  {

	/*static String temp = null;
		 static String tempSource = null;*/
	static String destValue = null;
	static String sourceValue = null;
	static int rowIndex;
	static String cellValTable = null;
	static String cellValPhyName = null;
	static String cellValTableSource = null;
	static String cellValPhyNameSource = null;
	//Constants.DQProviewJSONFile,Constants.DQDAJSONFile,Constants.DQGraphQLResponseJSONFile
	String GraphQLResponseFile = Constants.DQGraphQLResponseJSONXMLFile;//"C:\\\\Users\\\\rahul.jaman.jyothi\\\\Desktop\\\\JSON_XPATH\\\\GraphQLPoc\\\\NewGraphQLJson.xml";
	String ProviewAzureFile = Constants.DQProviewJSONXMLFile;//"C:\\Users\\rahul.jaman.jyothi\\Desktop\\JSON_XPATH\\GraphQLPoc\\ProviewXML.xml";
	String DAAzureFile = Constants.DQDAJSONXMLFile;//"C:\\Users\\rahul.jaman.jyothi\\Desktop\\JSON_XPATH\\GraphQLPoc\\DAXML.xml";
	static String filename = Constants.DQExcelMappingFile;//"C:\\Users\\rahul.jaman.jyothi\\Desktop\\JSON_XPATH\\Josn_Xpath.xlsx";
	static Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
	static int i=2;
	static String xpathAvl = null;
	static String ss = Constants.Provider_SSN;

	public compareAndReport() {

		super(ApiType.DQGRAPHQLAPI);

	}

	public void DQGraphQLAPITestJSON(String query,String id,int counter) throws ParseException, IOException, JSONException, XPathExpressionException, SAXException, ParserConfigurationException {


		FileInputStream fis = null;
		String sheetName = "";
		if(counter == 0) {
			if (query.equals("NPIID")) {
				sheetName = new Configuration().getDQByNpiIDQueryXmlMapping();
			}
			else if(query.equals("TAXID")){
				sheetName = new Configuration().getDQByTaxIDQueryXmlMapping();
			}else {
				sheetName = new Configuration().getDQByCaqhIDQueryXmlMapping();//"XMLMapping";

			}}
		else if(counter == 1) {
			sheetName = new Configuration().getDQByCaqhIDsQueryXmlMapping(); ;//"XMLMapping";
		}else  {
			sheetName = new Configuration().getDQByCaqhIDsQueryXmlMappingTwo();
		}


		try {

			fis = new FileInputStream(filename);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			Iterator rowIter = sheet.rowIterator(); 

			while(rowIter.hasNext()){
				XSSFRow myRow = (XSSFRow) rowIter.next();
				Iterator cellIter = myRow.cellIterator();
				Vector<String> cellStoreVector=new Vector<String>();
				while(cellIter.hasNext()){
					XSSFCell myCell = (XSSFCell) cellIter.next();
					String cellvalue = myCell.getStringCellValue();
					cellStoreVector.addElement(cellvalue);
				}
				String responseXpath = null;
				String sourceXpath = null;
				String sourceSystem = null;
				String conversionKeyword = null;

				int i = 0;
				responseXpath = cellStoreVector.get(i).toString();
				sourceXpath = cellStoreVector.get(i+1).toString();
				sourceSystem = cellStoreVector.get(i+2).toString();
				conversionKeyword = cellStoreVector.get(i+3).toString();

				/*System.out.println("responseXpath"+responseXpath);
					 System.out.println("sourceXpath"+sourceXpath);
					 System.out.println("sourceSystem"+sourceSystem);*/


				dataValidation(responseXpath ,sourceXpath,sourceSystem,conversionKeyword);

			}
			new excelReport().createAndWriteReport(data,query,id);
			data.clear();
			/*GraphQLResponseFile = Constants.DQGraphQLResponseJSONXMLFile;
				 ProviewAzureFile = Constants.DQProviewJSONXMLFile;
				 DAAzureFile = Constants.DQDAJSONXMLFile;;*/
			//softAssert.assertAll();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			if (fis != null) {

				fis.close();

			}

		}

	}

	public void dataValidation(String responseXpath,String sourceXpath,String sourceSystem,String conversionKeyword) throws XPathExpressionException, UnsupportedEncodingException, SAXException, IOException, ParserConfigurationException {

		if(!responseXpath.equals("ResponseXpath")) {
			if(conversionKeyword.equals(null)||conversionKeyword.equals("")) {

				destValue = getValueByXpath(responseXpath,GraphQLResponseFile);

			}else {

				String actDestValue = getValueByXpath(responseXpath,GraphQLResponseFile);
				destValue = new ConversionKeywords().conversionLogics(actDestValue, conversionKeyword);
			}

			if(sourceSystem.equals("DirectAssure")) {
				if(!((DAAzureFile==null)||(DAAzureFile==""))) {
					sourceValue = getValueByXpath(sourceXpath,DAAzureFile);

				}else {

					sourceValue = "DA file doesnt exists might be a Proview Provider";
				}

			}
			else if(sourceSystem.equals("ProView"))  {

				if(!((ProviewAzureFile==null)||(ProviewAzureFile==""))) {

					sourceValue = getValueByXpath(sourceXpath,ProviewAzureFile);
				}else {

					sourceValue = "Proview file doesnt exists something went wrong";


				}}

		}

		softAssert.assertEquals(destValue,sourceValue,"responseXpath is:"+responseXpath+"sourceXpath is:"+sourceXpath+"and sourceSystem is:"+sourceSystem);
		if(!responseXpath.equals("ResponseXpath")) {	 
			if(destValue.equals(sourceValue)) {
				putResult(destValue,sourceValue,"PASS",sourceSystem,responseXpath,sourceXpath,conversionKeyword);
			}
			else if(destValue.equals("Xpath not avaliable in File")||sourceValue.equals("Xpath not avaliable in File")) {
				putResult(destValue,sourceValue,"WARNING",sourceSystem,responseXpath,sourceXpath,conversionKeyword);
			}
			else if(sourceValue.equals("DA file doesnt exists might be a Proview Provider")) {
				putResult(destValue,sourceValue,"NO DA File",sourceSystem,responseXpath,sourceXpath,conversionKeyword);
			}
			else if(sourceValue.equals("Proview file doesnt exists something went wrong")) {
				putResult(destValue,sourceValue,"ALERT",sourceSystem,responseXpath,sourceXpath,conversionKeyword);
			}
			else {
				putResult(destValue,sourceValue,"FAIL",sourceSystem,responseXpath,sourceXpath,conversionKeyword);
			}

		}else {
			putResult("ResponseValue", "SourceValue", "Status","SourceSystem","ResponseXpath","SourceXpath","ConversionKeyword");
		}



	}
	public String getValueByXpath(String xpath,String fileName) throws XPathExpressionException, UnsupportedEncodingException, SAXException, IOException, ParserConfigurationException {
		try {
			File daFile = new File(fileName);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(daFile);
			XPath path = XPathFactory.newInstance().newXPath();
			NodeList node = (NodeList) path.compile(xpath).evaluate(doc, XPathConstants.NODESET);
			if(node.item(0).getTextContent()!= null && node.getLength() > 0) {
				xpathAvl = node.item(0).getTextContent();
				System.out.println(xpathAvl);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			xpathAvl = "Xpath not avaliable in File";
		}
		return xpathAvl;

	}

	public void putResult(String destVal,String sourceVal,String status,String sourceSystem,String responseXpath,String sourceXpath,String conversionKeyword) {

		while(i>1) {
			data.put(i, new Object[]{ destVal, sourceVal, status, sourceSystem, responseXpath, sourceXpath,conversionKeyword}); 
			i++;
			break;
		}	
	}

}
