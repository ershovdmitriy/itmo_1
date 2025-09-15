package lab7.server.commands.HumanBeingCommands;

import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.commands.ServerCommand;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
