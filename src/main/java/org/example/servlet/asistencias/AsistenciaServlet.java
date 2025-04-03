package org.example.servlet.asistencias;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 28-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.AsistenciaVista;
import org.example.services.AsistenciaVistaService;
import org.example.services.AsistenciaVistaServiceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AsistenciaVistaServlet")
public class AsistenciaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int usuarioId = (int) session.getAttribute("idUsuario");
        Connection conn = (Connection) req.getAttribute("conn");
        if (conn == null) {
            throw new ServletException("No hay conexión a la base de datos disponible");
        }
        AsistenciaVistaService service = new AsistenciaVistaServiceImplement(conn);

        try {
            // Inicializar con lista vacía por defecto
            List<AsistenciaVista> asistencias = new ArrayList<>();
            String jspPath = "WEB-INF/error.jsp"; // Ruta por defecto

            // Parámetros de filtrado
            String cedula = req.getParameter("cedula");
            String tipo = req.getParameter("tipo");
            String fecha = req.getParameter("fecha");

            switch (rol) {
                case "Administrador":
                    if (cedula != null && !cedula.isEmpty()) {
                        asistencias = service.listarPorCedula(cedula);
                    } else if (tipo != null && !tipo.isEmpty()) {
                        asistencias = service.listarPorTipo(tipo);
                    } else if (fecha != null && !fecha.isEmpty()) {
                        asistencias = service.listarPorFecha(fecha);
                    } else {
                        asistencias = service.listarTodas();
                    }
                    jspPath = "/WEB-INF/admin/asistenciasAdmin.jsp";
                    break;

                case "Entrenador":
                    try {
                        // Obtener todas las asistencias del entrenador sin paginación
                        if (cedula != null && !cedula.isEmpty()) {
                            asistencias = service.listarPorCedulaYEntrenador(cedula, usuarioId);
                        } else if (tipo != null && !tipo.isEmpty()) {
                            asistencias = service.listarPorTipoYEntrenador(tipo, usuarioId);
                        } else if (fecha != null && !fecha.isEmpty()) {
                            asistencias = service.listarPorFechaYEntrenador(fecha, usuarioId);
                        } else {
                            asistencias = service.listarPorEntrenador(usuarioId);
                        }

                        // Debug
                        System.out.println("Asistencias encontradas para entrenador " + usuarioId + ": " +
                                asistencias.size());

                        jspPath = "/WEB-INF/entrenador/asistenciasEntrenador.jsp";
                    } catch (Exception e) {
                        req.setAttribute("error", "Error al cargar asistencias: " + e.getMessage());
                        jspPath = "/WEB-INF/entrenador/asistenciasEntrenador.jsp";
                    }
                    break;

                case "Cliente":
                    asistencias = service.listarPorUsuario(usuarioId);
                    jspPath = "/WEB-INF/cliente/asistenciasCliente.jsp";
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Rol no reconocido: " + rol);
                    return;
            }

            req.setAttribute("asistencias", asistencias);
            req.setAttribute("totalAsistencias", asistencias.size());
            req.getRequestDispatcher(jspPath).forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "Error al filtrar asistencias: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }

    private List<AsistenciaVista> aplicarFiltros(HttpServletRequest req, AsistenciaVistaService service) {
        String tipoFiltro = req.getParameter("filtro");
        String valorFiltro = req.getParameter("valor");

        try {
            switch (tipoFiltro.toLowerCase()) {
                case "cedula":
                    return service.listarPorCedula(valorFiltro);
                case "tipo":
                    return service.listarPorTipo(valorFiltro);
                case "fecha":
                    return service.listarPorFecha(valorFiltro);
                default:
                    return service.listarTodas();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Valor de filtro inválido");
        }
    }
}