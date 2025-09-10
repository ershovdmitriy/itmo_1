package lab6.common.validators.humanBeingValidators;

import lab6.common.validators.Validator;

public class NameValidator implements Validator<String> {
  public boolean validate(String name) {
    return name != null && !name.isBlank();
  }
}
