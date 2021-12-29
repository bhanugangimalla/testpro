/*This class is used for converting Json to XML*/
package graphQLFinal;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.testng.annotations.Test;

public class JsontoXmlConvert 

{
	/*private static String InputPath = "C:\\\\Users\\\\rahul.jaman.jyothi\\\\Desktop\\\\JSON_XPATH\\\\GraphQLPoc\\\\DA.json";
    private static String OutputPath = "C:\\\\Users\\\\rahul.jaman.jyothi\\\\Desktop\\\\JSON_XPATH\\\\GraphQLPoc\\\\DAXML.xml";*/
	@Test
    
    
    public void jsonToXML(String InputPath,String OutputPath) throws FileNotFoundException, IOException, JSONException
    {
        //Read JSON File
        long startTime = System.currentTimeMillis();
        String json = readFile(InputPath);//Read File
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Read File Duration: "+duration);
        
        //Convert JSON to XML
        startTime = System.currentTimeMillis();
        String xml = convert(json, "root");//State name of root element tag
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("Process Data Duration: "+duration);
        
        //Write XML File
        startTime = System.currentTimeMillis();
        writeFile(OutputPath, xml);
         endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.println("Write File Duration: "+duration); 
    }
    
    public static String convert(String json, String root) throws JSONException
    {
        org.json.JSONObject jsonFileObject = new org.json.JSONObject(json);
        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<"+root+">" 
                     + org.json.XML.toString(jsonFileObject) + "</"+root+">";
        return xml;
    }
    
    public static String readFile(String filepath) throws FileNotFoundException, IOException
    {
        
        StringBuilder sb = new StringBuilder();
        InputStream in = new FileInputStream(filepath);
        Charset encoding = Charset.defaultCharset();
        
        Reader reader = new InputStreamReader(in, encoding);
        
        int r = 0;
        while ((r = reader.read()) != -1)//Note! use read() rather than readLine()
                                         //Can process much larger files with read()
        {
            char ch = (char) r;
            sb.append(ch);
        }
        
        in.close();
        reader.close();
        
        return sb.toString();
    }
    
    //The below block of code is commented out as it uses java 1.7 version
    /*public static void writeFile(String filepath, String output) throws FileNotFoundException, IOException
    {
        FileWriter ofstream = new FileWriter(filepath);
        try (BufferedWriter out = new BufferedWriter(ofstream)) {
        	String outputStartNode = addCharToNode(output,"<","A",2,".*<[0-9].*",".*\\d+.*");
        	String outputEndNode = addCharToNode(outputStartNode,"</","A",3,".*</[0-9].*",".*\\d+.*");
        	System.out.println("New"+outputStartNode);
        	System.out.println("Total:"+outputEndNode);
            out.write(outputEndNode);
        }
    }*/
    //Alternate to the above block of code
    public static void writeFile(String filepath, String output) throws FileNotFoundException, IOException
    {
    	FileWriter ofstream = new FileWriter(filepath);
    	BufferedWriter out = null;
        try  {
        	out = new BufferedWriter(ofstream);
        
        	String outputStartNode = addCharToNode(output,"<","A",2,".*<[0-9].*",".*\\d+.*");
        	String outputEndNode = addCharToNode(outputStartNode,"</","A",3,".*</[0-9].*",".*\\d+.*");
        	System.out.println("New"+outputStartNode);
        	System.out.println("Total:"+outputEndNode);
            out.write(outputEndNode);
        }
        finally {
      	  if (out != null) {
      	    try {
      	    	out.close();
      	    } catch (Exception e) {
      	      e.printStackTrace();
      	    }
      	  }}
    }
    
    //method to append 'A' character for xml nodes starting with digit for proper formation of the XML
    public static String addCharToNode(String output,String startString,String replaceChar,int counter,String compilepat,String matchpat) {
    	for (int i = 0; (i = output.indexOf(startString, i )) != -1; i++) {
    	    System.out.println("v"+i);
    	    int j=0;
    	    j=i+counter;
    	    String sub = output.substring(i, j);
    	    
    	  System.out.println("sub:"+sub);
    	  Pattern pattern = Pattern.compile(compilepat);
    	  Matcher matcher = pattern.matcher(sub);
    	  if(sub.matches(matchpat)) {
    		  System.out.println("paternback"+i);
    		  output = new  StringBuffer(output.trim()).insert(j-1, replaceChar).toString();
    	  }
    	} 
    	
    	System.out.println("Final:"+output);
    	return output;
    }
}
