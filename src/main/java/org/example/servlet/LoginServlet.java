package org.example.servlet;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 26-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.UsuarioLogin;
import org.example.services.UsuarioLoginService;
import org.example.services.UsuarioLoginServiceImplement;
import org.example.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        String rolFormulario = request.getParameter("rol");

        try (Connection conn = Conexion.getConnection()) {
            UsuarioLoginService loginService = new UsuarioLoginServiceImplement(conn);
            Optional<UsuarioLogin> usuarioValidado = loginService.autenticar(usuario, clave);

            if (usuarioValidado.isPresent()) {
                UsuarioLogin user = usuarioValidado.get();
                // Verificamos si el rol que se pasa en el formulario coincide con el rol del usuario autenticado
                if (user.getRol().equalsIgnoreCase(rolFormulario)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", user);

                    // Redirige según el rol del usuario
                    switch (rolFormulario.toLowerCase()) {
                        case "administrador":
                            response.sendRedirect("admin/home.jsp");
                            break;
                        case "entrenador":
                            response.sendRedirect("entrenador/home.jsp");
                            break;
                        case "cliente":
                            response.sendRedirect("cliente/home.jsp");
                            break;
                        default:
                            response.sendRedirect("index.jsp?error=invalid");
                            break;
                    }
                } else {
                    response.sendRedirect("index.jsp?error=invalid");
                }
            } else {
                response.sendRedirect("index.jsp?error=invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=server");
        }
    }
}

