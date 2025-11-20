package org.example;

import com.fastcgi.FCGIInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Responses.ErrorResponse;
import org.example.Responses.Response;
import org.example.Responses.SuccessResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App {
    public static PrintStream console = System.out;
    public static List<Row> resultTable = new ArrayList<>();

    public static void main(String[] args) {
        FCGIInterface fcgi = new FCGIInterface();
        console.println("Сервер запущен");

        while (fcgi.FCGIaccept() >= 0) {
            console.println("Запрос");
            try {
                String request = System.getProperties().getProperty("QUERY_STRING");
                console.println("REQUEST_URI: " + request);
                if (request.equals("resultTable")){
                    sendResultTable();
                }
                else {
                    handleCheckRequest(request);
                }
            } catch (Exception e) {
                Response response = new ErrorResponse(e.getMessage());
                sendResponse(response);
                e.printStackTrace(console);
            }
        }
    }

    private static void sendResultTable() {
        Response response = new SuccessResponse(resultTable);
        sendResponse(response);
    };

    private static void handleCheckRequest(String request) throws Exception {
        HashMap<String, Float> parsedRequest = parseReq(request);
        console.println("Значения: " + parsedRequest);

        boolean result;
        Instant startTime;
        Instant endTime;
        float x = parsedRequest.get("x");
        float y = parsedRequest.get("y");
        float r = parsedRequest.get("r");

        if (validate(x, y, r)) {
            startTime = Instant.now();
            result = calculate(x, y, r);
            endTime = Instant.now();
            console.println("Результат: " + result);

            long durationMs = ChronoUnit.MILLIS.between(startTime, endTime);
            Row row = new Row(x, y, r, result,durationMs, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            resultTable.add(row);
            List<Row> rows = new ArrayList<>();
            rows.add(row);
            Response response = new SuccessResponse(rows);
            sendResponse(response);
        } else {
            throw new Exception("Введенные данные неверны!");
        }
    }

    private static void sendResponse(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {
            String result = mapper.writeValueAsString(response);
            console.println("Ответ: " + result);

            System.out.println("Content-Type: application/json");
            System.out.println("Content-Length: " + result.getBytes(StandardCharsets.UTF_8).length);
            System.out.println();
            System.out.println(result);
            System.out.flush();

        } catch (IOException e) {
            e.printStackTrace(console);
            try {
                String errorJson = "{\"error\":\"Internal server error\"}";
                System.out.println("Content-Type: application/json");
                System.out.println("Content-Length: " + errorJson.getBytes(StandardCharsets.UTF_8).length);
                System.out.println();
                System.out.println(errorJson);
                System.out.flush();
            } catch (Exception ex) {
                ex.printStackTrace(console);
            }
        }
    }

    private static boolean validate(float x, float y, float r) {
        return (x >= -4 && x <= 4) && (y >= -3 && y <= 3) && (r >= 1 && r <= 4);
    }

    private static boolean calculate(float x, float y, float r) {
        if (x <= 0 && y >= 0) {
            return (x*x + y*y) <= r*r;
        }
        else if (x >= 0 && y >= 0) {
            return x <= r && y <= r/2;
        }
        else if (x >= 0 && y <= 0) {
            return y >= (x - r/2);
        }
        else {
            return false;
        }
    }

    private static HashMap<String, Float> parseReq(String request) {
        HashMap<String, Float> parsedData = new HashMap<>();
        if (request == null || request.isEmpty()) {
            return parsedData;
        }

        String[] args = request.split("&");
        for (String arg : args) {
            String[] splitArg = arg.split("=");
            if (splitArg.length == 2) {
                try {
                    parsedData.put(splitArg[0], Float.parseFloat(splitArg[1]));
                } catch (NumberFormatException e) {
                    console.println("Ошибка парсинга: " + arg);
                }
            }
        }
        return parsedData;
    }
}