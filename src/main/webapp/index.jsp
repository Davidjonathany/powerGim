<%--
  Created by IntelliJ IDEA.
  User: David
  Date: 27-03-2025
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>PowerGym</title>
</head>
<body>

<h2>Iniciar sesión</h2>

<% String error = request.getParameter("error"); %>
<% if (error != null) { %>
<p style="color: red;">
    <% if ("invalid".equals(error)) { %>
    Credenciales incorrectas, intente nuevamente.
    <% } else if ("server".equals(error)) { %>
    Error en el servidor. Intente más tarde.
    <% } %>
</p>
<% } %>

<form action="LoginServlet" method="post">
    <label for="usuario">Usuario:</label>
    <input type="text" id="usuario" name="usuario" required>

    <label for="clave">Contraseña:</label>
    <input type="password" id="clave" name="clave" required>

    <label for="rol">Rol:</label>
    <select id="rol" name="rol" required>
        <option value="Administrador">Administrador</option>
        <option value="Entrenador">Entrenador</option>
        <option value="Cliente">Cliente</option>
    </select>

    <button type="submit">Ingresar</button>
</form>

</body>
</html>
