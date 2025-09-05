package lab6.client.builders.humanBeingBuilder;

import java.util.NoSuchElementException;
import lab6.client.builders.ObjectBuilderNew;
import lab6.common.collection.HumanBeing.WeaponType;
import lab6.common.validators.InputValidator;

public class WeaponTypeBuilder extends ObjectBuilderNew<WeaponType> {

  public WeaponType build() throws IllegalArgumentException {
    try {
      System.out.println("Создание WeaponType:");
      InputValidator inputValidator = new InputValidator();
      String nextLine;
      while (true) {
        try {
          System.out.println("Выберите значение weaponType:");
          for (WeaponType vales : WeaponType.values()) {
            System.out.println(vales);
          }
          nextLine = scanner.nextLine().toUpperCase();
          if (inputValidator.validate(nextLine)) {
            return WeaponType.valueOf(nextLine);
          } else {
            System.out.println("Строка на должна быть пустой. Попробуй еще.");
          }
        } catch (IllegalArgumentException e) {
          System.out.println("Неправильное значение ввода. Попробуй еще.");
        }
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException(
          "Во время конструирования объекта произошла ошибка: " + e.getMessage());
    }
  }
}
