package org.example.servlet.asistencias;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.modelos.*;
import org.example.services.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/actualizar-asistencia")
public class ActualizarAsistenciaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Validación de sesión y obtención de datos del usuario
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int idUsuario = (int) session.getAttribute("idUsuario");
        Connection conn = (Connection) request.getAttribute("conn");

        if (conn == null) {
            throw new ServletException("No hay conexión a la base de datos");
        }

        AsistenciaService service = new AsistenciaServiceImplement(conn);
        UsuarioService usuarioService = new UsuarioServiceImplement(conn);

        try {
            // Obtener ID de la asistencia a editar
            int idAsistencia = Integer.parseInt(request.getParameter("id"));

            // Obtener los datos de la asistencia
            AsistenciaVista asistencia = service.obtenerVistaPorId(idAsistencia);

            if (asistencia == null) {
                response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?error=Asistencia no encontrada");
                return;
            }

            // Validación de permisos
            if ("Entrenador".equals(rol)) {
                if (!service.validarClienteDeEntrenador(asistencia.getIdUsuario(), idUsuario)) {
                    response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?error=No tiene permisos para editar esta asistencia");
                    return;
                }
            } else if ("Cliente".equals(rol)) {
                if (asistencia.getIdUsuario() != idUsuario) {
                    response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?error=No tiene permisos para editar esta asistencia");
                    return;
                }
            }

            // Preparar datos para la vista
            request.setAttribute("asistencia", asistencia);

            if ("Administrador".equals(rol)) {
                List<Usuario> usuarios = usuarioService.listar();
                request.setAttribute("usuarios", usuarios);
            }
// Todos los roles pasan por aquí:
            request.getRequestDispatcher("/WEB-INF/compartido/editarAsistencia.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?error=ID inválido");
        } catch (SQLException e) {
            throw new ServletException("Error al acceder a la base de datos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int idUsuarioSesion = (int) session.getAttribute("idUsuario");
        Connection conn = (Connection) request.getAttribute("conn");

        try {
            int idAsistencia = Integer.parseInt(request.getParameter("idAsistencia"));
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            String tipoAsistencia = request.getParameter("tipoAsistencia");
            String fechaStr = request.getParameter("fechaAsistencia");

            AsistenciaService service = new AsistenciaServiceImplement(conn);
            Asistencia asistenciaExistente = service.obtenerPorId(idAsistencia);

            // Validación de permisos
            if ("Entrenador".equals(rol) && !service.validarClienteDeEntrenador(idUsuario, idUsuarioSesion)) {
                response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?error=No autorizado");
                return;
            } else if ("Cliente".equals(rol) && idUsuario != idUsuarioSesion) {
                response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?error=No autorizado");
                return;
            }

            // Preparar objeto actualizado
            Asistencia asistenciaActualizada = new Asistencia();
            asistenciaActualizada.setIdAsistencia(idAsistencia);
            asistenciaActualizada.setIdUsuario(idUsuario);
            asistenciaActualizada.setTipoAsistencia(tipoAsistencia);
            if (fechaStr != null && !fechaStr.isEmpty()) {
                LocalDateTime dateTime = LocalDateTime.parse(fechaStr.replace(" ", "T"));
                asistenciaActualizada.setFechaAsistencia(Timestamp.valueOf(dateTime));
            } else {
                // Mantener la fecha original si no se modificó
                asistenciaActualizada.setFechaAsistencia(asistenciaExistente.getFechaAsistencia());
            }
            // Siempre actualizamos el registrador con quien hizo el cambio
            asistenciaActualizada.setIdRegistrador(idUsuarioSesion);

            if (service.actualizar(asistenciaActualizada)) {
                response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?success=Asistencia actualizada");
            } else {
                throw new ServletException("Error al actualizar");
            }

        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

}