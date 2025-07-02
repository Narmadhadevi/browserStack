package elpais.tests;

import elpais.base.BaseTest;
import elpais.pages.OpinionSectionView;
import org.testng.annotations.Test;

import java.util.List;

public class OpinionPageTest extends BaseTest {

    @Test
    public void tc3_clickOnOpinion() {
        OpinionSectionView clickToOpen = new OpinionSectionView(driver);
        clickToOpen.clickOpinionOption();
    }

    @Test
    public void tc4_printFirstFiveTitleAndContent() {
        OpinionSectionView printFirstFive = new OpinionSectionView(driver);
        printFirstFive.fetchFirstFiveArticleTitlesWithImages();
    }

    @Test
    public void tc5_printTranslatedTitleOfFirstFive() {
        OpinionSectionView printTranslatedFirstFive = new OpinionSectionView(driver);
        printTranslatedFirstFive.printTranslatedTitlesForFirstFiveArticles();
    }

    @Test
    public void tc6_countAndPrintRepeatedWordsInAllTitles() {
        OpinionSectionView countOfRepeatedWord = new OpinionSectionView(driver);
        List<String> translatedTitles = countOfRepeatedWord.printTranslatedTitlesForFirstFiveArticles();
        countOfRepeatedWord.printRepeatedWordsFromTranslatedTitles(translatedTitles);
    }

}
