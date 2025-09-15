package lab7.server.commands.HumanBeingCommands;

import lab7.common.collection.User;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.CollectionManager;
import lab7.server.collections.UserManager;
import lab7.server.commands.ServerCommand;

import java.util.Map;

public class Clear implements ServerCommand {
  private final CollectionManager<? extends Map<String, ?>, ?> manager;
  private final UserManager userManager;

  public Clear(CollectionManager<? extends Map<String, ?>, ?> manager, UserManager userManager) {
    this.manager = manager;
    this.userManager = userManager;
  }

  @Override
  public String getName() {
    return "clear";
  }

  @Override
  public String getDescr() {
    return "Очищает все элементы пользователя";
  }

  @Override
  public CommandResponse<?> execute(CommandRequest<?, ?> commandRequest) {
    User user = userManager.getUser(commandRequest.getUser(), commandRequest.getPassword());
    manager.clearCollection(user);
    return new CommandResponse<>(getName(), "Ваши элементы очищены.");
  }
}
