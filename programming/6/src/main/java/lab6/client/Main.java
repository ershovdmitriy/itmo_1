package lab6.client;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import lab6.client.builders.humanBeingBuilder.HumanBeingBuilder;
import lab6.client.commands.ClientCommand;
import lab6.client.commands.CommandExecutor;
import lab6.client.commands.CommandMap;
import lab6.client.commands.CommandMapForHumanBeing;
import lab6.client.network.UdpClient;
import lab6.common.collection.HumanBeing.HumanBeing;

public class Main {
  private static final String SERVER_HOST = "helios.cs.ifmo.ru";
  private static final int TIMEOUT_MS = 5000;
  private static final int MAX_RETRIES = 3;

  public static void main(String[] args) {
    System.out.println("Запуск приложения. Напишите номер порта для подключения:");
    int SERVER_PORT = (new Scanner(System.in)).nextInt();
    UdpClient udpManager = new UdpClient();
    try {
      udpManager.connect(SERVER_HOST, SERVER_PORT, TIMEOUT_MS);
    } catch (IOException e) {
      System.err.println("Не удалось подключиться к серверу.");
    }

    HumanBeingBuilder builder = new HumanBeingBuilder();
    CommandMap<LinkedHashMap<String, ClientCommand>> commandMap =
        new CommandMapForHumanBeing(builder);
    CommandExecutor<LinkedHashMap<String, ClientCommand>, HumanBeing> commandExecutor =
        new CommandExecutor<>(builder, udpManager, MAX_RETRIES, commandMap.getCommandMap());
    System.out.println("Для списка доступных команд, напишите help");
    commandExecutor.startExecuting(new Scanner(System.in));
  }
}
