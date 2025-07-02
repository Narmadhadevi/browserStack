package elpais.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class OpinionSectionView {

    WebDriver driver;

    public OpinionSectionView(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOpinionOption() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement opinionLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@cmp-ltrk='portada_menu'][normalize-space()='Opinión']")));
        opinionLink.click();
    }

    public void printFirstFiveArticlesWithImages() {
        List<WebElement> articles = driver.findElements(By.cssSelector("section article"));
        int total = Math.min(5, articles.size());

        for (int i = 0; i < total; i++) {
            WebElement article = articles.get(i);

            String title = article.findElement(By.cssSelector("h2 a")).getText();
            System.out.println("Title: " + title);

            String content = "";
            List<WebElement> descriptions = article.findElements(By.cssSelector("p.c_d"));
            if (!descriptions.isEmpty()) {
                content = descriptions.get(0).getText();
            }
            System.out.println("Content: " + content);

            // Download cover image if available
            List<WebElement> imageElements = article.findElements(By.cssSelector("img"));
            if (!imageElements.isEmpty()) {
                String imageUrl = imageElements.get(0).getAttribute("src");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    downloadImage(imageUrl, sanitizeFileName(title) + ".jpg");
                    System.out.println("Image downloaded: " + sanitizeFileName(title) + ".jpg");
                }
            } else {
                System.out.println("No image found.");
            }

            System.out.println("-------------------------------------------");
        }
    }

    private void downloadImage(String imageUrl, String fileName) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, Paths.get("images/" + fileName));
        } catch (IOException e) {
            System.out.println("Error while downloading image: " + e.getMessage());
        }
    }

    private String sanitizeFileName(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\-_\\.]", "_");
    }
}
