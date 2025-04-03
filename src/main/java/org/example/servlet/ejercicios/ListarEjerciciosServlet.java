package org.example.servlet.ejercicios;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.EjercicioVista;
import org.example.services.EjercicioService;
import org.example.services.EjercicioServiceImpl;
import org.example.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ejercicios")
public class ListarEjerciciosServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int usuarioId = (int) session.getAttribute("idUsuario");
        String cedulaFiltro = req.getParameter("cedula");
        String nombreFiltro = req.getParameter("nombre");

        // Establecer los atributos de rol ANTES de usarlos
        req.setAttribute("esAdministrador", "Administrador".equals(rol));
        req.setAttribute("esEntrenador", "Entrenador".equals(rol));

        try (Connection conn = Conexion.getConnection()) {
            EjercicioService service = new EjercicioServiceImpl(conn);
            List<EjercicioVista> ejercicios;

            switch (rol) {
                case "Administrador":
                    if (nombreFiltro != null && !nombreFiltro.trim().isEmpty()) {
                        ejercicios = service.listarTodosPorNombre(nombreFiltro.trim());
                    } else {
                        ejercicios = service.listarTodos(cedulaFiltro != null ? cedulaFiltro.trim() : null);
                    }
                    break;
                case "Entrenador":
                    if (nombreFiltro != null && !nombreFiltro.trim().isEmpty()) {
                        ejercicios = service.listarPorEntrenadorYNombre(usuarioId, nombreFiltro.trim());
                    } else {
                        ejercicios = service.listarPorEntrenador(
                                usuarioId,
                                cedulaFiltro != null ? cedulaFiltro.trim() : null
                        );
                    }
                    break;
                case "Cliente":
                    ejercicios = service.listarPorCliente(usuarioId);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
                    return;
            }

            req.setAttribute("ejercicios", ejercicios);
            req.getRequestDispatcher("/WEB-INF/ejercicios/listar.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al obtener ejercicios: " + e.getMessage());
        }
    }
}