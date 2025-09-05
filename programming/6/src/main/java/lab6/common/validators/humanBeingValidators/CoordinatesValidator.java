package lab6.common.validators.humanBeingValidators;

import lab6.common.collection.HumanBeing.Coordinates;
import lab6.common.validators.Validator;

public class CoordinatesValidator implements Validator<Coordinates> {
  public boolean validate(Coordinates coordinates) {
    return coordinates != null && new CoordinateXValidator().validate(coordinates.getX());
  }
}
