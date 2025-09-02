package lab5.commandManagers.Commands;

import lab5.commandManagers.Command;

public class Exit extends Command {

  public Exit() {
    super(false);
  }

  @Override
  public String getName() {
    return "exit";
  }

  @Override
  public String getDescr() {
    return "Завершает программу (без сохранения в файл)";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      return true;
    } else {
      System.out.println("exit не имеет аргументов!");
      return false;
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      System.out.println("Завершение работы.");
      System.exit(0);
    }
  }
}
