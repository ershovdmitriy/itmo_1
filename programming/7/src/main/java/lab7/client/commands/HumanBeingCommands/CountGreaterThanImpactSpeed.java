package lab7.client.commands.HumanBeingCommands;

import lab7.client.commands.ClientCommand;
import lab7.common.exception.CommandException;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;

public class CountGreaterThanImpactSpeed extends ClientCommand {

  @Override
  public String getName() {
    return "count_greater_than_impact_speed";
  }

  @Override
  public boolean checkArgument() {
    if (getArgument() == null) {
      System.out.println("count_greater_than_impact_speed имеет аргумент типа Long");
      return false;
    } else {
      try {
        Long.parseLong(getArgument());
        return true;
      } catch (NumberFormatException e) {
        System.out.println("count_greater_than_impact_speed имеет аргумент типа Long");
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
    System.out.println(commandResponse.getData());
  }
}
