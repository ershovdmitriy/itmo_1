package lab6.server.commands.HumanBeingCommands;

import java.util.Map;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.server.collection.CollectionManager;
import lab6.server.commands.ServerCommand;

public class RemoveKey implements ServerCommand {

  private final CollectionManager<? extends Map<String, ?>, ?> manager;

  public RemoveKey(CollectionManager<? extends Map<String, ?>, ?> manager) {
    this.manager = manager;
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
    String key = (String) commandRequest.getArgument();
    if (manager.checkObjectByKey(key)) {
      manager.removeElementToCollection(key);
      return new CommandResponse<>(getName(), "Элемент с ключом: " + key + " удален.");
    } else {
      return new CommandResponse<>(getName(), "Элемент с ключом: " + key + " не найден.");
    }
  }
}
