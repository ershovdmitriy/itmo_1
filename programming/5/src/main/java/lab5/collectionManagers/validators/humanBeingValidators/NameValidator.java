package lab5.collectionManagers.validators.humanBeingValidators;

import lab5.collectionManagers.validators.Validator;

public class NameValidator implements Validator<String> {
  public boolean validate(String name) {
    return name != null && !name.isBlank();
  }
}
