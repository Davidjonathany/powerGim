package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 01-04-2025

import org.example.modelos.Peso;
import org.example.modelos.PesoVista;
import java.sql.SQLException;
import java.util.List;

public interface PesoService {
    boolean crear(Peso peso, String rolUsuario, int idUsuario) throws SQLException;
    List<PesoVista> listarTodos(String filtro) throws SQLException;
    List<PesoVista> listarPorEntrenador(int idEntrenador, String filtro) throws SQLException;
    List<PesoVista> listarPorCliente(int idCliente) throws SQLException;
    Peso obtenerPorId(int id, String rolUsuario, int idUsuario) throws SQLException;
    PesoVista obtenerVistaPorId(int id, String rolUsuario, int idUsuario) throws SQLException;
    boolean actualizar(Peso peso, String rolUsuario, int idUsuario) throws SQLException;
    boolean eliminar(int id, String rolUsuario, int idUsuario) throws SQLException;
    List<PesoVista> listarTodosClientes() throws SQLException;
    List<PesoVista> listarClientesPorEntrenador(int idEntrenador) throws SQLException;
    boolean existeRegistroPeso(int idCliente) throws SQLException;
}