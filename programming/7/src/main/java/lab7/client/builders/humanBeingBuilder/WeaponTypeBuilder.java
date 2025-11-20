package lab7.client.builders.humanBeingBuilder;

import lab7.client.builders.ObjectBuilder;
import lab7.common.collection.HumanBeing.WeaponType;
import lab7.common.validators.InputValidator;

import java.util.NoSuchElementException;

public class WeaponTypeBuilder extends ObjectBuilder<WeaponType> {

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
            break;
          } else {
            System.out.println("Строка на должна быть пустой. Попробуй еще.");
          }
        } catch (IllegalArgumentException e) {
          System.out.println("Неправильное значение ввода. Попробуй еще.");
        }
      }
      return WeaponType.valueOf(nextLine);
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException(
          "Во время конструирования объекта произошла ошибка: " + e.getMessage());
    }
  }
}
