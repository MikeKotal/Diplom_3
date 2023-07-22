package praktikum.pageObjects;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StellarBurgerRecoverPassPage {

    private WebDriver driver;

    //Кнопка войти
    private By loginButton = By.linkText("Войти");

    public StellarBurgerRecoverPassPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Wait login button on recover password page")
    public void waitLoginButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
    }

    @Step("Click and check login button on recover password page")
    public void clickLoginButton() {
        Assert.assertTrue("Кнопка войти неактивна", driver.findElement(loginButton).isEnabled());
        driver.findElement(loginButton).click();
    }

}
