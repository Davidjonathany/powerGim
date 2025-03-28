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

@WebServlet("/UsuarioActualizar")
public class UsuarioActualizarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        UsuarioService service = new UsuarioServiceImplement(conn);

        // Obtiene el ID del usuario que se va a actualizar
        String idUsuarioStr = req.getParameter("id");
        if (idUsuarioStr != null && !idUsuarioStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idUsuarioStr);

                // Obtiene el usuario de la base de datos
                Usuario usuario = service.porId(id).orElse(null);

                // Verifica si el usuario fue encontrado
                if (usuario != null) {
                    req.setAttribute("usuario", usuario);
                    getServletContext().getRequestDispatcher("/admin/usuarioActualizar.jsp").forward(req, resp);
                } else {
                    req.setAttribute("error", "Usuario no encontrado.");
                    resp.sendRedirect(req.getContextPath() + "/UsuarioServlet");
                }

            } catch (NumberFormatException e) {
                req.setAttribute("error", "ID de usuario inválido.");
                getServletContext().getRequestDispatcher("/admin/usuario.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "ID de usuario no proporcionado.");
            getServletContext().getRequestDispatcher("/admin/usuario.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        UsuarioService service = new UsuarioServiceImplement(conn);

        // Obtiene los datos del formulario
        int id = Integer.parseInt(req.getParameter("id"));
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String usuario = req.getParameter("usuario");
        String clave = req.getParameter("clave");
        String rol = req.getParameter("rol");
        String correo = req.getParameter("correo");
        String telefono = req.getParameter("telefono");
        String cedula = req.getParameter("cedula");
        String direccion = req.getParameter("direccion");

        // Crea el objeto usuario con los datos del formulario
        Usuario usuarioActualizar = new Usuario(nombre, apellido, usuario, clave, rol, correo, telefono, cedula, direccion);
        usuarioActualizar.setId(id);

        // Llama al servicio para actualizar el usuario
        try {
            service.actualizar(id, usuarioActualizar);  // Llamada con el id y el objeto usuario
            resp.sendRedirect(req.getContextPath() + "/UsuarioServlet");
        } catch (Exception e) {
            req.setAttribute("error", "No se pudo actualizar el usuario.");
            getServletContext().getRequestDispatcher("/admin/usuarioActualizar.jsp").forward(req, resp);
        }
    }
}
