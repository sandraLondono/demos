package exeptions;

public class ValidationFailedException extends Error {

  public ValidationFailedException(String message) {
    super(message);
  }

  public ValidationFailedException(String message, Throwable testErrorException) {
    super(message, testErrorException);
  }
}
