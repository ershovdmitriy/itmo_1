<%@ page import="org.example.web_2.model.Point" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
  		<meta charset="utf-8">
		<title>Лабораторная работа №2: Результаты</title>
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
		<tbody>
		<%
			List<Point> results = (List<Point>) session.getAttribute("results");
			if (results != null) {
		%>
		<tr>
			<td><%= results.getLast().getX() %></td>
			<td><%= results.getLast().getY() %></td>
			<td><%= results.getLast().getR() %></td>
			<td><%= results.getLast().getStrRes() %></td>
			<td><%= results.getLast().getTime() %></td>
		</tr>
		<%
		}
		%>
		</tbody>
	</table>
	<a href="index.jsp"> Вернуться на главную страницу </a>
 	</body>
</html>