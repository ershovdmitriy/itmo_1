package lab5.collectionManagers.validators.humanBeingValidators;

import lab5.collectionManagers.validators.Validator;

public class SoundtrackNameValidator implements Validator<String> {
  public boolean validate(String soundtrackName) {
    return soundtrackName != null;
  }
}
