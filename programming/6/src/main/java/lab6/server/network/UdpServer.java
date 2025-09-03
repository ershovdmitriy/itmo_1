package lab6.server.network;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import lab6.client.commands.CommandExecutor;
import lab6.common.service.CommandResponse;
import lab6.common.service.Serializer;
import lab6.server.collection.CollectionManager;
import lab6.server.logging.ServerLogger;

public class UdpServer {
  private final int port;
  private final String host;
  private final int bufferSize;
  private volatile boolean running = true;
  private final CommandExecutor<?> commandExecutor;
  private final ConcurrentLinkedQueue<String> commandQueue = new ConcurrentLinkedQueue<>();
  private final CollectionManager<?, ?> collectionManager;

  public UdpServer(
      int port,
      String host,
      int bufferSize,
      CollectionManager<?, ?> collectionManager,
      CommandExecutor<?> commandExecutor) {
    this.port = port;
    this.host = host;
    this.bufferSize = bufferSize;
    this.collectionManager = collectionManager;
    this.commandExecutor = commandExecutor;
    startConsoleReader();
  }

  public void start() {
    try (DatagramSocket serverSocket = new DatagramSocket(port, InetAddress.getByName(host))) {
      ServerLogger.log("Сервер запущен на порту " + port);
      ServerLogger.log("Ожидание запросов...");
      serverSocket.setSoTimeout(1000);
      byte[] buffer = new byte[bufferSize];

      while (running) {
        processConsoleCommands();
        try {
          DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
          serverSocket.receive(receivePacket);
          CommandResponse<?> response = commandExecutor.processRequest(receivePacket.getData());
          byte[] responseData = Serializer.serialize(response);
          InetAddress clientAddress = receivePacket.getAddress();
          int clientPort = receivePacket.getPort();
          DatagramPacket sendPacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
          serverSocket.send(sendPacket);
          ServerLogger.log("Ответ отправлен клиенту " + clientAddress);

          Thread.sleep(10);
        } catch (SocketTimeoutException e){
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
