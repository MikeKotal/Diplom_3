package stellar_burger_ui.page_objects;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static stellar_burger_ui.helpers.Constants.BUNS;

public class StellarBurgerGeneralPage {

    private WebDriver driver;

    //Кнопка войти
    private By loginAccountButton = By.xpath(".//button[text()='Войти в аккаунт']");

    //Кнопка оформить заказ
    private By createOrderButton = By.xpath(".//button[text()='Оформить заказ']");

    //Кнопка личный кабинет
    private By personalAccount = By.xpath(".//p[text()='Личный Кабинет']");

    //Массив кнопок разделов для проверки индикации нажатия
    private By sectionButtons = By.xpath(".//div[@style = 'display: flex;']/div");

    public StellarBurgerGeneralPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Wait login button on general page")
    public void waitLoginButtonPage() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(loginAccountButton));
    }

    @Step("Wait order button after login on general page")
    public void waitOrderButtonPage() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(createOrderButton));
    }

    @Step("Click and check login button on general page")
    public void clickLoginAccountButton() {
        Assert.assertTrue("Кнопка входа неактивна", driver.findElement(loginAccountButton).isEnabled());
        driver.findElement(loginAccountButton).click();
    }

    @Step("Click and check personal account button on general page")
    public void clickPersonalAccountButton() {
        Assert.assertTrue("Кнопка личного кабинета неактивна", driver.findElement(personalAccount).isEnabled());
        driver.findElement(personalAccount).click();
    }

    @Step("Check section buttons on general page")
    public void checkSectionButtons(String sectionName) {
        List<WebElement> buttons = driver.findElements(sectionButtons);
        WebElement sectionButton = driver.findElement(By.xpath(String.format(".//span[text()='%s']", sectionName)));
        WebElement sectionButtonParent = driver.findElement(By.xpath(String.format(".//span[text()='%s']/parent::div", sectionName)));
        if (buttons.size() > 0) {
            Assert.assertTrue(String.format("Кнопка %s неактивна", sectionName), sectionButton.isEnabled());
            if (!sectionName.equals(BUNS)) {
                sectionButtonParent.click();
            }
            Assert.assertTrue(String.format("Кнопка %s не выбрана", sectionName),
                    sectionButtonParent.getAttribute("class").contains("tab_tab_type_current__2BEPc"));
        } else {
            Assert.fail("На странице отсутствуют кнопки разделов");
        }
    }
}
