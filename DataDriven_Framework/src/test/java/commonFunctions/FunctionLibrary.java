package commonFunctions;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Reporter;

import utilis.AppUtil;

public class FunctionLibrary extends AppUtil {

    
	
	  
	

	    // ================= LOGIN =================
	    public static boolean user_Login(String user, String pass) throws Throwable {

	        driver.get(conpro.getProperty("Url"));

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        // Click Sign In
	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath(conpro.getProperty("objsignin_button")))).click();

	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath(conpro.getProperty("objsign_in")))).click();

	        // Enter Username
	        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath(conpro.getProperty("objuser"))));
	        username.clear();
	        username.sendKeys(user);

	        // Enter Password
	        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath(conpro.getProperty("objpass"))));
	        password.clear();
	        password.sendKeys(pass);

	        // Click Login
	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath(conpro.getProperty("objsignin")))).click();

	        System.out.println("Login button clicked");

	        try {
	            // ✅ SUCCESS CASE
	            wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath(conpro.getProperty("objprofile_button"))));

	            Reporter.log("Login Successful", true);
	            return true;

	        } catch (TimeoutException e) {

	            // ✅ FAILURE CASE
	            wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath(conpro.getProperty("objlogin_error"))));

	            String errorMsg = getErrorMessage();
	            Reporter.log("Login Failed: " + errorMsg, true);

	            return false;
	        }
	    }

	    // ================= LOGOUT =================
	    public static boolean user_Logout() throws Throwable {

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        try {
	            // Click profile icon
	            WebElement profile = wait.until(ExpectedConditions.presenceOfElementLocated(
	                    By.xpath(conpro.getProperty("objprofile_button"))));

	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", profile);
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", profile);

	            // Wait for Sign Out
	            WebElement signout = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath(conpro.getProperty("objsignout"))));

	            signout.click();

	            // ✅ WAIT FOR LOGIN PAGE AGAIN (IMPORTANT FIX)
	            wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath(conpro.getProperty("objsignin_button"))));

	            Reporter.log("Logout Successful", true);
	            return true;

	        } catch (Exception e) {
	            e.printStackTrace();
	            Reporter.log("Logout Failed", true);
	            return false;
	        }
	    }

	    // ================= ERROR MESSAGE =================
	    public static String getErrorMessage() {
	        try {
	            return driver.findElement(
	                    By.xpath(conpro.getProperty("objlogin_error")))
	                    .getText();
	        } catch (Exception e) {
	            return "No error message displayed";
	        }
	    }
	}
	
	