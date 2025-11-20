package lab7.client.builders.humanBeingBuilder;

import lab7.client.builders.ObjectBuilder;
import lab7.common.collection.HumanBeing.Coordinates;
import lab7.common.validators.InputValidator;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class CoordinatesBuilder extends ObjectBuilder<Coordinates> {

  @Override
  public Coordinates build() throws IllegalArgumentException {
    try {
      System.out.println("Создание Coordinates:");
      Coordinates coordinates = new Coordinates();
      InputValidator inputValidator = new InputValidator();
      String nextLine;

      Double x;
      while (true) {
        try {
          System.out.println("Введите значение x (тип - Double, не может быть пустым):");
          nextLine = scanner.nextLine();
          if (inputValidator.validate(nextLine)) {
            x = Double.parseDouble(nextLine);
            coordinates.setX(x);
            break;
          } else {
            System.out.println("Строка на должна быть пустой. Попробуй еще.");
          }
        } catch (InputMismatchException | NumberFormatException e) {
          System.out.println("Неправильное значение ввода. Попробуй еще.");
        }
      }

      long y;
      while (true) {
        try {
          System.out.println("Введите значение y (тип - long, не может быть пустым):");
          nextLine = scanner.nextLine();
          if (inputValidator.validate(nextLine)) {
            y = Long.parseLong(nextLine);
            coordinates.setY(y);
            break;
          } else {
            System.out.println("Строка на должна быть пустой. Попробуй еще.");
          }
        } catch (InputMismatchException | NumberFormatException e) {
          System.out.println("Неправильное значение ввода. Попробуй еще.");
        }
      }
      return coordinates;
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException(
          "Во время конструирования объекта произошла ошибка: " + e.getMessage());
    }
  }
}
