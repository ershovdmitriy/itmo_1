package lab6.client.commands.HumanBeingCommands;

import lab6.client.commands.ClientCommand;
import lab6.common.exception.CommandException;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;

public class RemoveGreaterKey extends ClientCommand {

  @Override
  public String getName() {
    return "remove_greater_key";
  }

  @Override
  public boolean checkArgument() {
    if (getArgument() == null) {
      System.out.println("remove_greater_key имеет аргумент типа String");
      return false;
    } else {
      return true;
    }
  }

  @Override
  public CommandRequest<String, ?> buildRequest() throws CommandException {
    return new CommandRequest<>(getName(), getArgument());
  }

  @Override
  public void read(CommandResponse<?> commandResponse) throws CommandException {
    System.out.println(commandResponse.getMessage());
  }
}
