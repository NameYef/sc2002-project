package projectTest;


public abstract class ACollectProjectExceldata  {
	//change filepath to your pc file path
	private static final String filePath = "C:\\Users\\Ryan\\OneDrive - Nanyang Technological University\\Documents\\Y1S2 AY2024\\SC2002-Object Oriented Design and Programming\\ProjectList.xlsx";
	
	//getter method, to ensure that no one, the manager, hdb officer or applicant,
	//can access filepath
	//rmb, static. filepath SHOULD NOT change
	protected static String getfilePath() {
		return filePath;
	}
	
	
	
	
	
}
