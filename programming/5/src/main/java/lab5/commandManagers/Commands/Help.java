package lab5.commandManagers.Commands;

import java.util.LinkedHashMap;
import lab5.commandManagers.Command;

public class Help extends Command {

  LinkedHashMap<String, Command> commandMap;

  public Help(LinkedHashMap<String, Command> commandMap) {
    super(false);
    this.commandMap = commandMap;
  }

  @Override
  public String getName() {
    return "help";
  }

  @Override
  public String getDescr() {
    return "Выводит справку по доступным командам";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      return true;
    } else {
      System.out.println("help не имеет аргументов!");
      return false;
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      System.out.println("Доступные команды:");
      for (String command : commandMap.keySet()) {
        System.out.println(
            commandMap.get(command).getName() + ":" + commandMap.get(command).getDescr());
      }
    }
  }
}
