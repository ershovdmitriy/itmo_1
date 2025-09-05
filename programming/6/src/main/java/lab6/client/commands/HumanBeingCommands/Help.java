package lab6.client.commands.HumanBeingCommands;

import java.util.HashMap;
import lab6.client.commands.ClientCommand;
import lab6.common.exception.CommandException;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;

public class Help extends ClientCommand {

  @Override
  public String getName() {
    return "help";
  }

  @Override
  public boolean checkArgument() {
    if (getArgument() == null) {
      return true;
    } else {
      System.out.println("help не имеет аргументов!");
      return false;
    }
  }

  @Override
  public CommandRequest<?, ?> buildRequest() throws CommandException {
    return new CommandRequest<>(getName());
  }

  @Override
  public void read(CommandResponse<?> commandResponse) throws CommandException {
    System.out.println(commandResponse.getMessage());
    HashMap<String, String> helpMap = (HashMap<String, String>) commandResponse.getData();
    for (String key : helpMap.keySet()) {
      System.out.println(key + ": " + helpMap.get(key));
    }
  }
}
