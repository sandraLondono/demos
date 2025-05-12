package pages;

import base.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ScreenshotUtils;

import java.time.Duration;
import java.util.List;

public class ResultsFlyAppPage extends BasePage {

    public ResultsFlyAppPage(RemoteWebDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public List<WebElement> resultsImg(String fly){
        Allure.step("Obtener resultados de la busqueda");
        ScreenshotUtils.captureScreenshot(driver);
        By locator = By.xpath(String.format("//*[contains(@content-desc, '%s')]", fly));
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElements(locator);
    }


}
