package org.example.servlet;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.modelos.Asistencia;
import org.example.services.AsistenciaService;
import org.example.services.AsistenciaServiceImplement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/AsistenciaFormServlet")
public class AsistenciaFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Enviar al JSP para mostrar el formulario
        request.getRequestDispatcher("compartido/agregarAsistencia.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String cedula = request.getParameter("cedula");
        String fechaAsistencia = request.getParameter("fechaAsistencia");

        // Crear la conexión a la base de datos (esto debe manejarse correctamente con pool de conexiones)
        Connection conn = (Connection) request.getAttribute("conn");

        // Buscar el id_cliente basado en la cédula
        String sqlBuscarIdUsuario = "SELECT id FROM Usuarios WHERE cedula = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int idCliente = -1;

        try {
            stmt = conn.prepareStatement(sqlBuscarIdUsuario);
            stmt.setString(1, cedula);
            rs = stmt.executeQuery();

            if (rs.next()) {
                idCliente = rs.getInt("id");
            }

            if (idCliente != -1) {
                // Convertir la fecha de String a java.sql.Date
                java.sql.Date fechaAsistenciaSQL = java.sql.Date.valueOf(fechaAsistencia);

                // Crear un objeto Asistencia con los datos recibidos
                Asistencia nuevaAsistencia = new Asistencia();
                nuevaAsistencia.setIdCliente(idCliente);  // Establecer el ID del cliente
                nuevaAsistencia.setFechaAsistencia(fechaAsistenciaSQL);  // Establecer la fecha de asistencia

                // Crear el servicio para insertar la asistencia en la base de datos
                AsistenciaService service = new AsistenciaServiceImplement(conn);
                service.agregar(nuevaAsistencia);

                // Redirigir a la lista de asistencias
                response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet");
            } else {
                // Si no se encuentra el usuario por cédula, mostrar mensaje de error
                request.setAttribute("error", "La cédula no está registrada.");
                request.getRequestDispatcher("compartido/error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de base de datos
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
