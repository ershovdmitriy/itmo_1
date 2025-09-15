package lab7.server.commands.HumanBeingCommands;

import lab7.common.collection.HumanBeing.HumanBeing;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.CollectionManager;
import lab7.server.collections.UserManager;
import lab7.server.commands.ServerCommand;
import lab7.common.collection.User;

import java.util.Map;

public class RemoveGreaterKey implements ServerCommand {

  private final CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager;
  private final UserManager userManager;

  public RemoveGreaterKey(CollectionManager<? extends Map<String, HumanBeing>, HumanBeing> manager, UserManager userManager) {
    this.manager = manager;
    this.userManager = userManager;
  }

  @Override
  public String getName() {
    return "remove_greater_key";
  }

  @Override
  public String getDescr() {
    return "[String key] Удаляет из коллекции все элементы, ключ которых превышает заданный";
  }

  @Override
  public CommandResponse<?> execute(CommandRequest<?, ?> commandRequest) {
    User user = userManager.getUser(commandRequest.getUser(), commandRequest.getPassword());
    String key = (String) commandRequest.getArgument();
    manager.getCollection().keySet().stream()
        .filter(k -> k.compareTo(key) > 0 && manager.getCollection().get(k).getUser().compareTo(user) == 0)
        .forEach(k -> manager.removeElementToCollection(k, user));
    return new CommandResponse<>(getName(), "Все элементы ключом больше чем " + key + " удалены.");
  }
}
