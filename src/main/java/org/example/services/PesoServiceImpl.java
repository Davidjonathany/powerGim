package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 01-04-2025

import org.example.modelos.Peso;
import org.example.modelos.PesoVista;
import org.example.repositorio.PesoRepositorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PesoServiceImpl implements PesoService {
    private final PesoRepositorio repositorio;

    public PesoServiceImpl(Connection conn) {
        this.repositorio = new PesoRepositorio(conn);
    }

    @Override
    public boolean crear(Peso peso, String rolUsuario, int idUsuario) throws SQLException {
        if ("Cliente".equals(rolUsuario) && peso.getIdCliente() != idUsuario) {
            throw new SQLException("No tienes permisos para crear registros de otros clientes");
        }
        return repositorio.crear(peso);
    }

    @Override
    public List<PesoVista> listarTodos(String filtro) throws SQLException {
        return repositorio.listarTodos(filtro);
    }

    @Override
    public List<PesoVista> listarPorEntrenador(int idEntrenador, String filtro) throws SQLException {
        return repositorio.listarPorEntrenador(idEntrenador, filtro);
    }

    @Override
    public List<PesoVista> listarPorCliente(int idCliente) throws SQLException {
        return repositorio.listarPorCliente(idCliente);
    }

    @Override
    public Peso obtenerPorId(int id, String rolUsuario, int idUsuario) throws SQLException {
        Peso peso = repositorio.obtenerPorId(id);
        validarPermisos(peso, rolUsuario, idUsuario);
        return peso;
    }

    @Override
    public PesoVista obtenerVistaPorId(int id, String rolUsuario, int idUsuario) throws SQLException {
        PesoVista vista = repositorio.obtenerVistaPorId(id);
        validarPermisosVista(vista, rolUsuario, idUsuario);
        return vista;
    }

    @Override
    public boolean actualizar(Peso peso, String rolUsuario, int idUsuario) throws SQLException {
        Peso existente = repositorio.obtenerPorId(peso.getId());
        validarPermisos(existente, rolUsuario, idUsuario);

        // Solo actualizamos el peso actual y la fecha de registro se actualiza automáticamente
        existente.setPesoActual(peso.getPesoActual());
        return repositorio.actualizar(existente);
    }

    @Override
    public boolean eliminar(int id, String rolUsuario, int idUsuario) throws SQLException {
        if (!"Administrador".equals(rolUsuario)) {
            throw new SQLException("Solo los administradores pueden eliminar registros");
        }
        return repositorio.eliminar(id);
    }

    private void validarPermisos(Peso peso, String rolUsuario, int idUsuario) throws SQLException {
        if (peso == null) return;

        if ("Administrador".equals(rolUsuario)) return;

        if ("Entrenador".equals(rolUsuario)) {
            if (!repositorio.verificarRelacionEntrenadorCliente(idUsuario, peso.getIdCliente())) {
                throw new SQLException("No tienes permisos para acceder a este registro");
            }
            return;
        }

        if ("Cliente".equals(rolUsuario) && peso.getIdCliente() != idUsuario) {
            throw new SQLException("No tienes permisos para acceder a este registro");
        }
    }

    private void validarPermisosVista(PesoVista vista, String rolUsuario, int idUsuario) throws SQLException {
        if (vista == null) return;

        if ("Administrador".equals(rolUsuario)) return;

        if ("Entrenador".equals(rolUsuario)) {
            if (!repositorio.verificarRelacionEntrenadorCliente(idUsuario, vista.getIdCliente())) {
                throw new SQLException("No tienes permisos para acceder a este registro");
            }
            return;
        }

        if ("Cliente".equals(rolUsuario) && vista.getIdCliente() != idUsuario) {
            throw new SQLException("No tienes permisos para acceder a este registro");
        }
    }

    @Override
    public List<PesoVista> listarTodosClientes() throws SQLException {
        return repositorio.listarTodosClientes();
    }

    @Override
    public List<PesoVista> listarClientesPorEntrenador(int idEntrenador) throws SQLException {
        return repositorio.listarClientesPorEntrenador(idEntrenador);
    }

    @Override
    public boolean existeRegistroPeso(int idCliente) throws SQLException {
        return repositorio.existeRegistroPeso(idCliente);
    }
}