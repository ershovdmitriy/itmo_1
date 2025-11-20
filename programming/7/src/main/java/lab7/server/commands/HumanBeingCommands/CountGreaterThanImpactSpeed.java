package lab7.server.commands.HumanBeingCommands;

import lab7.common.collection.HumanBeing.HumanBeing;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.CollectionManager;
import lab7.server.commands.ServerCommand;

import java.util.Map;

public class CountGreaterThanImpactSpeed implements ServerCommand {

  private final CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager;

  public CountGreaterThanImpactSpeed(
      CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager) {
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "count_greater_than_impact_speed";
  }

  @Override
  public String getDescr() {
    return "[Long impact_speed] Выводит количество элементов, значение поля impactSpeed которых больше заданного";
  }

  @Override
  public CommandResponse<Long> execute(CommandRequest<?, ?> commandRequest) {
    Long impactSpeed = (Long) commandRequest.getArgument();
    long count =
        manager.getCollection().values().stream()
            .filter(obj -> obj.getImpactSpeed() != null)
            .filter(obj -> obj.getImpactSpeed() > impactSpeed)
            .count();
    return new CommandResponse<>(getName(), "Количество элементов: ", count);
  }
}
