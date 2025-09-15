package lab7.client.commands;

import lab7.client.builders.ObjectBuilder;
import lab7.client.network.ServerUnavailableException;
import lab7.client.network.UdpClient;
import lab7.common.exception.CommandException;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.common.validators.InputValidator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandExecutor<C extends Map<String, ClientCommand>, T> {

  private final C commandMap;
  private final ObjectBuilder<T> builder;
  private final UdpClient udpClient;
  private final ExecuteScript<C, T> executeScript;
  private boolean running = true;
  private Scanner scanner;
  private boolean fromFile;
  private final InputValidator inputValidator;
  private String user;
  private String password;

  public CommandExecutor(
          ObjectBuilder<T> builder, UdpClient udpClient, C commandMap) {
    this.builder = builder;
    this.udpClient = udpClient;
    this.commandMap = commandMap;
    executeScript = new ExecuteScript<>(this);
    inputValidator = new InputValidator();
  }

  public void startExecuting(Scanner scanner, boolean fromFile) {
    this.scanner = scanner;
    this.fromFile = fromFile;
    builder.setScanner(scanner);
    while (running && scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (line.isEmpty()) continue;
      try {
        executeCommand(line.split(" "));
        System.out.println();
      } catch (CommandException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void executeCommand(String[] args) {
    String argument = args.length > 1 ? args[1] : null;
    if (args[0].equals("exit")) {
      System.out.println("Завершение работы");
      running = false;
    } else if (args[0].equals("execute_script")) {
      executeScript.execute(argument);
    } else if (!commandMap.containsKey(args[0])) {
      throw new CommandException("\nКоманды " + args[0] + " не обнаружено!");
    } else {
      try {
        commandMap.get(args[0]).setArgument(argument);
        if (commandMap.get(args[0]).checkArgument()) {
          if (args[0].equals("registration") || args[0].equals("authorization")) {
            registration();
          }
          CommandRequest<?, ?> request = commandMap.get(args[0]).buildRequest();
          if (user != null) {
            request.setUser(user, password);
            CommandResponse<?> commandResponse = udpClient.sendRequest(request);
            if (commandResponse.getCommand() != null) {
              commandMap.get(commandResponse.getCommand()).read(commandResponse);
            } else {
              System.out.println(commandResponse.getMessage());
            }
          } else {
            System.out.println("Для выполнения команд необходимо авторизоваться(команда authorization) или зарегистрироваться(команда registration) в системе");
          }
        } else {
          System.out.println("Попробуйте еще раз");
        }
      } catch (IllegalArgumentException | NullPointerException | NoSuchElementException e) {
        throw new CommandException(
            "Выполнение команды "
                + args[0]
                + " пропущено из-за неправильных предоставленных аргументов:\n"
                + e.getMessage());
      } catch (ServerUnavailableException e) {
        throw new CommandException(
            "Выполнение команды "
                + args[0]
                + " прервано из-за ошибки работы с сервером:\n"
                + e.getMessage());
      } catch (Exception e) {
        throw new CommandException(
            "В командном менеджере произошла непредвиденная ошибка:\n" + e.getMessage());
      }
    }
  }

  public void registration() {
    System.out.println("Введите имя пользователя:");
    String nextLine = scanner.nextLine();
    if (inputValidator.validate(nextLine)) {
      user = nextLine;
    }
    System.out.println("Введите пароль:");
    if (!fromFile) {
      nextLine = String.valueOf(System.console().readPassword());
      password = nextLine;
    } else {
      password = scanner.nextLine();
    }
    password += "P!QJyPh8";
    try {
      MessageDigest md2 = MessageDigest.getInstance("MD2");
      byte[] hashBytes = md2.digest(password.getBytes());
      password = HexFormat.of().formatHex(hashBytes);
    } catch (NoSuchAlgorithmException e) {
      System.out.println("Ошибка при хешировании пароля");
    }

    System.out.println(user + " : " + password);

  }
}
