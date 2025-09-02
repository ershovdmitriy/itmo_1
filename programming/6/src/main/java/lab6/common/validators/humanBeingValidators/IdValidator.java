package lab6.common.validators.humanBeingValidators;

import lab6.common.validators.Validator;

public class IdValidator implements Validator<Integer> {
  public boolean validate(Integer id) {
    return id > 0;
  }
}
