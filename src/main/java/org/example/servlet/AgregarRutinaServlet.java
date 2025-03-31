package org.example.servlet;
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

@WebServlet("/AgregarRutinaServlet")
public class AgregarRutinaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        Connection conn = (Connection) request.getAttribute("conn");
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorio(conn);

        try {
            if ("Administrador".equals(rol)) {
                // Para admin: mostrar todos los clientes y entrenadores
                List<Usuario> usuarios = usuarioRepo.listar();
                List<Usuario> clientes = usuarios.stream()
                        .filter(u -> "Cliente".equals(u.getRol()))
                        .collect(Collectors.toList());
                List<Usuario> entrenadores = usuarios.stream()
                        .filter(u -> "Entrenador".equals(u.getRol()))
                        .collect(Collectors.toList());

                request.setAttribute("clientes", clientes);
                request.setAttribute("entrenadores", entrenadores);
                request.getRequestDispatcher("/admin/agregarRutinaAdmin.jsp").forward(request, response);

            } else if ("Entrenador".equals(rol)) {
                // Para entrenador: mostrar solo clientes
                List<Usuario> clientes = usuarioRepo.listar().stream()
                        .filter(u -> "Cliente".equals(u.getRol()))
                        .collect(Collectors.toList());

                request.setAttribute("clientes", clientes);
                request.getRequestDispatcher("/entrenador/agregarRutina.jsp").forward(request, response);

            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso no autorizado");
            }
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
        UsuarioRepositorio usuarioRepo = new UsuarioRepositorio(conn);

        try {
            Rutina rutina = new Rutina();

            // Validar y asignar cliente
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            if (!usuarioRepo.esCliente(idCliente)) {
                throw new IllegalArgumentException("El ID no corresponde a un cliente válido");
            }
            rutina.setIdCliente(idCliente);

            // Asignar entrenador según rol
            if ("Administrador".equals(rol)) {
                int idEntrenador = Integer.parseInt(request.getParameter("idEntrenador"));
                if (!usuarioRepo.esEntrenador(idEntrenador)) {
                    throw new IllegalArgumentException("El ID no corresponde a un entrenador válido");
                }
                rutina.setIdEntrenador(idEntrenador);
            } else {
                // Para entrenador, se autoasigna
                rutina.setIdEntrenador(idUsuario);
            }

            // Campos comunes
            rutina.setTipoEntrenamiento(request.getParameter("tipoEntrenamiento"));
            rutina.setObservaciones(request.getParameter("observaciones"));

            if (rutinaService.crear(rutina)) {
                response.sendRedirect(request.getContextPath() + "/RutinaServlet?success=Rutina creada correctamente");
            } else {
                throw new RuntimeException("Error al guardar la rutina en la base de datos");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=IDs inválidos");
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/RutinaServlet?error=" + e.getMessage());
        } catch (SQLException e) {
            throw new ServletException("Error al validar roles", e);
        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar la solicitud: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
}