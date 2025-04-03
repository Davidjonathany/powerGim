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

@WebServlet("/membresias/eliminar")
public class EliminarMembresiaServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Validación de sesión y rol de administrador
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        if (!"Administrador".equals(rol)) {
            response.sendRedirect(request.getContextPath() + "/membresias?error=Acceso+denegado");
            return;
        }

        // Mostrar página de confirmación
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            try (Connection conn = Conexion.getConnection()) {
                MembresiaService service = new MembresiaServiceImpl(conn);
                MembresiaVista membresia = service.obtenerVistaPorId(id);

                if (membresia == null) {
                    response.sendRedirect(request.getContextPath() + "/membresias?error=Membres%C3%ADa+no+encontrada");
                    return;
                }

                request.setAttribute("membresia", membresia);
                // Ruta actualizada
                request.getRequestDispatcher("/WEB-INF/membresias/eliminar.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/membresias?error=Error+al+cargar+membres%C3%ADa");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Validación de sesión y rol de administrador
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        if (!"Administrador".equals(rol)) {
            response.sendRedirect(request.getContextPath() + "/membresias?error=Acceso+denegado");
            return;
        }

        // Procesar eliminación
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            try (Connection conn = Conexion.getConnection()) {
                MembresiaService service = new MembresiaServiceImpl(conn);

                if (service.eliminar(id)) {
                    response.sendRedirect(request.getContextPath() + "/membresias?success=Membres%C3%ADa+eliminada+exitosamente");
                } else {
                    throw new SQLException("No se pudo eliminar la membresía");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/membresias?error=Error+al+eliminar+membres%C3%ADa: " + e.getMessage());
        }
    }
}