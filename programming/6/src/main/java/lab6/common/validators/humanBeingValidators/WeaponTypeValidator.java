package lab6.common.validators.humanBeingValidators;

import lab6.common.collection.HumanBeing.WeaponType;
import lab6.common.validators.Validator;

public class WeaponTypeValidator implements Validator<WeaponType> {
  public boolean validate(WeaponType weaponType) {
    return weaponType != null;
  }
}
