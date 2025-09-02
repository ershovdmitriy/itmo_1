package lab5.collectionManagers.validators.humanBeingValidators;

import lab5.collection.HumanBeing.WeaponType;
import lab5.collectionManagers.validators.Validator;

public class WeaponTypeValidator implements Validator<WeaponType> {
  public boolean validate(WeaponType weaponType) {
    return weaponType != null;
  }
}
