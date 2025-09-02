package lab6.common.service;

import java.io.Serializable;

public class CommandResponse<T> implements Serializable {
  private String command;
  private String message;
  private T data;

  public CommandResponse(String command, String message) {
    this.command = command;
    this.message = message;
  }

  public CommandResponse(String command, String message, T data) {
    this.command = command;
    this.message = message;
    this.data = data;
  }

  public String getCommand() {
    return command;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }
}
