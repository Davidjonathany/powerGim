package org.example.servlet;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.modelos.AsistenciaVista;
import org.example.services.AsistenciaService;
import org.example.services.AsistenciaVistaService;
import org.example.services.AsistenciaVistaServiceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/AsistenciaVistaServlet")
public class AsistenciaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        AsistenciaVistaService service = new AsistenciaVistaServiceImplement(conn); // Usar AsistenciaVistaService

        // Obtener las asistencias con los detalles completos desde la vista
        List<AsistenciaVista> asistencias = service.listarAsistenciasVista(); // Asegúrate de llamar al método correcto

        // Verificar si hay datos
        req.setAttribute("asistencias", asistencias);
        getServletContext().getRequestDispatcher("/compartido/asistencia.jsp").forward(req, resp);
    }
}



