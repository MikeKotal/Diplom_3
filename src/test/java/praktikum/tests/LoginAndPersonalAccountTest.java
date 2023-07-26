package praktikum.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import praktikum.helpers.Initializer;
import praktikum.page_objects.*;

import java.io.IOException;
import java.util.Random;

import static praktikum.clients.ClientsHelper.createUserAndGetToken;
import static praktikum.clients.ClientsHelper.deleteUserApi;
import static praktikum.helpers.Constants.*;

public class LoginAndPersonalAccountTest extends Initializer {

    Random random = new Random();
    private WebDriver driver;
    private String name = "TestT" + random.nextInt(1000);
    private String email = "SomethingS" + random.nextInt(100) + "@yandex.ru";
    private String password = "PassP" + random.nextInt(100000);

    @After
    public void closeBrowser() {
        driver.quit();
    }

    @Test
    @DisplayName("Check login user from general page and check exit button")
    public void checkLoginUserFromGeneral() throws IOException {
        driver = createDriver();
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
    public void checkLoginUserFromPersonalAcc() throws IOException {
        driver = createDriver();
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
    public void checkLoginUserFromRegister() throws IOException {
        driver = createDriver();
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
    public void checkLoginUserFromRecover() throws IOException {
        driver = createDriver();
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
    public void checkConstructorButton() throws IOException {
        driver = createDriver();
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
    public void checkLogoButton() throws IOException {
        driver = createDriver();
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
