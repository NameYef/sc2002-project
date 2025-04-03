package projectTest;



//the class project excel data will implement these methods, which are subsequently
//inherited by project manager, HDB officer and applicant
//accessibility of certain methods will be controlled. 
 interface ICollectprojectReadOnlydata {
	void showProjectExcelData();
	void collectFromProjectExcel();
	
}
/*
 interface IProjectManagerExcel{
	 void addtoProjectExcel();
	 void deletefromProjectExcel();
 }
*/