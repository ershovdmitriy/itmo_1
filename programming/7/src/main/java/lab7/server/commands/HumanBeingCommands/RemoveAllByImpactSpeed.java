package lab7.server.commands.HumanBeingCommands;

import lab7.common.collection.HumanBeing.HumanBeing;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.CollectionManager;
import lab7.server.collections.UserManager;
import lab7.server.commands.ServerCommand;
import lab7.common.collection.User;

import java.util.Map;

public class RemoveAllByImpactSpeed implements ServerCommand {

  private final CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager;
  private final UserManager userManager;

  public RemoveAllByImpactSpeed(
      CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager, UserManager userManager) {
    this.manager = manager;
    this.userManager = userManager;
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
    User user = userManager.getUser(commandRequest.getUser(), commandRequest.getPassword());
    Long impactSpeed = (Long) commandRequest.getArgument();
    manager.getCollection().entrySet().stream()
            .filter(entry -> entry.getValue().getImpactSpeed() != null
                    && entry.getValue().getImpactSpeed().equals(impactSpeed)
                    && entry.getValue().getUser().compareTo(user) == 0)
            .forEach(entry -> manager.removeElementToCollection(entry.getKey(), user));
    return new CommandResponse<>(
        getName(), "Элементы с impactSpeed = " + impactSpeed + " удалены.");
  }
}
