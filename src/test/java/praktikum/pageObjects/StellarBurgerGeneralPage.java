package praktikum.pageObjects;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class StellarBurgerGeneralPage {

    private WebDriver driver;

    //Кнопка войти
    private By loginAccountButton = By.xpath(".//button[text()='Войти в аккаунт']");

    //Кнопка оформить заказ
    private By createOrderButton = By.xpath(".//button[text()='Оформить заказ']");

    //Кнопка личный кабинет
    private By personalAccount = By.xpath(".//p[text()='Личный Кабинет']");

    //Массив кнопок разделов
    private By burgersIngredients = By.xpath(".//span[@class='text text_type_main-default']");

    //Массив наименований разделов
    private By sectionName = By.className("text text_type_main-medium mb-6 mt-10");

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

    @Step("Wait section element after picking part of burger")
    public void waitSectionElements(WebElement sectionName) {
        new WebDriverWait(driver, 3)
                .until(driver -> (sectionName.getText() != null
                        && !sectionName.getText().isEmpty()
                ));
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

    @Step("Click and check sections and section buttons on general page")
    public void clickAndCheckSectionButtons() {
        List<WebElement> sectionsButtons = driver.findElements(burgersIngredients);
        List<WebElement> sectionNames = driver.findElements(sectionName);

        if (sectionsButtons.size() > 0) {
            for (int i = sectionsButtons.size() - 1; i <= 0; i--) {
                Assert.assertTrue(String.format("Кнопка %s неактивна", sectionsButtons.get(i).getText()),
                        sectionsButtons.get(i).isEnabled());
                sectionsButtons.get(i).click();
                Assert.assertTrue(String.format("Кнопка раздела %s не выбрана", sectionNames.get(i).getText()),
                        sectionsButtons.get(i).isSelected());
                waitSectionElements(sectionNames.get(i));
                Assert.assertTrue(String.format("Раздел %s скрыт", sectionNames.get(i).getText()),
                        sectionNames.get(i).isDisplayed());
                Assert.assertEquals("Кнопка должна соответствовать разделу",
                        sectionsButtons.get(i).getText(), sectionNames.get(i).getText());
            }
        } else {
            Assert.fail("На странице отсутствуют кнопки разделов");
        }
    }
}
