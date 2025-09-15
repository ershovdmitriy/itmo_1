package lab7.client.commands.HumanBeingCommands;

import lab7.client.commands.ClientCommand;
import lab7.common.exception.CommandException;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;

import java.util.LinkedHashMap;

public class Show<T> extends ClientCommand {

  @Override
  public String getName() {
    return "show";
  }

  @Override
  public boolean checkArgument() {
    if (getArgument() == null) {
      return true;
    } else {
      System.out.println("show не имеет аргументов!");
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
    if (commandResponse.getData() != null) {
      LinkedHashMap<String, T> map = (LinkedHashMap<String, T>) commandResponse.getData();
      for (String key : map.keySet()) {
        System.out.println(key + ": " + map.get(key));
      }
    }
  }
}
