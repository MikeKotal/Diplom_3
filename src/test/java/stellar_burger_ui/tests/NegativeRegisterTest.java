package stellar_burger_ui.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import stellar_burger_ui.helpers.Initializer;
import stellar_burger_ui.page_objects.StellarBurgerGeneralPage;
import stellar_burger_ui.page_objects.StellarBurgerLoginPage;
import stellar_burger_ui.page_objects.StellarBurgerRegisterPage;

import java.io.IOException;
import java.util.Random;

import static stellar_burger_ui.helpers.Constants.GENERAL_PAGE;

@RunWith(Parameterized.class)
public class NegativeRegisterTest extends Initializer {

    private WebDriver driver;
    private String name;
    private String email;
    public String password;

    public NegativeRegisterTest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] getRegisterInfo() {
        Random random = new Random();
        return new Object[][] {
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "1"},
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "Q"},
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "Fifth"}
        };
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

    @Test
    @DisplayName("Check registration new user and password field")
    public void checkRegistrationNegative() throws IOException {
        driver = createDriver();
        driver.get(GENERAL_PAGE);
        StellarBurgerGeneralPage generalPage = new StellarBurgerGeneralPage(driver);
        StellarBurgerLoginPage loginPage = new StellarBurgerLoginPage(driver);
        StellarBurgerRegisterPage registerPage = new StellarBurgerRegisterPage(driver);

        generalPage.waitLoginButtonPage();
        generalPage.clickLoginAccountButton();
        loginPage.waitLoginButton();
        loginPage.clickRegisterButton();
        registerPage.waitRegisterButton();
        registerPage.fillRegisterInfo(name, email, password);

        registerPage.clickRegisterButton();
        Assert.assertTrue("Отсутствует ошибка длины пароля", registerPage.isIncorrectPassword());
    }

}
