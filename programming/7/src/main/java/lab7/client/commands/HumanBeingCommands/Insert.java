package lab7.client.commands.HumanBeingCommands;

import lab7.client.builders.ObjectBuilder;
import lab7.client.commands.ClientCommand;
import lab7.common.exception.CommandException;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;

public class Insert<T> extends ClientCommand {

  private final ObjectBuilder<T> builder;

  public Insert(ObjectBuilder<T> builder) {
    this.builder = builder;
  }

  @Override
  public String getName() {
    return "insert";
  }

  @Override
  public boolean checkArgument() {
    if (getArgument() == null) {
      System.out.println("insert имеет аргумент типа String");
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
