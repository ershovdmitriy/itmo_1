package lab6.server.commands.HumanBeingCommands;

import java.util.Map;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.server.collection.CollectionManager;
import lab6.server.commands.ServerCommand;

public class Info implements ServerCommand {

  private final CollectionManager<? extends Map<?, ?>, ?> manager;

  public Info(CollectionManager<? extends Map<?, ?>, ?> manager) {
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "info";
  }

  @Override
  public String getDescr() {
    return "Выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
  }

  @Override
  public CommandResponse<?> execute(CommandRequest<?, ?> commandRequest) {
    String message =
        "Информация о коллекции:"
            + "\nТип коллекции: "
            + manager.getCollection().getClass()
            + "\nДата создания: "
            + manager.getCreationDate()
            + "\nКоличество элементов: "
            + manager.getCollection().size();
    return new CommandResponse<>(getName(), message);
  }
}
