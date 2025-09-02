package lab5.commandManagers.Commands;

import java.util.Map;
import lab5.collectionManagers.CollectionManager;
import lab5.commandManagers.Command;

public class Clear extends Command {
  private final CollectionManager<? extends Map<String, ?>, ?> manager;

  public Clear(CollectionManager<? extends Map<String, ?>, ?> manager) {
    super(false);
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "clear";
  }

  @Override
  public String getDescr() {
    return "Очищает коллекцию";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      return true;
    } else {
      System.out.println("clear не имеет аргументов!");
      return false;
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      manager.clearCollection();
      System.out.println("Коллекция очищена.");
    }
  }
}
