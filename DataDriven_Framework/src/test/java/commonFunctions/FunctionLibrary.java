package commonFunctions;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Reporter;

import utilis.AppUtil;

public class FunctionLibrary extends AppUtil {

    
    public static boolean user_Login(String user, String pass) throws Throwable {

        driver.get(conpro.getProperty("Url"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

       
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        
        WebElement signInBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(conpro.getProperty("objsignin_button"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signInBtn);

       
        WebElement signInDrop = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(conpro.getProperty("objsign_in"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signInDrop);

        
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(conpro.getProperty("objuser"))));
        username.clear();
        username.sendKeys(user);

       
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(conpro.getProperty("objpass"))));
        password.clear();
        password.sendKeys(pass);

       
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(conpro.getProperty("objsignin"))));
        loginBtn.click();

        System.out.println("Login button clicked");

       
        Thread.sleep(3000);

       
        if (!isElementPresent(conpro.getProperty("objsignin_button"))) {
            Reporter.log("Login Successful", true);
            return true;
        }

        
        String errorMsg = getErrorMessage();
        Reporter.log("Login Failed: " + errorMsg, true);

        return false;
    }

   
    public static boolean user_Logout() throws Throwable {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement profile = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(conpro.getProperty("objprofile_button"))));
            profile.click();

            WebElement signout = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(conpro.getProperty("objsignout"))));
            signout.click();

            Reporter.log("Logout Successful", true);
            return true;

        } catch (Exception e) {
            Reporter.log("Logout Failed", true);
            return false;
        }
    }

    
    public static boolean isElementPresent(String xpath) {
        try {
            driver.findElement(By.xpath(xpath));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
    public static String getErrorMessage() {
        try {
            return driver.findElement(
                    By.xpath("//div[contains(text(),'not') or contains(text(),'incorrect')]"))
                    .getText();
        } catch (Exception e) {
            return "No error message displayed";
        }
    }
}