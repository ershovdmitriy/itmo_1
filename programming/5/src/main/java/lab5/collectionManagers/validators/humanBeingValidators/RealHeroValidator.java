package lab5.collectionManagers.validators.humanBeingValidators;

import lab5.collectionManagers.validators.Validator;

public class RealHeroValidator implements Validator<Boolean> {
  public boolean validate(Boolean realHero) {
    return realHero != null;
  }
}
