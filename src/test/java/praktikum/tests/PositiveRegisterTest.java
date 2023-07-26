package praktikum.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import praktikum.helpers.Initializer;
import praktikum.page_objects.StellarBurgerGeneralPage;
import praktikum.page_objects.StellarBurgerLoginPage;
import praktikum.page_objects.StellarBurgerRegisterPage;

import java.io.IOException;
import java.util.Random;

import static praktikum.clients.ClientsHelper.deleteUserApi;
import static praktikum.clients.ClientsHelper.userAuthToken;
import static praktikum.helpers.Constants.*;

@RunWith(Parameterized.class)
public class PositiveRegisterTest extends Initializer {

    private WebDriver driver;
    private String name;
    private String email;
    public String password;

    public PositiveRegisterTest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] getRegisterInfo() {
        Random random = new Random();
        return new Object[][] {
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "999999"},
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "Кишмиш"},
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "Qwerty"}
        };
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

    @Test
    @DisplayName("Check registration new user and password field")
    public void checkRegistrationPositive() throws IOException {
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
        Assert.assertFalse("Некорректная ошибка длины пароля", registerPage.isIncorrectPassword());
        loginPage.waitLoginButton();
        Assert.assertTrue("Отсутствует окно авторизации",
                driver.findElement(By.xpath(".//button[text()='Войти']")).isDisplayed());
        deleteUserApi(userAuthToken(email, password));
    }
}
