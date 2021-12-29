package graphQLFinal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.proview.Configuration;

public class DQSftp {
	protected Session session ;
	protected Channel channel ;
	protected SoftAssert softAssert;
	//	protected Configuration configuration;
	protected ChannelSftp channelSftp = null;

	//@Test

	//method to download files from sftp folder
	public String sftpJobBatchFileDownload(String jobID) throws SftpException, JSchException, InterruptedException {

		//String jobID = "1042_FullRoster_20190118122717";
		String poID = jobID.substring(0, jobID.indexOf("_"));
		String filesDir = System.getProperty("user.home") + "\\Documents\\"+jobID+"\\";
		JSch jsch = new JSch();

		session = jsch.getSession(new Configuration().getDQSFTPUser(), new Configuration().getDQSFTPHost(), new Configuration().getDQSFTPPort());
		session.setPassword(new Configuration().getDQSFTPPASSWORD());
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		Thread.sleep(5000);
		session.setConfig(config);
		session.connect();
		System.out.println(session.isConnected());
		Assert.assertTrue(session.isConnected(),"Session is not connected");
		channel = session.openChannel("sftp");
		channel.connect();
		channelSftp = (ChannelSftp) channel;

		channelSftp.cd(poID+new Configuration().getDQSFTPWorkingOutDir());
		channelSftp.cd(jobID);
		Vector<ChannelSftp.LsEntry> list = channelSftp.ls("*Master_*");
		System.out.println("Size:"+list.size());
		String [] filenames = new String[list.size()];
		int i=0;
		for(ChannelSftp.LsEntry entry:list) {

			filenames[i] = entry.getFilename();
			System.out.println(entry.getFilename());
			i++;
		}
		System.out.println("array:"+Arrays.asList(filenames));
		int counter;
		//once the file is available we download it and exit the loop
		for(counter = 0;counter<filenames.length;counter++) {
			//String baselinePath = System.getProperty("user.home") + "\\Documents\\";

			File directory = new File(String.valueOf(filesDir));

			if(!directory.exists()){
				directory.mkdir();
			}
			System.out.println("Roster Exception file available in server");
			channelSftp.get(filenames[counter], filesDir + filenames[counter]);
			System.out.println("Roster Exception file downloaded sucessfully");
		}
		return filesDir;
	}

	//method to read the folder where sftp files are downloaded and capture Caqh IDs
	public ArrayList readFilesCaptureCaqh(String filesDir) throws IOException {
		ArrayList<String> allCaqhIDs = new ArrayList<String>();
		File folder = new File(filesDir);
		File[] listOfFiles = folder.listFiles();
		String srtWord = "caqhProviderId\":";
		String endWord = ",";
		for(int i=0;i<listOfFiles.length;i++) {
			String fileName = listOfFiles[i].toString();
			String fileContents = Files.readAllLines(Paths.get(fileName)).toString();
			System.out.println("fileContents:"+fileContents);
			if(fileContents.contains("caqhProviderId")) {
				String[] CaqhID = getCaqhIDsFromFile(fileContents,srtWord,endWord);
				System.out.println("CaqhID:"+Arrays.asList(CaqhID));
				for(int count = 0;count<CaqhID.length;count++) {

					allCaqhIDs.add(CaqhID[count]);
				}

			}else {
				System.out.println("Caqh ID isnt present in File:"+fileName);
			}

		}
		System.out.println("CaqhIDs:"+allCaqhIDs);
		return allCaqhIDs;

	}
	//to check all the files are in proper Json format from SFTP Folder
	public ArrayList jsonFormatCheck(String filesDir) throws IOException {
		ArrayList<String> fileFormatVar = new ArrayList<String>();
		File folder = new File(filesDir);
		File[] listOfFiles = folder.listFiles();
		for(int i=0;i<listOfFiles.length;i++) {
			String fileName = listOfFiles[i].toString();
			String fileContents = Files.readAllLines(Paths.get(fileName)).toString();
			try {
			       ObjectMapper mapper = new ObjectMapper();
			       mapper.readTree(fileContents);
			       
			    } catch (IOException e) {
			    	fileFormatVar.add(fileName+": format is invalid");
			    	} 
			

		}
		return fileFormatVar;

	}
	//to capture the output response Json from SFTP file for a given CaqhID
	public String captureJsonFromFileforCaqhID(String filesDir,String caqhID) throws IOException {
		String caqhIDString = "\"caqhProviderId\":#CAQHID#";
		String jsonMessage = "";
		caqhIDString = caqhIDString.replace("#CAQHID#", caqhID);
		File folder = new File(filesDir);
		File[] listOfFiles = folder.listFiles();
		String messageStart = "practitionerProfileDetail\":";
		String messageEnd = "}]}";
		String temp = "";
		int startIndexMessage;
		int endIndexMessage;
		for(int i=0;i<listOfFiles.length;i++) {
			String fileName = listOfFiles[i].toString();
			String fileContents = Files.readAllLines(Paths.get(fileName)).toString();
			System.out.println("fileContents:"+fileContents);
			if(fileContents.contains(caqhIDString)) {
				temp = fileContents.substring(0, fileContents.indexOf(caqhIDString));
				startIndexMessage = temp.lastIndexOf(messageStart);
				endIndexMessage = fileContents.indexOf(messageEnd, startIndexMessage);
				jsonMessage = fileContents.substring(startIndexMessage, endIndexMessage);

			}else {
				System.out.println("Caqh ID isnt present in File:"+fileName);
			}

		}
		return jsonMessage;

	}
	//capture all the Caqh IDs present in a file
	private String[] getCaqhIDsFromFile(String str, String word,String nxtWord)  
	{ 
		int counter = 0;

		int index = 0;
		int indexNxt;

		int count = StringUtils.countMatches(str, word);
		String locationIDs []= new String[count+1];
		while(index >= 0) {
			index = str.indexOf(word, index+1);
			indexNxt = str.indexOf(nxtWord, index);
			locationIDs[counter] = str.substring(index+word.length(), indexNxt).replaceAll("\"", "").replaceAll(",", "").replaceAll(":", "");
			counter++;
		}
		return Arrays.copyOf(locationIDs, locationIDs.length-1);
	}

	@BeforeSuite(alwaysRun=true)
	public void aaaLoginFileName() throws IOException{
		DateFormat format = new SimpleDateFormat("MM-dd-yy-HH-mm-ss");
		if(System.getProperty("logFileName") == null){
			System.setProperty("logFileName", "log-" + format.format(new Date()) + ".txt");
		}


	}



}

