package org.example.repositorio;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

import org.example.modelos.Asistencia;
import org.example.modelos.AsistenciaVista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaRepositorio {

        private Connection connection;

        public AsistenciaRepositorio(Connection connection) {
            this.connection = connection;
        }

    // Método para listar asistencias con datos de usuario (usando la vista)
    public List<AsistenciaVista> listar() {
        List<AsistenciaVista> asistencias = new ArrayList<>();
        String query = "SELECT * FROM VistaAsistencias"; // Usamos la vista

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AsistenciaVista asistencia = new AsistenciaVista();
                asistencia.setIdAsistencia(rs.getInt("id_asistencia"));
                asistencia.setIdCliente(rs.getInt("id_cliente"));
                asistencia.setNombreCliente(rs.getString("nombre_cliente"));
                asistencia.setApellidoCliente(rs.getString("apellido_cliente"));

                // Convertimos java.sql.Date a String
                asistencia.setFechaAsistencia(rs.getDate("fecha_asistencia").toString());

                asistencias.add(asistencia);
            }


        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }

        return asistencias;
    }
    public boolean agregar(Asistencia asistencia) {
            String query = "INSERT INTO Asistencias (id_cliente, fecha_asistencia) VALUES (?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, asistencia.getIdCliente());
                ps.setDate(2, new Date(asistencia.getFechaAsistencia().getTime())); // Convierte java.util.Date a java.sql.Date

                int result = ps.executeUpdate();
                return result > 0;

            } catch (SQLException e) {
                e.printStackTrace(); // Manejo básico de errores
            }

            return false;
        }

        // Método para obtener asistencia por ID
        public Asistencia porId(int idAsistencia) {
            Asistencia asistencia = null;
            String query = "SELECT * FROM Asistencias WHERE id = ?";

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, idAsistencia);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    asistencia = new Asistencia();
                    asistencia.setIdAsistencia(rs.getInt("id"));
                    asistencia.setIdCliente(rs.getInt("id_cliente"));
                    asistencia.setFechaAsistencia(rs.getDate("fecha_asistencia"));
                }
            } catch (SQLException e) {
                e.printStackTrace();  // Manejo de errores
            }

            return asistencia;
        }

    public List<Asistencia> listarAsistenciasNormales() {
        List<Asistencia> asistencias = new ArrayList<>();
        String query = "SELECT * FROM Asistencias"; // Consulta a la tabla normal

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Asistencia asistencia = new Asistencia();
                asistencia.setIdAsistencia(rs.getInt("id_asistencia"));
                asistencia.setIdCliente(rs.getInt("id_cliente"));
                asistencia.setFechaAsistencia(rs.getDate("fecha_asistencia"));
                asistencias.add(asistencia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return asistencias;
    }

}

