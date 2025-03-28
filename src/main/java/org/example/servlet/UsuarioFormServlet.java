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

@WebServlet("/UsuarioFormServlet")
public class UsuarioFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Muestra el formulario de agregar usuario
        getServletContext().getRequestDispatcher("/admin/usuarioform.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener datos del formulario
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String usuario = req.getParameter("usuario");
        String clave = req.getParameter("clave");
        String rol = req.getParameter("rol");
        String correo = req.getParameter("correo");
        String telefono = req.getParameter("telefono");
        String cedula = req.getParameter("cedula");
        String direccion = req.getParameter("direccion");

        // Crear el objeto Usuario
        Usuario nuevoUsuario = new Usuario(nombre, apellido, usuario, clave, rol, correo, telefono, cedula, direccion);

        // Guardar el nuevo usuario en la base de datos
        Connection conn = (Connection) req.getAttribute("conn");
        UsuarioService service = new UsuarioServiceImplement(conn);
        service.guardar(nuevoUsuario); // Llamada a guardar en lugar de agregar

        // Redirigir a la lista de usuarios después de agregar
        resp.sendRedirect(req.getContextPath() + "/UsuarioServlet");
    }
}
