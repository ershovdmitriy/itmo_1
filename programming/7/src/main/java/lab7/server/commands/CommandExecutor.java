package lab7.server.commands;

import lab7.common.collection.User;
import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.server.collections.UserManager;
import lab7.server.logging.ServerLogger;
import lab7.server.network.ResponseSender;

import java.net.SocketAddress;

public class CommandExecutor implements Runnable{
  private final ServerCommand command;
  private final UserManager userManager;
  private final SocketAddress clientAddress;
  private final CommandRequest<?, ?> request;
  private final ResponseSender responseSender;

  public CommandExecutor(ServerCommand command, UserManager userManager, CommandRequest<?, ?> request, ResponseSender responseSender, SocketAddress clientAddress) {
    this.command = command;
    this.responseSender = responseSender;
    this.userManager = userManager;
    this.request = request;
    this.clientAddress = clientAddress;
  }

  @Override
  public void run() {
    CommandResponse<?> response;
    try {
      User user = userManager.getUser(request.getUser(), request.getPassword());
      if(userManager.checkUser(user) || command.getName().equals("registration")){
        response = command.execute(request);
        ServerLogger.log("Команда успешно выполнена");
      }
      else{
        response = new CommandResponse<>(null, "Пользователь не авторизован или не зарегистрирован");
        ServerLogger.log("Полученная команда не выполнена из-за того, что пользователя: " + user.login + " нет в базе данных");
      }
    } catch (Exception e) {
      response = new CommandResponse<>(null, "Ошибка при исполнении команды");
      ServerLogger.error("Ошибка при исполнении команды");
    }
    responseSender.sendResponse(response, clientAddress);
  }
}
