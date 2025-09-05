package lab6.client.builders.humanBeingBuilder;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import lab6.client.builders.ObjectBuilderNew;
import lab6.common.collection.HumanBeing.Car;
import lab6.common.validators.InputValidator;

public class CarBuilder extends ObjectBuilderNew<Car> {

  @Override
  public Car build() throws IllegalArgumentException {
    try {
      System.out.println("Создание Car:");
      Car car = new Car();
      InputValidator inputValidator = new InputValidator();
      String nextLine;

      boolean cool;
      while (true) {
        try {
          System.out.println("Введите значение cool(тип - boolean, не может быть пустым):");
          nextLine = scanner.nextLine();
          if (inputValidator.validate(nextLine)) {
            if (!nextLine.equalsIgnoreCase("true") && !nextLine.equalsIgnoreCase("false")) {
              throw new InputMismatchException();
            }
            cool = Boolean.parseBoolean(nextLine);
            car.setCool(cool);
            return car;
          } else {
            System.out.println("Строка на должна быть пустой. Попробуй еще.");
          }
        } catch (InputMismatchException e) {
          System.out.println("Неправильное значение ввода. Попробуй еще.");
        }
      }
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException(
          "Во время конструирования объекта произошла ошибка: " + e.getMessage());
    }
  }
}
