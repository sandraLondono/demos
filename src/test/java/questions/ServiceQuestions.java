package questions;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

import ability.ManageTextFile;
import java.math.BigDecimal;
import net.serenitybdd.screenplay.Question;

public class ServiceQuestions {

  public static Question<String> getDeviceName(){
    return Question.about("get the name for device founded")
        .answeredBy(actor -> lastResponse().body().jsonPath().getString("[0].name"));
  }

  public static Question<BigDecimal> getDevicePrice(){
    return Question.about("get the price for device founded")
        .answeredBy(actor -> new BigDecimal(lastResponse().body().jsonPath().getString("[0].data.price")));
  }

  public static Question<Boolean> theFileExistAt(){
    return Question.about("valdiate that the file with response exists in the path")
        .answeredBy(actor -> {
          String path = actor.recall("finalPath");
          return ManageTextFile.as(actor).fileExists(path);
        });
  }
}
