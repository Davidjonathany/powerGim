package org.example.servlet.ejercicios;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.Ejercicio;
import org.example.modelos.VistaRutina;
import org.example.services.EjercicioService;
import org.example.services.EjercicioServiceImpl;
import org.example.services.RutinaService;
import org.example.services.RutinaServiceImpl;
import org.example.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ejercicios/gestion")
public class GestionEjercicioServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int usuarioId = (int) session.getAttribute("idUsuario");
        String idStr = req.getParameter("id");

        try (Connection conn = Conexion.getConnection()) {
            // Servicio para cargar rutinas disponibles
            RutinaService rutinaService = new RutinaServiceImpl(conn);
            List<VistaRutina> rutinasDisponibles;

            // Filtramos rutinas según el rol
            if ("Administrador".equals(rol)) {
                rutinasDisponibles = rutinaService.listarTodas();
            } else if ("Entrenador".equals(rol)) {
                rutinasDisponibles = rutinaService.listarPorEntrenador(usuarioId);
            } else {
                rutinasDisponibles = rutinaService.listarPorCliente(usuarioId);
            }

            req.setAttribute("rutinas", rutinasDisponibles);

            if (idStr != null && !idStr.isEmpty()) {
                // Modo edición
                EjercicioService service = new EjercicioServiceImpl(conn);
                Ejercicio ejercicio = service.obtenerPorId(Integer.parseInt(idStr));

                if (ejercicio == null) {
                    resp.sendRedirect(req.getContextPath() + "/ejercicios?error=Ejercicio+no+encontrado");
                    return;
                }

                req.setAttribute("ejercicio", ejercicio);
                req.setAttribute("modo", "editar");
            } else {
                // Modo creación
                req.setAttribute("modo", "crear");
            }

            // Ruta actualizada a WEB-INF
            req.getRequestDispatcher("/WEB-INF/ejercicios/formulario.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/ejercicios?error=Error+al+cargar+datos");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String idStr = req.getParameter("id");
        String accion = req.getParameter("accion");

        try (Connection conn = Conexion.getConnection()) {
            EjercicioService service = new EjercicioServiceImpl(conn);

            if ("eliminar".equals(accion)) {
                // Eliminar ejercicio
                if (service.eliminar(Integer.parseInt(idStr))) {
                    resp.sendRedirect(req.getContextPath() + "/ejercicios?success=Ejercicio+eliminado");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/ejercicios?error=No+se+pudo+eliminar+ejercicio");
                }
            } else {
                // Crear o actualizar
                Ejercicio ejercicio = new Ejercicio();
                if (idStr != null && !idStr.isEmpty()) {
                    ejercicio.setId(Integer.parseInt(idStr));
                }

                ejercicio.setIdRutina(Integer.parseInt(req.getParameter("idRutina")));
                ejercicio.setNombre(req.getParameter("nombre"));
                ejercicio.setRepeticiones(parseOrDefault(req.getParameter("repeticiones"), 0));
                ejercicio.setSeries(parseOrDefault(req.getParameter("series"), 0));
                ejercicio.setTiempo(parseOrDefault(req.getParameter("tiempo"), 0));
                ejercicio.setDescanso(parseOrDefault(req.getParameter("descanso"), 0));

                boolean resultado;
                if (ejercicio.getId() == 0) {
                    resultado = service.crear(ejercicio);
                } else {
                    resultado = service.actualizar(ejercicio);
                }

                if (resultado) {
                    resp.sendRedirect(req.getContextPath() + "/ejercicios?success=Ejercicio+guardado");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/ejercicios?error=Error+al+guardar+ejercicio");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/ejercicios?error=Error+en+la+base+de+datos");
        }
    }

    private int parseOrDefault(String param, int defaultValue) {
        if (param == null || param.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }


}