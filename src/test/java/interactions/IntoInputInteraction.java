package interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Hit;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.Keys;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class IntoInputInteraction implements Interaction {

  private final Target target;
  private final String taskName;

  public IntoInputInteraction(Target target, String taskName) {
    this.target = target;
    this.taskName = taskName;
  }

  @Override
  @Step("{0} clicks on #target")
  public <T extends Actor> void performAs(T theActor) {
    theActor.attemptsTo(
        Enter.theValue(taskName).into(target),
        Hit.the(Keys.ENTER).into(target)
    );
  }

  public static IntoInputInteraction value(Target target, String taskName) {
    return instrumented(IntoInputInteraction.class, target, taskName);
  }
}
