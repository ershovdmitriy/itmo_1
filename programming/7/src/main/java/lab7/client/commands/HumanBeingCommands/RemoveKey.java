package lab7.client.commands.HumanBeingCommands;

import lab7.client.commands.ClientCommand;
import lab7.common.exception.CommandException;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;

public class RemoveKey extends ClientCommand {

  @Override
  public String getName() {
    return "remove_key";
  }

  @Override
  public boolean checkArgument() {
    if (getArgument() == null) {
      System.out.println("remove_key имеет аргумент типа String");
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
