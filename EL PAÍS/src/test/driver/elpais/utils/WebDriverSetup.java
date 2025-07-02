package elpais.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverSetup {

    public WebDriver initializeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}
