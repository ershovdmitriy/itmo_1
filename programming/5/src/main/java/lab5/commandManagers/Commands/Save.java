package lab5.commandManagers.Commands;

import java.util.Map;
import lab5.collectionManagers.CollectionManager;
import lab5.commandManagers.Command;

public class Save extends Command {

  private final CollectionManager<? extends Map<String, ?>, ?> manager;

  public Save(CollectionManager<? extends Map<String, ?>, ?> manager) {
    super(false);
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "save";
  }

  @Override
  public String getDescr() {
    return "Сохраняет коллекцию в файл";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      return true;
    } else {
      System.out.println("save не имеет аргументов!");
      return false;
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      manager.saveCollection();
      System.out.println("Коллекция сохранена.");
    }
  }
}
