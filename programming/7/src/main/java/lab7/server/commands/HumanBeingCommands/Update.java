package lab7.server.commands.HumanBeingCommands;

import lab7.common.collection.HavingId;
import lab7.common.collection.User;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.CollectionManager;
import lab7.server.collections.UserManager;
import lab7.server.commands.ServerCommand;

import java.util.Map;

public class Update<T extends HavingId> implements ServerCommand {

  private final CollectionManager<? extends Map<String, T>, T> manager;
  private final UserManager userManager;

  public Update(CollectionManager<? extends Map<String, T>, T> manager, UserManager userManager) {
    this.manager = manager;
    this.userManager = userManager;
  }

  @Override
  public String getName() {
    return "update";
  }

  @Override
  public String getDescr() {
    return "[Int id] Обновляет значение элемента коллекции, id которого равен заданному";
  }

  @Override
  public CommandResponse<?> execute(CommandRequest<?, ?> commandRequest) {
    User user = userManager.getUser(commandRequest.getUser(), commandRequest.getPassword());
    Integer id = (Integer) commandRequest.getArgument();
    T object = (T) commandRequest.getObject();
    String key = manager.getKeyById(id);
    if (key == null) {
      return new CommandResponse<>(
          getName(), "Элемента с таким id-номером нет в текущей коллекции!");
    } else {
      manager.updateElementToCollection(key, object, user);
      return new CommandResponse<>(getName(), "Элемента с данным ID успешно заменен.");
    }
  }
}
