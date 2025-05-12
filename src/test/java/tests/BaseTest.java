package tests;

import base.BaseTestConfig;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.LoginAppPage;
import pages.MenuAppPage;
import pages.ResultsFlyAppPage;
import pages.StartAppPage;

import java.net.MalformedURLException;
import java.util.List;

public class BaseTest extends BaseTestConfig {

    private static RemoteWebDriver driver;
    private StartAppPage startAppPage;
    private MenuAppPage menuAppPage;
    private ResultsFlyAppPage resultsFlyAppPage;
    private LoginAppPage loginAppPage;


    @BeforeEach
    public void initPages() throws MalformedURLException {
        driver = setupDriver();
        startAppPage = new StartAppPage(driver);
        menuAppPage = new MenuAppPage(driver);
        resultsFlyAppPage = new ResultsFlyAppPage(driver);
        loginAppPage = new LoginAppPage(driver);
    }

    @AfterEach
    public void tearDownTest() {
        tearDown();
    }

    @Epic("Validar APP")
    @Feature("Test en Android")
    @Story("Buscar Vuelos")
    @Test
    public void testSearchFly() {
        startAppPage.closeWindow();
        menuAppPage.searchFly("San Andrés");
        List<WebElement> results = resultsFlyAppPage.resultsImg("San Andrés");
        Allure.step("Validacion de la busqueda");
        Assertions.assertFalse(results.isEmpty(), "Validar resultado de la busqueda");

    }

    @Epic("Validar APP")
    @Feature("Test en Android")
    @Story("Validar Login")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Tag("regression")
    public void loginFailedTest(){
        startAppPage.closeWindow();
        menuAppPage.login();
        loginAppPage.login("test@test.com", "test");
        Boolean msg = loginAppPage.getErrorMessage();
        Allure.step("Validacion resultado del login incorrecto");
        Assertions.assertTrue(msg, "Validar resultado del login incorrecto");

    }
}