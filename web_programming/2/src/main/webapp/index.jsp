<%@ page import="org.example.web_2.model.Point" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Лабораторная работа №2: Запрос</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>

    <header>
        <table>
            <tr>
                <th> Лабораторная работа №2 </th>
            </tr>
            <tr>
                <td>Имя: Ершов Дмитрий Александрович</td>
            </tr>
            <tr>
                <td>Группа: P3231</td>
            </tr>
            <tr>
                <td>Вариант: 34571</td>
            </tr>
        </table>
    </header>
    <main>
        <div id="notifications"></div>

        <form id="main_form">
            <table>
                <tr>
                    <canvas id="myCanvas" width="500" height="500"></canvas>
                </tr>
                <tr>
                    <td>
                        <label for="input_x">X:</label>
                    </td>
                    <td>
                        <% String[] xValues = {"-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3"}; %>
                        <% for (String xVal : xValues) { %>
                        <input type="radio" id="input_x" name="x" value="<%= xVal %>"> <%= xVal %>
                        <% } %>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label for="input_y">Y:</label>
                    </td>
                    <td>
                        <input type="text" id="input_y" name="y" placeholder="Введите число от -3 до 3">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="input_r">R:</label>
                    </td>
                    <td>
                        <% String[] rValues = {"1", "1.5", "2", "2.5", "3"}; %>
                        <% for (String rVal : rValues) { %>
                        <input type="checkbox" id="input_r" name="r" value="<%= rVal %>"> <%= rVal %>
                        <% } %>
                    </td>
                </tr>

                <tr>
                    <td colspan="2">
                        <button type="submit" id="submit-btn">Отправить</button>
                    </td>
                </tr>
            </table>
        </form>
        <table id="table_result">
            <thead>
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Результат</th>
                <th>Время сервера</th>
            </tr>
            </thead>
            <tbody  id="result_tbody">
            <%
                List<Point> results = (List<Point>) session.getAttribute("results");
                if (results != null) {
                    for (Point point : results) {
            %>
            <tr>
                <td id="res_x"><%= point.getX() %></td>
                <td id="res_y"><%= point.getY() %></td>
                <td><%= point.getR() %></td>
                <td id="result"><%= point.getStrRes() %></td>
                <td><%= point.getTime() %></td>
            </tr>
            <%      }
            }
            %>
            </tbody>
        </table>
    </main>
    <script src="scripts/script.js"></script>
</body>
</html>
