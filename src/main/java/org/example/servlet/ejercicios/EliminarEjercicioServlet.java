package org.example.servlet.ejercicios;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.EjercicioVista;
import org.example.services.EjercicioService;
import org.example.services.EjercicioServiceImpl;
import org.example.util.Conexion;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/ejercicios/eliminar")
public class EliminarEjercicioServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !((String) session.getAttribute("rol")).matches("Administrador|Entrenador")) {
            response.sendRedirect(request.getContextPath() + "/ejercicios?error=Acceso+denegado");
            return;
        }

        try (Connection conn = Conexion.getConnection()) {
            int idEjercicio = Integer.parseInt(request.getParameter("id"));
            EjercicioService service = new EjercicioServiceImpl(conn);
            EjercicioVista ejercicio = service.obtenerVistaPorId(idEjercicio);

            if (ejercicio == null) {
                request.setAttribute("error", "No se encontró el ejercicio con ID: " + idEjercicio);
                request.getRequestDispatcher("/WEB-INF/ejercicios/eliminar.jsp").forward(request, response); // Muestra el error en eliminar.jsp
                return;
            }

            request.setAttribute("ejercicio", ejercicio);
            request.getRequestDispatcher("/WEB-INF/ejercicios/eliminar.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar el ejercicio: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/ejercicios/eliminar.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idEjercicio = Integer.parseInt(request.getParameter("id"));

            try (Connection conn = Conexion.getConnection()) {
                EjercicioService service = new EjercicioServiceImpl(conn);

                if (service.eliminar(idEjercicio)) {
                    response.sendRedirect(request.getContextPath() + "/ejercicios?success=Ejercicio+eliminado+exitosamente");
                } else {
                    throw new Exception("No se pudo eliminar el ejercicio");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ejercicios?error=Error+al+eliminar+ejercicio: " + e.getMessage());
        }
    }
}