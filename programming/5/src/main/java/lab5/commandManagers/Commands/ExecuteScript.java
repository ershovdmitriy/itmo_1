package lab5.commandManagers.Commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import lab5.commandManagers.Command;
import lab5.commandManagers.CommandExecutor;

public class ExecuteScript<C extends Map<String, Command>, T> extends Command {

  CommandExecutor<C, T> commandExecutor;

  public ExecuteScript(CommandExecutor<C, T> commandExecutor) {
    super(false);
    this.commandExecutor = commandExecutor;
  }

  @Override
  public String getName() {
    return "execute_script";
  }

  @Override
  public String getDescr() {
    return "Считывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      System.out.println("execute_script имеет аргумент типа String");
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      try {
        System.out.println("\nНачало выполнения скрипта\n");
        commandExecutor.startExecuting(new FileInputStream(getArgument()));
        System.out.println("\nКонец выполнения скрипта\n");
      } catch (FileNotFoundException e) {
        System.out.println("Файл " + getArgument() + " не обнаружен.");
      }
    }
  }
}
