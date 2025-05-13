package stepdefinitions;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import ability.ManageTextFile;
import exeptions.ValidationFailedException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import models.ProductsModel;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.util.EnvironmentVariables;
import questions.ServiceQuestions;
import tasks.commons.FileTask;
import tasks.service.MethodsServicesTask;
import utils.DataTableMapper;

public class MobileDeviceStepDefinitions {

  private Actor actor;
  private EnvironmentVariables environmentVariables;

  @Before
  public void setUp() {
    String url = environmentVariables.getProperty("service.devs.demo");
    actor = Actor.named("Analyst")
        .whoCan(CallAnApi.at(url))
        .whoCan(ManageTextFile.with());
  }

  @When("i call service for id {string}")
  public void iCallAServiceFor(String id) {
    actor.attemptsTo(
        MethodsServicesTask.getDeviceWithId(id)
    );
  }

  @Then("i can validate response")
  public void iCanValidateResponse(DataTable table) {

    List<ProductsModel> productsModels = DataTableMapper.fromDataTable(table, ProductsModel.class);
    actor.should(
        seeThat(ServiceQuestions.getDeviceName(),
            containsString(productsModels.get(0).getProductName()))
            .orComplainWith(ValidationFailedException.class
                , "The validation dont works with name property"),
        seeThat(ServiceQuestions.getDevicePrice(), is(productsModels.get(0).getProductPrice()))
            .orComplainWith(ValidationFailedException.class
                , "The validation dont works with price property")
    );
  }

  @When("i call service")
  public void iCallService() {
    actor.attemptsTo(MethodsServicesTask.getAllDevices());
    String content = lastResponse().getBody().prettyPrint();
    String time = String.valueOf(lastResponse().getTime());
    String finalPath = environmentVariables.getProperty("file.interaction.test.path") + "response"
        + UUID.randomUUID() + ".txt";

    actor.remember("content", content);
    actor.attemptsTo(
        FileTask.createFile(finalPath)
    );
    finalPath = actor.recall("finalPath");
    actor.attemptsTo(
        FileTask.writeFile(finalPath, content)
    );
  }

  @Then("i can save response")
  public void iCanSaveResponse() throws IOException {
    String finalPath = actor.recall("finalPath");
    Serenity.recordReportData()
        .withTitle("attached file")
        .fromFile(Path.of(finalPath));

    actor.should(seeThat(ServiceQuestions.theFileExistAt(), is(true)));
    actor.attemptsTo(FileTask.deleteFile(finalPath));
  }

  @After
  public void tearDown() {
    System.out.println("paso usado para ejecutar funciones despues de finalizada la prueba");
  }


}
