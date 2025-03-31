package org.example.servlet;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 29-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.VistaRutina;
import org.example.services.RutinaService;
import org.example.services.RutinaServiceImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/RutinaServlet")
public class RutinaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int usuarioId = (int) session.getAttribute("idUsuario");
        Connection conn = (Connection) req.getAttribute("conn");
        RutinaService service = new RutinaServiceImpl(conn);

        try {
            List<VistaRutina> rutinas;
            String jspPath;

            // Parámetros comunes
            String nombreCliente = req.getParameter("nombreCliente");
            String nombreEntrenador = req.getParameter("nombreEntrenador");
            String tipoEntrenamiento = req.getParameter("tipoEntrenamiento");

            switch (rol) {
                case "Administrador":
                    // Lógica de paginación para admin
                    int pagina = 1;
                    int registrosPorPagina = 10;
                    if (req.getParameter("pagina") != null) {
                        pagina = Integer.parseInt(req.getParameter("pagina"));
                    }

                    if ((nombreCliente != null && !nombreCliente.isEmpty()) ||
                            (tipoEntrenamiento != null && !tipoEntrenamiento.isEmpty()) ||
                            (nombreEntrenador != null && !nombreEntrenador.isEmpty())) {

                        rutinas = service.filtrarRutinasAvanzado(
                                nombreCliente,
                                nombreEntrenador,
                                tipoEntrenamiento,
                                (pagina-1)*registrosPorPagina,
                                registrosPorPagina
                        );
                    } else {
                        rutinas = service.listarTodasConPaginacion(
                                (pagina-1)*registrosPorPagina,
                                registrosPorPagina
                        );
                    }

                    int totalRegistros = service.contarRutinasFiltradas(
                            nombreCliente,
                            nombreEntrenador,
                            tipoEntrenamiento
                    );
                    int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);

                    req.setAttribute("totalRegistros", totalRegistros);
                    req.setAttribute("totalPaginas", totalPaginas);
                    req.setAttribute("paginaActual", pagina);
                    req.setAttribute("registrosPorPagina", registrosPorPagina);
                    jspPath = "/admin/rutinasAdmin.jsp";
                    break;

                case "Entrenador":
                    // Lógica SIN paginación para entrenador
                    if (tipoEntrenamiento != null && !tipoEntrenamiento.isEmpty()) {
                        rutinas = service.listarPorEntrenadorYFiltro(usuarioId, tipoEntrenamiento);
                    } else {
                        rutinas = service.listarPorEntrenador(usuarioId);
                    }
                    req.setAttribute("totalRegistros", rutinas.size()); // Mostrar total real
                    jspPath = "/entrenador/rutinasEntrenador.jsp";
                    break;

                case "Cliente":
                    rutinas = service.listarPorCliente(usuarioId);
                    req.setAttribute("totalRegistros", rutinas.size()); // Mostrar total real
                    jspPath = "/cliente/rutinasCliente.jsp";
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                            "Rol no reconocido: " + rol);
                    return;
            }

            req.setAttribute("rutinas", rutinas);
            req.setAttribute("idUsuario", usuarioId);
            req.getRequestDispatcher(jspPath).forward(req, resp);

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Formato de número inválido: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Error inesperado: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}