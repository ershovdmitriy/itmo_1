package lab6.server.commands;

import java.util.Map;

public interface CommandMap<C extends Map<String, ServerCommand>> {
  C getCommandMap();
}
