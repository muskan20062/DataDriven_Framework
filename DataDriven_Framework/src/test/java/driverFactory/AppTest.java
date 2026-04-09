package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;

import org.testng.annotations.Test;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilis.AppUtil;

import utilis.ExcelFileUtil;

public class AppTest extends AppUtil
{
String fileinputpath ="./DataTables/TestData.xlsx";
String fileoutputpath ="./DataTables/DataDrivenResults.xlsx";
ExtentReports report;
ExtentTest logger;
@Test
public void startTest() throws Throwable
{
	
	report = new ExtentReports("./target/ExtentReports/userLogin.html");
	
	ExcelFileUtil xl = new ExcelFileUtil(fileinputpath);
	
	int rc = xl.rowCount("LoginData");
	Reporter.log("No of rows are::"+rc,true);
	for(int i=1;i<=rc;i++)
	{
		logger= report.startTest("Validate login");
		logger.assignAuthor("ranga");
		
		String username = xl.getCelldata("LoginData", i, 0);
		String password = xl.getCelldata("LoginData", i, 1);
		logger.log(LogStatus.INFO,username+"-------"+ password);
		
		boolean res = FunctionLibrary.user_Login(username, password);
		if(res)
		{
			
			xl.setCelldata("LoginData", i, 2, "Pass", fileoutputpath);
			logger.log(LogStatus.PASS, "Login Success");
		}
		else
		{
			File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screen, new File("./target/Screenshot/"+i+"  "+"Loginpage.png"));
			//if res is false write as Fail into status cell
			xl.setCelldata("LoginData", i, 2, "Fail", fileoutputpath);
			logger.log(LogStatus.FAIL, "Login Fail");
		}
		report.endTest(logger);
		report.flush();
		
	}
}



}
