package pages;

import base.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class StartAppPage  extends BasePage {

    public StartAppPage(RemoteWebDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"No autorizo\"]")
    private WebElement noAcceptButton;

    public void closeWindow(){
        Allure.step("Cerrar ventana de Permisos");
        noAcceptButton.click();
    }


}
