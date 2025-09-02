package lab6.common.validators.humanBeingValidators;

import lab6.common.validators.Validator;

public class CoordinateXValidator implements Validator<Double> {
  public boolean validate(Double x) {
    return x != null;
  }
}
