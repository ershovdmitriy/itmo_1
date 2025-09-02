package lab5.collectionManagers.validators.humanBeingValidators;

import lab5.collection.HumanBeing.Car;
import lab5.collectionManagers.validators.Validator;

public class CarValidator implements Validator<Car> {
  public boolean validate(Car car) {
    return car != null;
  }
}
