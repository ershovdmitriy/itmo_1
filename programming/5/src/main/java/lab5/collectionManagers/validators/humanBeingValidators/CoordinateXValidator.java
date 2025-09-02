package lab5.collectionManagers.validators.humanBeingValidators;

import lab5.collectionManagers.validators.Validator;

public class CoordinateXValidator implements Validator<Double> {
  public boolean validate(Double x) {
    return x != null;
  }
}
