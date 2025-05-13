package exeptions;

import net.serenitybdd.model.exceptions.TestCompromisedException;

public class InfoNotExistsException extends TestCompromisedException {

    public InfoNotExistsException(String message) {
      super(message);
    }

    public InfoNotExistsException(String message, Throwable cause) {
      super(message, cause);
    }

}
