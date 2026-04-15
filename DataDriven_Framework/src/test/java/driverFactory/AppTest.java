package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.*;

import commonFunctions.FunctionLibrary;
import utilis.AppUtil;
import utilis.ExcelFileUtil;

public class AppTest extends AppUtil {

    String fileinputpath ="./DataTables/TestData.xlsx";
    String fileoutputpath ="./DataTables/DataDrivenResults.xlsx";

    ExtentReports report;
    ExtentTest logger;

    @Test
    public void userLoginTest() throws Throwable {

        // Create report
        report = new ExtentReports("./target/ExtentReports/userLogin.html");

        // Excel object
        ExcelFileUtil xl = new ExcelFileUtil(fileinputpath);

        // Row count
        int rc = xl.rowCount("LoginData");
        Reporter.log("No of rows are::"+rc,true);

        // Loop through data
        for(int i=1;i<=rc;i++)
        {
            logger = report.startTest("User Login Test");

            String username = xl.getCelldata("LoginData", i, 0);
            String password = xl.getCelldata("LoginData", i, 1);

            driver.manage().deleteAllCookies();
            driver.get(conpro.getProperty("Url"));

            boolean res = false;

            try {
                res = FunctionLibrary.user_Login(username, password);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                res = false;
            }

            if(res)
            {
                xl.setCelldata("LoginData", i, 2, "Pass", fileoutputpath);
                logger.log(LogStatus.PASS, "Login Success");

                FunctionLibrary.user_Logout();
            }
            else
            {
                File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screen, new File("./target/Screenshots/"+i+"_Login.png"));

                xl.setCelldata("LoginData", i, 2, "Fail", fileoutputpath);
                logger.log(LogStatus.FAIL, "Login Fail");
            }

            report.endTest(logger);
            report.flush();
        }
}
}