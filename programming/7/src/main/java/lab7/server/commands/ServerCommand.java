package lab7.server.commands;

import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;

public interface ServerCommand {
  CommandResponse execute(CommandRequest<?, ?> commandRequest) throws IllegalArgumentException;

  String getName();

  String getDescr();
}
