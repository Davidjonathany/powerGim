package org.example.servlet;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        Connection conn = (Connection) req.getAttribute("conn");
        UsuarioService service = new UsuarioServiceImplement(conn);

        // Verifica si la solicitud es para eliminar un usuario
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            // Obtiene el ID del usuario a eliminar y maneja posibles errores de conversión
            String idUsuarioStr = req.getParameter("idUsuario");
            if (idUsuarioStr != null && !idUsuarioStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idUsuarioStr);

                    // Elimina el usuario con el ID proporcionado
                    service.eliminar(id);

                    // Redirige nuevamente a la lista después de la eliminación
                    resp.sendRedirect(req.getContextPath() + "/UsuarioServlet");
                    return;  // Importante: termina la ejecución después de la redirección
                } catch (NumberFormatException e) {
                    // Manejo de error si el ID no es válido (no es un número)
                    req.setAttribute("error", "ID de usuario inválido.");
                    getServletContext().getRequestDispatcher("/admin/usuario.jsp").forward(req, resp);
                    return;
                }
            } else {
                // Manejo de error si no se proporciona un ID de usuario
                req.setAttribute("error", "No se proporcionó un ID de usuario.");
                getServletContext().getRequestDispatcher("/admin/usuario.jsp").forward(req, resp);
                return;
            }
        }

        // Si no es una solicitud de eliminación, procede a listar los usuarios
        List<Usuario> usuarios = service.listar();

        // Verifica si la lista de usuarios está vacía
        if (usuarios != null && !usuarios.isEmpty()) {
            req.setAttribute("usuarios", usuarios);
        } else {
            req.setAttribute("usuarios", null);  // Pasa una lista vacía o null si no hay usuarios
        }

        // Redirige al JSP para mostrar la lista de usuarios
        getServletContext().getRequestDispatcher("/admin/usuario.jsp").forward(req, resp);
    }
}
