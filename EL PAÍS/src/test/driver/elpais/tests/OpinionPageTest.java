package elpais.tests;

import elpais.base.BaseTest;
import elpais.pages.OpinionSectionView;
import org.testng.annotations.Test;

public class OpinionPageTest extends BaseTest {

    @Test
    public void tc3_clickOnOpinion() {
        OpinionSectionView clickToOpen = new OpinionSectionView(driver);
        clickToOpen.clickOpinionOption();
    }

    @Test
    public void tc4_printFirstFiveTitleAndContent() {
        OpinionSectionView printFirstFive = new OpinionSectionView(driver);
        printFirstFive.printFirstFiveArticlesWithImages();
    }
}
