<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 27/3/2025
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<p>Bienvenido</p>
<form action="../UsuarioServlet" method="get">
    <button type="submit">Usuarios</button>
</form>
<form action="../AsistenciaVistaServlet" method="get">
    <button type="submit">Ver Asistencias</button>
</form>
</body>
</html>
