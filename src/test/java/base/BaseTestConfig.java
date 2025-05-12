package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.qameta.allure.Allure;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;


public class BaseTestConfig {

    protected static RemoteWebDriver driver;


    public RemoteWebDriver setupDriver() throws MalformedURLException {
        Allure.step("Iniciar la aplicación");
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Samsung S23")
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setAppPackage("com.plm.opain.eldorado")
                .setAppActivity("com.plm.opain.eldorado.MainActivity")
                .setAutoGrantPermissions(true)
                .setNoReset(true)
                .setNewCommandTimeout(Duration.ofSeconds(30));

        URL appiumServerUrl = new URL("http://localhost:4723/wd/hub");
        return driver = new AndroidDriver(appiumServerUrl, options);
    }


    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
