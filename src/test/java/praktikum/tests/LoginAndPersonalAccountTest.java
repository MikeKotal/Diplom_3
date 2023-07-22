package praktikum.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import praktikum.pageObjects.*;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static praktikum.helpers.Constants.*;

@RunWith(Parameterized.class)
public class LoginAndPersonalAccountTest {

    private WebDriver driver;
    private String name;
    private String email;
    private String password;
    private String browser;

    public LoginAndPersonalAccountTest(String name, String email, String password, String browser) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.browser = browser;
    }

    @Parameterized.Parameters
    public static Object[][] getInfoNewUser() {
        Random random = new Random();
        return new Object[][]{
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru",
                        "PassP" + random.nextInt(100000), CHROME},
                {"TestT" + random.nextInt(1000), "SomethingS" + random.nextInt(100) + "@yandex.ru",
                        "PassP" + random.nextInt(100000), YANDEX}
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

    @Step("Register test user and get authToken for delete")
    public String createUserAndGetToken(String name, String email, String password) {
        String jsonBody = String.format("{\"name\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}", name, email, password);
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post(ENDPOINT_REGISTER)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getString("accessToken")
                .split(" ")[1];
    }

    @Test
    @DisplayName("Check login user from general page and check exit button")
    public void checkLoginUserFromGeneral() {
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
        StellarBurgerPersonalAccountPage personalAccountPage = new StellarBurgerPersonalAccountPage(driver);
        String accessToken = createUserAndGetToken(name, email, password);

        generalPage.waitLoginButtonPage();
        generalPage.clickLoginAccountButton();

        loginPage.waitLoginButton();
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();

        generalPage.waitOrderButtonPage();
        generalPage.clickPersonalAccountButton();

        personalAccountPage.waitExitButton();
        personalAccountPage.clickExitProfileButton();

        loginPage.waitLoginButton();

        deleteUserApi(accessToken);
    }

    @Test
    @DisplayName("Check login user from personal account button and exit")
    public void checkLoginUserFromPersonalAcc() {
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
        StellarBurgerPersonalAccountPage personalAccountPage = new StellarBurgerPersonalAccountPage(driver);
        String accessToken = createUserAndGetToken(name, email, password);

        generalPage.waitLoginButtonPage();
        generalPage.clickPersonalAccountButton();

        loginPage.waitLoginButton();
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();

        generalPage.waitOrderButtonPage();
        generalPage.clickPersonalAccountButton();

        personalAccountPage.waitExitButton();
        personalAccountPage.clickExitProfileButton();

        loginPage.waitLoginButton();

        deleteUserApi(accessToken);
    }

    @Test
    @DisplayName("Check login user from registration page and exit")
    public void checkLoginUserFromRegister() {
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
        StellarBurgerPersonalAccountPage personalAccountPage = new StellarBurgerPersonalAccountPage(driver);
        StellarBurgerRegisterPage registerPage = new StellarBurgerRegisterPage(driver);
        String accessToken = createUserAndGetToken(name, email, password);

        generalPage.waitLoginButtonPage();
        generalPage.clickLoginAccountButton();

        loginPage.waitLoginButton();
        loginPage.clickRegisterButton();

        registerPage.waitRegisterButton();
        registerPage.clickLoginButton();

        loginPage.waitLoginButton();
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();

        generalPage.waitOrderButtonPage();
        generalPage.clickPersonalAccountButton();

        personalAccountPage.waitExitButton();
        personalAccountPage.clickExitProfileButton();

        loginPage.waitLoginButton();

        deleteUserApi(accessToken);
    }

    @Test
    @DisplayName("Check login user from recover password page and exit")
    public void checkLoginUserFromRecover() {
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
        StellarBurgerPersonalAccountPage personalAccountPage = new StellarBurgerPersonalAccountPage(driver);
        StellarBurgerRecoverPassPage recoverPassPage = new StellarBurgerRecoverPassPage(driver);
        String accessToken = createUserAndGetToken(name, email, password);

        generalPage.waitLoginButtonPage();
        generalPage.clickLoginAccountButton();

        loginPage.waitLoginButton();
        loginPage.clickRecoverPasswordButton();

        recoverPassPage.waitLoginButton();
        recoverPassPage.clickLoginButton();

        loginPage.waitLoginButton();
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();

        generalPage.waitOrderButtonPage();
        generalPage.clickPersonalAccountButton();

        personalAccountPage.waitExitButton();
        personalAccountPage.clickExitProfileButton();

        loginPage.waitLoginButton();

        deleteUserApi(accessToken);
    }

    @Test
    @DisplayName("Check constructor button from personal account page")
    public void checkConstructorButton() {
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
        StellarBurgerPersonalAccountPage personalAccountPage = new StellarBurgerPersonalAccountPage(driver);
        String accessToken = createUserAndGetToken(name, email, password);

        generalPage.waitLoginButtonPage();
        generalPage.clickLoginAccountButton();

        loginPage.waitLoginButton();
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();

        generalPage.waitOrderButtonPage();
        generalPage.clickPersonalAccountButton();

        personalAccountPage.waitExitButton();
        personalAccountPage.clickConstructorButton();

        generalPage.waitOrderButtonPage();
        generalPage.clickPersonalAccountButton();

        personalAccountPage.waitExitButton();
        personalAccountPage.clickExitProfileButton();

        loginPage.waitLoginButton();

        deleteUserApi(accessToken);
    }

    @Test
    @DisplayName("Check logo button from personal account page")
    public void checkLogoButton() {
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
        StellarBurgerPersonalAccountPage personalAccountPage = new StellarBurgerPersonalAccountPage(driver);
        String accessToken = createUserAndGetToken(name, email, password);

        generalPage.waitLoginButtonPage();
        generalPage.clickLoginAccountButton();

        loginPage.waitLoginButton();
        loginPage.fillLoginInfo(email, password);
        loginPage.clickLoginButton();

        generalPage.waitOrderButtonPage();
        generalPage.clickPersonalAccountButton();

        personalAccountPage.waitExitButton();
        personalAccountPage.clickLogoButton();

        generalPage.waitOrderButtonPage();
        generalPage.clickPersonalAccountButton();

        personalAccountPage.waitExitButton();
        personalAccountPage.clickExitProfileButton();

        loginPage.waitLoginButton();

        deleteUserApi(accessToken);
    }
}
