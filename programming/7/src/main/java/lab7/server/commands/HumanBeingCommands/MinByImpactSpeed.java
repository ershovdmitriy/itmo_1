package lab7.server.commands.HumanBeingCommands;

import lab7.common.collection.HumanBeing.HumanBeing;
import lab7.common.collection.HumanBeing.comparators.HumanBeingComparatorByImpactSpeed;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.CollectionManager;
import lab7.server.commands.ServerCommand;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class MinByImpactSpeed implements ServerCommand {

  CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager;
  HumanBeingComparatorByImpactSpeed comparator;

  public MinByImpactSpeed(
      CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager,
      HumanBeingComparatorByImpactSpeed comparator) {
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
  public CommandResponse<HumanBeing> execute(CommandRequest<?, ?> commandRequest) {
    Optional<HumanBeing> minObject =
        manager.getCollection().values().stream()
            .filter(obj -> obj.getImpactSpeed() != null)
            .min(Comparator.comparingLong(HumanBeing::getImpactSpeed));

    if (minObject.isPresent()) {
      return new CommandResponse<>(
          getName(), "Элемент с минимальным Impact Speed:", minObject.get());
    } else {
      return new CommandResponse<>(getName(), "Элементов с заданным Impact Speed нет в коллекции");
    }
  }
}
