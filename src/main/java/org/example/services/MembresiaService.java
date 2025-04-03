package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025
import org.example.modelos.Membresia;
import org.example.modelos.MembresiaVista;

import java.sql.SQLException;
import java.util.List;

public interface MembresiaService {
    List<MembresiaVista> listarTodas() throws SQLException;
    List<MembresiaVista> listarPorCliente(int idCliente) throws SQLException;
    List<MembresiaVista> listarPorEntrenador(int idEntrenador) throws SQLException;
    Membresia obtenerPorId(int id) throws SQLException;
    boolean crear(Membresia membresia) throws SQLException;
    boolean actualizar(Membresia membresia) throws SQLException;
    boolean eliminar(int id) throws SQLException;
    MembresiaVista obtenerVistaPorId(int id) throws SQLException;
    List<MembresiaVista> listarPorCedula(String cedula) throws SQLException;
    List<MembresiaVista> listarPorEntrenadorYCedula(int idEntrenador, String cedula) throws SQLException;
    List<MembresiaVista> listarPorNombreApellido(String nombre, String apellido) throws SQLException;
    List<MembresiaVista> listarPorEntrenadorYNombreApellido(int idEntrenador, String nombre, String apellido) throws SQLException;
}