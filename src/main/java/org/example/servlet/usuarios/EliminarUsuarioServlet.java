package org.example.servlet.usuarios;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creacion 27/03/2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.Usuario;
import org.example.services.UsuarioService;
import org.example.services.UsuarioServiceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/EliminarUsuario")
public class EliminarUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Validación de sesión y rol de administrador (añadido)
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        if (!"Administrador".equals(rol)) {
            resp.sendRedirect(req.getContextPath() + "/home?error=Acceso+denegado");
            return;
        }

        // Código original
        String idStr = req.getParameter("id");

        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                Connection conn = (Connection) req.getAttribute("conn");
                UsuarioService service = new UsuarioServiceImplement(conn);
                Optional<Usuario> usuarioOptional = service.porId(id);

                if (usuarioOptional.isPresent()) {
                    Usuario usuario = usuarioOptional.get();
                    req.setAttribute("usuario", usuario);
                    req.getRequestDispatcher("/WEB-INF/admin/eliminarusuario.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/UsuarioServlet");
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID proporcionado no es válido.");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID no está presente en la solicitud.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Validación de sesión y rol de administrador (añadido)
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        if (!"Administrador".equals(rol)) {
            resp.sendRedirect(req.getContextPath() + "/home?error=Acceso+denegado");
            return;
        }

        // Código original
        String idUsuarioStr = req.getParameter("idUsuario");
        int id = Integer.parseInt(idUsuarioStr);

        Connection conn = (Connection) req.getAttribute("conn");
        UsuarioService service = new UsuarioServiceImplement(conn);
        service.eliminar(id);

        resp.sendRedirect(req.getContextPath() + "/UsuarioServlet");
    }
}