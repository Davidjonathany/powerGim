package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 27-03-2025

import org.example.modelos.AsistenciaVista;
import org.example.repositorio.AsistenciaRepositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaVistaServiceImplement implements AsistenciaVistaService {
    private final AsistenciaRepositorio repositorio;

    public AsistenciaVistaServiceImplement(Connection conn) {
        this.repositorio = new AsistenciaRepositorio(conn);
    }

    @Override
    public List<AsistenciaVista> listarTodas() {
        return repositorio.listarTodasConDetalles();
    }

    @Override
    public List<AsistenciaVista> listarTodasConPaginacion(int offset, int limit) {
        String sql = "SELECT * FROM VistaAsistencias ORDER BY fecha_asistencia DESC LIMIT ?, ?";
        return ejecutarConsultaConDosParametros(sql, offset, limit);
    }

    @Override
    public List<AsistenciaVista> listarPorUsuario(int idUsuario) {
        String sql = "SELECT * FROM VistaAsistencias WHERE id_usuario = ? ORDER BY fecha_asistencia DESC";
        return ejecutarConsultaConParametro(sql, idUsuario);
    }

    @Override
    public List<AsistenciaVista> listarPorTipo(String tipoAsistencia) {
        String sql = "SELECT * FROM VistaAsistencias WHERE tipo_asistencia = ? ORDER BY fecha_asistencia DESC";
        return ejecutarConsultaConParametro(sql, tipoAsistencia);
    }

    @Override
    public List<AsistenciaVista> listarPorRegistrador(int idRegistrador) {
        String sql = "SELECT v.* FROM VistaAsistencias v " +
                "JOIN Asistencias a ON v.id_asistencia = a.id " +
                "WHERE a.id_registrador = ? ORDER BY v.fecha_asistencia DESC";
        return ejecutarConsultaConParametro(sql, idRegistrador);
    }

    @Override
    public List<AsistenciaVista> listarPorEntrenador(int idEntrenador) {
        String sql = "SELECT v.* FROM VistaAsistencias v " +
                "JOIN Rutinas r ON v.id_usuario = r.id_cliente " +
                "WHERE r.id_entrenador = ? AND v.rol = 'Cliente' " +
                "ORDER BY v.fecha_asistencia DESC";
        return ejecutarConsultaConParametro(sql, idEntrenador);
    }

    @Override
    public AsistenciaVista obtenerPorId(int idAsistencia) {
        String sql = "SELECT * FROM VistaAsistencias WHERE id_asistencia = ?";
        try (PreparedStatement ps = repositorio.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idAsistencia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return repositorio.mapearAsistenciaVista(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener asistencia por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<AsistenciaVista> listarPorCedula(String cedula) {
        String sql = "SELECT * FROM VistaAsistencias WHERE cedula LIKE ? ORDER BY fecha_asistencia DESC";
        return ejecutarConsultaConParametro(sql, "%" + cedula + "%");
    }

    @Override
    public List<AsistenciaVista> listarPorFecha(String fecha) {
        String sql = "SELECT * FROM VistaAsistencias WHERE DATE(fecha_asistencia) = ? ORDER BY fecha_asistencia DESC";
        return ejecutarConsultaConParametro(sql, fecha);
    }

    @Override
    public List<AsistenciaVista> listarPorCedulaYEntrenador(String cedula, int idEntrenador) {
        String sql = "SELECT v.* FROM VistaAsistencias v " +
                "JOIN Rutinas r ON v.id_usuario = r.id_cliente " +
                "WHERE v.cedula LIKE ? AND r.id_entrenador = ? AND v.rol = 'Cliente' " +
                "ORDER BY v.fecha_asistencia DESC";
        return ejecutarConsultaConDosParametros(sql, "%" + cedula + "%", idEntrenador);
    }

    @Override
    public List<AsistenciaVista> listarPorTipoYEntrenador(String tipo, int idEntrenador) {
        String sql = "SELECT v.* FROM VistaAsistencias v " +
                "JOIN Rutinas r ON v.id_usuario = r.id_cliente " +
                "WHERE v.tipo_asistencia = ? AND r.id_entrenador = ? AND v.rol = 'Cliente' " +
                "ORDER BY v.fecha_asistencia DESC";
        return ejecutarConsultaConDosParametros(sql, tipo, idEntrenador);
    }

    @Override
    public List<AsistenciaVista> listarPorFechaYEntrenador(String fecha, int idEntrenador) {
        String sql = "SELECT v.* FROM VistaAsistencias v " +
                "JOIN Rutinas r ON v.id_usuario = r.id_cliente " +
                "WHERE DATE(v.fecha_asistencia) = ? AND r.id_entrenador = ? AND v.rol = 'Cliente' " +
                "ORDER BY v.fecha_asistencia DESC";
        return ejecutarConsultaConDosParametros(sql, fecha, idEntrenador);
    }

    @Override
    public List<AsistenciaVista> filtrarAsistencias(String cedula, String tipo, String fecha, int offset, int limit) {
        StringBuilder sql = new StringBuilder("SELECT * FROM VistaAsistencias WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (cedula != null && !cedula.isEmpty()) {
            sql.append("AND cedula LIKE ? ");
            params.add("%" + cedula + "%");
        }
        if (tipo != null && !tipo.isEmpty()) {
            sql.append("AND tipo_asistencia = ? ");
            params.add(tipo);
        }
        if (fecha != null && !fecha.isEmpty()) {
            sql.append("AND DATE(fecha_asistencia) = ? ");
            params.add(fecha);
        }

        sql.append("ORDER BY fecha_asistencia DESC LIMIT ?, ?");
        params.add(offset);
        params.add(limit);

        try (PreparedStatement ps = repositorio.getConnection().prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<AsistenciaVista> asistencias = new ArrayList<>();
                while (rs.next()) {
                    asistencias.add(repositorio.mapearAsistenciaVista(rs));
                }
                return asistencias;
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar asistencias: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public int contarAsistencias(String cedula, String tipo, String fecha) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM VistaAsistencias WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (cedula != null && !cedula.isEmpty()) {
            sql.append("AND cedula LIKE ? ");
            params.add("%" + cedula + "%");
        }
        if (tipo != null && !tipo.isEmpty()) {
            sql.append("AND tipo_asistencia = ? ");
            params.add(tipo);
        }
        if (fecha != null && !fecha.isEmpty()) {
            sql.append("AND DATE(fecha_asistencia) = ? ");
            params.add(fecha);
        }

        try (PreparedStatement ps = repositorio.getConnection().prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al contar asistencias: " + e.getMessage());
            return 0;
        }
    }

    private List<AsistenciaVista> ejecutarConsultaConParametro(String sql, Object parametro) {
        List<AsistenciaVista> asistencias = new ArrayList<>();
        try (Connection conn = repositorio.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (parametro instanceof String) {
                ps.setString(1, (String) parametro);
            } else if (parametro instanceof Integer) {
                ps.setInt(1, (Integer) parametro);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asistencias.add(repositorio.mapearAsistenciaVista(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en consulta con parámetro: " + e.getMessage());
            e.printStackTrace();
        }
        return asistencias;
    }

    private List<AsistenciaVista> ejecutarConsultaConDosParametros(String sql, Object param1, Object param2) {
        List<AsistenciaVista> asistencias = new ArrayList<>();
        try (Connection conn = repositorio.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Primer parámetro
            if (param1 instanceof String) {
                ps.setString(1, (String) param1);
            } else if (param1 instanceof Integer) {
                ps.setInt(1, (Integer) param1);
            }

            // Segundo parámetro
            if (param2 instanceof String) {
                ps.setString(2, (String) param2);
            } else if (param2 instanceof Integer) {
                ps.setInt(2, (Integer) param2);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asistencias.add(repositorio.mapearAsistenciaVista(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en consulta con dos parámetros: " + e.getMessage());
            e.printStackTrace();
        }
        return asistencias;
    }

}
