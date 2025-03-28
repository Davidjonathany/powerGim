package org.example.servlet;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creacion 27/03/2025
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
import java.util.Optional;

@WebServlet("/EliminarUsuario")  // Indica la URL a la que responde este servlet
public class EliminarUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener el parámetro 'id' de la URL
        String idStr = req.getParameter("id");

        // Validar si el parámetro 'id' no es nulo o vacío
        if (idStr != null && !idStr.isEmpty()) {
            try {
                // Convertir 'id' a entero
                int id = Integer.parseInt(idStr);

                // Crear servicio y consultar el usuario
                Connection conn = (Connection) req.getAttribute("conn");
                UsuarioService service = new UsuarioServiceImplement(conn);
                Optional<Usuario> usuarioOptional = service.porId(id);

                if (usuarioOptional.isPresent()) {
                    // Si el usuario existe, lo enviamos a la vista
                    Usuario usuario = usuarioOptional.get();
                    req.setAttribute("usuario", usuario);
                    getServletContext().getRequestDispatcher("/admin/eliminarusuario.jsp").forward(req, resp);
                } else {
                    // Si no se encuentra el usuario, redirigir a la lista de usuarios
                    resp.sendRedirect(req.getContextPath() + "/UsuarioServlet");
                }
            } catch (NumberFormatException e) {
                // Si el id no es un número válido
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID proporcionado no es válido.");
            }
        } else {
            // Si el id es nulo o vacío
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID no está presente en la solicitud.");
        }
    }
    // Método para manejar las solicitudes POST
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idUsuarioStr = req.getParameter("idUsuario");
        int id = Integer.parseInt(idUsuarioStr);

        Connection conn = (Connection) req.getAttribute("conn");
        UsuarioService service = new UsuarioServiceImplement(conn);
        service.eliminar(id);

        resp.sendRedirect(req.getContextPath() + "/UsuarioServlet");
    }
}
