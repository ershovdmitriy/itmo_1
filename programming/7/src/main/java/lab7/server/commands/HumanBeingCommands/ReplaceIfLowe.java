package lab7.server.commands.HumanBeingCommands;

import lab7.common.collection.User;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.CollectionManager;
import lab7.server.collections.UserManager;
import lab7.server.commands.ServerCommand;

import java.util.Comparator;
import java.util.Map;

public class ReplaceIfLowe<T> implements ServerCommand {

  private final CollectionManager<? extends Map<String, T>, T> manager;
  private final Comparator<T> comparator;
  private final UserManager userManager;

  public ReplaceIfLowe(
      CollectionManager<? extends Map<String, T>, T> manager, Comparator<T> comparator, UserManager userManager) {
    this.manager = manager;
    this.comparator = comparator;
    this.userManager = userManager;
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
    User user = userManager.getUser(commandRequest.getUser(), commandRequest.getPassword());
    String key = (String) commandRequest.getArgument();
    T object = (T) commandRequest.getObject();
    System.out.println(object);
    if (comparator.compare(object, manager.getCollection().get(key)) < 0) {
      manager.addElementToCollection(key, object, user);
    }
    return new CommandResponse<>(getName(), "Элемент с ключом " + key + " изменен.");
  }
}
