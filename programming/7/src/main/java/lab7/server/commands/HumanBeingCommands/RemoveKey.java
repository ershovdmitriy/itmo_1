package lab7.server.commands.HumanBeingCommands;

import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.CollectionManager;
import lab7.server.collections.UserManager;
import lab7.server.commands.ServerCommand;
import lab7.common.collection.User;

import java.util.Map;

public class RemoveKey implements ServerCommand {

  private final CollectionManager<? extends Map<String, ?>, ?> manager;
  private final UserManager userManager;

  public RemoveKey(CollectionManager<? extends Map<String, ?>, ?> manager, UserManager userManager) {
    this.manager = manager;
    this.userManager = userManager;
  }

  @Override
  public String getName() {
    return "remove_key";
  }

  @Override
  public String getDescr() {
    return "[String key] Удаляет элемент из коллекции по его ключу";
  }

  @Override
  public CommandResponse<?> execute(CommandRequest<?, ?> commandRequest) {
    User user = userManager.getUser(commandRequest.getUser(), commandRequest.getPassword());
    String key = (String) commandRequest.getArgument();
    if (manager.checkObjectByKey(key)) {
      manager.removeElementToCollection(key, user);
      return new CommandResponse<>(getName(), "Элемент с ключом: " + key + " удален.");
    } else {
      return new CommandResponse<>(getName(), "Элемент с ключом: " + key + " не найден.");
    }
  }
}
