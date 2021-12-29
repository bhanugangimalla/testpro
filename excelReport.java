/*This class is used for creating and formatting excel for field level Validations*/
package graphQLFinal;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.proview.util.Constants;

public class excelReport {
	
		public void createAndWriteReport(Map<Integer, Object[]> data,String query,String id) 
		    { 
		        // Blank workbook 
		        //XSSFWorkbook workbook = new XSSFWorkbook(); --
		        SXSSFWorkbook workbook = new SXSSFWorkbook(); 
		  
		        // Create a blank sheet 
		        //XSSFSheet sheet = workbook.createSheet("Result");--
		        SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet("Result");
		        
		        //code to add borders in an excel
		        //XSSFCellStyle style=workbook.createCellStyle();--
		        CellStyle style=workbook.createCellStyle();
		        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		        
		        // Iterate over data and write to sheet 
		        Set<Integer> keyset = data.keySet(); 
		        int rownum = 0; 
		        for (Integer key : keyset) { 
		            // this creates a new row in the sheet 
		            Row row = sheet.createRow(rownum++); 
		            Object[] objArr = data.get(key); 
		            int cellnum = 0; 
		            for (Object obj : objArr) { 
		                // this line creates a cell in the next column of that row 
		                Cell cell = row.createCell(cellnum++); 
		                if (obj instanceof String) 
		                    cell.setCellValue((String)obj); 
		                	
		                else if (obj instanceof Integer) 
		                    cell.setCellValue((Integer)obj); 
		                
		                //below are the steps for beautification of result excel
		                // commit to add borders
		                cell.setCellStyle(style);
		                //to configure column width sizing in excel
		                int columnIndex = cell.getColumnIndex();
		                sheet.autoSizeColumn(columnIndex);
		                //to set color for status column values
//		                XSSFCellStyle cellStyle = workbook.createCellStyle();--
		                CellStyle cellStyle = workbook.createCellStyle();
	                    cellStyle = workbook.createCellStyle();
		                if(obj.equals("PASS")) {
		                    cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		                    cellBeautifycommit(cellStyle,cell);
		                }else if(obj.equals("FAIL")) {
		                	cellStyle.setFillForegroundColor(HSSFColor.RED.index);
		                	cellBeautifycommit(cellStyle,cell);
		                }else if(obj.equals("WARNING")) {
		                	cellStyle.setFillForegroundColor(HSSFColor.ORANGE.index);
		                    cellBeautifycommit(cellStyle,cell);
		                }else if(obj.equals("NO DA File")) {
		                	cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
		                    cellBeautifycommit(cellStyle,cell);
		                }else if(obj.equals("ALERT")) {
		                	cellStyle.setFillForegroundColor(HSSFColor.DARK_RED.index);
		                    cellBeautifycommit(cellStyle,cell);
		                }
		             
		             
		            } 
		            
		            
		        } 
		        try { 
		            // this Writes the workbook appending the timestamp
		        	String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Timestamp(System.currentTimeMillis()));
		        	String resultDir = System.getProperty("user.home") + "\\Documents\\GraphQLResults\\";
		        	File directory = new File(String.valueOf(resultDir));

		        	 if(!directory.exists()){
		        	             directory.mkdir();
		        	 }
		            FileOutputStream out = new FileOutputStream(new File(resultDir+"GraphQLComparisionResult_"+query+"_"+id+"_"+timeStamp+".xlsx")); 
		            workbook.write(out);
		            out.close(); 
		            System.out.println("Result written successfully."); 
		        } 
		        catch (Exception e) { 
		            e.printStackTrace(); 
		        } 
		    } 
	public void cellBeautifycommit(CellStyle cellStyle, Cell cell) { //XSSF--
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderTop((short) 1);
        cellStyle.setBorderBottom((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cell.setCellStyle(cellStyle);
		
	}
		} 
