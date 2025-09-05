package lab6.common.validators.humanBeingValidators;

import lab6.common.validators.Validator;

public class SoundtrackNameValidator implements Validator<String> {
  public boolean validate(String soundtrackName) {
    return soundtrackName != null;
  }
}
