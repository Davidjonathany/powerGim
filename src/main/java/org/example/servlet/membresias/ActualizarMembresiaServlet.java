package org.example.servlet.membresias;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.modelos.Membresia;
import org.example.modelos.MembresiaVista;
import org.example.services.MembresiaService;
import org.example.services.MembresiaServiceImpl;
import org.example.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/membresias/actualizar")
public class ActualizarMembresiaServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            try (Connection conn = Conexion.getConnection()) {
                MembresiaService service = new MembresiaServiceImpl(conn);
                MembresiaVista membresia = service.obtenerVistaPorId(id); // Método corregido

                if (membresia == null) {
                    response.sendRedirect(request.getContextPath() + "/membresias?error=Membres%C3%ADa+no+encontrada");
                    return;
                }

                request.setAttribute("membresia", membresia);
                request.getRequestDispatcher("/WEB-INF/membresias/actualizar.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/membresias?error=Error+al+cargar+membres%C3%ADa");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembresiaVista membresiaVista = new MembresiaVista();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            String tipo = request.getParameter("tipo");
            Date fechaInicio = Date.valueOf(request.getParameter("fechaInicio"));
            Date fechaVencimiento = Date.valueOf(request.getParameter("fechaVencimiento"));
            int diasRestantes = Integer.parseInt(request.getParameter("diasRestantes"));
            String estado = request.getParameter("estado");
            String cedulaCliente = request.getParameter("cedulaCliente");

            // Validación automática de estado
            if (diasRestantes <= 0 && !"Inactiva".equals(estado)) {
                estado = "Inactiva"; // Forzar estado inactivo si los días son <= 0
            }

            try (Connection conn = Conexion.getConnection()) {
                MembresiaService service = new MembresiaServiceImpl(conn);

                Membresia membresia = new Membresia();
                membresia.setId(id);
                membresia.setIdCliente(idCliente);
                membresia.setTipo(tipo);
                membresia.setFechaInicio(fechaInicio);
                membresia.setFechaVencimiento(fechaVencimiento);
                membresia.setDiasRestantes(diasRestantes);
                membresia.setEstado(estado);

                if (service.actualizar(membresia)) {
                    response.sendRedirect(request.getContextPath() + "/membresias?success=Membres%C3%ADa+actualizada+exitosamente");
                } else {
                    throw new SQLException("No se pudo actualizar la membresía");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            // Rellenar el objeto para mostrar en el formulario
            membresiaVista.setId(request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0);
            membresiaVista.setIdCliente(request.getParameter("idCliente") != null ? Integer.parseInt(request.getParameter("idCliente")) : 0);
            membresiaVista.setClienteCedula(request.getParameter("cedulaCliente"));
            membresiaVista.setTipo(request.getParameter("tipo"));
            membresiaVista.setFechaInicio(request.getParameter("fechaInicio") != null ? Date.valueOf(request.getParameter("fechaInicio")) : null);
            membresiaVista.setFechaVencimiento(request.getParameter("fechaVencimiento") != null ? Date.valueOf(request.getParameter("fechaVencimiento")) : null);
            membresiaVista.setDiasRestantes(request.getParameter("diasRestantes") != null ? Integer.parseInt(request.getParameter("diasRestantes")) : 0);
            membresiaVista.setEstado(request.getParameter("estado"));

            request.setAttribute("membresia", membresiaVista);
            request.setAttribute("error", "Error al actualizar: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/membresias/actualizar.jsp").forward(request, response);
        }
    }
}