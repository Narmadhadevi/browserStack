package elpais.base;

import elpais.utils.WebDriverSetup;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected static WebDriver driver;

    @BeforeSuite
    public void setUpSuite() {
        WebDriverSetup setup = new WebDriverSetup();
        driver = setup.initializeDriver();
        driver.manage().window().maximize();
        driver.get("https://elpais.com/");
    }

    @AfterSuite
    public void tearDownSuite() {
        if (driver != null) {
            driver.quit();
        }
    }
}
