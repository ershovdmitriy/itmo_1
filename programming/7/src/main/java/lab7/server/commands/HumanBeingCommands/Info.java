package lab7.server.commands.HumanBeingCommands;

import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.CollectionManager;
import lab7.server.collections.MetaSqlManager;
import lab7.server.commands.ServerCommand;


import java.util.Map;

public class Info implements ServerCommand {

  private final CollectionManager<? extends Map<?, ?>, ?> manager;
  private final MetaSqlManager metaManager;

  public Info(CollectionManager<? extends Map<?, ?>, ?> manager, MetaSqlManager metaManager) {
    this.manager = manager;
    this.metaManager = metaManager;
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
            + metaManager.getType()
            + "\nДата создания: "
            + metaManager.getDate()
            + "\nКоличество элементов: "
            + manager.getCollection().size();
    return new CommandResponse<>(getName(), message);
  }
}
