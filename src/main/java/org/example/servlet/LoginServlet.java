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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("index.jsp");
    }
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String usuario = request.getParameter("usuario");
            String clave = request.getParameter("clave");
            String rolFormulario = request.getParameter("rol");

            try (Connection conn = Conexion.getConnection()) {
                UsuarioLoginService loginService = new UsuarioLoginServiceImplement(conn);
                Optional<UsuarioLogin> usuarioValidado = loginService.autenticar(usuario, clave);

                if (!usuarioValidado.isPresent()) {
                    response.sendRedirect("index.jsp?error=nouser");
                    return;
                }

                UsuarioLogin user = usuarioValidado.get();

                if (!user.getRol().equals(rolFormulario)) {
                    response.sendRedirect("index.jsp?error=wrongrole");
                    return;
                }

                HttpSession session = request.getSession();
                session.setAttribute("usuario", user);
                session.setAttribute("rol", user.getRol());
                session.setAttribute("idUsuario", user.getId());
                session.setAttribute("cedula", user.getCedula());  // Guardamos la cédula

                switch (user.getRol()) {
                    case "Administrador":
                        response.sendRedirect(request.getContextPath() + "/admin/home.jsp");
                        break;
                    case "Entrenador":
                        response.sendRedirect(request.getContextPath() + "/entrenador/home.jsp");
                        break;
                    case "Cliente":
                        response.sendRedirect(request.getContextPath() + "/cliente/home.jsp");
                        break;
                    default:
                        session.invalidate();
                        response.sendRedirect("index.jsp?error=invalidrole");
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("index.jsp?error=server");
            }
        }
    }



