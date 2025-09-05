package lab6.server.commands.HumanBeingCommands;

import java.util.Map;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.server.collection.CollectionManager;
import lab6.server.commands.ServerCommand;

public class Insert<T> implements ServerCommand {

  private final CollectionManager<? extends Map<String, T>, T> manager;

  public Insert(CollectionManager<? extends Map<String, T>, T> manager) {
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "insert";
  }

  @Override
  public String getDescr() {
    return "[String key] Добавляет новый элемент с заданным ключом";
  }

  @Override
  public CommandResponse<?> execute(CommandRequest<?, ?> commandRequest) {
    String key = (String) commandRequest.getArgument();
    T object = (T) commandRequest.getObject();
    manager.addElementToCollection(key, object);
    return new CommandResponse<>(getName(), "Элемент добавлен в коллекцию.");
  }
}
