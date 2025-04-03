package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 27-03-2025

import org.example.modelos.Asistencia;
import org.example.modelos.AsistenciaVista;
import org.example.modelos.UsuarioLogin;

import java.sql.SQLException;
import java.util.List;

public interface AsistenciaService {
    boolean agregar(Asistencia asistencia);
    boolean actualizar(Asistencia asistencia) throws SQLException;
    List<Asistencia> listar();
    List<AsistenciaVista> listarConDetalles();
    List<AsistenciaVista> listarPorUsuario(int idUsuario);
    Asistencia obtener(int idAsistencia);
    AsistenciaVista obtenerConDetalles(int idAsistencia);
    int obtenerIdUsuarioPorCedula(String cedula);
    List<AsistenciaVista> listarPorCedula(String busqueda);
    List<UsuarioLogin> obtenerClientesPorEntrenador(int idEntrenador);
    List<UsuarioLogin> listarTodosUsuarios();
    AsistenciaVista obtenerVistaPorId(int id) throws SQLException;
    Asistencia obtenerPorId(int id) throws SQLException;
    boolean validarClienteDeEntrenador(int idCliente, int idEntrenador) throws SQLException;
    boolean eliminarAsistencia(int idAsistencia) throws SQLException;
}