package org.example.servlet;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.ReporteCliente;
import org.example.services.ReporteClienteService;
import org.example.services.ReporteClienteServiceImpl;
import org.example.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/ReporteClienteServlet")
public class ReporteClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Validar sesión y rol
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int idUsuario = (int) session.getAttribute("idUsuario");

        // 2. Obtener parámetros de filtro
        String cedula = req.getParameter("cedula");
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");

        // 3. Obtener conexión y procesar reporte según el rol
        try (Connection conn = Conexion.getConnection()) {
            ReporteClienteService reporteService = new ReporteClienteServiceImpl(conn);
            List<ReporteCliente> reportes;

            if ("Administrador".equals(rol)) {
                if (cedula != null && !cedula.isEmpty() || nombre != null && !nombre.isEmpty() || apellido != null && !apellido.isEmpty()) {
                    reportes = reporteService.filtrarClientes(cedula, nombre, apellido);
                } else {
                    reportes = reporteService.generarReporteClientes();
                }
            } else if ("Entrenador".equals(rol)) {
                if (nombre != null && !nombre.isEmpty() || apellido != null && !apellido.isEmpty()) {
                    reportes = reporteService.filtrarClientesPorEntrenador(idUsuario, null, nombre, apellido);
                } else {
                    reportes = reporteService.generarReportePorEntrenador(idUsuario);
                }
            } else if ("Cliente".equals(rol)) {
                reportes = reporteService.generarReportePorCliente(idUsuario);
            } else {
                req.setAttribute("error", "Acceso denegado: No tienes permisos para ver este reporte");
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
                return;
            }

            // 4. Pasar datos a la vista
            req.setAttribute("reportes", reportes.isEmpty() ? null : reportes);
            req.getRequestDispatcher("/WEB-INF/reporte/reporteClientes.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error al generar el reporte: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }
}
