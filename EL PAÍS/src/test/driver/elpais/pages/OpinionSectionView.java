package elpais.pages;


import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<String> fetchFirstFiveArticleTitlesWithImages() {
        List<String> titles = new ArrayList<>();

        List<WebElement> articles = driver.findElements(By.cssSelector("section article"));
        int total = Math.min(5, articles.size());

        for (int i = 0; i < total; i++) {
            WebElement article = articles.get(i);

            String title = article.findElement(By.cssSelector("h2 a")).getText();
            titles.add(title);
            System.out.println("Original Title: " + title);

            String content = "";
            List<WebElement> descriptions = article.findElements(By.cssSelector("p.c_d"));
            if (!descriptions.isEmpty()) {
                content = descriptions.get(0).getText();
            }
            System.out.println("Content: " + content);

            List<WebElement> imageElements = article.findElements(By.cssSelector("img"));
            if (!imageElements.isEmpty()) {
                String imageUrl = imageElements.get(0).getAttribute("src");

                if (imageUrl != null && !imageUrl.isEmpty()) {
                    if (imageUrl.startsWith("//")) {
                        imageUrl = "https:" + imageUrl;
                    } else if (imageUrl.startsWith("/")) {
                        imageUrl = "https://elpais.com" + imageUrl;
                    }

                    System.out.println("Attempting to download image from: " + imageUrl);

                    boolean success = downloadImage(imageUrl, sanitizeFileName(title) + ".jpg");
                    if (success) {
                        System.out.println("Image downloaded: " + sanitizeFileName(title) + ".jpg");
                    }
                } else {
                    System.out.println("Image URL is empty or null.");
                }
            } else {
                System.out.println("No image found.");
            }

            System.out.println("-------------------------------------------");
        }

        return titles;
    }

    private boolean downloadImage(String imageUrl, String fileName) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.createDirectories(Paths.get("images"));
            Files.copy(in, Paths.get("images/" + fileName), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.out.println("Error while downloading image from URL: " + imageUrl);
            System.out.println("Error Details: " + e.getMessage());
            return false;
        }
    }

    private String sanitizeFileName(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\-_\\.]", "_");
    }

    public List<String> printTranslatedTitlesForFirstFiveArticles() {
        List<String> originalTitles = fetchFirstFiveArticleTitlesWithImages();
        List<String> translatedTitles = new ArrayList<>();

        System.out.println("Translated Titles:");
        for (String title : originalTitles) {
            String translatedTitle = translateToEnglish(title);
            System.out.println(translatedTitle);
            translatedTitles.add(translatedTitle);
        }

        return translatedTitles;
    }

    private String translateToEnglish(String text) {
        String apiKey = "7a34b92ef5mshc65339c56756f81p168f72jsn2efc8dfe2b16";
        String apiHost = "rapid-translate-multi-traduction.p.rapidapi.com";

        try {
            String urlStr = "https://rapid-translate-multi-traduction.p.rapidapi.com/t";
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("X-RapidAPI-Key", apiKey);
            conn.setRequestProperty("X-RapidAPI-Host", apiHost);
            conn.setDoOutput(true);

            // Build JSON body
            String jsonBody = String.format(
                    "{\"from\":\"es\", \"to\":\"en\", \"e\":\"\", \"q\":[\"%s\"]}",
                    text.replace("\"", "\\\"") // Escape quotes inside text
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            String jsonResponse = response.toString();
            System.out.println("API Raw Response: " + jsonResponse);

            JSONArray jsonArray = new JSONArray(jsonResponse);
            if (jsonArray.length() > 0) {
                String translatedText = jsonArray.getString(0);
                return translatedText;
            } else {
                System.out.println("Empty translation array received.");
                return text;
            }

        } catch (Exception e) {
            System.out.println("Translation failed: " + e.getMessage());
            return text;
        }
    }

    public void printRepeatedWordsFromTranslatedTitles(List<String> translatedTitles) {
        Map<String, Integer> wordCount = new HashMap<>();

        for (String title : translatedTitles) {

            String[] words = title.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");

            for (String word : words) {
                if (word.isEmpty()) continue;
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("\nRepeated Words (Appearing more than 2 times):");
        boolean found = false;
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No words repeated more than twice.");
        }
    }

}
