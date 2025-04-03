package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 01-04-2025

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
        return ejecutarConsulta("SELECT * FROM ReporteClientesDetallado", null);
    }

    @Override
    public List<ReporteCliente> generarReportePorEntrenador(int idEntrenador) {
        return ejecutarConsulta("SELECT * FROM ReporteClientesDetallado WHERE id_entrenador = ?", idEntrenador);
    }

    @Override
    public List<ReporteCliente> generarReportePorCliente(int idCliente) {
        return ejecutarConsulta("SELECT * FROM ReporteClientesDetallado WHERE id_cliente = ?", idCliente);
    }

    @Override
    public List<ReporteCliente> filtrarClientes(String cedula, String nombre, String apellido) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ReporteClientesDetallado WHERE 1=1");
        List<String> parametros = new ArrayList<>();

        if (cedula != null && !cedula.isEmpty()) {
            sql.append(" AND cedula LIKE ?");
            parametros.add("%" + cedula + "%");
        }
        if (nombre != null && !nombre.isEmpty()) {
            sql.append(" AND nombre LIKE ?");
            parametros.add("%" + nombre + "%");
        }
        if (apellido != null && !apellido.isEmpty()) {
            sql.append(" AND apellido LIKE ?");
            parametros.add("%" + apellido + "%");
        }

        return ejecutarConsultaFiltrada(sql.toString(), parametros);
    }

    @Override
    public List<ReporteCliente> filtrarClientesPorEntrenador(int idEntrenador, String cedula, String nombre, String apellido) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ReporteClientesDetallado WHERE id_entrenador = ?");
        List<String> parametros = new ArrayList<>();
        parametros.add(String.valueOf(idEntrenador));

        if (cedula != null && !cedula.isEmpty()) {
            sql.append(" AND cedula LIKE ?");
            parametros.add("%" + cedula + "%");
        }
        if (nombre != null && !nombre.isEmpty()) {
            sql.append(" AND nombre LIKE ?");
            parametros.add("%" + nombre + "%");
        }
        if (apellido != null && !apellido.isEmpty()) {
            sql.append(" AND apellido LIKE ?");
            parametros.add("%" + apellido + "%");
        }

        return ejecutarConsultaFiltrada(sql.toString(), parametros);
    }

    private List<ReporteCliente> ejecutarConsulta(String sql, Integer parametro) {
        List<ReporteCliente> reportes = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (parametro != null) {
                stmt.setInt(1, parametro);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reportes.add(mapearReporte(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el reporte: " + e.getMessage(), e);
        }
        return reportes;
    }

    private List<ReporteCliente> ejecutarConsultaFiltrada(String sql, List<String> parametros) {
        List<ReporteCliente> reportes = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < parametros.size(); i++) {
                stmt.setString(i + 1, parametros.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reportes.add(mapearReporte(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al filtrar clientes: " + e.getMessage(), e);
        }
        return reportes;
    }

    private ReporteCliente mapearReporte(ResultSet rs) throws SQLException {
        ReporteCliente reporte = new ReporteCliente();

        // Mapeo de todos los campos del reporte
        reporte.setIdCliente(rs.getInt("id_cliente"));
        reporte.setNombre(rs.getString("nombre") != null ? rs.getString("nombre") : "");
        reporte.setApellido(rs.getString("apellido") != null ? rs.getString("apellido") : "");
        reporte.setUsuario(rs.getString("usuario") != null ? rs.getString("usuario") : "");
        reporte.setCorreo(rs.getString("correo") != null ? rs.getString("correo") : "");
        reporte.setTelefono(rs.getString("telefono") != null ? rs.getString("telefono") : "");
        reporte.setCedula(rs.getString("cedula") != null ? rs.getString("cedula") : "");
        reporte.setTipoMembresia(rs.getString("tipo_membresia") != null ? rs.getString("tipo_membresia") : "");
        reporte.setFechaInicio(rs.getString("fecha_inicio") != null ? rs.getString("fecha_inicio") : "");
        reporte.setFechaVencimiento(rs.getString("fecha_vencimiento") != null ? rs.getString("fecha_vencimiento") : "");
        reporte.setDiasRestantes(rs.getInt("dias_restantes"));
        reporte.setEstadoMembresia(rs.getString("estado_membresia") != null ? rs.getString("estado_membresia") : "");
        reporte.setPesoInicial(rs.getDouble("peso_inicial"));
        reporte.setPesoActual(rs.getDouble("peso_actual"));
        reporte.setProgresoPeso(rs.getDouble("progreso_peso"));
        reporte.setUltimaActualizacionPeso(rs.getString("ultima_actualizacion_peso") != null ? rs.getString("ultima_actualizacion_peso") : "");
        reporte.setTotalAsistencias(rs.getInt("total_asistencias"));
        reporte.setUltimaAsistencia(rs.getString("ultima_asistencia") != null ? rs.getString("ultima_asistencia") : "");
        reporte.setAsistenciasUltimaSemana(rs.getInt("asistencias_ultima_semana"));
        reporte.setAsistenciasUltimoMes(rs.getInt("asistencias_ultimo_mes"));
        reporte.setTotalRutinas(rs.getInt("total_rutinas"));
        reporte.setEntrenadores(rs.getString("entrenadores") != null ? rs.getString("entrenadores") : "");

        return reporte;
    }
}