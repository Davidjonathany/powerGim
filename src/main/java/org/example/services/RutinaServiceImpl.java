package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 29-03-2025

import org.example.modelos.Rutina;
import org.example.modelos.VistaRutina;
import org.example.repositorio.RutinaRepositorio;
import org.example.repositorio.UsuarioRepositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RutinaServiceImpl implements RutinaService {
    private final RutinaRepositorio repositorio;
    private final RutinaRepositorio rutinaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public RutinaServiceImpl(Connection connection) {
        this.repositorio = new RutinaRepositorio(connection);
        this.rutinaRepositorio = new RutinaRepositorio(connection);
        this.usuarioRepositorio = new UsuarioRepositorio(connection);
    }

    @Override
    public boolean crear(Rutina rutina) {
        // Validación básica
        if (rutina.getTipoEntrenamiento() == null || rutina.getTipoEntrenamiento().trim().isEmpty()) {
            System.err.println("El tipo de entrenamiento no puede estar vacío");
            return false;
        }

        if (rutina.getIdCliente() <= 0 || rutina.getIdEntrenador() <= 0) {
            System.err.println("IDs de cliente o entrenador inválidos");
            return false;
        }

        return repositorio.crear(rutina);
    }

    @Override
    public boolean actualizar(Rutina rutina) {
        // Validación básica
        if (rutina.getId() <= 0) {
            System.err.println("ID de rutina inválido");
            return false;
        }

        if (rutina.getTipoEntrenamiento() == null || rutina.getTipoEntrenamiento().trim().isEmpty()) {
            System.err.println("El tipo de entrenamiento no puede estar vacío");
            return false;
        }

        return repositorio.actualizar(rutina);
    }

    @Override
    public boolean eliminar(int idRutina) {
        if (idRutina <= 0) {
            System.err.println("ID de rutina inválido");
            return false;
        }

        return repositorio.eliminar(idRutina);
    }

    @Override
    public Rutina obtenerPorId(int idRutina) {
        if (idRutina <= 0) {
            System.err.println("ID de rutina inválido");
            return null;
        }

        return repositorio.obtenerPorId(idRutina);
    }

    @Override
    public List<VistaRutina> listarTodas() {
        return repositorio.listarTodas();
    }

    @Override
    public List<VistaRutina> filtrarPorTipoEntrenamiento(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            System.err.println("Tipo de entrenamiento no puede estar vacío");
            return repositorio.listarTodas();
        }

        return repositorio.filtrarPorTipoEntrenamiento(tipo);
    }

    @Override
    public List<VistaRutina> listarPorCliente(int idCliente) {
        try {
            if (!usuarioRepositorio.esCliente(idCliente)) {
                throw new IllegalArgumentException("El ID proporcionado no corresponde a un cliente");
            }
            return rutinaRepositorio.listarPorCliente(idCliente);
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar rol de cliente", e);
        }
    }

    @Override
    public List<VistaRutina> listarPorEntrenador(int idEntrenador) {
        try {
            if (!usuarioRepositorio.esEntrenador(idEntrenador)) {
                throw new IllegalArgumentException("El ID proporcionado no corresponde a un entrenador");
            }
            return rutinaRepositorio.listarPorEntrenador(idEntrenador);
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar rol de entrenador", e);
        }
    }
    @Override
    public VistaRutina obtenerVistaPorId(int idRutina) {
        if (idRutina <= 0) {
            System.err.println("ID de rutina inválido");
            return null;
        }

        return repositorio.obtenerVistaPorId(idRutina);
    }

    @Override
    public List<VistaRutina> listarPorEntrenadorYFiltro(int idEntrenador, String tipoEntrenamiento) {
        if (idEntrenador <= 0) {
            System.err.println("ID de entrenador inválido");
            return List.of();
        }

        if (tipoEntrenamiento == null || tipoEntrenamiento.trim().isEmpty()) {
            return listarPorEntrenador(idEntrenador);
        }

        return repositorio.listarPorEntrenadorYFiltro(idEntrenador, tipoEntrenamiento);
    }

    @Override
    public List<VistaRutina> filtrarRutinasAvanzado(String nombreCliente, String nombreEntrenador,
                                                    String tipoEntrenamiento, int offset, int limit) {
        // Validación de parámetros de paginación
        if (offset < 0 || limit <= 0) {
            System.err.println("Parámetros de paginación inválidos");
            return List.of();
        }

        return repositorio.filtrarRutinasAvanzado(nombreCliente, nombreEntrenador,
                tipoEntrenamiento, offset, limit);
    }
    @Override
    public int contarRutinasFiltradas(String nombreCliente, String nombreEntrenador,
                                      String tipoEntrenamiento) {
        return repositorio.contarRutinasFiltradas(nombreCliente, nombreEntrenador, tipoEntrenamiento);
    }

    @Override
    public boolean existeRelacionClienteEntrenador(int idCliente, int idEntrenador) {
        if (idCliente <= 0 || idEntrenador <= 0) {
            System.err.println("IDs de cliente o entrenador inválidos");
            return false;
        }

        return repositorio.existeRelacionClienteEntrenador(idCliente, idEntrenador);
    }

    // Métodos adicionales para paginación
    @Override
    public List<VistaRutina> listarTodasConPaginacion(int offset, int limit) {
        if (offset < 0 || limit <= 0) {
            System.err.println("Parámetros de paginación inválidos");
            return List.of();
        }
        return repositorio.listarTodasConPaginacion(offset, limit);
    }

    @Override
    public int contarTotalRutinas() {
        return repositorio.contarTotalRutinas();
    }

    @Override
    public VistaRutina obtenerVistaRutinaCompletaPorId(int idRutina) {
        if (idRutina <= 0) {
            System.err.println("ID de rutina inválido");
            return null;
        }
        return repositorio.obtenerVistaRutinaCompletaPorId(idRutina);
    }

}