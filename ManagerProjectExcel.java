package projectTest;

//import the libraries for date formatting, for dd/mm/yy and date datatype
import java.util.stream.Collectors;
import java.text.ParseException; // ADD THIS IMPORT
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//in this class, this is a prototype of the add and delete mechanic
//however,instead of directly editing the excel, the array will be edited first
//both the add and delete mechanics will overwrite the excel with the newly edited array,
// to simplify the mechanism


//public class ManagerProjectExcel extends CollectProjectExceldata implements IProjectManagerExcel (old format)

public class ManagerProjectExcel extends CollectProjectExceldata{
    //bogus values are added to test the mechanism of adding and deleting
    
	public void addtoProjectExcel() {
		
		//once again, as with the previous example, adding in a row of nonsense data to test the addition mechanic
		   Object[][] data = {
	                {"Bayshore Palms", "Bedok", "4-room", 2 , 400000, "5-room", 1, 480000, 260723, 250706, "David", 4, "Richmond"},
	                {"Crawford Heights", "Kallang", "4-room", 1 , 560000, "5-room", 1, 630000, 270721, 250705, "Davoudi", 5, "Richard"}
	            };
		 
		 
		   //use the deepstring library for a 2d array. 
		   //reference: https://www.tutorialspoint.com/java/util/arrays_deeptostring.html
		 for (Object[] row : data) {
			  //initialize a temporary 1d array list to contain the new row 
		        ArrayList<String> addRow = new ArrayList<>();
		        for (Object cell : row) {
		            addRow.add(cell.toString());
		        }
		        projArr.add(addRow);
		    }
        
	}
	
	
	public void deletefromProjectExcel() {
		//assume we are deleting the middle row
		int i = 1;
		//remove the row of interest from thw 2d array list
		projArr.remove(i);
		
		
	}
	
	public void updateProjectExcel() {
		//obtain filepath from the protected abstract super super class getter method
		String filePath = getfilePath();
		//reference:https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/text/SimpleDateFormat.html
		// Support multiple date formats including the lazy "8/14/22" format
	  
	    
		try (FileInputStream fis = new FileInputStream(filePath)) {
			Workbook workbook = new XSSFWorkbook();
			
		    Sheet sheet = workbook.createSheet("TestData");
           // fis.close();
            
         //
            int rowCount = projArr.size();
            int headerCell = projArr.get(0).size();
            Row row = sheet.createRow(0);
            //add the first row, which are the header values
            for(int i = 0; i<headerCell;i++) {
            	 row.createCell(i).setCellValue(projArr.get(0).get(i).toString());
            }
            
 
         // Data rows
            for (int j = 1; j < rowCount; j++) {
            	//start a new row
                Row rowdata = sheet.createRow(j);
               
               

                	//return the datatypes of each cell to their original values
                	rowdata.createCell(0).setCellValue(projArr.get(j).get(0).toString());
                	rowdata.createCell(1).setCellValue(projArr.get(j).get(1).toString());
                	rowdata.createCell(2).setCellValue(projArr.get(j).get(2).toString());
                	rowdata.createCell(3).setCellValue(projArr.get(j).get(3).toString());
                	rowdata.createCell(4).setCellValue(projArr.get(j).get(4).toString());
                	rowdata.createCell(5).setCellValue(projArr.get(j).get(5).toString());
                	rowdata.createCell(6).setCellValue(projArr.get(j).get(6).toString());
                	rowdata.createCell(7).setCellValue(projArr.get(j).get(7).toString());
                	rowdata.createCell(8).setCellValue(projArr.get(j).get(8).toString());
                	rowdata.createCell(9).setCellValue(projArr.get(j).get(9).toString());
                	rowdata.createCell(10).setCellValue(projArr.get(j).get(10).toString());
                	rowdata.createCell(11).setCellValue(projArr.get(j).get(11).toString());
                	rowdata.createCell(12).setCellValue(projArr.get(j).get(12).toString());
                    
                	
                }  
            
            
            // Auto-size columns for each row
            for (int i = 0; i < rowCount; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Save file
            FileOutputStream out = new FileOutputStream(filePath);
                workbook.write(out);
                System.out.println("Data written to Excel successfully!");
            
            workbook.close();
            out.close();
            
        } 
	
		//if operation unsuccessful, return error message.
		catch (Exception e) {
            System.err.println("Error writing to Excel:");
            e.printStackTrace();
        }
}//end of update method
		

	
	
	
	
}//end of class

	


