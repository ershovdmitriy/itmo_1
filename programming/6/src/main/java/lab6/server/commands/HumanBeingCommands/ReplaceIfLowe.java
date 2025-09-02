package lab6.server.commands.HumanBeingCommands;

import java.util.Comparator;
import java.util.Map;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.server.collection.CollectionManager;
import lab6.server.commands.ServerCommand;

public class ReplaceIfLowe<T> implements ServerCommand {

  private final CollectionManager<? extends Map<String, T>, T> manager;
  private final Comparator<T> comparator;

  public ReplaceIfLowe(
      CollectionManager<? extends Map<String, T>, T> manager, Comparator<T> comparator) {
    this.manager = manager;
    this.comparator = comparator;
  }

  @Override
  public String getName() {
    return "replace_if_lowe";
  }

  @Override
  public String getDescr() {
    return "[String key] Заменяет значение по ключу, если новое значение меньше старого";
  }

  @Override
  public CommandResponse<?> execute(CommandRequest<?, ?> commandRequest) {
    String key = (String) commandRequest.getArgument();
    T object = (T) commandRequest.getObject();
    System.out.println(object);
    if (comparator.compare(object, manager.getCollection().get(key)) < 0) {
      manager.addElementToCollection(key, object);
    }
    return new CommandResponse<>(getName(), "Элемент с ключом " + key + " изменен.");
  }
}
