package org.example.servlet.asistencias;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 27-03-2025

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.modelos.Asistencia;
import org.example.modelos.AsistenciaVista;
import org.example.modelos.UsuarioLogin;
import org.example.services.AsistenciaService;
import org.example.services.AsistenciaServiceImplement;
import org.example.services.AsistenciaVistaService;
import org.example.services.AsistenciaVistaServiceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/registrar-asistencia")
public class AsistenciaFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UsuarioLogin usuario = validarSesionYROL(session, request, response);
        if(usuario == null) return;

        Connection conn = (Connection) request.getAttribute("conn");

        // Usamos AsistenciaVistaService para las operaciones de visualización
        AsistenciaVistaService vistaService = new AsistenciaVistaServiceImplement(conn);

        try {
            switch(usuario.getRol()) {
                case "Administrador":
                    List<AsistenciaVista> usuariosCompletos = vistaService.listarTodosUsuariosCompletos();
                    request.setAttribute("usuarios", usuariosCompletos);
                    request.getRequestDispatcher("/WEB-INF/admin/registroAsistencia.jsp").forward(request, response);
                    break;

                case "Entrenador":
                    List<AsistenciaVista> clientesCompletos = vistaService.listarClientesPorEntrenador(usuario.getId());
                    if(clientesCompletos.isEmpty()) {
                        request.setAttribute("warning", "No tienes clientes asignados");
                    }
                    request.setAttribute("clientes", clientesCompletos);
                    request.getRequestDispatcher("/WEB-INF/entrenador/registroAsistencia.jsp").forward(request, response);
                    break;

                case "Cliente":
                    request.setAttribute("cedulaCliente", usuario.getCedula());
                    request.getRequestDispatcher("/WEB-INF/cliente/registroAsistencia.jsp").forward(request, response);
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Rol no reconocido");
            }
        } catch (Exception e) {
            manejarError(request, response, usuario, "Error al cargar el formulario: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UsuarioLogin registrador = validarSesionYROL(session, request, response);
        if(registrador == null) return;

        Connection conn = (Connection) request.getAttribute("conn");

        // Usamos AsistenciaService para operaciones CRUD de asistencias
        AsistenciaService asistenciaService = new AsistenciaServiceImplement(conn);
        // Y AsistenciaVistaService para obtener datos de usuarios
        AsistenciaVistaService vistaService = new AsistenciaVistaServiceImplement(conn);

        try {
            int idUsuario;
            String tipoAsistencia = request.getParameter("tipoAsistencia");

            if(registrador.getRol().equals("Cliente")) {
                idUsuario = registrador.getId();
            } else {
                String cedula = request.getParameter("cedula");
                // Usamos el servicio de asistencias para obtener ID por cédula
                idUsuario = asistenciaService.obtenerIdUsuarioPorCedula(cedula);

                if(idUsuario == -1) {
                    throw new IllegalArgumentException("No se encontró usuario con cédula: " + cedula);
                }

                if(registrador.getRol().equals("Entrenador")) {
                    // Usamos el servicio de asistencias para validar relación
                    if(!asistenciaService.validarClienteDeEntrenador(idUsuario, registrador.getId())) {
                        throw new SecurityException("No tiene permisos para registrar asistencia a este cliente");
                    }
                }
            }

            // Crear y guardar la asistencia usando AsistenciaService
            Asistencia asistencia = new Asistencia();
            asistencia.setIdUsuario(idUsuario);
            asistencia.setIdRegistrador(registrador.getId());
            asistencia.setTipoAsistencia(tipoAsistencia);
            asistencia.setFechaAsistencia(Timestamp.valueOf(LocalDateTime.now()));

            if(!asistenciaService.agregar(asistencia)) {
                throw new RuntimeException("Error al guardar la asistencia en la base de datos");
            }

            response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?success=Asistencia registrada exitosamente");

        } catch (Exception e) {
            manejarError(request, response, registrador, e.getMessage());
        }
    }

    private UsuarioLogin validarSesionYROL(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return null;
        }
        return (UsuarioLogin) session.getAttribute("usuario");
    }

    private void manejarError(HttpServletRequest request, HttpServletResponse response,
                              UsuarioLogin usuario, String mensajeError)
            throws ServletException, IOException {
        request.setAttribute("error", mensajeError);

        if(usuario.getRol().equals("Cliente")) {
            request.getRequestDispatcher("/WEB-INF/cliente/registroAsistencia.jsp").forward(request, response);
        } else {
            doGet(request, response);
        }
    }
}