package lab5.collectionManagers.buildersManagers.humanBeingBuilder;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import lab5.collection.HumanBeing.Coordinates;
import lab5.collectionManagers.buildersManagers.ObjectBuilder;
import lab5.collectionManagers.validators.InputValidator;

public class CoordinatesBuilder implements ObjectBuilder<Coordinates> {

  private Scanner scanner;

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

  @Override
  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }
}
