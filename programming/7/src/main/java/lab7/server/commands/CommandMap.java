package lab7.server.commands;

import java.util.Map;

public interface CommandMap<C extends Map<String, ServerCommand>> {
  C getCommandMap();
}
