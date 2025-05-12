package pages;

import base.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MenuAppPage extends BasePage {

    public MenuAppPage(RemoteWebDriver driver) {
        super(driver);
    }

    @AndroidFindBy(className = "android.widget.EditText")
    private WebElement searchFlyInput;

    @AndroidFindBy(xpath = "//android.widget.EditText/android.widget.Button")
    private WebElement searchFlyButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.ImageView\").instance(11)")
    private WebElement loginButton;

    public void searchFly(String fly) {
        Allure.step("Buscar vuelos");
        searchFlyInput.click();
        searchFlyInput.sendKeys(fly);
        searchFlyButton.click();
    }

    public void login() {
        Allure.step("Ir al login de la app");
        loginButton.click();
    }


}
