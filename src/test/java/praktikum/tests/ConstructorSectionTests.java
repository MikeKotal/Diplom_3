package praktikum.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import praktikum.helpers.Initializer;
import praktikum.page_objects.StellarBurgerGeneralPage;

import java.io.IOException;

import static praktikum.helpers.Constants.*;

@RunWith(Parameterized.class)
public class ConstructorSectionTests extends Initializer {

    private WebDriver driver;
    private String sectionName;

    public ConstructorSectionTests(String sectionName) {
        this.sectionName = sectionName;
    }

    @Parameterized.Parameters
    public static Object[][] getSectionName() {
        return new Object[][] {
                {BUNS},
                {SAUCES},
                {FILLINGS}
        };
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

    @Test
    @DisplayName("Check click buttons and scroll to object")
    public void checkSectionButtons() throws IOException {
        driver = createDriver();
        driver.get(GENERAL_PAGE);
        StellarBurgerGeneralPage generalPage = new StellarBurgerGeneralPage(driver);
        generalPage.waitLoginButtonPage();
        generalPage.checkSectionButtons(sectionName);
    }
}
