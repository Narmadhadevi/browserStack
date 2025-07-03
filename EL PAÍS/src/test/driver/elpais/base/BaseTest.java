package elpais.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected static WebDriver driver;

    @Parameters({"executionMode", "browser", "os","osVersion", "device"})
    @BeforeClass
    public void setupSuite(@Optional("browserstack") String executionMode,
                           @Optional("chrome") String browser,
                           @Optional("") String os,
                           @Optional("") String osVersion,
                           @Optional("") String device) throws MalformedURLException {

        if (executionMode.equalsIgnoreCase("browserstack")) {
            String username = "narmadhadevimj_Vfy0dw";
            String accessKey = "qzokreamcWdjX3zGFK58";

            DesiredCapabilities caps = new DesiredCapabilities();

            caps.setCapability("browserName", browser);

            Map<String, Object> bstackOptions = new HashMap<>();

            if (device.isEmpty()) {
                if (!os.isEmpty()) {
                    bstackOptions.put("os", os);
                }
                bstackOptions.put("osVersion", osVersion);
            } else {
                bstackOptions.put("deviceName", device);
                bstackOptions.put("realMobile", "true");
            }

            bstackOptions.put("buildName", "Final Run of Build");
            bstackOptions.put("sessionName", "ElPais Opinion Tests");
            bstackOptions.put("projectName", "ElPais Automation");
            bstackOptions.put("seleniumVersion", "4.20.0");

            caps.setCapability("bstack:options", bstackOptions);
            driver = new RemoteWebDriver(
                    new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), caps);

        }
        driver.get("https://elpais.com/");
    }

    @AfterSuite
    public void tearDownSuite() {
        if (driver != null) {
            driver.quit();
        }
    }
}