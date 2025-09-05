package lab6.server.commands.HumanBeingCommands;

import java.util.Map;
import lab6.common.collection.HumanBeing.HumanBeing;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.server.collection.CollectionManager;
import lab6.server.commands.ServerCommand;

public class RemoveAllByImpactSpeed implements ServerCommand {

  private final CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager;

  public RemoveAllByImpactSpeed(
      CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager) {
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "remove_all_by_impact_speed";
  }

  @Override
  public String getDescr() {
    return "[Long impact_speed] Удаляет из коллекции все элементы, значение поля impactSpeed которого эквивалентно заданному";
  }

  @Override
  public CommandResponse<?> execute(CommandRequest<?, ?> commandRequest) {
    Long impactSpeed = (Long) commandRequest.getArgument();

    manager
        .getCollection()
        .entrySet()
        .removeIf(
            entry -> {
              HumanBeing obj = entry.getValue();
              return obj != null
                  && obj.getImpactSpeed() != null
                  && obj.getImpactSpeed().equals(impactSpeed);
            });
    return new CommandResponse<>(
        getName(), "Элементы с impactSpeed = " + impactSpeed + " удалены.");
  }
}
