package praktikum.pageObjects;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class StellarBurgerRegisterPage {

    private WebDriver driver;

    //Кнопка зарегистрироваться
    private By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");

    //Кнопка войти
    private By loginButton = By.linkText("Войти");

    //Массив полей регистрации
    private By registrationFields = By.xpath(".//input[@class='text input__textfield text_type_main-default']");

    //Массив названий полей регистрации
    private By fieldsName = By.xpath(".//label[@class='input__placeholder text noselect text_type_main-default']");

    //Ошибка пароля
    private By passwordError = By.xpath(".//p[@class='input__error text_type_main-default']");

    public StellarBurgerRegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Wait registration button on register page")
    public void waitRegisterButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(registerButton));
    }

    @Step("Check and fill registration fields test dates on register page")
    public void fillRegisterInfo(String name, String email, String password) {
        List<WebElement> registerFields = driver.findElements(registrationFields);
        List<WebElement> nameOfFields = driver.findElements(fieldsName);
        for (int i = 0; i < registerFields.size(); i++) {
            Assert.assertTrue(String.format("Поле %s неактивно", nameOfFields.get(i).getText()),
                    registerFields.get(i).isEnabled());
            switch (nameOfFields.get(i).getText()) {
                case "Имя":
                    registerFields.get(i).sendKeys(name);
                    break;
                case "Email":
                    registerFields.get(i).sendKeys(email);
                    break;
                case "Пароль":
                    registerFields.get(i).sendKeys(password);
                    break;
            }
        }
    }

    @Step("Click and check registration button on register page")
    public void clickRegisterButton() {
        Assert.assertTrue("Кнопка регистрации неактивна", driver.findElement(registerButton).isEnabled());
        driver.findElement(registerButton).click();
    }

    @Step("Click and check login button on register page")
    public void clickLoginButton() {
        Assert.assertTrue("Кнопка войти неактивна", driver.findElement(loginButton).isEnabled());
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(loginButton));
        driver.findElement(loginButton).click();
    }

    @Step("Check incorrect password error message")
    public boolean isIncorrectPassword() {
        return !driver.findElements(passwordError).isEmpty();
    }

}
