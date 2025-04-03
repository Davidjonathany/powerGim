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
import org.example.modelos.Usuario;
import org.example.repositorio.UsuarioRepositorio;
import org.example.services.MembresiaService;
import org.example.services.MembresiaServiceImpl;
import org.example.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@WebServlet("/membresias/crear")
public class CrearMembresiaServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejar verificación de cliente
        if (request.getParameter("cedula") != null) {
            verificarCliente(request, response);
            return;
        }

        // Mostrar formulario de creación
        request.getRequestDispatcher("/WEB-INF/membresias/crear.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String accion = request.getParameter("accion");
            String cedula = request.getParameter("cedulaCliente");
            String tipo = request.getParameter("tipo");
            Date fechaInicio = Date.valueOf(request.getParameter("fechaInicio"));

            if (!validarTipoMembresia(tipo)) {
                throw new IllegalArgumentException("Tipo de membresía inválido. Debe ser Mensual, Trimestral o Anual");
            }

            try (Connection conn = Conexion.getConnection()) {
                MembresiaService service = new MembresiaServiceImpl(conn);

                if ("renovar".equals(accion)) {
                    // Lógica para renovación
                    int idMembresia = Integer.parseInt(request.getParameter("idMembresia"));
                    actualizarMembresia(request, response, service, idMembresia, tipo, fechaInicio);
                } else {
                    // Lógica para creación nueva
                    int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                    crearNuevaMembresia(request, response, service, idCliente, tipo, fechaInicio);
                }
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/membresias/crear.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en la base de datos: " + e.getMessage());
            request.getRequestDispatcher("/membresias/crear.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/membresias?error=Error+inesperado");
        }
    }

    private void verificarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cedula = request.getParameter("cedula");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection conn = Conexion.getConnection()) {
            UsuarioRepositorio usuarioRepo = new UsuarioRepositorio(conn);
            MembresiaService membresiaService = new MembresiaServiceImpl(conn);

            // Buscar cliente por cédula
            Usuario cliente = usuarioRepo.buscarClientePorCedula(cedula);

            if (cliente == null) {
                response.getWriter().write("{\"existe\":false}");
                return;
            }

            // Verificar membresías del cliente
            List<MembresiaVista> membresias = membresiaService.listarPorCliente(cliente.getId());
            boolean tieneMembresia = !membresias.isEmpty();
            boolean membresiaActiva = membresias.stream().anyMatch(m -> "Activa".equals(m.getEstado()));

            // Obtener la última membresía (si existe)
            int idMembresia = 0;
            if (tieneMembresia) {
                idMembresia = membresias.get(0).getId(); // Tomamos la primera (asumimos que es la más reciente)
            }

            String json = String.format(
                    "{\"existe\":true, \"idCliente\":%d, \"tieneMembresia\":%b, \"membresiaActiva\":%b, \"idMembresia\":%d}",
                    cliente.getId(), tieneMembresia, membresiaActiva, idMembresia
            );

            response.getWriter().write(json);
        } catch (SQLException e) {
            response.getWriter().write("{\"error\":\"Error al verificar cliente: " + e.getMessage() + "\"}");
        }
    }

    private void actualizarMembresia(HttpServletRequest request, HttpServletResponse response,
                                     MembresiaService service, int idMembresia, String tipo, Date fechaInicio)
            throws SQLException, IOException, ServletException {

        Membresia membresia = service.obtenerPorId(idMembresia);

        if (membresia == null) {
            throw new IllegalArgumentException("Membresía no encontrada");
        }

        // Actualizar datos de la membresía existente
        membresia.setTipo(tipo);
        membresia.setFechaInicio(fechaInicio);
        membresia.setEstado("Activa");

        // Recalcular fechas
        LocalDate inicio = fechaInicio.toLocalDate();
        LocalDate vencimiento = calcularFechaVencimiento(inicio, tipo);
        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), vencimiento);

        membresia.setFechaVencimiento(Date.valueOf(vencimiento));
        membresia.setDiasRestantes((int) (diasRestantes > 0 ? diasRestantes : 0));

        if (service.actualizar(membresia)) {
            response.sendRedirect(request.getContextPath() + "/membresias?success=Membres%C3%ADa+renovada+exitosamente");
        } else {
            throw new SQLException("No se pudo renovar la membresía");
        }
    }

    private void crearNuevaMembresia(HttpServletRequest request, HttpServletResponse response,
                                     MembresiaService service, int idCliente, String tipo, Date fechaInicio)
            throws SQLException, IOException, ServletException {

        if (tieneMembresiaActiva(service, idCliente)) {
            throw new IllegalArgumentException("El cliente ya tiene una membresía activa");
        }

        Membresia membresia = crearMembresiaConCalculos(idCliente, tipo, fechaInicio);

        if (service.crear(membresia)) {
            response.sendRedirect(request.getContextPath() + "/membresias?success=Membres%C3%ADa+creada+exitosamente");
        } else {
            throw new SQLException("No se pudo crear la membresía");
        }
    }

    private boolean validarTipoMembresia(String tipo) {
        return tipo != null && (tipo.equals("Mensual") || tipo.equals("Trimestral") || tipo.equals("Anual"));
    }

    private boolean tieneMembresiaActiva(MembresiaService service, int idCliente) throws SQLException {
        List<MembresiaVista> membresias = service.listarPorCliente(idCliente);
        return membresias.stream().anyMatch(m -> "Activa".equals(m.getEstado()));
    }

    private Membresia crearMembresiaConCalculos(int idCliente, String tipo, Date fechaInicio) {
        Membresia membresia = new Membresia();
        membresia.setIdCliente(idCliente);
        membresia.setTipo(tipo);
        membresia.setFechaInicio(fechaInicio);
        membresia.setEstado("Activa"); // Siempre se crea como activa

        // Calcular fecha de vencimiento según el tipo
        LocalDate inicio = fechaInicio.toLocalDate();
        LocalDate vencimiento = calcularFechaVencimiento(inicio, tipo);

        // Calcular días restantes desde hoy
        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), vencimiento);

        // Asignar valores calculados
        membresia.setFechaVencimiento(Date.valueOf(vencimiento));
        membresia.setDiasRestantes((int) (diasRestantes > 0 ? diasRestantes : 0));

        return membresia;
    }

    private LocalDate calcularFechaVencimiento(LocalDate inicio, String tipo) {
        switch(tipo) {
            case "Mensual": return inicio.plusMonths(1);
            case "Trimestral": return inicio.plusMonths(3);
            case "Anual": return inicio.plusYears(1);
            default: throw new IllegalArgumentException("Tipo de membresía no válido");
        }
    }
}