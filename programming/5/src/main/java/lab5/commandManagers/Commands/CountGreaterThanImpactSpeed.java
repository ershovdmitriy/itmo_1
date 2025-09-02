package lab5.commandManagers.Commands;

import java.util.Map;
import lab5.collection.HumanBeing.HumanBeing;
import lab5.collectionManagers.CollectionManager;
import lab5.commandManagers.Command;

public class CountGreaterThanImpactSpeed extends Command {

  private final CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager;

  public CountGreaterThanImpactSpeed(
      CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager) {
    super(false);
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "count_greater_than_impact_speed [impact_speed]";
  }

  @Override
  public String getDescr() {
    return "Выводит количество элементов, значение поля impactSpeed которых больше заданного";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      System.out.println("count_greater_than_impact_speedимеет аргумент типа Long");
      return false;
    } else {
      try {
        Long.parseLong((String) inputArgument);
        return true;
      } catch (NumberFormatException e) {
        System.out.println("count_greater_than_impact_speed имеет аргумент типа Long");
        return false;
      }
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      int countObject = 0;
      for (String key : manager.getCollection().keySet()) {
        if (manager.getCollection().get(key).getImpactSpeed() > Long.parseLong(getArgument())) {
          countObject += 1;
        }
      }
      System.out.println("Количество элементов: " + countObject);
    }
  }
}
