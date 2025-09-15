package lab7.client.commands;

import lab7.common.exception.CommandException;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;

public interface ClientCommandInterface {

  String getName();

  boolean checkArgument();

  CommandRequest<?, ?> buildRequest() throws CommandException;

  void read(CommandResponse<?> commandResponse) throws CommandException;
}
