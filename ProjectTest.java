package projectTest;

import java.util.ArrayList; // import the ArrayList class
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class ProjectTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int row;
		
		//to initialize the 2d dynamic array class
		 //source:https://www.geeksforgeeks.org/multidimensional-collections-in-java/
		 ArrayList<ArrayList<String>> projArr = new ArrayList<ArrayList<String>>();
		 String filePath = "C:\\Users\\Ryan\\OneDrive - Nanyang Technological University\\Documents\\Y1S2 AY2024\\SC2002-Object Oriented Design and Programming\\ProjectList.xlsx";
		 row = readFromExcel(filePath, projArr);
		 row-=1;
		 //show the current number of entries
		 System.out.printf("Current no of entries: %d\n\n", row);
		 //to verify that the excel data captured from the 2D array is useable
		 showData(projArr);
		 
		 //write to xlsx
		 writeToExcel(filePath,projArr,row+1);
		 
		 //then read from the excel and get updated number of rows 
		 row = readFromExcel(filePath, projArr);
		 row-=1;
		 //show the current number of entries
		 System.out.printf("Current no of entries: %d\n\n", row);
		 //to verify that the excel data captured from the 2D array is useable
		 showData(projArr);
		 
		 
	}
	
	
	
	public static int readFromExcel(String filePath, ArrayList<ArrayList<String>> projArr ) {
        String strIndex ; 
        int norow = 0;
        int nocolumn = 0;
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("\nReading Excel data:");

            //traverse through each row of excel sheet
            for (Row row : sheet) {
            	//traverse through each cell of excel row
            	ArrayList<String> rowEntry = new ArrayList<>();
                for (Cell cell : row) {
                	//at every individual cell, verify the data type of the cell
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            strIndex = String.valueOf(cell.getNumericCellValue());
                            break;
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            strIndex = cell.getStringCellValue();
                            break;
                        default:
                        	strIndex = "";
                            System.out.print("[UNKNOWN]\t");
                         
                       
                    }//end switch
                  rowEntry.add(strIndex);
                  nocolumn+=1;
                }//end cell(column) traversal
                //add entire row to the array list 
                projArr.add(rowEntry);
                System.out.println();
                norow+=1;
                nocolumn = 0;
            }//end row traversal
        } 
        catch (Exception e) {
            System.err.println("Error reading from Excel:");
            e.printStackTrace();
        }//exception is when excel cant be read

        return norow;
    }
	
	
	public static void showData(ArrayList<ArrayList<String>> projArr) {
		System.out.println("Showing captured data:");
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
	
	//TEST ONLY: Final key in version SHOULD NOT have this method of implementation
	//this function is just to test the mechanism of writing to the excel
	public static void writeToExcel(String filePath, ArrayList<ArrayList<String>> projArr, int startingRow) {
		//first, try running the write operation
		//https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFWorkbook.html
		try (FileInputStream fis = new FileInputStream(filePath)) {
			Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            fis.close();
            
            // Add sample data
            Object[][] data = {
                {"Champions court", "Woodlands", "4-room", 2 , 480000, "5-room", 1, 520000, 260703, 250705, "Desmond", 3, "Raymond"},
                {"Bayshore Palms", "Bedok", "4-room", 2 , 400000, "5-room", 1, 480000, 260723, 250706, "David", 4, "Richmond"},
                {"Crawford Heights", "Kallang", "4-room", 1 , 560000, "5-room", 1, 630000, 270721, 250705, "Davoudi", 5, "Richard"}
            };
            int rowCount = sheet.getLastRowNum();
            
            for (int i = 0; i < data.length; i++) {
            	//start a new row
            	Row row = sheet.createRow(++rowCount);
            	//go through every cell in each row 
                row.createCell(0).setCellValue(data[i][0].toString());
                row.createCell(1).setCellValue(data[i][1].toString());
                row.createCell(2).setCellValue(data[i][2].toString());
                row.createCell(3).setCellValue((Integer)data[i][3]);
                row.createCell(4).setCellValue((Integer)data[i][4]);
                row.createCell(5).setCellValue(data[i][5].toString());
                row.createCell(6).setCellValue((Integer)data[i][6]);
                row.createCell(7).setCellValue((Integer)data[i][7]);
                row.createCell(8).setCellValue((Integer)data[i][8]);
                row.createCell(9).setCellValue((Integer)data[i][9]);
                row.createCell(10).setCellValue(data[i][10].toString());
                row.createCell(11).setCellValue((Integer)data[i][11]);
                row.createCell(12).setCellValue(data[i][12].toString());
                
            }  
            // Auto-size columns for each row
            for (int i = 0; i < 3; i++) {
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
	}
	

}
