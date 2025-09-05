package lab6.client.commands.HumanBeingCommands;

import lab6.client.commands.ClientCommand;
import lab6.common.exception.CommandException;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;

public class RemoveAllByImpactSpeed extends ClientCommand {

  @Override
  public String getName() {
    return "remove_all_by_impact_speed";
  }

  @Override
  public boolean checkArgument() {
    if (getArgument() == null) {
      System.out.println("remove_all_by_impact_speed имеет аргумент типа Long");
      return false;
    } else {
      try {
        Long.parseLong(getArgument());
        return true;
      } catch (NumberFormatException e) {
        System.out.println("remove_all_by_impact_speed имеет аргумент типа Long");
        return false;
      }
    }
  }

  @Override
  public CommandRequest<Long, ?> buildRequest() throws CommandException {
    return new CommandRequest<>(getName(), Long.parseLong(getArgument()));
  }

  @Override
  public void read(CommandResponse<?> commandResponse) throws CommandException {
    System.out.println(commandResponse.getMessage());
  }
}
