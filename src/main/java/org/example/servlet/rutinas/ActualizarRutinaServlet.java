package org.example.servlet.rutinas;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 29-03-2025
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.modelos.*;
import org.example.repositorio.*;
import org.example.services.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/ActualizarRutinaServlet")
public class ActualizarRutinaServlet extends HttpServlet {

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
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorio(conn);

        try {
            int idRutina = Integer.parseInt(request.getParameter("id"));
            VistaRutina vistaRutina = rutinaService.obtenerVistaPorId(idRutina);

            if (vistaRutina == null) {
                response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=Rutina no encontrada");
                return;
            }

            // Verificar permisos
            if ("Entrenador".equals(rol) && vistaRutina.getIdEntrenador() != idUsuario) {
                response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=No tiene permisos para editar esta rutina");
                return;
            }

            request.setAttribute("rutina", vistaRutina);

            if ("Administrador".equals(rol)) {
                List<Usuario> entrenadores = usuarioRepo.listar().stream()
                        .filter(u -> "Entrenador".equals(u.getRol()))
                        .collect(Collectors.toList());
                request.setAttribute("entrenadores", entrenadores);
                request.getRequestDispatcher("/WEB-INF/admin/editarRutina.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/entrenador/editarRutina.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=ID inválido");
        } catch (SQLException e) {
            throw new ServletException("Error al acceder a la base de datos", e);
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
            Rutina rutinaExistente = rutinaService.obtenerPorId(idRutina);

            if (rutinaExistente == null) {
                response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=Rutina no encontrada");
                return;
            }

            // Verificar permisos
            if ("Entrenador".equals(rol) && rutinaExistente.getIdEntrenador() != idUsuario) {
                response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=No tiene permisos para editar esta rutina");
                return;
            }

            // Actualizar campos según rol
            Rutina rutinaActualizada = new Rutina();
            rutinaActualizada.setId(idRutina);
            rutinaActualizada.setIdCliente(rutinaExistente.getIdCliente()); // No se modifica

            if ("Administrador".equals(rol)) {
                int idEntrenador = Integer.parseInt(request.getParameter("idEntrenador"));
                rutinaActualizada.setIdEntrenador(idEntrenador);
            } else {
                rutinaActualizada.setIdEntrenador(rutinaExistente.getIdEntrenador()); // Mantiene el mismo
            }

            rutinaActualizada.setTipoEntrenamiento(request.getParameter("tipoEntrenamiento"));
            rutinaActualizada.setObservaciones(request.getParameter("observaciones"));

            if (rutinaService.actualizar(rutinaActualizada)) {
                response.sendRedirect(request.getContextPath() + "/RutinaServlet?success=Rutina actualizada correctamente");
            } else {
                throw new RuntimeException("Error al actualizar la rutina");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=IDs inválidos");
        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar la solicitud: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
}