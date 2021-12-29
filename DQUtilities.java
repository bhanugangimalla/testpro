/*this class is used for holding all the reusable Utilities*/
package graphQLFinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class DQUtilities {
	
	public String unZipFloders(String zippedfilesDir) {
		String unZippedfileDir = zippedfilesDir+"allUnZippedFiles\\";
		File directory = new File(String.valueOf(unZippedfileDir));

		if(!directory.exists()){
			directory.mkdir();
		}
		File folder = new File(zippedfilesDir);
		File[] listOfFiles = folder.listFiles();
		for(int i=0;i<listOfFiles.length;i++) {
			String sourceFolderName = listOfFiles[i].toString();
		try {
		    ZipFile zipFile = new ZipFile(sourceFolderName);
		    zipFile.extractAll(unZippedfileDir);
		} catch (ZipException e) {
		    e.printStackTrace();
		}
		
	}
		return unZippedfileDir;

}
	public String fileContentToString(String filePath) {
		
		String fileContent = "";
		try {
			fileContent = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
		
	}
	//converting comma seperated String in an Array
		public String[] stringToArray(String commaSepString) throws IOException{

			char delimitChar = ',';

			//to know the count of the commas so that to initialize the length of an array
			int count = numerOfOccurences(commaSepString,delimitChar);


			String[] caqhIds =  commaSepString.split(",");

			String eligileCaqhIDs []= new String[count+1];

			// converting the String separated by commas in array

			for(int i=0; i<caqhIds.length;i++){

				eligileCaqhIDs[i]=caqhIds[i];
			}
			return eligileCaqhIDs;

		}
		//reading the query from a file to a string
		public String readQueryFile(String file) throws IOException {
			BufferedReader reader = new BufferedReader(new FileReader (file));
			String         line = null;
			StringBuilder  stringBuilder = new StringBuilder();
			String         ls = System.getProperty("line.separator");

			try {
				while((line = reader.readLine()) != null) {
					stringBuilder.append(line);
					stringBuilder.append(ls);
				}

				return stringBuilder.toString();
			} finally {
				reader.close();
			}
		}
		private int numerOfOccurences(String strValue,char delimitChar) {
			int count = 0;
			for (int i = 0; i < strValue.length(); i++) {
				if (strValue.charAt(i) == delimitChar) {
					count++;
				}
			}
			return count;

		}
		public String[] arraysMerge(String[] firstArray,String[] secondArray) {
			
		      String[]mergedArray = new String[firstArray.length+secondArray.length];
		      int count = 0;
		      
		      for(int i = 0; i < firstArray.length; i++) { 
		    	  mergedArray[i] = firstArray[i];
		         count++;
		      } 
		      for(int j = 0; j < secondArray.length;j++) { 
		    	  mergedArray[count++] = secondArray[j];
		      } 
			return mergedArray;
		   } 
}
