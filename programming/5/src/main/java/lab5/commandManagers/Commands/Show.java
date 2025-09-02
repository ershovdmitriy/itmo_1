package lab5.commandManagers.Commands;

import java.util.Map;
import lab5.collectionManagers.CollectionManager;
import lab5.commandManagers.Command;

public class Show extends Command {

  private final CollectionManager<? extends Map<?, ?>, ?> manager;

  public Show(CollectionManager<? extends Map<?, ?>, ?> manager) {
    super(false);
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "show";
  }

  @Override
  public String getDescr() {
    return "Выводит в стандартный поток вывода все элементы коллекции в строковом представлении";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      return true;
    } else {
      System.out.println("show не имеет аргументов!");
      return false;
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      if (manager.getCollection().isEmpty()) {
        System.out.println("Коллекция пуста.");
      }
      System.out.println("Все элементы коллекции:");
      for (var key : manager.getCollection().keySet()) {
        System.out.println("{" + key + "} - " + manager.getCollection().get(key).toString());
      }
    }
  }
}
