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
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/ejercicios/editar")
public class EditarEjercicioServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String idEjercicioStr = req.getParameter("id");
        if (idEjercicioStr == null || idEjercicioStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/ejercicios?error=ID+de+ejercicio+no+especificado");
            return;
        }

        try (Connection conn = Conexion.getConnection()) {
            EjercicioService ejercicioService = new EjercicioServiceImpl(conn);
            RutinaService rutinaService = new RutinaServiceImpl(conn);

            Ejercicio ejercicio = ejercicioService.obtenerPorId(Integer.parseInt(idEjercicioStr));
            if (ejercicio == null) {
                resp.sendRedirect(req.getContextPath() + "/ejercicios?error=Ejercicio+no+encontrado");
                return;
            }

            VistaRutina rutina = rutinaService.obtenerVistaPorId(ejercicio.getIdRutina());
            if (rutina == null) {
                resp.sendRedirect(req.getContextPath() + "/ejercicios?error=Rutina+asociada+no+encontrada");
                return;
            }

            String rol = (String) session.getAttribute("rol");
            int usuarioId = (int) session.getAttribute("idUsuario");

            if (!validarPermisos(rol, usuarioId, rutina)) {
                resp.sendRedirect(req.getContextPath() + "/ejercicios?error=No+tienes+permiso+para+editar+este+ejercicio");
                return;
            }

            configurarAtributosVista(req, ejercicio, rutina);
            req.getRequestDispatcher("/WEB-INF/ejercicios/editar.jsp").forward(req, resp);

        } catch (SQLException e) {
            manejarError(req, resp, e, "Error al cargar ejercicio para edición");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String idEjercicioStr = req.getParameter("id");
        if (idEjercicioStr == null || idEjercicioStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/ejercicios?error=ID+de+ejercicio+no+especificado");
            return;
        }

        try (Connection conn = Conexion.getConnection()) {
            EjercicioService ejercicioService = new EjercicioServiceImpl(conn);
            RutinaService rutinaService = new RutinaServiceImpl(conn);

            Ejercicio ejercicioActual = validarEjercicioYPermisos(
                    ejercicioService,
                    rutinaService,
                    Integer.parseInt(idEjercicioStr),
                    session,
                    req,
                    resp
            );

            if (ejercicioActual == null) return;

            Ejercicio ejercicioActualizado = crearEjercicioActualizado(req, ejercicioActual);

            if (ejercicioService.actualizar(ejercicioActualizado)) {
                resp.sendRedirect(req.getContextPath() + "/ejercicios?success=Ejercicio+actualizado");
            } else {
                resp.sendRedirect(req.getContextPath() + "/ejercicios?error=Error+al+actualizar+ejercicio");
            }
        } catch (SQLException e) {
            manejarError(req, resp, e, "Error al actualizar ejercicio");
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/ejercicios?error=Formato+de+número+inválido");
        }
    }

    private boolean validarPermisos(String rol, int usuarioId, VistaRutina rutina) {
        if ("Administrador".equals(rol)) return true;
        if ("Entrenador".equals(rol)) return rutina.getIdEntrenador() == usuarioId;
        if ("Cliente".equals(rol)) return rutina.getIdCliente() == usuarioId;
        return false;
    }

    private void configurarAtributosVista(HttpServletRequest req, Ejercicio ejercicio, VistaRutina rutina) {
        req.setAttribute("ejercicio", ejercicio);
        req.setAttribute("rutina", rutina);
        req.setAttribute("esAdministrador", "Administrador".equals(req.getSession().getAttribute("rol")));
        req.setAttribute("esEntrenador", "Entrenador".equals(req.getSession().getAttribute("rol")));
    }

    private Ejercicio validarEjercicioYPermisos(
            EjercicioService ejercicioService,
            RutinaService rutinaService,
            int idEjercicio,
            HttpSession session,
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws SQLException, IOException {
        Ejercicio ejercicio = ejercicioService.obtenerPorId(idEjercicio);
        if (ejercicio == null) {
            resp.sendRedirect(req.getContextPath() + "/ejercicios?error=Ejercicio+no+encontrado");
            return null;
        }

        VistaRutina rutina = rutinaService.obtenerVistaPorId(ejercicio.getIdRutina());
        String rol = (String) session.getAttribute("rol");
        int usuarioId = (int) session.getAttribute("idUsuario");

        if (!validarPermisos(rol, usuarioId, rutina)) {
            resp.sendRedirect(req.getContextPath() + "/ejercicios?error=No+tienes+permiso+para+editar+este+ejercicio");
            return null;
        }

        return ejercicio;
    }

    private Ejercicio crearEjercicioActualizado(HttpServletRequest req, Ejercicio ejercicioActual) {
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId(ejercicioActual.getId());
        ejercicio.setIdRutina(ejercicioActual.getIdRutina());
        ejercicio.setNombre(req.getParameter("nombre"));
        ejercicio.setRepeticiones(parseOrDefault(req.getParameter("repeticiones"), 0)); // valor por defecto 0
        ejercicio.setSeries(parseOrDefault(req.getParameter("series"), 0)); // valor por defecto 0
        ejercicio.setTiempo(parseOrDefault(req.getParameter("tiempo"), 0)); // valor por defecto 0
        ejercicio.setDescanso(parseOrDefault(req.getParameter("descanso"), 0)); // valor por defecto 0
        return ejercicio;
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

    private void manejarError(HttpServletRequest req, HttpServletResponse resp, Exception e, String mensajeBase) throws IOException {
        e.printStackTrace();
        String mensajeError = mensajeBase + ": " + e.getMessage();
        resp.sendRedirect(req.getContextPath() + "/ejercicios?error=" + URLEncoder.encode(mensajeError, "UTF-8"));
    }
}