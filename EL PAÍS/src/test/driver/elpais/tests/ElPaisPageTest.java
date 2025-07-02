package elpais.tests;

import elpais.base.BaseTest;
import elpais.pages.ElPaisInSpanishView;
import org.testng.annotations.Test;

public class ElPaisPageTest extends BaseTest {

    @Test
    public void tc1_popUpHandle() {
        ElPaisInSpanishView handlePopUp = new ElPaisInSpanishView(driver);
        handlePopUp.popUpAccept();
    }

    @Test
    public void tc2_verifyElPaisInSpanish() {
        ElPaisInSpanishView viewPage = new ElPaisInSpanishView(driver);
        viewPage.verifyElPaisInSpanish();
    }
}
