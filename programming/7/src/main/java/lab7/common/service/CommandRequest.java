package lab7.common.service;

import java.io.Serializable;

public class CommandRequest<T, B> implements Serializable {
  private final String commandName;
  private String user;
  private String password;
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

  public void setUser(String user, String password) {
    this.user = user;
    this.password = password;
  }

  public String getCommandName() {
    return commandName;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }

  public B getObject() {
    return object;
  }

  public T getArgument() {
    return argument;
  }
}
