package lab7.server.network;

import lab7.server.collections.UserManager;
import lab7.server.commands.CommandMap;
import lab7.server.commands.ServerCommand;
import lab7.server.logging.ServerLogger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UdpServer<C extends Map<String, ServerCommand>> {
  private final int port;
  private final int bufferSize;
  private static final int REQUEST_POOL_SIZE = 10;
  private static final int RESPONSE_POOL_SIZE = 10;
  private volatile boolean running = true;
  private final CommandMap<C> commandMap;
  private final ConcurrentLinkedQueue<String> commandQueue = new ConcurrentLinkedQueue<>();
  private final UserManager userManager;

  public UdpServer(
      int port,
      int bufferSize,
      CommandMap<C> commandMap,
      UserManager userManager) {
    this.port = port;
    this.bufferSize = bufferSize;
    this.commandMap = commandMap;
    this.userManager = userManager;
    startConsoleReader();
  }

  public void start() {
    ExecutorService requestPool = Executors.newFixedThreadPool(REQUEST_POOL_SIZE);
    ResponseSender responseSender = null;
    try (DatagramSocket serverSocket = new DatagramSocket(port)) {
      ServerLogger.log("Сервер запущен на порту " + port);
      ServerLogger.log("Ожидание запросов...");
      responseSender = new ResponseSender(serverSocket, RESPONSE_POOL_SIZE);
      serverSocket.setSoTimeout(1000);

      while (running) {
        processConsoleCommands();
        try {
          byte[] buffer = new byte[bufferSize];
          DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
          serverSocket.receive(receivePacket);
          Runnable requestHandler = new RequestHandler<>(receivePacket, commandMap, userManager, responseSender);
          requestPool.execute(requestHandler);
        } catch (SocketTimeoutException e) {
        } catch (IOException e) {
          ServerLogger.error("Ошибка ввода-вывода: " + e);
        }
      }
    } catch (IOException e) {
      ServerLogger.error("Ошибка запуска сервера: " + e);
    } finally {
      requestPool.shutdown();
      if (responseSender != null) {
        responseSender.shutdown();
      }
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
        if (command.toLowerCase().trim().equals("exit")) {
            ServerLogger.log("Завершение работы сервера...");
            running = false;
        } else {
            ServerLogger.error("Неизвестная команда: " + command);
        }
    }
  }
}
