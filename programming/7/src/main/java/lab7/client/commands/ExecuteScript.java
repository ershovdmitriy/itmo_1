package lab7.client.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ExecuteScript<C extends Map<String, ClientCommand>, T> {

  private final CommandExecutor<C, T> commandExecutor;
  private ArrayList<String> list = new ArrayList<>();

  public ExecuteScript(CommandExecutor<C, T> commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      System.out.println("execute_script имеет аргумент типа String");
      return false;
    } else {
      return true;
    }
  }

  public void execute(String argument) {
    if (checkArgument(argument)) {
      try {
        if (!list.contains(argument)) {
          list.add(argument);
          System.out.println("\nНачало выполнения скрипта \"" + argument + "\"\n");
          commandExecutor.startExecuting(new Scanner(new FileInputStream(argument)), true);
          System.out.println("\nКонец выполнения скрипта \"" + argument + "\"\n");
          list.remove(argument);
        } else {
          System.out.println("Исполнение \"" + argument + "\" пропущено\n");
        }
      } catch (FileNotFoundException e) {
        System.out.println("Файл \"" + argument + "\" не обнаружен.");
      }
    }
  }
}
