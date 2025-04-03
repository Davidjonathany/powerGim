package org.example.servlet;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.modelos.MembresiaVista;
import org.example.modelos.Usuario;
import org.example.repositorio.UsuarioRepositorio;
import org.example.services.MembresiaService;
import org.example.services.MembresiaServiceImpl;
import org.example.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/membresias/verificar-cliente")
public class VerificarClienteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String cedula = request.getParameter("cedula");
        if (cedula == null || cedula.trim().isEmpty()) {
            response.getWriter().write("{\"error\":\"Cédula no proporcionada\"}");
            return;
        }

        try (Connection conn = Conexion.getConnection()) {
            UsuarioRepositorio usuarioRepo = new UsuarioRepositorio(conn);
            MembresiaService membresiaService = new MembresiaServiceImpl(conn);

            // Buscar cliente por cédula
            Usuario cliente = usuarioRepo.buscarPorCedula(cedula);

            if (cliente == null) {
                response.getWriter().write("{\"existe\":false}");
                return;
            }

            // Verificar membresías del cliente
            List<MembresiaVista> membresias = membresiaService.listarPorCliente(cliente.getId());
            boolean tieneMembresia = !membresias.isEmpty();
            boolean membresiaActiva = membresias.stream().anyMatch(m -> "Activa".equals(m.getEstado()));

            // Obtener ID de la última membresía (si existe)
            int idMembresia = tieneMembresia ? membresias.get(0).getId() : 0;

            // Construir respuesta JSON
            String json = String.format(
                    "{\"existe\":true, \"idCliente\":%d, \"tieneMembresia\":%b, \"membresiaActiva\":%b, \"idMembresia\":%d}",
                    cliente.getId(), tieneMembresia, membresiaActiva, idMembresia
            );

            response.getWriter().write(json);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\":\"Error en la base de datos: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\":\"Error inesperado: " + e.getMessage() + "\"}");
        }
    }
}
