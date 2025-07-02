package elpais.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ElPaisInSpanishView {

    WebDriver driver;

    public ElPaisInSpanishView(WebDriver driver) {
        this.driver = driver;
    }

    public void popUpAccept(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement acceptOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Accept']")));
        acceptOption.click();
    }

    public void verifyElPaisInSpanish() {
        String language = driver.findElement(By.tagName("html")).getAttribute("lang");
        System.out.println("Website language detected: " + language);
        Assert.assertTrue(language.toLowerCase().startsWith("es"), "Website is not displayed in Spanish!");
    }
}
