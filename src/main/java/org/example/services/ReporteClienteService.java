package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 01-04-2025
import org.example.modelos.ReporteCliente;
import java.util.List;

public interface ReporteClienteService {
    List<ReporteCliente> generarReporteClientes();
    List<ReporteCliente> generarReportePorEntrenador(int idEntrenador);
    List<ReporteCliente> generarReportePorCliente(int idCliente);
    List<ReporteCliente> filtrarClientes(String cedula, String nombre, String apellido);
    List<ReporteCliente> filtrarClientesPorEntrenador(int idEntrenador, String cedula, String nombre, String apellido);
}
