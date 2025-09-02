package lab5.commandManagers.Commands;

import java.util.Map;
import lab5.collection.HumanBeing.HumanBeing;
import lab5.collection.HumanBeing.comparators.HumanBeingComparatorByImpactSpeed;
import lab5.collectionManagers.CollectionManager;
import lab5.commandManagers.Command;

public class MinByImpactSpeed extends Command {

  CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager;
  HumanBeingComparatorByImpactSpeed comparator;

  public MinByImpactSpeed(
      CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager,
      HumanBeingComparatorByImpactSpeed comparator) {
    super(false);
    this.manager = manager;
    this.comparator = comparator;
  }

  @Override
  public String getName() {
    return "min_by_impact_speed";
  }

  @Override
  public String getDescr() {
    return "Выводит любой объект из коллекции, значение поля impactSpeed которого является минимальным";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      return true;
    } else {
      System.out.println("min_by_impact_speed не имеет аргументов!");
      return false;
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      HumanBeing minImpactSpeed = new HumanBeing();
      minImpactSpeed.setImpactSpeed(Long.MAX_VALUE);
      for (String key : manager.getCollection().keySet()) {
        if (comparator.compare(minImpactSpeed, manager.getCollection().get(key)) < 0) {
          minImpactSpeed = manager.getCollection().get(key);
        }
      }
      System.out.println("Элемент с минимальным Impact Speed:");
      System.out.println(minImpactSpeed.toString());
    }
  }
}
