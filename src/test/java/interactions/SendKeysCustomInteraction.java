package interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class SendKeysCustomInteraction implements Interaction {

    private final Keys[] keys;

    public SendKeysCustomInteraction(Keys... keys) {
        this.keys = keys;
    }

    public static SendKeysCustomInteraction of(Keys... keys) {
        return Tasks.instrumented(SendKeysCustomInteraction.class, (Object) keys);
    }

    @Override
    @Step("{0} interact with element by keyboard")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        for (Keys key : keys) {
            driver.switchTo().activeElement().sendKeys(key);
        }
    }
}
