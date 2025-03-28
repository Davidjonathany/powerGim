package org.example.services;

import org.example.modelos.AsistenciaVista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AsistenciaVistaServiceImplement implements AsistenciaVistaService {
    private Connection conn;

    public AsistenciaVistaServiceImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<AsistenciaVista> listarAsistenciasVista() {
        List<AsistenciaVista> asistencias = new ArrayList<>();
        String sql = "SELECT * FROM VistaAsistencias"; // Consulta a la vista

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AsistenciaVista asistencia = new AsistenciaVista();
                asistencia.setIdAsistencia(rs.getInt("id_asistencia"));
                asistencia.setIdCliente(rs.getInt("id_cliente"));
                asistencia.setNombreCliente(rs.getString("nombre"));
                asistencia.setApellidoCliente(rs.getString("apellido"));
                asistencia.setFechaAsistencia(rs.getString("fecha_asistencia"));
                asistencias.add(asistencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asistencias;
    }
}