package lab6.common.service;

import java.io.Serializable;

public class CommandRequest<T, B> implements Serializable {
  private String commandName;
  private T argument;
  private B object;

  public CommandRequest(String commandName) {
    this.commandName = commandName;
  }

  public CommandRequest(String commandName, T argument) {
    this.commandName = commandName;
    this.argument = argument;
  }

  public CommandRequest(String commandName, T argument, B object) {
    this.commandName = commandName;
    this.argument = argument;
    this.object = object;
  }

  public String getCommandName() {
    return commandName;
  }

  public B getObject() {
    return object;
  }

  public T getArgument() {
    return argument;
  }
}
