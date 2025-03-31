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
import java.util.Optional;

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

        // Validaciones en backend
        if (!telefono.matches("\\d{10}")) {
            req.setAttribute("error", "Teléfono no válido");
            Usuario usuarioExistente = service.porId(id).orElse(null);
            req.setAttribute("usuario", usuarioExistente);
            getServletContext().getRequestDispatcher("/admin/usuarioActualizar.jsp").forward(req, resp);
            return;
        }

        if (!cedula.matches("\\d{10}")) {
            req.setAttribute("error", "Cédula no válida");
            Usuario usuarioExistente = service.porId(id).orElse(null);
            req.setAttribute("usuario", usuarioExistente);
            getServletContext().getRequestDispatcher("/admin/usuarioActualizar.jsp").forward(req, resp);
            return;
        }

        // Verificar si la cédula ya existe (excepto para el usuario actual)
        try {
            Optional<Usuario> usuarioConCedula = service.buscarPorCedula(cedula);
            if (usuarioConCedula.isPresent() && usuarioConCedula.get().getId() != id) {
                req.setAttribute("error", "La cédula " + cedula + " ya está registrada por otro usuario.");
                Usuario usuarioExistente = service.porId(id).orElse(null);
                req.setAttribute("usuario", usuarioExistente);
                getServletContext().getRequestDispatcher("/admin/usuarioActualizar.jsp").forward(req, resp);
                return;
            }
        } catch (Exception e) {
            req.setAttribute("error", "Error al verificar la cédula.");
            Usuario usuarioExistente = service.porId(id).orElse(null);
            req.setAttribute("usuario", usuarioExistente);
            getServletContext().getRequestDispatcher("/admin/usuarioActualizar.jsp").forward(req, resp);
            return;
        }
        // Validacion de correo
        if (!validarCorreo(correo)) {
            req.setAttribute("error", "Correo electrónico no válido");
            Usuario usuarioExistente = service.porId(id).orElse(null);
            req.setAttribute("usuario", usuarioExistente);
            getServletContext().getRequestDispatcher("/admin/usuarioActualizar.jsp").forward(req, resp);
            return;
        }

        // Crea el objeto usuario con los datos del formulario
        Usuario usuarioActualizar = new Usuario(nombre, apellido, usuario, clave, rol, correo, telefono, cedula, direccion);
        usuarioActualizar.setId(id);

        // Llama al servicio para actualizar el usuario
        try {
            service.actualizar(id, usuarioActualizar);
            resp.sendRedirect(req.getContextPath() + "/UsuarioServlet");
        } catch (Exception e) {
            req.setAttribute("error", "No se pudo actualizar el usuario: " + e.getMessage());
            Usuario usuarioExistente = service.porId(id).orElse(null);
            req.setAttribute("usuario", usuarioExistente);
            getServletContext().getRequestDispatcher("/admin/usuarioActualizar.jsp").forward(req, resp);
        }
    }
    private boolean validarCorreo(String correo) {
        String regex = "^[^@]+@[^@]+\\.[^@]+$";
        if (!correo.matches(regex)) {
            return false;
        }
        String dominio = correo.split("@")[1];
        return dominio.endsWith(".com") || dominio.endsWith(".ec");
    }
}
