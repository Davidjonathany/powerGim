package org.example.servlet.pesos;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 03-04-2025

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.modelos.Peso;
import org.example.modelos.PesoVista;
import org.example.services.PesoService;
import org.example.services.PesoServiceImpl;
import org.example.util.Conexion;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/pesos/actualizar")
public class ActualizarPesoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/pesos?error=ID+no+valido");
            return;
        }

        try (Connection conn = Conexion.getConnection()) {
            PesoService service = new PesoServiceImpl(conn);

            // Usar obtenerVistaPorId en lugar de obtenerPorId
            PesoVista pesoVista = service.obtenerVistaPorId(
                    Integer.parseInt(idStr),
                    (String) session.getAttribute("rol"),
                    (int) session.getAttribute("idUsuario")
            );

            if (pesoVista == null) {
                resp.sendRedirect(req.getContextPath() + "/pesos?error=Registro+no+encontrado");
                return;
            }

            req.setAttribute("peso", pesoVista);
            req.getRequestDispatcher("/WEB-INF/pesos/actualizar.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/pesos?error=Error+de+base+de+datos");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/pesos?error=" + URLEncoder.encode("Mensaje con espacios o caracteres especiales", "UTF-8"));}
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int usuarioId = (int) session.getAttribute("idUsuario");
        String idStr = req.getParameter("id");

        try (Connection conn = Conexion.getConnection()) {
            PesoService service = new PesoServiceImpl(conn);

            // Verificar permisos primero
            Peso pesoExistente = service.obtenerPorId(Integer.parseInt(idStr), rol, usuarioId);
            if (pesoExistente == null) {
                resp.sendRedirect(req.getContextPath() + "/pesos?error=No+tienes+permisos+para+esta+acción");
                return;
            }

            // Solo actualizamos el peso actual
            double nuevoPeso = Double.parseDouble(req.getParameter("pesoActual"));

            Peso peso = new Peso();
            peso.setId(Integer.parseInt(idStr));
            peso.setPesoActual(nuevoPeso);

            boolean resultado = service.actualizar(peso, rol, usuarioId);

            if (resultado) {
                resp.sendRedirect(req.getContextPath() + "/pesos?success=Peso+actualizado+correctamente");
            } else {
                resp.sendRedirect(req.getContextPath() + "/pesos?error=Error+al+actualizar+el+peso");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/pesos?error=Peso+no+válido");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/pesos?error=Error+de+base+de+datos");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/pesos?error=" + e.getMessage());
        }
    }
}