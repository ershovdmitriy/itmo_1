package lab6.server.commands.HumanBeingCommands;

import java.util.ConcurrentModificationException;
import java.util.Map;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.server.collection.CollectionManager;
import lab6.server.commands.ServerCommand;

public class RemoveLowerKey implements ServerCommand {

  private final CollectionManager<? extends Map<String, ?>, ?> manager;

  public RemoveLowerKey(CollectionManager<? extends Map<String, ?>, ?> manager) {
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "remove_lower_key";
  }

  @Override
  public String getDescr() {
    return "[String key] Удаляет из коллекции все элементы, ключ которых меньше, чем заданный";
  }

  @Override
  public CommandResponse<?> execute(CommandRequest<?, ?> commandRequest) {
    String key = (String) commandRequest.getArgument();
    try {
      manager.getCollection().keySet().stream()
          .filter(k -> k.compareTo(key) < 0)
          .forEach(manager::removeElementToCollection);
    } catch (ConcurrentModificationException e) {
    }
    return new CommandResponse<>(
        getName(), "Все элементы с ключом меньше чем " + key + " удалены.");
  }
}
