package utilis;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class AppUtil {

    public static WebDriver driver;
    public static Properties conpro;

    @BeforeTest
    public void setUp() throws Throwable {

        conpro = new Properties();
        conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));

        if (conpro.getProperty("Browser").equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        } 
        else if (conpro.getProperty("Browser").equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } 
        else {
            throw new IllegalArgumentException("Browser Value is Not matching");
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

