package org.example.web_2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.web_2.model.Point;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "AreaCheckServlet", value = "/area-check")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("AreaCheckServlet: Получен GET-запрос от " + request.getRemoteAddr());
        String xParam = request.getParameter("x");
        String yParam = request.getParameter("y");
        String[] rParams = request.getParameterValues("r");
        System.out.println("AreaCheckServlet: Полученные значения: [X: " + xParam + ", Y: " + yParam + ", R: " + rParams[0] + "]");
        try {
            double x = parseX(xParam);
            double y = parseY(yParam);
            double r = parseR(rParams[0]);
            boolean isInArea = checkHit(x, y, r);
            System.out.println("AreaCheckServlet: Результат попадания: " + isInArea);
            HttpSession session = request.getSession();
            List<Point> results = (List<Point>) session.getAttribute("results");
            if (results == null) {
                results = new ArrayList<>();
            }

            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("dd.MM.yyyy HH:mm:ss")
                    .withLocale(new Locale("ru", "RU"));

            Point result = new Point(results.size(), x, y, r, isInArea, LocalDateTime.now().format(formatter));

            results.add(result);
            session.setAttribute("results", results);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/result.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            System.out.println("AreaCheckServlet: " + e + ":" + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка во время обработки запроса:" + e.getMessage());
        }
    }

    private double parseX(String xParam) {
        String[] allowedX = {"-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3"};
        boolean isValid = false;
        for (String allowed : allowedX) {
            if (allowed.equals(xParam)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new IllegalArgumentException("X должен быть одним из [-5, -4, -3, -2, -1, 0, 1, 2, 3]");
        }
        return Double.parseDouble(xParam);
    }

    private double parseY(String yParam) {
        double y = Double.parseDouble(yParam);
        if (y < -3 || y > 3) {
            throw new IllegalArgumentException("Y должен быть от -3 до 3");
        }
        return y;
    }

    private double parseR(String rParam) {
        String[] allowedR = {"1", "1.5", "2", "2.5", "3"};
        boolean isValid = false;
        for (String allowed : allowedR) {
            if (allowed.equals(rParam)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new IllegalArgumentException("R должен быть одним из [1, 1.5, 2, 2.5, 3]");
        }
        return Double.parseDouble(rParam);
    }

    private boolean checkHit(double x, double y, double r) {
        if (x >= 0 && y >= 0) {
            return (x * x + y * y) <= (r * r);
        }

        else if (x <= 0 && y <= 0) {
            return (x + y) >= -r;
        }

        else if (x >= 0 && y <= 0) {
            return x <= r && y >= -r/2;
        }

        return false;
    }
}