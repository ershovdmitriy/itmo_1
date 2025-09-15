package lab7.client.builders.humanBeingBuilder;

import lab7.client.builders.ObjectBuilder;
import lab7.common.collection.HumanBeing.HumanBeing;
import lab7.common.validators.InputValidator;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class HumanBeingBuilder extends ObjectBuilder<HumanBeing> {

  @Override
  public HumanBeing build() throws IllegalArgumentException {
    try {
      System.out.println("Создание HumanBeing:");
      HumanBeing humanBeing = new HumanBeing();
      InputValidator inputValidator = new InputValidator();
      String nextLine;

      String name;
      while (true) {
        System.out.println("Введите значение name (тип - String, не может быть пустым):");
        nextLine = scanner.nextLine();
        if (inputValidator.validate(nextLine)) {
          name = nextLine;
          humanBeing.setName(name);
          break;
        } else {
          System.out.println("Строка на должна быть пустой. Попробуй еще.");
        }
      }

      Date date = new Date();
      humanBeing.setCreationDate(date);

      CoordinatesBuilder coordinatesBuilder = new CoordinatesBuilder();
      coordinatesBuilder.setScanner(scanner);
      humanBeing.setCoordinates(coordinatesBuilder.build());

      Boolean realHero;
      while (true) {
        try {
          System.out.println("Введите значение realHero (тип - Boolean, не может быть пустым):");
          nextLine = scanner.nextLine();
          if (inputValidator.validate(nextLine)) {
            if (!nextLine.equalsIgnoreCase("true") && !nextLine.equalsIgnoreCase("false")) {
              throw new InputMismatchException();
            }
            realHero = Boolean.valueOf(nextLine);
            humanBeing.setRealHero(realHero);
            break;
          } else {
            System.out.println("Строка на должна быть пустой. Попробуй еще.");
          }
        } catch (InputMismatchException e) {
          System.out.println("Неправильное значение ввода. Попробуй еще.");
        }
      }

      boolean hasToothpick;
      while (true) {
        try {
          System.out.println(
              "Введите значение hasToothpick (тип - boolean, не может быть пустым):");
          nextLine = scanner.nextLine();
          if (inputValidator.validate(nextLine)) {
            if (!nextLine.equalsIgnoreCase("true") && !nextLine.equalsIgnoreCase("false")) {
              throw new InputMismatchException();
            }
            hasToothpick = Boolean.parseBoolean(nextLine);
            humanBeing.setHasToothpick(hasToothpick);
            break;
          } else {
            System.out.println("Строка на должна быть пустой. Попробуй еще.");
          }
        } catch (InputMismatchException e) {
          System.out.println("Неправильное значение ввода. Попробуй еще.");
        }
      }

      Long impactSpeed;
      while (true) {
        try {
          System.out.println(
              "Введите значение impactSpeed (тип - Long, значение может быть пустым):");
          nextLine = scanner.nextLine();
          if (nextLine.isBlank()) {
            impactSpeed = null;
          } else {
            impactSpeed = Long.parseLong(nextLine);
          }
          humanBeing.setImpactSpeed(impactSpeed);
          break;
        } catch (InputMismatchException | NumberFormatException e) {
          System.out.println("Неправильное значение ввода. Попробуй еще.");
        }
      }

      String soundtrackName;
      while (true) {
        System.out.println("Введите значение soundtrackName (тип - String, не может быть пустым):");
        nextLine = scanner.nextLine();
        if (inputValidator.validate(nextLine)) {
          soundtrackName = nextLine;
          humanBeing.setSoundtrackName(soundtrackName);
          break;
        } else {
          System.out.println("Строка на должна быть пустой. Попробуй еще.");
        }
      }

      long minutesOfWaiting;
      while (true) {
        try {
          System.out.println(
              "Введите значение minutesOfWaiting (тип - long, не может быть пустым):");
          nextLine = scanner.nextLine();
          if (inputValidator.validate(nextLine)) {
            minutesOfWaiting = Long.parseLong(nextLine);
            humanBeing.setMinutesOfWaiting(minutesOfWaiting);
            break;
          } else {
            System.out.println("Строка на должна быть пустой. Попробуй еще.");
          }
        } catch (InputMismatchException | NumberFormatException e) {
          System.out.println("Неправильное значение ввода. Попробуй еще.");
        }
      }

      WeaponTypeBuilder weaponTypeBuilder = new WeaponTypeBuilder();
      weaponTypeBuilder.setScanner(scanner);
      humanBeing.setWeaponType(weaponTypeBuilder.build());

      CarBuilder carBuilder = new CarBuilder();
      carBuilder.setScanner(scanner);
      humanBeing.setCar(carBuilder.build());

      return humanBeing;

    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException(
          "Во время конструирования объекта произошла ошибка: " + e.getMessage());
    }
  }
}
