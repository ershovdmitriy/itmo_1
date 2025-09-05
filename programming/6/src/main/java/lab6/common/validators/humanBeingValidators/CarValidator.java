package lab6.common.validators.humanBeingValidators;

import lab6.common.collection.HumanBeing.Car;
import lab6.common.validators.Validator;

public class CarValidator implements Validator<Car> {
  public boolean validate(Car car) {
    return car != null;
  }
}
