package elpais.base;

import elpais.utils.WebDriverSetup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    protected static WebDriver driver;

    @Parameters({"executionMode", "browser", "os", "device"})
    @BeforeSuite
    public void setupSuite(@Optional("local") String executionMode,
                           @Optional("chrome") String browser,
                           @Optional("") String os,
                           @Optional("") String device) throws MalformedURLException {

        if (executionMode.equalsIgnoreCase("browserstack")) {
            String username = "narmadhadevim_mhKvi4";
            String accessKey = "pXj6MXrzEY4xFDvmqqHz";

            DesiredCapabilities caps = new DesiredCapabilities();

            if (device.isEmpty()) {
                caps.setCapability("browserName", browser);
                caps.setCapability("os", os);
            } else {
                caps.setCapability("browserName", browser);
                caps.setCapability("device", device);
                caps.setCapability("realMobile", true);
            }

            caps.setCapability("project", "ElPais Automation");
            caps.setCapability("build", "Build 1");
            caps.setCapability("name", "ElPais Opinion Tests");

            driver = new RemoteWebDriver(
                    new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), caps);

        } else {
            WebDriverSetup setup = new WebDriverSetup();
            driver = setup.initializeDriver();
        }

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
