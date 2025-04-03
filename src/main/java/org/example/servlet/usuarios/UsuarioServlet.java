package org.example.servlet.usuarios;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

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
import java.util.List;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Validar sesión
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        // 2. Validar rol
        String rol = (String) session.getAttribute("rol");
        if (rol == null || !"Administrador".equals(rol)) {
            session.setAttribute("error", "Acceso denegado: no tienes permisos de administrador");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Connection conn = (Connection) req.getAttribute("conn");
        UsuarioService service = new UsuarioServiceImplement(conn);

        // 3. Manejar acción de eliminación
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            String idUsuarioStr = req.getParameter("idUsuario");
            if (idUsuarioStr != null && !idUsuarioStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idUsuarioStr);
                    service.eliminar(id);
                    resp.sendRedirect(req.getContextPath() + "/UsuarioServlet?success=Usuario+eliminado");
                } catch (NumberFormatException e) {
                    req.setAttribute("error", "ID de usuario inválido.");
                } catch (Exception e) {
                    req.setAttribute("error", "Error al eliminar: " + e.getMessage());
                }
            } else {
                req.setAttribute("error", "ID de usuario no proporcionado.");
            }
        }

        // 4. Manejar búsqueda
        String searchTerm = req.getParameter("searchTerm");
        List<Usuario> usuarios;

        try {
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                usuarios = service.buscarUsuariosCompleto(searchTerm.trim());
            } else {
                usuarios = service.listar();
            }

            req.setAttribute("usuarios", usuarios.isEmpty() ? null : usuarios);
        } catch (Exception e) {
            req.setAttribute("error", "Error al buscar usuarios: " + e.getMessage());
            try {
                usuarios = service.listar();
                req.setAttribute("usuarios", usuarios.isEmpty() ? null : usuarios);
            } catch (Exception ex) {
                req.setAttribute("error", req.getAttribute("error") + " | " + ex.getMessage());
            }
        }


        // 5. Redirigir al JSP (ruta actualizada a WEB-INF)
        req.getRequestDispatcher("/WEB-INF/admin/usuario.jsp").forward(req, resp);
    }
}