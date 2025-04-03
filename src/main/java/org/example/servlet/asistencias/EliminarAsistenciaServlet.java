package org.example.servlet.asistencias;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.services.AsistenciaService;
import org.example.services.AsistenciaServiceImplement;
import org.example.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/eliminar-asistencia")
public class EliminarAsistenciaServlet extends HttpServlet {
    private AsistenciaService asistenciaService;

    @Override
    public void init() throws ServletException {
        try {
            Connection conn = Conexion.getConnection();
            this.asistenciaService = new AsistenciaServiceImplement(conn);
        } catch (SQLException e) {
            throw new ServletException("Error al conectar con la base de datos", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Validación corregida para usar "Administrador" en lugar de "admin"
        if (session == null || session.getAttribute("usuario") == null ||
                !"Administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/admin/asistencias.jsp?error=ID no proporcionado");
                return;
            }

            int idAsistencia = Integer.parseInt(idParam);
            var asistencia = asistenciaService.obtenerConDetalles(idAsistencia);

            if (asistencia == null) {
                response.sendRedirect(request.getContextPath() + "/admin/asistencias.jsp?error=Asistencia no encontrada");
                return;
            }

            request.setAttribute("asistencia", asistencia);
            request.getRequestDispatcher("/WEB-INF/admin/eliminar-asistencia.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/asistencias.jsp?error=ID inválido");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/admin/asistencias.jsp?error=Error al procesar la solicitud");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null ||
                !"Administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                // Redirigir al servlet con su nombre actual
                response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?error=ID no proporcionado");
                return;
            }

            int idAsistencia = Integer.parseInt(idParam);
            boolean eliminado = asistenciaService.eliminarAsistencia(idAsistencia);

            // Usar el nombre actual del servlet en las redirecciones
            if (eliminado) {
                response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?success=Asistencia eliminada correctamente");
            } else {
                response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?error=No se pudo eliminar la asistencia");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?error=ID inválido");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?error=Error al eliminar la asistencia: " + e.getMessage());
        }
    }
}