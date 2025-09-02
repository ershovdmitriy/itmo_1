package lab5.collectionManagers.buildersManagers.humanBeingBuilder;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import lab5.collection.HumanBeing.HumanBeing;
import lab5.collectionManagers.IdManager;
import lab5.collectionManagers.buildersManagers.ObjectBuilder;
import lab5.collectionManagers.validators.InputValidator;

public class HumanBeingBuilder implements ObjectBuilder<HumanBeing> {

  private Scanner scanner;

  private final IdManager<HumanBeing> idManager;

  public HumanBeingBuilder(IdManager<HumanBeing> idManager) {
    this.idManager = idManager;
  }

  public HumanBeing build() throws IllegalArgumentException {
    try {
      System.out.println("Создание HumanBeing:");
      HumanBeing humanBeing = new HumanBeing();
      InputValidator inputValidator = new InputValidator();
      String nextLine;

      int id = idManager.generateId();
      humanBeing.setId(id);

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

      CoordinatesBuilder coordinatesBuilder = new CoordinatesBuilder();
      coordinatesBuilder.setScanner(scanner);
      humanBeing.setCoordinates(coordinatesBuilder.build());

      Date creationDate = new Date();
      humanBeing.setCreationDate(creationDate);

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

  @Override
  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }
}
