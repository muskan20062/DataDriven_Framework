package commonFunctions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import utilis.AppUtil;

public class SignupFunction extends AppUtil
{
	public static boolean user_Signup(String fname, String lname,
            String username, String email,
            String password) throws Throwable
{
driver.get(conpro.getProperty("Url"));
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

// 🔹 Click Sign in → Signup
driver.findElement(By.xpath(conpro.getProperty("obj_signin_link"))).click();
driver.findElement(By.xpath(conpro.getProperty("obj_signup_link"))).click();

// 🔹 Enter Signup Details
WebElement firstname = driver.findElement(By.xpath(conpro.getProperty("obj_firstname")));
firstname.clear();
firstname.sendKeys(fname);

WebElement lastname = driver.findElement(By.xpath(conpro.getProperty("obj_lastname")));
lastname.clear();
lastname.sendKeys(lname);

WebElement user = driver.findElement(By.xpath(conpro.getProperty("obj_username")));
user.clear();
user.sendKeys(username);

// 🔥 Dynamic Email
String finalEmail = email + System.currentTimeMillis() + "@gmail.com";

WebElement mail = driver.findElement(By.xpath(conpro.getProperty("obj_email")));
mail.clear();
mail.sendKeys(finalEmail);

WebElement pass = driver.findElement(By.xpath(conpro.getProperty("obj_password")));
pass.clear();
pass.sendKeys(password);

// 🔹 Gender (dropdown)
Select sel = new Select(driver.findElement(By.xpath(conpro.getProperty("obj_gender"))));
sel.selectByIndex(1); // or selectByVisibleText("Female")

// 🔹 Terms checkbox
driver.findElement(By.xpath(conpro.getProperty("obj_terms_checkbox"))).click();

// 🔹 Click Signup
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

WebElement signupBtn = wait.until(
    ExpectedConditions.elementToBeClickable(
        By.xpath(conpro.getProperty("obj_signup_button"))
    )
);

signupBtn.click();

// 🔥 VALIDATION SECTION

// First Name Error
if (isElementPresent(conpro.getProperty("obj_fname_error"))) {
Reporter.log("Invalid First Name", true);
return false;
}

// Last Name Error
if (isElementPresent(conpro.getProperty("obj_lname_error"))) {
Reporter.log("Invalid Last Name", true);
return false;
}

// Username Error
if (isElementPresent(conpro.getProperty("obj_username_error"))) {
Reporter.log("Invalid Username", true);
return false;
}

// Email Error
if (isElementPresent(conpro.getProperty("obj_email_error"))) {
Reporter.log("Invalid Email", true);
return false;
}

// Password Error
if (isElementPresent(conpro.getProperty("obj_password_error"))) {
Reporter.log("Invalid Password", true);
return false;
}

// Gender Error
if (isElementPresent(conpro.getProperty("obj_gender_error"))) {
Reporter.log("Gender not selected", true);
return false;
}

// Checkbox Error
if (isElementPresent(conpro.getProperty("obj_checkbox_error"))) {
Reporter.log("Terms not accepted", true);
return false;
}

// 🔥 SUCCESS VALIDATION
if (driver.findElement(By.xpath(conpro.getProperty("obj_verify_text"))).isDisplayed()) {
Reporter.log("Signup Success - Verify Page Displayed", true);
return true;
}

Reporter.log("Signup Failed - Unknown Error", true);
return false;

}

private static boolean isElementPresent(String xpath) {
try {
return driver.findElement(By.xpath(xpath)).isDisplayed();
} catch (Exception e) {
return false;
}
}
}
