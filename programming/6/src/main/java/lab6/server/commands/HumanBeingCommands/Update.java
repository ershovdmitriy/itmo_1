package lab6.server.commands.HumanBeingCommands;

import java.util.Map;
import lab6.common.collection.HavingId;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.server.collection.CollectionManager;
import lab6.server.collection.IdManager;
import lab6.server.commands.ServerCommand;

public class Update<T extends HavingId> implements ServerCommand {

  private final CollectionManager<? extends Map<String, T>, T> manager;
  private final IdManager<T> idManager;

  public Update(CollectionManager<? extends Map<String, T>, T> manager, IdManager<T> idManager) {
    this.manager = manager;
    this.idManager = idManager;
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
    Integer id = (Integer) commandRequest.getArgument();
    T object = (T) commandRequest.getObject();
    String key = idManager.checkObjectById(id);
    if (key == null) {
      return new CommandResponse<>(
          getName(), "Элемента с таким id-номером нет в текущей коллекции!");
    } else {
      manager.addElementToCollection(key, object);
      return new CommandResponse<>(getName(), "Элемента с данным ID успешно заменен.");
    }
  }
}
