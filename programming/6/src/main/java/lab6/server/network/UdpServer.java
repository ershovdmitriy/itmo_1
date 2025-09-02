package lab6.server.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.common.service.Serializer;
import lab6.server.collection.CollectionManager;
import lab6.server.commands.CommandMap;
import lab6.server.commands.ServerCommand;
import lab6.server.logging.ServerLogger;

public class UdpServer<C extends Map<String, ServerCommand>> {
  private final int port;
  private final String host;
  private final int bufferSize;
  private final C commandMap;
  private volatile boolean running = true;

  private final ConcurrentLinkedQueue<String> commandQueue = new ConcurrentLinkedQueue<>();
  private final CollectionManager<?, ?> collectionManager;

  public UdpServer(
      int port,
      String host,
      int bufferSize,
      CommandMap<C> commandMap,
      CollectionManager<?, ?> collectionManager) {
    this.port = port;
    this.host = host;
    this.bufferSize = bufferSize;
    this.commandMap = commandMap.getCommandMap();

    this.collectionManager = collectionManager;
    startConsoleReader();
  }

  public void start() {
    try (DatagramChannel channel = DatagramChannel.open()) {
      channel.bind(new InetSocketAddress(host, port));
      channel.configureBlocking(false);
      ServerLogger.log("Сервер запущен на порту " + port);
      ServerLogger.log("Ожидание запросов...");
      ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

      while (running) {
        try {
          buffer.clear();
          SocketAddress clientAddress = channel.receive(buffer);
          if (clientAddress != null) {
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            buffer.clear();
            processRequest(channel, clientAddress, data);
          }

          processConsoleCommands();

          Thread.sleep(10);
        } catch (InterruptedException e) {
          ServerLogger.log("Сервер остановлен");
          break;
        } catch (IOException e) {
          ServerLogger.error("Ошибка ввода-вывода: " + e.getMessage());
        }
      }
    } catch (IOException e) {
      ServerLogger.error("Ошибка запуска сервера: " + e.getMessage());
    }
  }

  private void processRequest(DatagramChannel channel, SocketAddress clientAddress, byte[] data) {
    CommandResponse<?> response;
    try {
      CommandRequest<?, ?> request = Serializer.deserialize(data, CommandRequest.class);
      ServerLogger.log("Получена команда: " + request.getCommandName());
      ServerCommand command = commandMap.get(request.getCommandName());
      response = command.execute(request);
    } catch (Exception e) {
      response = new CommandResponse<>(null, "Ошибка: " + e.getMessage());
    }
    try {
      byte[] responseData = Serializer.serialize(response);
      ByteBuffer buffer = ByteBuffer.wrap(responseData);
      channel.send(buffer, clientAddress);
      ServerLogger.log("Ответ отправлен клиенту " + clientAddress);
    } catch (IOException e) {
      ServerLogger.error("Ошибка при отправке ответа: " + e.getMessage());
    }
    ServerLogger.log("Запрос от " + clientAddress + " обработан");
  }

  private void startConsoleReader() {
    Thread consoleThread =
        new Thread(
            () -> {
              try (Scanner scanner = new Scanner(System.in)) {
                while (running) {
                  if (scanner.hasNextLine()) {
                    String command = scanner.nextLine().trim();
                    commandQueue.add(command);
                  }
                }
              }
            });
    consoleThread.setDaemon(true);
    consoleThread.start();
  }

  private void processConsoleCommands() {
    while (!commandQueue.isEmpty()) {
      String command = commandQueue.poll();
      switch (command.toLowerCase().trim()) {
        case "save":
          collectionManager.saveCollection();
          ServerLogger.log("Данные успешно сохранены!");
          break;
        case "exit":
          ServerLogger.log("Завершение работы сервера...");
          collectionManager.saveCollection();
          running = false;
          break;
        default:
          ServerLogger.error("Неизвестная команда: " + command);
      }
    }
  }
}
