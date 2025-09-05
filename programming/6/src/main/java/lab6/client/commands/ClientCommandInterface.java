package lab6.client.commands;

import lab6.common.exception.CommandException;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;

public interface ClientCommandInterface {

  String getName();

  boolean checkArgument();

  CommandRequest<?, ?> buildRequest() throws CommandException;

  void read(CommandResponse<?> commandResponse) throws CommandException;
}
