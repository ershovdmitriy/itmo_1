package lab5.collectionManagers.validators.humanBeingValidators;

import lab5.collectionManagers.validators.Validator;

public class IdValidator implements Validator<Integer> {
  public boolean validate(Integer id) {
    return id > 0;
  }
}
