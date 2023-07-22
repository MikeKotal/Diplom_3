package praktikum.pageObjects;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StellarBurgerPersonalAccountPage {

    private WebDriver driver;

    //Кнопка выход
    private By exitProfileButton = By.xpath(".//button[text()='Выход']");

    //Кнопка конструктор
    private By constructorButton = By.xpath(".//p[text()='Конструктор']");

    //Кнопка логотип
    private By logoButton = By.className("AppHeader_header__logo__2D0X2");

    public StellarBurgerPersonalAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Wait exit button on personal account page")
    public void waitExitButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(exitProfileButton));
    }

    @Step("Click and check exit button on personal account page")
    public void clickExitProfileButton() {
        Assert.assertTrue("Кнопка выхода из профиля неактивна",
                driver.findElement(exitProfileButton).isEnabled());
        driver.findElement(exitProfileButton).click();
    }

    @Step("Click and check constructor button on personal account page")
    public void clickConstructorButton() {
        Assert.assertTrue("Кнопка перехода к конструктору неактивна",
                driver.findElement(constructorButton).isEnabled());
        driver.findElement(constructorButton).click();
    }

    @Step("Click and check logo button on personal account page")
    public void clickLogoButton() {
        Assert.assertTrue("Кнопка лого неактивна", driver.findElement(logoButton).isEnabled());
        driver.findElement(logoButton).click();
    }

}
