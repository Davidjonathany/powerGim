package org.example.servlet;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 27-03-2025

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.modelos.Asistencia;
import org.example.modelos.UsuarioLogin;
import org.example.services.AsistenciaService;
import org.example.services.AsistenciaServiceImplement;

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
        UsuarioLogin usuario = validarSesionYROL(session, response, request);
        if(usuario == null) return;

        Connection conn = (Connection) request.getAttribute("conn");
        AsistenciaService service = new AsistenciaServiceImplement(conn);

        try {
            switch(usuario.getRol()) {
                case "Administrador":
                    request.getRequestDispatcher("/admin/registroAsistencia.jsp").forward(request, response);
                    break;

                case "Entrenador":
                    List<UsuarioLogin> clientes = service.obtenerClientesPorEntrenador(usuario.getId());
                    request.setAttribute("clientes", clientes);
                    request.getRequestDispatcher("/entrenador/registroAsistencia.jsp").forward(request, response);
                    break;

                case "Cliente":
                    request.setAttribute("cedulaCliente", usuario.getCedula());
                    request.getRequestDispatcher("/cliente/registroAsistencia.jsp").forward(request, response);
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Rol no reconocido");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // En lugar de redirigir a error.jsp, mostramos el error en la página actual
            request.setAttribute("error", "Error al cargar el formulario: " + e.getMessage());

            // Redirigimos a la página principal correspondiente al rol
            String redirectPath = switch(usuario.getRol()) {
                case "Administrador" -> "/admin/home.jsp";
                case "Entrenador" -> "/entrenador/home.jsp";
                case "Cliente" -> "/cliente/home.jsp";
                default -> "/";
            };
            request.getRequestDispatcher(redirectPath).forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UsuarioLogin registrador = validarSesionYROL(session, response, request);
        if(registrador == null) return;

        Connection conn = (Connection) request.getAttribute("conn");
        AsistenciaService service = new AsistenciaServiceImplement(conn);

        try {
            int idUsuario;
            String tipoAsistencia = request.getParameter("tipoAsistencia");

            if(registrador.getRol().equals("Cliente")) {
                idUsuario = registrador.getId();
            } else {
                String cedula = request.getParameter("cedula");
                idUsuario = service.obtenerIdUsuarioPorCedula(cedula);

                if(idUsuario == -1) {
                    throw new IllegalArgumentException("No se encontró usuario con cédula: " + cedula);
                }

                if(registrador.getRol().equals("Entrenador")) {
                    if(!service.validarClienteDeEntrenador(idUsuario, registrador.getId())) {
                        throw new SecurityException("No tiene permisos para registrar asistencia a este cliente");
                    }
                }
            }

            Asistencia asistencia = new Asistencia();
            asistencia.setIdUsuario(idUsuario);
            asistencia.setIdRegistrador(registrador.getId());
            asistencia.setTipoAsistencia(tipoAsistencia);
            asistencia.setFechaAsistencia(Timestamp.valueOf(LocalDateTime.now()));

            if(!service.agregar(asistencia)) {
                throw new RuntimeException("Error al guardar la asistencia en la base de datos");
            }

            // Redirección al servlet de vista de asistencias
            response.sendRedirect(request.getContextPath() + "/AsistenciaVistaServlet?success=Asistencia registrada exitosamente");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());

            if(registrador.getRol().equals("Cliente")) {
                request.getRequestDispatcher("/cliente/registroAsistencia.jsp").forward(request, response);
            } else {
                doGet(request, response);
            }
        }
    }
    private UsuarioLogin validarSesionYROL(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if(session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return null;
        }
        return (UsuarioLogin) session.getAttribute("usuario");
    }
}