package org.example.servlet;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 31-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("rol") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        // Pasar el rol a la vista JSP
        String rol = (String) session.getAttribute("rol");
        req.setAttribute("userRol", rol);

        // Usar un único JSP para todos los roles
        req.getRequestDispatcher("/WEB-INF/home/home.jsp").forward(req, resp);
    }
}