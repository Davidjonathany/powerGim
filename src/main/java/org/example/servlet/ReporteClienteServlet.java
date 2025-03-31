package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.modelos.ReporteCliente;
import org.example.services.ReporteClienteService;
import org.example.services.ReporteClienteServiceImpl;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "ReporteClienteServlet", urlPatterns = "/ReporteClienteServlet")
public class ReporteClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener la conexión desde el contexto (ajusta según tu configuración)
        Connection conn = (Connection) req.getAttribute("conn");

        // Inicializar el servicio
        ReporteClienteService reporteService = new ReporteClienteServiceImpl(conn);

        // Obtener los datos del reporte
        List<ReporteCliente> reportes = reporteService.generarReporteClientes();

        // Pasar los datos al JSP
        req.setAttribute("reportes", reportes);

        // Redirigir a la vista JSP
        req.getRequestDispatcher("/cliente/reporteClientes.jsp").forward(req, resp);
    }
}