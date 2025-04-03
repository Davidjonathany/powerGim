package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025

import org.example.modelos.Ejercicio;
import org.example.modelos.EjercicioVista;
import org.example.repositorio.EjercicioRepositorio;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EjercicioServiceImpl implements EjercicioService {
    private final EjercicioRepositorio repositorio;

    public EjercicioServiceImpl(Connection conn) {
        this.repositorio = new EjercicioRepositorio(conn);
    }

    @Override
    public boolean crear(Ejercicio ejercicio) throws SQLException {
        return repositorio.crear(ejercicio);
    }

    @Override
    public List<EjercicioVista> listarTodos(String cedulaFiltro) throws SQLException {
        return repositorio.listarTodos(cedulaFiltro);
    }

    @Override
    public List<EjercicioVista> listarTodosPorNombre(String nombreFiltro) throws SQLException {
        return repositorio.listarTodosPorNombre(nombreFiltro);
    }

    @Override
    public List<EjercicioVista> listarPorEntrenador(int idEntrenador, String cedulaFiltro) throws SQLException {
        return repositorio.listarPorEntrenador(idEntrenador, cedulaFiltro);
    }

    @Override
    public List<EjercicioVista> listarPorEntrenadorYNombre(int idEntrenador, String nombreFiltro) throws SQLException {
        return repositorio.listarPorEntrenadorYNombre(idEntrenador, nombreFiltro);
    }

    @Override
    public List<EjercicioVista> listarPorCliente(int idCliente) throws SQLException {
        return repositorio.listarPorCliente(idCliente);
    }

    @Override
    public Ejercicio obtenerPorId(int id) throws SQLException {
        return repositorio.obtenerPorId(id);
    }

    @Override
    public EjercicioVista obtenerVistaPorId(int id) throws SQLException {
        return repositorio.obtenerVistaPorId(id);
    }

    @Override
    public boolean actualizar(Ejercicio ejercicio) throws SQLException {
        return repositorio.actualizar(ejercicio);
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        return repositorio.eliminar(id);
    }
}

