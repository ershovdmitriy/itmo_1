package lab6.common.validators.humanBeingValidators;

import lab6.common.collection.HumanBeing.HumanBeing;
import lab6.common.validators.Validator;

public class HumanBeingValidator implements Validator<HumanBeing> {
  public boolean validate(HumanBeing humanBeing) {
    return new IdValidator().validate(humanBeing.getId())
        && new NameValidator().validate(humanBeing.getName())
        && new CoordinatesValidator().validate(humanBeing.getCoordinates())
        && new DateValidator().validate(humanBeing.getCreationDate())
        && new RealHeroValidator().validate(humanBeing.getRealHero())
        && new SoundtrackNameValidator().validate(humanBeing.getSoundtrackName())
        && new WeaponTypeValidator().validate(humanBeing.getWeaponType())
        && new CarValidator().validate(humanBeing.getCar());
  }
}
