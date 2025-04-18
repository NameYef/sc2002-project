package com.project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.io.FileInputStream;
import java.io.FileOutputStream;



public class ExcelWriter {

	
	public void writetoExcel(List<Project> Projects, String filePath) throws IOException {
		//reference: https://poi.apache.org/components/spreadsheet/quick-guide.html
		//instantiate workbook object
		Workbook book = new  XSSFWorkbook();
		
		//name the sheet
		Sheet sheet = book.createSheet("Manager sheet");
		
		//create header column
		Row excelrows = sheet.createRow(0);
		
		//reference for manually setting style of cell: https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFWorkbook.html#createCellStyle--
		//reference for cellstyle class:https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/CellStyle.html
		
		
		//instantiate the cellstyle class 
		CellStyle dateFormat = book.createCellStyle();
		//call the setDataformat method of the cellstyle class
		//using the creation helper method of the book object, add a custom format of the localDate
		//in the desired format of dd, MM, yyyy
		//reference for the creation helper class, create and getformat methods: 
		//https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFCreationHelper.html
		dateFormat.setDataFormat(book.getCreationHelper().createDataFormat().getFormat("dd MM yyyy"));
		
		//now expand the header row to columns, currently 12 cols, pls edit to include your new manager col
		excelrows.createCell(0).setCellValue("Project Name");
		excelrows.createCell(1).setCellValue("Neighbourhood");
		excelrows.createCell(2).setCellValue("Type 1");
		excelrows.createCell(3).setCellValue("Number of units for Type 1");
		excelrows.createCell(4).setCellValue("Selling price for Type 1");
		excelrows.createCell(5).setCellValue("Type 2");
		excelrows.createCell(6).setCellValue("Number of units for Type 2");
		excelrows.createCell(7).setCellValue("Selling price for Type 2");
		excelrows.createCell(8).setCellValue("Application opening date");
		excelrows.createCell(9).setCellValue("Application closing date");
		excelrows.createCell(10).setCellValue("Manager");
		excelrows.createCell(11).setCellValue("Officer slot");
		excelrows.createCell(12).setCellValue("Officer name");
		//commented this out, you can reinsert it at any cell you like
		//then renumber the cells after it
		//excelrows.createCell(13).setCellValue("Manager NRIC");
		
		//obtain the number of entries from project list
		int rowCount = Projects.size();
		//now, get the total number of cells for each entry. 13. 
		int headerCount = 13;
		//uncomment this one to use the NRIC version
		//int headerCount = 14;
		
		//reference for the xssfcell class and methods: https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFCell.html
		//loop through each row in the project list and excel
		//remember to align the structures. 
		//first row in projectlist is second row in excel sheet, therefore row number
		//always needs to be decremented by 1
		//enter data rows
		for(int j = 1; j<rowCount; j++) {
			Row dataRow = sheet.createRow(j);
			
			dataRow.createCell(0).setCellValue(Projects.get(j-1).getName());
			dataRow.createCell(1).setCellValue(Projects.get(j-1).getNeighborhood());
			dataRow.createCell(2).setCellValue(Projects.get(j-1).getType1());
			dataRow.createCell(3).setCellValue(Projects.get(j-1).getNoType1());
			dataRow.createCell(4).setCellValue(Projects.get(j-1).getPriceType1());
			dataRow.createCell(5).setCellValue(Projects.get(j-1).getType2());
			dataRow.createCell(6).setCellValue(Projects.get(j-1).getNoType2());
			dataRow.createCell(7).setCellValue(Projects.get(j-1).getPriceType2());
			//cell indexes 8 and 9 need to be custom formatted to fit date time
			//reference: https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/XSSFCell.html
			Cell cell = dataRow.createCell(8);
			cell.setCellValue(Projects.get(j).getOpenDate().toString());
			cell.setCellStyle(dateFormat);
			Cell cell2 = dataRow.createCell(9);
			cell2.setCellValue(Projects.get(j).getOpenDate().toString());
			cell2.setCellStyle(dateFormat);
			
			dataRow.createCell(10).setCellValue(Projects.get(j).getName());
			dataRow.createCell(11).setCellValue(Projects.get(j).getName());
			dataRow.createCell(12).setCellValue(Projects.get(j).getName());
		   //uncomment this and reposition if you wish to add NRIC next to managers name
			//Note: you need to retrieve the manager nric from somewhere else
			//projectlist does not contain NRIC by default!
		   //dataRow.createCell(13).setCellValue(nric);
		}
		
		//resize the columns
		for(int j = 0; j<rowCount; j++) {
			sheet.autoSizeColumn(j);
		}
		
		
		//now, write this sheet into manager sheet
		try(FileOutputStream fileOutput = new FileOutputStream("C:\\Users\\Ryan\\sc2002-project\\data\\ProjectList.xlsx")) {
			book.write(fileOutput);
		}//end try
		
		//catch for any errors during the write operation
		catch(Exception e){
			System.out.println("There was an error when writing to excel.");
			System.out.println(e);
		}
		
		//close the workbook
		book.close();
	}
	

}
