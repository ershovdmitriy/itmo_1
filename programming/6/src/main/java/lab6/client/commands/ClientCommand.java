package lab6.client.commands;

import lab6.common.exception.CommandException;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;

public abstract class ClientCommand implements ClientCommandInterface {

  private String argument;

  @Override
  public abstract String getName();

  public String getArgument() {
    return argument;
  }

  public void setArgument(String argument) {
    this.argument = argument;
  }

  @Override
  public abstract boolean checkArgument();

  @Override
  public abstract CommandRequest<?, ?> buildRequest() throws CommandException;

  @Override
  public abstract void read(CommandResponse<?> commandResponse) throws CommandException;
}
