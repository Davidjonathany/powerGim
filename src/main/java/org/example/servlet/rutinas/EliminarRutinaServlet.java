package org.example.servlet.rutinas;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 29-03-2025

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.modelos.*;
import org.example.services.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/EliminarRutinaServlet")
public class EliminarRutinaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int idUsuario = (int) session.getAttribute("idUsuario");
        Connection conn = (Connection) request.getAttribute("conn");
        RutinaService rutinaService = new RutinaServiceImpl(conn);

        try {
            int idRutina = Integer.parseInt(request.getParameter("id"));
            VistaRutina vistaRutina = rutinaService.obtenerVistaPorId(idRutina);

            if (vistaRutina == null) {
                response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=Rutina no encontrada");
                return;
            }

            // Verificar permisos
            if ("Entrenador".equals(rol) && vistaRutina.getIdEntrenador() != idUsuario) {
                response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=No tiene permisos para eliminar esta rutina");
                return;
            }

            request.setAttribute("rutina", vistaRutina);
            // Ruta actualizada
            request.getRequestDispatcher("/WEB-INF/compartido/confirmarEliminarRutina.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=ID inválido");
        } catch (Exception e) {
            request.setAttribute("error", "Error al acceder a los datos: " + e.getMessage());
            // Ruta actualizada
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int idUsuario = (int) session.getAttribute("idUsuario");
        Connection conn = (Connection) request.getAttribute("conn");
        RutinaService rutinaService = new RutinaServiceImpl(conn);

        try {
            int idRutina = Integer.parseInt(request.getParameter("idRutina"));
            Rutina rutina = rutinaService.obtenerPorId(idRutina);

            if (rutina == null) {
                response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=Rutina no encontrada");
                return;
            }

            // Verificar permisos
            if ("Entrenador".equals(rol) && rutina.getIdEntrenador() != idUsuario) {
                response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=No tiene permisos para eliminar esta rutina");
                return;
            }

            if (rutinaService.eliminar(idRutina)) {
                response.sendRedirect(request.getContextPath() + "/RutinaServlet?success=Rutina eliminada correctamente");
            } else {
                throw new RuntimeException("Error al eliminar la rutina");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=ID inválido");
        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar la solicitud: " + e.getMessage());
            // Ruta actualizada
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
}