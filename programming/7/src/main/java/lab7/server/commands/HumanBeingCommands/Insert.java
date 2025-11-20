package lab7.server.commands.HumanBeingCommands;

import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.CollectionManager;
import lab7.server.collections.UserManager;
import lab7.server.commands.ServerCommand;
import lab7.common.collection.User;

import java.util.Map;

public class Insert<T> implements ServerCommand {

  private final CollectionManager<? extends Map<String, T>, T> manager;
  private final UserManager userManager;

  public Insert(CollectionManager<? extends Map<String, T>, T> manager, UserManager userManager) {
    this.manager = manager;
    this.userManager = userManager;
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
    User user = userManager.getUser(commandRequest.getUser(), commandRequest.getPassword());
    String key = (String) commandRequest.getArgument();
    T object = (T) commandRequest.getObject();
    manager.addElementToCollection(key, object, user);
    return new CommandResponse<>(getName(), "Элемент добавлен в коллекцию.");
  }
}
