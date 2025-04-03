package projectTest;

import java.util.ArrayList; // import the ArrayList class
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class ProjectTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 ManagerProjectExcel manager = new ManagerProjectExcel();
		 ArrayList<ArrayList<String>> projArr;
		 
		//to initialize the 2d dynamic array class
		 //source:https://www.geeksforgeeks.org/multidimensional-collections-in-java/
		 //show the current number of entries
		 System.out.printf("Current List:\n");
		 //to verify that the excel data captured from the 2D array is useable
		 manager.collectFromProjectExcel();
		 manager.showProjectExcelData();
		 
		//Add 2 rows. 
		 System.out.printf("Adding to List:\n");
		 manager.addtoProjectExcel();
		 
		 //show updated list
		 System.out.printf("Updated List:\n");
		 manager.updateProjectExcel();
		 
		 System.out.printf("Current List:\n");
		 //to verify that the excel data captured from the 2D array is useable
		 manager.showProjectExcelData();
		 
		 //delete row 1. 
		 System.out.printf("Deleting List:\n");
		 manager.deletefromProjectExcel();
		 
		 //show updated list
		 System.out.printf("Updated List:\n");
		 manager.updateProjectExcel();
		 
		 System.out.printf("Current List:\n");
		 //to verify that the excel data captured from the 2D array is useable
		 manager.showProjectExcelData();
		 
		 
		 
		 
	}
	
	
	

	

}
