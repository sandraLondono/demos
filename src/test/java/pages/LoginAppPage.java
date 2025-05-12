package pages;

import base.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.ScreenshotUtils;

public class LoginAppPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.widget.EditText[1]")
    private WebElement usernameInput;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.widget.EditText[2]")
    private WebElement passwordInput;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Ingresar a mi cuenta\"]")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Ocurrió un error al iniciar sesión.\"]")
    private WebElement errorMessage;

    public LoginAppPage(RemoteWebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        Allure.step("Ingresar a la aplicacion");
        usernameInput.click();
        usernameInput.sendKeys(username);
        passwordInput.click();
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public Boolean getErrorMessage() {
        Allure.step("validar msg de error");
        ScreenshotUtils.captureScreenshot(driver, "msg error");
        return errorMessage.isDisplayed();
    }


}
