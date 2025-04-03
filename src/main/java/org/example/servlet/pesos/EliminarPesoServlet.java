package org.example.servlet.pesos;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 02-04-2025

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
import java.net.URLEncoder;

@WebServlet("/pesos/eliminar")
public class EliminarPesoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Validar sesión y rol (solo Administrador puede eliminar)
        if (session == null || !"Administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect(request.getContextPath() + "/pesos?error=" +
                    URLEncoder.encode("Acceso denegado", "UTF-8"));
            return;
        }

        try (Connection conn = Conexion.getConnection()) {
            int idPeso = Integer.parseInt(request.getParameter("id"));
            PesoService service = new PesoServiceImpl(conn);
            PesoVista peso = service.obtenerVistaPorId(idPeso,
                    (String) session.getAttribute("rol"),
                    (int) session.getAttribute("idUsuario"));

            if (peso == null) {
                request.setAttribute("error", "No se encontró el registro de peso con ID: " + idPeso);
                request.getRequestDispatcher("/WEB-INF/pesos/eliminar.jsp").forward(request, response);
                return;
            }

            request.setAttribute("peso", peso);
            request.getRequestDispatcher("/WEB-INF/pesos/eliminar.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar el registro: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pesos/eliminar.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"Administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect(request.getContextPath() + "/pesos?error=" +
                    URLEncoder.encode("Acceso denegado", "UTF-8"));
            return;
        }

        try {
            int idPeso = Integer.parseInt(request.getParameter("id"));

            try (Connection conn = Conexion.getConnection()) {
                PesoService service = new PesoServiceImpl(conn);

                // Verificar existencia antes de eliminar
                PesoVista peso = service.obtenerVistaPorId(idPeso,
                        (String) session.getAttribute("rol"),
                        (int) session.getAttribute("idUsuario"));

                if (peso == null) {
                    response.sendRedirect(request.getContextPath() + "/pesos?error=" +
                            URLEncoder.encode("Registro no encontrado", "UTF-8"));
                    return;
                }

                if (service.eliminar(idPeso,
                        (String) session.getAttribute("rol"),
                        (int) session.getAttribute("idUsuario"))) {

                    response.sendRedirect(request.getContextPath() + "/pesos?success=" +
                            URLEncoder.encode("Registro de peso eliminado exitosamente", "UTF-8"));
                } else {
                    throw new Exception("No se pudo eliminar el registro");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/pesos?error=" +
                    URLEncoder.encode("Error al eliminar: " + e.getMessage(), "UTF-8"));
        }
    }
}