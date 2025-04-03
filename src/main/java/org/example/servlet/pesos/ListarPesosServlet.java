package org.example.servlet.pesos;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 01-04-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.PesoVista;
import org.example.services.PesoService;
import org.example.services.PesoServiceImpl;
import org.example.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/pesos")
public class ListarPesosServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        // Validación de sesión mejorada
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        // Validación de atributos de sesión
        if (session.getAttribute("rol") == null || session.getAttribute("idUsuario") == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Atributos de sesión incompletos");
            return;
        }

        String rol = ((String) session.getAttribute("rol")).trim(); // Elimina espacios en blanco
        int usuarioId = (int) session.getAttribute("idUsuario");
        String filtro = req.getParameter("filtro");

        // Depuración (puedes eliminarlo después)
        System.out.println("Rol detectado: " + rol);
        System.out.println("ID Usuario: " + usuarioId);

        try (Connection conn = Conexion.getConnection()) {
            PesoService service = new PesoServiceImpl(conn);
            List<PesoVista> pesos;

            switch (rol) {
                case "Administrador":
                    pesos = service.listarTodos(filtro);
                    req.setAttribute("esAdministrador", true);
                    req.setAttribute("esEntrenador", false);
                    break;
                case "Entrenador":
                    pesos = service.listarPorEntrenador(usuarioId, filtro);
                    req.setAttribute("esAdministrador", false);
                    req.setAttribute("esEntrenador", true);
                    break;
                case "Cliente":
                    pesos = service.listarPorCliente(usuarioId);
                    req.setAttribute("esAdministrador", false);
                    req.setAttribute("esEntrenador", false);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Rol no reconocido: " + rol);
                    return;
            }

            req.setAttribute("pesos", pesos);
            req.getRequestDispatcher("/WEB-INF/pesos/listar.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener registros de peso: " + e.getMessage());
        } catch (ClassCastException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en los tipos de datos de sesión");
        }
    }
}