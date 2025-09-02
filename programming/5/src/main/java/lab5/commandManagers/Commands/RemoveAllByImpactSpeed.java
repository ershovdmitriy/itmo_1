package lab5.commandManagers.Commands;

import java.util.Map;
import lab5.collection.HumanBeing.HumanBeing;
import lab5.collectionManagers.CollectionManager;
import lab5.commandManagers.Command;

public class RemoveAllByImpactSpeed extends Command {

  private final CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager;

  public RemoveAllByImpactSpeed(
      CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager) {
    super(false);
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "remove_all_by_impact_speed [impact_speed]";
  }

  @Override
  public String getDescr() {
    return "Удаляет из коллекции все элементы, значение поля impactSpeed которого эквивалентно заданному";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      System.out.println("remove_all_by_impact_speed имеет аргумент типа Long");
      return false;
    } else {
      try {
        Long.parseLong((String) inputArgument);
        return true;
      } catch (NumberFormatException e) {
        System.out.println("remove_all_by_impact_speed имеет аргумент типа Long");
        return false;
      }
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      for (String key : manager.getCollection().keySet()) {
        if (manager.getCollection().get(key).getImpactSpeed() == Long.parseLong(getArgument())) {
          manager.removeElementToCollection(key);
        }
      }
      System.out.println("Элементы с impactSpeed = " + getArgument() + " удалены.");
    }
  }
}
