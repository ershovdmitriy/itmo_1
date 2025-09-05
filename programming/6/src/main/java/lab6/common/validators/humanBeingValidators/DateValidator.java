package lab6.common.validators.humanBeingValidators;

import java.util.Date;
import lab6.common.validators.Validator;

public class DateValidator implements Validator<Date> {
  public boolean validate(Date date) {
    return date != null;
  }
}
