package lab5.collectionManagers.validators.humanBeingValidators;

import java.util.Date;
import lab5.collectionManagers.validators.Validator;

public class DateValidator implements Validator<Date> {
  public boolean validate(Date date) {
    return date != null;
  }
}
