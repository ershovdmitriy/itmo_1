package lab6.server.commands;

import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;

public interface ServerCommand {
  CommandResponse execute(CommandRequest<?, ?> commandRequest) throws IllegalArgumentException;

  String getName();

  String getDescr();
}
