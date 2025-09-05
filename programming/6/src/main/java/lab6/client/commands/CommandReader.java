package lab6.client.commands;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import lab6.client.builders.ObjectBuilder;
import lab6.client.network.ServerUnavailableException;
import lab6.client.network.UdpClient;
import lab6.common.exception.CommandException;
import lab6.common.service.CommandResponse;

public class CommandReader<C extends Map<String, ClientCommand>, T> {

  private C commandMap;
  private final ObjectBuilder<T> builder;
  private final UdpClient udpClient;
  private final int maxRetries;
  private final ExecuteScript<C, T> executeScript;

  public CommandReader(ObjectBuilder<T> builder, UdpClient udpClient, int maxRetries) {
    this.builder = builder;
    this.udpClient = udpClient;
    this.maxRetries = maxRetries;
    executeScript = new ExecuteScript<>(this);
  }

  public void startExecuting(Scanner scanner) {
    builder.setScanner(scanner);
    while (scanner.hasNext()) {
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
      System.exit(0);
    }
    if (args[0].equals("execute_script")) {
      executeScript.execute(argument);
    } else if (!commandMap.containsKey(args[0])) {
      throw new CommandException("\nКоманды " + args[0] + " не обнаружено!");
    } else {
      try {
        commandMap.get(args[0]).setArgument(argument);
        if (commandMap.get(args[0]).checkArgument()) {
          CommandResponse<?> commandResponse =
              udpClient.sendRequest(commandMap.get(args[0]).buildRequest(), maxRetries);
          if (commandResponse.getCommand() != null) {
            commandMap.get(commandResponse.getCommand()).read(commandResponse);
          } else {
            System.out.println(commandResponse.getMessage());
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

  public void setCommandMap(C commandMap) {
    this.commandMap = commandMap;
  }
}
