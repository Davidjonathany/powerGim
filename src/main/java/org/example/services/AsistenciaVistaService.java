package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 27-03-2025

import org.example.modelos.AsistenciaVista;
import java.util.List;

public interface AsistenciaVistaService {
    List<AsistenciaVista> listarTodas();
    List<AsistenciaVista> listarTodasConPaginacion(int offset, int limit);
    List<AsistenciaVista> listarPorUsuario(int idUsuario);
    List<AsistenciaVista> listarPorTipo(String tipoAsistencia);
    List<AsistenciaVista> listarPorRegistrador(int idRegistrador);
    AsistenciaVista obtenerPorId(int idAsistencia);
    List<AsistenciaVista> listarPorCedula(String busqueda);
    List<AsistenciaVista> listarPorEntrenador(int idEntrenador);
    List<AsistenciaVista> listarPorFecha(String fecha);
    List<AsistenciaVista> listarPorCedulaYEntrenador(String cedula, int idEntrenador);
    List<AsistenciaVista> listarPorTipoYEntrenador(String tipo, int idEntrenador);
    List<AsistenciaVista> listarPorFechaYEntrenador(String fecha, int idEntrenador);
    List<AsistenciaVista> filtrarAsistencias(String cedula, String tipo, String fecha, int offset, int limit);
    int contarAsistencias(String cedula, String tipo, String fecha);
    List<AsistenciaVista> listarTodosUsuariosCompletos();
    List<AsistenciaVista> listarClientesPorEntrenador(int idEntrenador);
}