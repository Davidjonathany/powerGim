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
import org.example.services.ServiceException;
import org.example.services.UsuarioService;
import org.example.services.UsuarioServiceImplement;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/UsuarioFormServlet")
public class UsuarioFormServlet extends HttpServlet {

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

        // Código original exactamente igual
        getServletContext().getRequestDispatcher("/WEB-INF/admin/usuarioform.jsp").forward(req, resp);
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

        // Código original exactamente igual
        // Obtener datos del formulario
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String usuario = req.getParameter("usuario");
        String clave = req.getParameter("clave");
        String rolUsuario = req.getParameter("rol");
        String correo = req.getParameter("correo");
        String telefono = req.getParameter("telefono");
        String cedula = req.getParameter("cedula");
        String direccion = req.getParameter("direccion");

        // Validaciones en backend
        if (!telefono.matches("\\d{10}")) {
            req.setAttribute("error", "Teléfono inválido.");
            req.getRequestDispatcher("/WEB-INF/admin/usuarioform.jsp").forward(req, resp);
            return;
        }

        if (!cedula.matches("\\d{10}")) {
            req.setAttribute("error", "Cédula inválida.");
            req.getRequestDispatcher("/WEB-INF/admin/usuarioform.jsp").forward(req, resp);
            return;
        }
        if (!correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|ec|edu|net|org|gov|mil|biz|info|mobi|name|aero|jobs|museum)$")) {
            req.setAttribute("error", "Correo electrónico no válido");
            req.getRequestDispatcher("/WEB-INF/admin/usuarioform.jsp").forward(req, resp);
            return;
        }

        // Crear el objeto Usuario
        Usuario nuevoUsuario = new Usuario(nombre, apellido, usuario, clave, rolUsuario, correo, telefono, cedula, direccion);

        // Guardar el nuevo usuario
        Connection conn = (Connection) req.getAttribute("conn");
        UsuarioService service = new UsuarioServiceImplement(conn);

        try {
            service.guardar(nuevoUsuario);
            resp.sendRedirect(req.getContextPath() + "/UsuarioServlet");
        } catch (ServiceException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/admin/usuarioform.jsp").forward(req, resp);
        }
    }
}