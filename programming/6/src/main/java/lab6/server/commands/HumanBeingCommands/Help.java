package lab6.server.commands.HumanBeingCommands;

import java.util.HashMap;
import java.util.LinkedHashMap;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.server.commands.ServerCommand;

public class Help implements ServerCommand {

  LinkedHashMap<String, ServerCommand> commandMap;

  public Help(LinkedHashMap<String, ServerCommand> commandMap) {
    this.commandMap = commandMap;
  }

  @Override
  public String getName() {
    return "help";
  }

  @Override
  public String getDescr() {
    return "Выводит справку по доступным командам";
  }

  @Override
  public CommandResponse<HashMap<String, String>> execute(CommandRequest<?, ?> commandRequest) {
    HashMap<String, String> helpMap = new HashMap<>();
    for (String command : commandMap.keySet()) {
      helpMap.put(commandMap.get(command).getName(), commandMap.get(command).getDescr());
    }
    return new CommandResponse<>(getName(), "Доступные команды:", helpMap);
  }
}
