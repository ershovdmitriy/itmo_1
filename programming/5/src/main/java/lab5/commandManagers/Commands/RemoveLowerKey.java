package lab5.commandManagers.Commands;

import java.util.Map;
import lab5.collectionManagers.CollectionManager;
import lab5.commandManagers.Command;

public class RemoveLowerKey extends Command {

  private final CollectionManager<? extends Map<String, ?>, ?> manager;

  public RemoveLowerKey(CollectionManager<? extends Map<String, ?>, ?> manager) {
    super(false);
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "remove_lower_key [key]";
  }

  @Override
  public String getDescr() {
    return "Удаляет из коллекции все элементы, ключ которых меньше, чем заданный";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      System.out.println("remove_lower_key имеет аргумент типа String");
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      for (String key : manager.getCollection().keySet()) {
        if (key.compareTo(getArgument()) < 0) {
          manager.removeElementToCollection(key);
        }
      }
      System.out.println("Все элементы с ключом меньше чем " + getArgument() + " удалены.");
    }
  }
}
