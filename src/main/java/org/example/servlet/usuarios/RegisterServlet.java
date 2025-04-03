package org.example.servlet.usuarios;

// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 02-04-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.modelos.Usuario;
import org.example.services.ServiceException;
import org.example.services.UsuarioService;
import org.example.services.UsuarioServiceImplement;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/registro")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/registro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener parámetros del formulario
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String usuario = req.getParameter("usuario");
        String clave = req.getParameter("clave");
        String correo = req.getParameter("correo");
        String telefono = req.getParameter("telefono");
        String cedula = req.getParameter("cedula");
        String direccion = req.getParameter("direccion");

        // Variables para errores
        boolean hayError = false;

        // Validaciones
        if (!validarTelefono(telefono)) {
            req.setAttribute("errorTelefono", "Teléfono no válido");
            hayError = true;
        }

        if (!validarCedula(cedula)) {
            req.setAttribute("errorCedula", "Cédula inválida");
            hayError = true;
        }

        if (!validarCorreo(correo)) {
            req.setAttribute("errorCorreo", "Correo electrónico no válido");
            hayError = true;
        }

        if (hayError) {
            volverAlFormulario(req, resp, nombre, apellido, usuario, correo, telefono, cedula, direccion);
            return;
        }

        // Crear usuario
        Usuario nuevoUsuario = new Usuario(nombre, apellido, usuario, clave, "Cliente", correo, telefono, cedula, direccion);

        // Guardar en BD
        Connection conn = (Connection) req.getAttribute("conn");
        UsuarioService service = new UsuarioServiceImplement(conn);

        try {
            service.guardar(nuevoUsuario);
            resp.sendRedirect(req.getContextPath() + "/LoginServlet?registro=exito");
        } catch (ServiceException e) {
            manejarErrorRegistro(req, resp, e, nombre, apellido, usuario, correo, telefono, cedula, direccion);
        }
    }

    private boolean validarTelefono(String telefono) {
        return telefono != null && telefono.matches("\\d{10}");
    }

    private boolean validarCedula(String cedula) {
        return cedula != null && cedula.matches("\\d{10}");
    }

    private boolean validarCorreo(String correo) {
        return correo != null && correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private void volverAlFormulario(HttpServletRequest req, HttpServletResponse resp,
                                    String nombre, String apellido, String usuario,
                                    String correo, String telefono, String cedula,
                                    String direccion) throws ServletException, IOException {
        req.setAttribute("nombre", nombre);
        req.setAttribute("apellido", apellido);
        req.setAttribute("usuario", usuario);
        req.setAttribute("correo", correo);
        req.setAttribute("telefono", telefono);
        req.setAttribute("cedula", cedula);
        req.setAttribute("direccion", direccion);
        doGet(req, resp);
    }

    private void manejarErrorRegistro(HttpServletRequest req, HttpServletResponse resp,
                                      ServiceException e, String nombre, String apellido,
                                      String usuario, String correo, String telefono,
                                      String cedula, String direccion) throws ServletException, IOException {
        String mensajeError = e.getMessage().toLowerCase();

        if (mensajeError.contains("usuario")) {
            req.setAttribute("errorUsuario", "Este nombre de usuario ya existe");
        } else if (mensajeError.contains("cedula")) {
            req.setAttribute("errorCedula", "Esta cédula ya está registrada");
        } else if (mensajeError.contains("correo")) {
            req.setAttribute("errorCorreo", "Este correo ya está en uso");
        } else {
            req.setAttribute("error", "Error en el registro: " + e.getMessage());
        }

        volverAlFormulario(req, resp, nombre, apellido, usuario, correo, telefono, cedula, direccion);
    }
}
