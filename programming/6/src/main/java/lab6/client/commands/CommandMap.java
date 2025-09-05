package lab6.client.commands;

import java.util.Map;

public interface CommandMap<C extends Map<String, ClientCommand>> {
  C getCommandMap();
}
