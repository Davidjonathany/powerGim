package org.example.services;

import org.example.modelos.ReporteCliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteClienteServiceImpl implements ReporteClienteService {
    private Connection conn;

    public ReporteClienteServiceImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<ReporteCliente> generarReporteClientes() {
        List<ReporteCliente> reportes = new ArrayList<>();
        String sql = "SELECT * FROM ReporteCliente";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ReporteCliente reporte = new ReporteCliente();
                // Mapeo de todos los campos
                reporte.setNombreCliente(rs.getString("nombre_cliente"));
                reporte.setIdCliente(rs.getInt("id_cliente"));
                reporte.setContactoCliente(rs.getString("contacto_cliente"));
                reporte.setCorreo(rs.getString("correo"));
                reporte.setTipoMembresia(rs.getString("tipo_membresia"));
                reporte.setFechaInicio(rs.getString("fecha_inicio"));
                reporte.setFechaVencimiento(rs.getString("fecha_vencimiento"));
                reporte.setDiasRestantes(rs.getInt("dias_restantes"));
                reporte.setEstadoMembresia(rs.getString("estado_membresia"));
                reporte.setNombreEntrenador(rs.getString("nombre_entrenador"));
                reporte.setContactoEntrenador(rs.getString("contacto_entrenador"));
                reporte.setFechaAsistencia(rs.getString("fecha_asistencia"));
                reporte.setTipoEntrenamiento(rs.getString("tipo_entrenamiento"));
                reporte.setEjercicio(rs.getString("ejercicio"));
                reporte.setRepeticiones(rs.getInt("repeticiones"));
                reporte.setPeso(rs.getDouble("peso"));
                reporte.setTiempo(rs.getString("tiempo"));

                reportes.add(reporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el reporte: " + e.getMessage(), e);
        }
        return reportes;
    }
}