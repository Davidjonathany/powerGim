package org.example.servlet.membresias;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.MembresiaVista;
import org.example.services.MembresiaService;
import org.example.services.MembresiaServiceImpl;
import org.example.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/membresias")
public class ListarMembresiasServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int usuarioId = (int) session.getAttribute("idUsuario");
        String cedula = req.getParameter("cedula");
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");

        try (Connection conn = Conexion.getConnection()) {
            MembresiaService service = new MembresiaServiceImpl(conn);
            List<MembresiaVista> membresias;

            switch (rol) {
                case "Administrador":
                    if (cedula != null && !cedula.trim().isEmpty()) {
                        membresias = service.listarPorCedula(cedula);
                    } else if ((nombre != null && !nombre.trim().isEmpty()) ||
                            (apellido != null && !apellido.trim().isEmpty())) {
                        membresias = service.listarPorNombreApellido(nombre, apellido);
                    } else {
                        membresias = service.listarTodas();
                    }
                    req.setAttribute("esAdministrador", true);
                    req.setAttribute("esEntrenador", false);
                    break;
                case "Entrenador":
                    if ((nombre != null && !nombre.trim().isEmpty()) ||
                            (apellido != null && !apellido.trim().isEmpty())) {
                        membresias = service.listarPorEntrenadorYNombreApellido(usuarioId, nombre, apellido);
                    } else if (cedula != null && !cedula.trim().isEmpty()) {
                        membresias = service.listarPorEntrenadorYCedula(usuarioId, cedula);
                    } else {
                        membresias = service.listarPorEntrenador(usuarioId);
                    }
                    req.setAttribute("esAdministrador", false);
                    req.setAttribute("esEntrenador", true);
                    break;
                case "Cliente":
                    membresias = service.listarPorCliente(usuarioId);
                    req.setAttribute("esAdministrador", false);
                    req.setAttribute("esEntrenador", false);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
                    return;
            }

            req.setAttribute("membresias", membresias);
            req.getRequestDispatcher("/WEB-INF/membresias/listar.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener membresías");
        }
    }
}