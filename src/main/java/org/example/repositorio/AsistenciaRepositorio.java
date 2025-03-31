package org.example.repositorio;
// Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025
import org.example.modelos.Asistencia;
import org.example.modelos.AsistenciaVista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaRepositorio {
    private final Connection connection;

    public AsistenciaRepositorio(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    // Método para búsqueda por cédula (mejorado)
    public List<AsistenciaVista> listarPorCedula(String busqueda) {
        List<AsistenciaVista> asistencias = new ArrayList<>();
        String sql = "SELECT * FROM VistaAsistencias WHERE cedula LIKE ? ORDER BY fecha_asistencia DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + busqueda + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asistencias.add(mapearAsistenciaVista(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar asistencias por cédula: " + e.getMessage());
        }
        return asistencias;
    }

    // Método para listar todas las asistencias con paginación
    public List<AsistenciaVista> listarTodasConPaginacion(int offset, int limit) {
        List<AsistenciaVista> asistencias = new ArrayList<>();
        String sql = "SELECT * FROM VistaAsistencias ORDER BY fecha_asistencia DESC LIMIT ?, ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asistencias.add(mapearAsistenciaVista(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar asistencias con paginación: " + e.getMessage());
        }
        return asistencias;
    }

    // Método para filtrado avanzado
    public List<AsistenciaVista> filtrarAsistencias(String cedula, String tipo, String fecha, int offset, int limit) {
        List<AsistenciaVista> asistencias = new ArrayList<>();
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

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asistencias.add(mapearAsistenciaVista(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar asistencias: " + e.getMessage());
        }
        return asistencias;
    }

    // Métodos existentes (sin cambios)
    public List<AsistenciaVista> listarTodasConDetalles() {
        List<AsistenciaVista> asistencias = new ArrayList<>();
        String sql = "SELECT * FROM VistaAsistencias ORDER BY fecha_asistencia DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                asistencias.add(mapearAsistenciaVista(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar asistencias con detalles: " + e.getMessage());
        }
        return asistencias;
    }

    public List<AsistenciaVista> listarPorUsuario(int idUsuario) {
        List<AsistenciaVista> asistencias = new ArrayList<>();
        String sql = "SELECT * FROM VistaAsistencias WHERE id_usuario = ? ORDER BY fecha_asistencia DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asistencias.add(mapearAsistenciaVista(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar asistencias por usuario: " + e.getMessage());
        }
        return asistencias;
    }

    public boolean agregar(Asistencia asistencia) {
        String sql = "INSERT INTO Asistencias (id_usuario, id_registrador, tipo_asistencia) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, asistencia.getIdUsuario());
            ps.setInt(2, asistencia.getIdRegistrador());
            ps.setString(3, asistencia.getTipoAsistencia());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        asistencia.setIdAsistencia(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar asistencia: " + e.getMessage());
        }
        return false;
    }

    public List<AsistenciaVista> listarPorEntrenador(int idEntrenador) {
        List<AsistenciaVista> asistencias = new ArrayList<>();
        String sql = "SELECT v.* FROM VistaAsistencias v " +
                "JOIN Rutinas r ON v.id_usuario = r.id_cliente " +
                "WHERE r.id_entrenador = ? AND v.rol = 'Cliente' " +
                "ORDER BY v.fecha_asistencia DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asistencias.add(mapearAsistenciaVista(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar asistencias por entrenador: " + e.getMessage());
        }
        return asistencias;
    }

    public boolean validarClienteDeEntrenador(int idCliente, int idEntrenador) {
        String sql = "SELECT COUNT(*) FROM Rutinas WHERE id_cliente = ? AND id_entrenador = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idEntrenador);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Error validando relación entrenador-cliente: " + e.getMessage());
            return false;
        }
    }

    // Métodos para conteo de registros
    public int contarTodasAsistencias() {
        String sql = "SELECT COUNT(*) FROM VistaAsistencias";
        return contarRegistros(sql);
    }

    public int contarAsistenciasFiltradas(String cedula, String tipo, String fecha) {
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

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al contar asistencias filtradas: " + e.getMessage());
            return 0;
        }
    }

    private int contarRegistros(String sql) {
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            System.err.println("Error al contar registros: " + e.getMessage());
            return 0;
        }
    }

    public AsistenciaVista mapearAsistenciaVista(ResultSet rs) throws SQLException {
        AsistenciaVista asistencia = new AsistenciaVista();
        asistencia.setIdAsistencia(rs.getInt("id_asistencia")); // Coincide con la vista
        asistencia.setIdUsuario(rs.getInt("id_usuario")); // Coincide
        asistencia.setNombre(rs.getString("nombre")); // Del usuario
        asistencia.setApellido(rs.getString("apellido")); // Del usuario
        asistencia.setCedula(rs.getString("cedula")); // Del usuario
        asistencia.setRol(rs.getString("rol")); // Del usuario
        asistencia.setFechaAsistencia(rs.getTimestamp("fecha_asistencia")); // Coincide
        asistencia.setTipoAsistencia(rs.getString("tipo_asistencia")); // Coincide
        asistencia.setNombreRegistrador(rs.getString("nombre_registrador")); // Del registrador
        asistencia.setApellidoRegistrador(rs.getString("apellido_registrador")); // Del registrador
        asistencia.setRolRegistrador(rs.getString("rol_registrador")); // Del registrador
        return asistencia;
    }
}