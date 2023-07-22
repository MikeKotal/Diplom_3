package praktikum.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import praktikum.pageObjects.StellarBurgerGeneralPage;

import static praktikum.helpers.Constants.*;

@RunWith(Parameterized.class)
public class ConstructorSectionTests {

    private WebDriver driver;
    private String browser;

    public ConstructorSectionTests(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters
    public static Object[][] getBrowser() {
        return new Object[][] {
                {CHROME},
                {YANDEX}
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

    @Test
    @DisplayName("Check click buttons and scroll to object")
    public void checkSectionButtons() {
        switch (browser) {
            case CHROME:
                driver = new ChromeDriver();
                break;
            case YANDEX:
                ChromeOptions options = new ChromeOptions();
                options.setBinary(LOCAL_PATH_TO_YANDEX_BROWSER);
                driver = new ChromeDriver(options);
        }
        driver.get(GENERAL_PAGE);
        StellarBurgerGeneralPage generalPage = new StellarBurgerGeneralPage(driver);
        generalPage.waitLoginButtonPage();
        generalPage.clickAndCheckSectionButtons();
    }
}
