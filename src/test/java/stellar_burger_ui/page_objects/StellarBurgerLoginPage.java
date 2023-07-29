package stellar_burger_ui.page_objects;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StellarBurgerLoginPage {

    private WebDriver driver;

    //Кнопка войти
    private By loginButton = By.xpath(".//button[text()='Войти']");

    //Поле ввода email
    private By emailField = By.name("name");

    //Поле ввода пароля
    private By passwordField = By.name("Пароль");

   //Кнопка зарегистрироваться
    private By registerButton = By.linkText("Зарегистрироваться");

   //Кнопка восстановления пароля
    private By recoverPass = By.linkText("Восстановить пароль");

    public StellarBurgerLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Wait login button on login page")
    public void waitLoginButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
    }

    @Step("Check fields of login info and fill test user info on login page")
    public void fillLoginInfo(String email, String password) {
        Assert.assertTrue("Поле email неактивно", driver.findElement(emailField).isEnabled());
        Assert.assertTrue("Поле пароль неактивно", driver.findElement(passwordField).isEnabled());
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Click and check login button on login page")
    public void clickLoginButton() {
        Assert.assertTrue("Кнопка войти неактивна", driver.findElement(loginButton).isEnabled());
        driver.findElement(loginButton).click();
    }

    @Step("Click and check registration button on login page")
    public void clickRegisterButton() {
        Assert.assertTrue("Кнока регистрации неактивна", driver.findElement(registerButton).isEnabled());
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(registerButton));
        driver.findElement(registerButton).click();
    }

    @Step("Click and check recover password button on login page")
    public void clickRecoverPasswordButton() {
        Assert.assertTrue("Кнопка восстановления пароля неактивна", driver.findElement(recoverPass).isEnabled());
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(recoverPass));
        driver.findElement(recoverPass).click();
    }
}
