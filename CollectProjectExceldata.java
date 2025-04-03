package projectTest;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;


//notes: For HDB officer and Applicant, the project excel data is READ-ONLY.
//notes: Only the manager is allowed to read and edit the BTO project list
//note 3: In this draft, the data isnt directly read from excel
//one method to collect data from excel and put to 2D array
//cell values all converted to string
//another to write out the collected cell 
//the array copy method can then be freely called out by classes and subclasses to do their operations
//manager class can then be a complete inheritor of the read only superclass and add its onw stuff
//same with officee and applicant, which require their own functions. 
public class CollectProjectExceldata extends ACollectProjectExceldata implements ICollectprojectReadOnlydata{
	
	public ArrayList<ArrayList<String>> projArr = new ArrayList<ArrayList<String>>();
	
	
	@Override
	//method to show the all the BTO project data collated in the excel list
	public void showProjectExcelData() {
		//run the collect operation to fill the local instance of projArr before showing
		System.out.println("Showing captured data:\n");
		int rownum, colnum;
		rownum = 0;
		colnum = 0;
		//traverse each row entry
		//source: https://stackoverflow.com/questions/48471691/search-2d-arraylist-by-user-input-display-row-in-java
		for(rownum=0; rownum<projArr.size();rownum++) {
			//traverse each cell
			for(colnum =0; colnum<projArr.get(rownum).size();colnum++) {
				//add a space for each entry
				System.out.print(projArr.get(rownum).get(colnum) + "\t");
				
			}
			//leave a line after every row
			System.out.print("\n");
		}
	}
	
    
	@Override
	//a method that is only within this class 
	public void collectFromProjectExcel() {
		String filePath = getfilePath();
		String cellItem; 
		//built in apache poi class to format cell data
		//with this class, the returned cell data can be converted to string regardless of data type
		//allowing for straightforward addition to a string arraylist
		DataFormatter strConvert = new DataFormatter();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("\nReading Excel data:\n");

            //traverse through each row of excel sheet
            for (Row row : sheet) {
            	//traverse through each cell of excel row
            	ArrayList<String> rowEntry = new ArrayList<>();
                for (Cell cell : row) {
                	//with this method, the returned cell data can be converted to string regardless of data type
                	//reference:https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/DataFormatter.html
                	cellItem = 	strConvert.formatCellValue(cell);
                			
                   rowEntry.add(cellItem);
                }//end cell(column) traversal
                //add entire row to the array list 
                projArr.add(rowEntry);
            }//end row traversal
        } 
        catch (Exception e) {
            System.err.println("Error! Excel cannot be opened!:");
            e.printStackTrace();
        }//exception is when excel cant be read

      
	}
	
	public ArrayList<ArrayList<String>> getProjArr(){
		return projArr;
	}
	
	

}
