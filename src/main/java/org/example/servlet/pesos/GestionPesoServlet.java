package org.example.servlet.pesos;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 01-04-2025

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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/pesos/gestion")
public class GestionPesoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/LoginServlet");
            return;
        }

        String rol = (String) session.getAttribute("rol");
        int usuarioId = (int) session.getAttribute("idUsuario");

        try (Connection conn = Conexion.getConnection()) {
            PesoService service = new PesoServiceImpl(conn);
            List<PesoVista> clientes = new ArrayList<>();

            if ("Administrador".equals(rol)) {
                clientes = service.listarTodosClientes();
            } else if ("Entrenador".equals(rol)) {
                clientes = service.listarClientesPorEntrenador(usuarioId);
            }
            // Para Cliente no necesitamos cargar lista

            req.setAttribute("clientes", clientes);
            req.setAttribute("rol", rol);

            String idStr = req.getParameter("id");
            String idClienteStr = req.getParameter("idCliente");

            if (idStr != null && !idStr.isEmpty()) {
                PesoVista pesoVista = service.obtenerVistaPorId(Integer.parseInt(idStr), rol, usuarioId);
                req.setAttribute("peso", pesoVista);
                req.setAttribute("modo", "editar");
            } else if (idClienteStr != null && !idClienteStr.isEmpty()) {
                // Caso cuando se redirige porque ya existe un registro
                PesoVista pesoVista = new PesoVista();
                pesoVista.setIdCliente(Integer.parseInt(idClienteStr));
                // Obtener nombre del cliente para mostrar
                for (PesoVista cliente : clientes) {
                    if (cliente.getIdCliente() == Integer.parseInt(idClienteStr)) {
                        pesoVista.setNombreCliente(cliente.getNombreCliente());
                        pesoVista.setApellidoCliente(cliente.getApellidoCliente());
                        break;
                    }
                }
                req.setAttribute("peso", pesoVista);
                req.setAttribute("modo", "editar"); // Mostrar como edición aunque no tengamos el ID
            } else {
                req.setAttribute("modo", "crear");
            }

            req.getRequestDispatcher("/WEB-INF/pesos/formulario.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/pesos?error=Error+al+cargar+datos");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/pesos?error=" + e.getMessage());
        }
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
            Peso peso = new Peso();

            if (idStr != null && !idStr.isEmpty()) {
                peso.setId(Integer.parseInt(idStr));
            }

            int idCliente = Integer.parseInt(req.getParameter("idCliente"));
            double pesoActual = Double.parseDouble(req.getParameter("pesoActual"));

            // Verificar si es un nuevo registro y si el cliente ya tiene peso registrado
            if (peso.getId() == 0 && service.existeRegistroPeso(idCliente)) {
                resp.sendRedirect(req.getContextPath() + "/pesos/gestion?idCliente=" + idCliente + "&error=El+cliente+ya+tiene+un+registro+de+peso");
                return;
            }

            peso.setIdCliente(idCliente);
            peso.setPesoInicial(pesoActual);
            peso.setPesoActual(pesoActual);

            boolean resultado;
            if (peso.getId() == 0) {
                resultado = service.crear(peso, rol, usuarioId);
            } else {
                resultado = service.actualizar(peso, rol, usuarioId);
            }

            if (resultado) {
                resp.sendRedirect(req.getContextPath() + "/pesos?success=Registro+guardado");
            } else {
                resp.sendRedirect(req.getContextPath() + "/pesos?error=Error+al+guardar+registro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/pesos?error=Error+en+la+base+de+datos");
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/pesos?error=Datos+inválidos");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/pesos?error=" + e.getMessage());
        }
    }
    }