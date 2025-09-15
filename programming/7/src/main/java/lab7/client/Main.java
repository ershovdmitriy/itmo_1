package lab7.client;

import lab7.client.builders.humanBeingBuilder.HumanBeingBuilder;
import lab7.client.commands.ClientCommand;
import lab7.client.commands.CommandExecutor;
import lab7.client.commands.CommandMap;
import lab7.client.commands.CommandMapForHumanBeing;
import lab7.client.network.UdpClient;
import lab7.common.collection.HumanBeing.HumanBeing;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {
    private static final String SERVER_HOST = "localhost";
    private static final int TIMEOUT_MS = 5000;
    private static final int MAX_RETRIES = 3;

    public static void main(String[] args) {
        System.out.println("Запуск приложения. Напишите номер порта для подключения:");
        int serverPort = 0;
        while (serverPort == 0) {
            try {
                int nextInt = (new Scanner(System.in)).nextInt();
                if (nextInt > 1024 && nextInt < 65536) {
                    serverPort = nextInt;
                }
                else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e){
                System.out.println("Необходимо ввести число от 1024 до 65536");
            }
        }
        UdpClient udpManager = new UdpClient(TIMEOUT_MS, MAX_RETRIES);
        try {
            udpManager.connect(SERVER_HOST, serverPort);
        } catch (IOException e) {
            System.err.println("Не удалось подключиться к серверу.");
        }

        HumanBeingBuilder builder = new HumanBeingBuilder();
        CommandMap<LinkedHashMap<String, ClientCommand>> commandMap =
                new CommandMapForHumanBeing(builder);
        CommandExecutor<LinkedHashMap<String, ClientCommand>, HumanBeing> commandExecutor =
                new CommandExecutor<>(builder, udpManager, commandMap.getCommandMap());
        System.out.println("Для выполнения команд необходимо авторизоваться(команда authorization) или зарегистрироваться(команда registration) в системе");
        System.out.println("Для списка доступных команд напишите \"help\"");
        commandExecutor.startExecuting(new Scanner(System.in), false);
    }
}
