package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025

import org.example.modelos.Ejercicio;
import org.example.modelos.EjercicioVista;
import java.sql.SQLException;
import java.util.List;

public interface EjercicioService {
    boolean crear(Ejercicio ejercicio) throws SQLException;
    List<EjercicioVista> listarTodos(String cedulaFiltro) throws SQLException;
    List<EjercicioVista> listarTodosPorNombre(String nombreFiltro) throws SQLException; // Nuevo método
    List<EjercicioVista> listarPorEntrenador(int idEntrenador, String cedulaFiltro) throws SQLException;
    List<EjercicioVista> listarPorEntrenadorYNombre(int idEntrenador, String nombreFiltro) throws SQLException; // Nuevo método
    List<EjercicioVista> listarPorCliente(int idCliente) throws SQLException;
    Ejercicio obtenerPorId(int id) throws SQLException;
    boolean actualizar(Ejercicio ejercicio) throws SQLException;
    boolean eliminar(int id) throws SQLException;
    EjercicioVista obtenerVistaPorId(int id) throws SQLException;
}