package tasks.service;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class MethodsServicesTask {

  public static Performable getDeviceWithId(String id) {
    return Task.where("{0} get a device by id"
        , Get.resource("/objects?id={id}")
            .with(req -> req.pathParam("id", id))

    );
  }

  public static Performable getAllDevices() {
    return Task.where("{0} get a device by id"
        , Get.resource("/objects")
    );
  }


}
