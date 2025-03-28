package org.example.repositorio;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {
    // Métodos para manejar las operaciones CRUD de Usuario
    List<T> listar() throws SQLException;
    T porId(int id) throws SQLException;
    void guardar(T t) throws SQLException;
    void eliminar(int id) throws SQLException;
    void actualizar(int id, T t) throws SQLException;
    boolean usuarioExistePorCedula(String cedula) throws SQLException;
    T buscarPorCedula(String cedula) throws SQLException;  // Método actualizado para buscar por cédula
}
