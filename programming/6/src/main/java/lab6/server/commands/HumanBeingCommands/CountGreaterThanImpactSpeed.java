package lab6.server.commands.HumanBeingCommands;

import java.util.Map;
import lab6.common.collection.HumanBeing.HumanBeing;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.server.collection.CollectionManager;
import lab6.server.commands.ServerCommand;

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
