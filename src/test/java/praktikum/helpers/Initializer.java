package praktikum.helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

import static praktikum.helpers.Constants.*;
import static praktikum.helpers.Constants.LOCAL_PATH_TO_YANDEX_BROWSER;

public class Initializer {

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        RestAssured.baseURI = GENERAL_PAGE;
    }

    public WebDriver createDriver() throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("environment.properties"));
        String browser = System.getProperty("browser");
        WebDriver driver = null;
        switch (browser) {
            case CHROME:
                driver = new ChromeDriver();
                break;
            case YANDEX:
                ChromeOptions options = new ChromeOptions();
                options.setBinary(LOCAL_PATH_TO_YANDEX_BROWSER);
                driver = new ChromeDriver(options);
        }
        return driver;
    }
}
