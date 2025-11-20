package lab7.server.commands.HumanBeingCommands;

import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.CollectionManager;
import lab7.server.commands.ServerCommand;

import java.util.LinkedHashMap;

public class Show<T> implements ServerCommand {

  private final CollectionManager<LinkedHashMap<String, T>, T> manager;

  public Show(CollectionManager<LinkedHashMap<String, T>, T> manager) {
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "show";
  }

  @Override
  public String getDescr() {
    return "Выводит все элементы коллекции в строковом представлении";
  }

  @Override
  public CommandResponse<LinkedHashMap<String, T>> execute(CommandRequest<?, ?> commandRequest) {
    if (manager.getCollection().isEmpty()) {
      return new CommandResponse<>(getName(), "Коллекция пуста.");
    }
    manager.sortCollection();
    return new CommandResponse<>(getName(), "Все элементы коллекции:", manager.getCollection());
  }
}
