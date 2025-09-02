package lab5.collectionManagers.validators.humanBeingValidators;

import lab5.collection.HumanBeing.Coordinates;
import lab5.collectionManagers.validators.Validator;

public class CoordinatesValidator implements Validator<Coordinates> {
  public boolean validate(Coordinates coordinates) {
    return coordinates != null && new CoordinateXValidator().validate(coordinates.getX());
  }
}
