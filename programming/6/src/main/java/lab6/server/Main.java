package lab6.server;

import java.util.LinkedHashMap;
import java.util.Scanner;

import lab6.client.commands.CommandExecutor;
import lab6.common.collection.HumanBeing.HumanBeing;
import lab6.server.collection.HumanBeingManagers;
import lab6.server.collection.IdManager;
import lab6.server.collection.JsonManager;
import lab6.server.commands.CommandMapForHumanBeing;
import lab6.server.commands.ServerCommand;
import lab6.server.logging.ServerLogger;
import lab6.server.network.UdpServer;

public class Main {
  private static final String HOST = "localhost";
  private static final int BUFFER_SIZE = 65536;

  public static void main(String[] args) {
    try {
      System.out.println("Запуск приложения. Напишите номер порта:");
      int PORT = (new Scanner(System.in)).nextInt();
      JsonManager<LinkedHashMap<String, HumanBeing>> jsonManager = new JsonManager<>();
      HumanBeingManagers humanBeingManagers = new HumanBeingManagers(jsonManager, "FILE_NAME");
      IdManager<HumanBeing> idManager = new IdManager<>(humanBeingManagers);
      humanBeingManagers.setIdManager(idManager);
      CommandMapForHumanBeing commandMapForHumanBeing =
          new CommandMapForHumanBeing(humanBeingManagers, idManager);
      CommandExecutor<LinkedHashMap<String, ServerCommand>> commandExecutor = new CommandExecutor<>(commandMapForHumanBeing);
      UdpServer udpServer =
          new UdpServer(PORT, HOST, BUFFER_SIZE, humanBeingManagers, commandExecutor);

      udpServer.start();
    } catch (Exception e) {
      ServerLogger.error("Критическая ошибка при запуске сервера: " + e.getMessage());
    }
  }
}
