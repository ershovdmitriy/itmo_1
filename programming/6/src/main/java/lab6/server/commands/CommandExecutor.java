package lab6.server.commands;

import java.util.Map;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.common.service.Serializer;
import lab6.server.logging.ServerLogger;

public class CommandExecutor<C extends Map<String, ServerCommand>> {
  private final C commandMap;

  public CommandExecutor(CommandMap<C> commandMap) {
    this.commandMap = commandMap.getCommandMap();
  }

  public CommandResponse<?> processRequest(byte[] requestData) {
    CommandResponse<?> response;
    try {
      CommandRequest<?, ?> request = Serializer.deserialize(requestData, CommandRequest.class);
      ServerLogger.log("Получена команда: " + request.getCommandName());
      ServerCommand command = commandMap.get(request.getCommandName());
      response = command.execute(request);
    } catch (Exception e) {
      response = new CommandResponse<>(null, "Ошибка: " + e.getMessage());
    }
    return response;
  }
}
