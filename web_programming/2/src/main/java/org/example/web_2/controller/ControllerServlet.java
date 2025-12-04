package org.example.web_2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ControllerServlet", value = "/controller")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ControllerServlet: Получен GET-запрос от " + request.getRemoteAddr());
        System.out.println("ControllerServlet: " + request.getRequestURI());

        RequestDispatcher dispatcher;
        if (hasAllPointParameters(request)) {

            System.out.println("ControllerServlet: Полученный запрос содержит параметры. Перенаправление в AreaCheckServlet");
            dispatcher = request.getRequestDispatcher("/area-check");
        } else {

            System.out.println("ControllerServlet: Полученный запрос не содержит параметры. Показываем основную страницу");
            dispatcher = request.getRequestDispatcher("/index.jsp");
        }

        dispatcher.forward(request, response);
        System.out.println("ControllerServlet: Обработка запроса завершена");
    }

    private boolean hasAllPointParameters(HttpServletRequest request) {
        String xParam = request.getParameter("x");
        String yParam = request.getParameter("y");
        String rParam = request.getParameter("r");
        return xParam != null && !xParam.trim().isEmpty() &&
                yParam != null && !yParam.trim().isEmpty() &&
                rParam != null && !rParam.trim().isEmpty();
    }
}