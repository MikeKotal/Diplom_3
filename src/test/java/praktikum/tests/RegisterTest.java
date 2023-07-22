package praktikum.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import praktikum.pageObjects.StellarBurgerGeneralPage;
import praktikum.pageObjects.StellarBurgerLoginPage;
import praktikum.pageObjects.StellarBurgerRegisterPage;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static praktikum.helpers.Constants.*;

@RunWith(Parameterized.class)
public class RegisterTest {

    private WebDriver driver;
    private String name;
    private String email;
    private String password;
    private String browser;

    public RegisterTest(String name, String email, String password, String browser) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.browser = browser;
    }

    @Parameterized.Parameters
    public static Object[][] getRegisterInfo() {
        Random random = new Random();
        return new Object[][]{
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "12345", CHROME},
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "1", CHROME},
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "123456", CHROME},
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "12345", YANDEX},
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "1", YANDEX},
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru", "123456", YANDEX}
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        RestAssured.baseURI = GENERAL_PAGE;
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

    @Step("Delete test user after creating")
    public void deleteUserApi(String token) {
        given().auth().oauth2(token).when().delete(ENDPOINT_DELETE).then().statusCode(HttpStatus.SC_ACCEPTED);
    }

    @Step("Login test user and get authToken for delete")
    public String userAuthToken(String email, String password) {
        String jsonBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post(ENDPOINT_LOGIN)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getString("accessToken")
                .split(" ")[1];
    }

    @Test
    @DisplayName("Check registration new user and password field")
    public void checkRegistrationAndPassField() {
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
        StellarBurgerLoginPage loginPage = new StellarBurgerLoginPage(driver);
        StellarBurgerRegisterPage registerPage = new StellarBurgerRegisterPage(driver);

        generalPage.waitLoginButtonPage();
        generalPage.clickLoginAccountButton();
        loginPage.waitLoginButton();
        loginPage.clickRegisterButton();
        registerPage.waitRegisterButton();
        registerPage.fillRegisterInfo(name, email, password);

        if (password.length() < 6) {
            registerPage.clickRegisterButton();
            Assert.assertTrue("Отсутствует ошибка длины пароля", registerPage.isIncorrectPassword());
        } else {
            registerPage.clickRegisterButton();
            Assert.assertFalse("Некорректная ошибка длины пароля", registerPage.isIncorrectPassword());
            loginPage.waitLoginButton();
            Assert.assertTrue("Отсутствует окно авторизации",
                    driver.findElement(By.xpath(".//button[text()='Войти']")).isDisplayed());
            deleteUserApi(userAuthToken(email, password));
        }
    }
}
