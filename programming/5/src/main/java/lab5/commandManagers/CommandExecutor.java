package lab5.commandManagers;

import java.io.InputStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import lab5.collectionManagers.buildersManagers.ObjectBuilder;
import lab5.exceptions.CommandInterruptedException;

public class CommandExecutor<C extends Map<String, Command>, T> {

  private C commandMap;
  private final ObjectBuilder<T> builder;

  public CommandExecutor(ObjectBuilder<T> builder) {
    this.builder = builder;
  }

  public void startExecuting(InputStream input) {
    Scanner scanner = new Scanner(input);
    builder.setScanner(scanner);
    while (scanner.hasNext()) {
      String line = scanner.nextLine().trim();
      if (line.isEmpty()) continue;
      try {
        executeCommand(line.split(" "));
        System.out.println();
      } catch (CommandInterruptedException | NoSuchElementException e) {
        System.err.println("Выполнение команды было прервано. Вы можете продолжать работу.");
      }
    }
  }

  public void executeCommand(String[] args) {
    if (!commandMap.containsKey(args[0])) {
      throw new CommandInterruptedException("\nКоманды " + args[0] + " не обнаружено!");
    } else {
      try {
        if (args.length > 1) {
          commandMap.get(args[0]).setArgument(args[1]);
        } else {
          commandMap.get(args[0]).setArgument(null);
        }
        commandMap.get(args[0]).execute();
      } catch (IllegalArgumentException | NullPointerException | NoSuchElementException e) {
        throw new CommandInterruptedException(
            "Выполнение команды пропущено из-за неправильных предоставленных аргументов! ("
                + e.getMessage()
                + ")");
      } catch (Exception e) {
        throw new CommandInterruptedException(
            "В командном менеджере произошла непредвиденная ошибка!");
      }
    }
  }

  public void setCommandMap(C commandMap) {
    this.commandMap = commandMap;
  }
}
