package lab5.collectionManagers.validators;

public class InputValidator implements Validator<String> {

  public boolean validate(String input) {
    return input != null && !input.isBlank();
  }
}
