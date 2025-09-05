package lab6.common.validators.humanBeingValidators;

import lab6.common.validators.Validator;

public class RealHeroValidator implements Validator<Boolean> {
  public boolean validate(Boolean realHero) {
    return realHero != null;
  }
}
