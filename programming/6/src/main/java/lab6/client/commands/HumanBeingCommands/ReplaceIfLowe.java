package lab6.client.commands.HumanBeingCommands;

import lab6.client.builders.ObjectBuilderNew;
import lab6.client.commands.ClientCommand;
import lab6.common.exception.CommandException;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;

public class ReplaceIfLowe<T> extends ClientCommand {

  private final ObjectBuilderNew<T> builder;

  public ReplaceIfLowe(ObjectBuilderNew<T> builder) {
    this.builder = builder;
  }

  @Override
  public String getName() {
    return "replace_if_lowe";
  }

  @Override
  public boolean checkArgument() {
    if (getArgument() == null) {
      System.out.println("replace_if_lowe имеет аргумент типа String");
      return false;
    } else {
      return true;
    }
  }

  @Override
  public CommandRequest<String, T> buildRequest() throws CommandException {
    return new CommandRequest<>(getName(), getArgument(), builder.build());
  }

  @Override
  public void read(CommandResponse<?> commandResponse) throws CommandException {
    System.out.println(commandResponse.getMessage());
  }
}
