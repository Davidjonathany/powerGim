package org.example.servlet;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 28-03-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.AsistenciaVista;
import org.example.services.AsistenciaVistaService;
import org.example.services.AsistenciaVistaServiceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/AsistenciaVistaServlet")
public class AsistenciaServlet extends HttpServlet {

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
        AsistenciaVistaService service = new AsistenciaVistaServiceImplement(conn);

        try {
            List<AsistenciaVista> asistencias;
            String jspPath;

            // Parámetros de paginación
            int pagina = 1;
            int registrosPorPagina = 10;
            if (req.getParameter("pagina") != null) {
                pagina = Integer.parseInt(req.getParameter("pagina"));
            }

            // Parámetros de filtrado
            String cedula = req.getParameter("cedula");
            String tipo = req.getParameter("tipo");
            String fecha = req.getParameter("fecha");

            switch (rol) {
                case "Administrador":
                    if ((cedula != null && !cedula.isEmpty()) ||
                            (tipo != null && !tipo.isEmpty()) ||
                            (fecha != null && !fecha.isEmpty())) {

                        asistencias = service.filtrarAsistencias(cedula, tipo, fecha,
                                (pagina-1)*registrosPorPagina, registrosPorPagina);
                    } else if (req.getParameter("filtro") != null && req.getParameter("valor") != null
                            && !req.getParameter("valor").isEmpty()) {
                        asistencias = aplicarFiltros(req, service);
                    } else {
                        asistencias = service.listarTodasConPaginacion(
                                (pagina-1)*registrosPorPagina, registrosPorPagina);
                    }

                    // Calcular total de registros
                    int totalRegistros = service.contarAsistencias(cedula, tipo, fecha);
                    int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);

                    req.setAttribute("totalAsistencias", totalRegistros);
                    req.setAttribute("paginaActual", pagina);
                    req.setAttribute("totalPaginas", totalPaginas);

                    jspPath = "/admin/asistenciasAdmin.jsp";
                    break;

                case "Entrenador":
                    if (req.getParameter("filtro") != null && req.getParameter("valor") != null) {
                        asistencias = aplicarFiltrosEntrenador(req, service, usuarioId);
                    } else {
                        asistencias = service.listarPorEntrenador(usuarioId);
                    }
                    jspPath = "/entrenador/asistenciasEntrenador.jsp";
                    break;

                case "Cliente":
                    asistencias = service.listarPorUsuario(usuarioId);
                    jspPath = "/cliente/asistenciasCliente.jsp";
                    break;

                default:
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                            "Rol no reconocido: " + rol);
                    return;
            }

            req.setAttribute("asistencias", asistencias);
            req.getRequestDispatcher(jspPath).forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error al filtrar: " + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    private List<AsistenciaVista> aplicarFiltros(HttpServletRequest req, AsistenciaVistaService service) {
        String tipoFiltro = req.getParameter("filtro");
        String valorFiltro = req.getParameter("valor");

        try {
            switch (tipoFiltro.toLowerCase()) {
                case "cedula":
                    return service.listarPorCedula(valorFiltro);
                case "tipo":
                    return service.listarPorTipo(valorFiltro);
                case "fecha":
                    return service.listarPorFecha(valorFiltro);
                default:
                    return service.listarTodas();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Valor de filtro inválido");
        }
    }

    private List<AsistenciaVista> aplicarFiltrosEntrenador(HttpServletRequest req,
                                                           AsistenciaVistaService service, int idEntrenador) {
        String tipoFiltro = req.getParameter("filtro");
        String valorFiltro = req.getParameter("valor");

        try {
            switch (tipoFiltro.toLowerCase()) {
                case "cedula":
                    return service.listarPorCedulaYEntrenador(valorFiltro, idEntrenador);
                case "tipo":
                    return service.listarPorTipoYEntrenador(valorFiltro, idEntrenador);
                case "fecha":
                    return service.listarPorFechaYEntrenador(valorFiltro, idEntrenador);
                default:
                    return service.listarPorEntrenador(idEntrenador);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Valor de filtro inválido");
        }
    }
}