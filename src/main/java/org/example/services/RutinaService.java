package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 29-03-2025

import org.example.modelos.Rutina;
import org.example.modelos.VistaRutina;
import java.util.List;

public interface RutinaService {
    // Operaciones CRUD básicas
    boolean crear(Rutina rutina);
    boolean actualizar(Rutina rutina);
    boolean eliminar(int idRutina);
    Rutina obtenerPorId(int idRutina);

    // Operaciones con la vista
    List<VistaRutina> listarTodas();
    List<VistaRutina> listarTodasConPaginacion(int offset, int limit); // Método añadido
    List<VistaRutina> filtrarPorTipoEntrenamiento(String tipo);
    List<VistaRutina> listarPorCliente(int idCliente);
    List<VistaRutina> listarPorEntrenador(int idEntrenador);
    VistaRutina obtenerVistaPorId(int idRutina);
    List<VistaRutina> listarPorEntrenadorYFiltro(int idEntrenador, String tipoEntrenamiento);
    List<VistaRutina> filtrarRutinasAvanzado(String nombreCliente, String nombreEntrenador,
                                             String tipoEntrenamiento, int offset, int limit);

    VistaRutina obtenerVistaRutinaCompletaPorId(int idRutina);
    // Métodos para conteo y paginación
    int contarTotalRutinas(); // Método añadido
    int contarRutinasFiltradas(String nombreCliente, String nombreEntrenador,
                               String tipoEntrenamiento);

    // Validaciones
    boolean existeRelacionClienteEntrenador(int idCliente, int idEntrenador);
}