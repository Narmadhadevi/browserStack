package elpais.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ElPaisInSpanishView {

    WebDriver driver;

    public ElPaisInSpanishView(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindAll({@FindBy(xpath = "//a[text()='Accept and continue']"),
    @FindBy(xpath = "//button[@id='didomi-notice-agree-button']")
    })

    private WebElement agreeContinue;

    public void popUpAccept(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(agreeContinue)).click();
    }

    public void verifyElPaisInSpanish() {
        String language = driver.findElement(By.tagName("html")).getAttribute("lang");
        System.out.println("Website language detected: " + language);
        Assert.assertTrue(language.toLowerCase().startsWith("es"), "Website is not displayed in Spanish!");
    }
}
