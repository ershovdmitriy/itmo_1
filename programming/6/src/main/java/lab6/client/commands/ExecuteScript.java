package lab6.client.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class ExecuteScript<C extends Map<String, ClientCommand>, T> {

  CommandReader<C, T> commandReader;

  public ExecuteScript(CommandReader<C, T> commandReader) {
    this.commandReader = commandReader;
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
        System.out.println("\nНачало выполнения скрипта \"" + argument + "\"\n");
        commandReader.startExecuting(new Scanner(new FileInputStream(argument)));
        System.out.println("\nКонец выполнения скрипта \"" + argument + "\"\n");
      } catch (FileNotFoundException e) {
        System.out.println("Файл \"" + argument + "\" не обнаружен.");
      }
    }
  }
}
