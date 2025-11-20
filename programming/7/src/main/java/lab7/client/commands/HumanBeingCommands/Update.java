package lab7.client.commands.HumanBeingCommands;

import lab7.client.builders.ObjectBuilder;
import lab7.client.commands.ClientCommand;
import lab7.common.exception.CommandException;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;

public class Update<T> extends ClientCommand {

  private final ObjectBuilder<T> builder;

  public Update(ObjectBuilder<T> builder) {
    this.builder = builder;
  }

  @Override
  public String getName() {
    return "update";
  }

  @Override
  public boolean checkArgument() {
    if (getArgument() == null) {
      System.out.println("update имеет аргумент типа int");
      return false;
    } else {
      try {
        Integer.parseInt(getArgument());
        return true;
      } catch (NumberFormatException e) {
        System.out.println("update имеет аргумент типа int");
        return false;
      }
    }
  }

  @Override
  public CommandRequest<Integer, T> buildRequest() throws CommandException {
    return new CommandRequest<>(getName(), Integer.parseInt(getArgument()), builder.build());
  }

  @Override
  public void read(CommandResponse<?> commandResponse) throws CommandException {
    System.out.println(commandResponse.getMessage());
  }
}
