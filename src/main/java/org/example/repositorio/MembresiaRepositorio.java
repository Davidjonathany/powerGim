package org.example.repositorio;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025
import org.example.modelos.Membresia;
import org.example.modelos.MembresiaVista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembresiaRepositorio {
    private final Connection conn;

    public MembresiaRepositorio(Connection conn) {
        this.conn = conn;
    }

    public List<MembresiaVista> listarTodas() throws SQLException {
        String sql = "SELECT m.*, u.nombre AS cliente_nombre, u.apellido AS cliente_apellido, u.cedula AS cliente_cedula " +
                "FROM Membresias m JOIN Usuarios u ON m.id_cliente = u.id";
        return ejecutarConsultaConVista(sql);
    }

    public List<MembresiaVista> listarPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT m.*, u.nombre AS cliente_nombre, u.apellido AS cliente_apellido, u.cedula AS cliente_cedula " +
                "FROM Membresias m JOIN Usuarios u ON m.id_cliente = u.id " +
                "WHERE m.id_cliente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            return ejecutarConsultaConVista(stmt);
        }
    }

    public List<MembresiaVista> listarPorEntrenador(int idEntrenador) throws SQLException {
        String sql = "SELECT m.*, u.nombre AS cliente_nombre, u.apellido AS cliente_apellido, u.cedula AS cliente_cedula " +
                "FROM Membresias m " +
                "JOIN Usuarios u ON m.id_cliente = u.id " +
                "WHERE u.id IN (SELECT DISTINCT r.id_cliente FROM Rutinas r WHERE r.id_entrenador = ?) " +
                "ORDER BY m.fecha_vencimiento DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntrenador);
            try (ResultSet rs = stmt.executeQuery()) {
                return mapearResultadosVista(rs);
            }
        }
    }

    public Membresia obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Membresias WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMembresia(rs);
                }
            }
        }
        return null;
    }

    public boolean crear(Membresia membresia) throws SQLException {
        String sql = "INSERT INTO Membresias (id_cliente, tipo, fecha_inicio, fecha_vencimiento, dias_restantes, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, membresia.getIdCliente());
            stmt.setString(2, membresia.getTipo());
            stmt.setDate(3, new java.sql.Date(membresia.getFechaInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(membresia.getFechaVencimiento().getTime()));
            stmt.setInt(5, membresia.getDiasRestantes());
            stmt.setString(6, membresia.getEstado());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    membresia.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    public boolean actualizar(Membresia membresia) throws SQLException {
        String sql = "UPDATE Membresias SET id_cliente = ?, tipo = ?, fecha_inicio = ?, " +
                "fecha_vencimiento = ?, dias_restantes = ?, estado = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, membresia.getIdCliente());
            stmt.setString(2, membresia.getTipo());
            stmt.setDate(3, new java.sql.Date(membresia.getFechaInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(membresia.getFechaVencimiento().getTime()));
            stmt.setInt(5, membresia.getDiasRestantes());
            stmt.setString(6, membresia.getEstado());
            stmt.setInt(7, membresia.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Membresias WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private List<MembresiaVista> ejecutarConsultaConVista(String sql) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return mapearResultadosVista(rs);
        }
    }

    private List<MembresiaVista> ejecutarConsultaConVista(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            return mapearResultadosVista(rs);
        }
    }

    public MembresiaVista obtenerVistaPorId(int id) throws SQLException {
        String sql = "SELECT m.*, u.nombre AS cliente_nombre, u.apellido AS cliente_apellido, u.cedula AS cliente_cedula " +
                "FROM Membresias m JOIN Usuarios u ON m.id_cliente = u.id " +
                "WHERE m.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMembresiaVista(rs);
                }
            }
        }
        return null;
    }
    public List<MembresiaVista> listarPorCedula(String cedula) throws SQLException {
        String sql = "SELECT m.*, u.nombre AS cliente_nombre, u.apellido AS cliente_apellido, u.cedula AS cliente_cedula " +
                "FROM Membresias m JOIN Usuarios u ON m.id_cliente = u.id " +
                "WHERE u.cedula LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + cedula + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                return mapearResultadosVista(rs);
            }
        }
    }

    public List<MembresiaVista> listarPorEntrenadorYCedula(int idEntrenador, String cedula) throws SQLException {
        String sql = "SELECT m.*, u.nombre AS cliente_nombre, u.apellido AS cliente_apellido, u.cedula AS cliente_cedula " +
                "FROM Membresias m JOIN Usuarios u ON m.id_cliente = u.id " +
                "JOIN Rutinas r ON u.id = r.id_cliente " +
                "WHERE r.id_entrenador = ? AND u.cedula LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntrenador);
            stmt.setString(2, "%" + cedula + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                return mapearResultadosVista(rs);
            }
        }
    }

    private Membresia mapearMembresia(ResultSet rs) throws SQLException {
        Membresia membresia = new Membresia();
        membresia.setId(rs.getInt("id"));
        membresia.setIdCliente(rs.getInt("id_cliente"));
        membresia.setTipo(rs.getString("tipo"));
        membresia.setFechaInicio(rs.getDate("fecha_inicio"));
        membresia.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
        membresia.setDiasRestantes(rs.getInt("dias_restantes"));
        membresia.setEstado(rs.getString("estado"));
        return membresia;
    }

    private List<MembresiaVista> mapearResultadosVista(ResultSet rs) throws SQLException {
        List<MembresiaVista> membresias = new ArrayList<>();
        while (rs.next()) {
            membresias.add(mapearMembresiaVista(rs));
        }
        return membresias;
    }

    private MembresiaVista mapearMembresiaVista(ResultSet rs) throws SQLException {
        MembresiaVista membresia = new MembresiaVista();
        membresia.setId(rs.getInt("id"));
        membresia.setIdCliente(rs.getInt("id_cliente"));
        membresia.setClienteNombre(rs.getString("cliente_nombre"));
        membresia.setClienteApellido(rs.getString("cliente_apellido"));
        membresia.setClienteCedula(rs.getString("cliente_cedula"));
        membresia.setTipo(rs.getString("tipo"));
        membresia.setFechaInicio(rs.getDate("fecha_inicio"));
        membresia.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
        membresia.setDiasRestantes(rs.getInt("dias_restantes"));
        membresia.setEstado(rs.getString("estado"));
        return membresia;
    }
    public List<MembresiaVista> listarPorNombreApellido(String nombre, String apellido) throws SQLException {
        String sql = "SELECT m.*, u.nombre AS cliente_nombre, u.apellido AS cliente_apellido, u.cedula AS cliente_cedula " +
                "FROM Membresias m JOIN Usuarios u ON m.id_cliente = u.id " +
                "WHERE (u.nombre LIKE ? OR ? IS NULL) " +
                "AND (u.apellido LIKE ? OR ? IS NULL)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + (nombre != null ? nombre : "") + "%");
            stmt.setString(2, nombre);
            stmt.setString(3, "%" + (apellido != null ? apellido : "") + "%");
            stmt.setString(4, apellido);

            try (ResultSet rs = stmt.executeQuery()) {
                return mapearResultadosVista(rs);
            }
        }
    }

    public List<MembresiaVista> listarPorEntrenadorYNombreApellido(int idEntrenador, String nombre, String apellido) throws SQLException {
        String sql = "SELECT m.*, u.nombre AS cliente_nombre, u.apellido AS cliente_apellido, u.cedula AS cliente_cedula " +
                "FROM Membresias m JOIN Usuarios u ON m.id_cliente = u.id " +
                "JOIN Rutinas r ON u.id = r.id_cliente " +
                "WHERE r.id_entrenador = ? " +
                "AND (u.nombre LIKE ? OR ? IS NULL) " +
                "AND (u.apellido LIKE ? OR ? IS NULL)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntrenador);
            stmt.setString(2, "%" + (nombre != null ? nombre : "") + "%");
            stmt.setString(3, nombre);
            stmt.setString(4, "%" + (apellido != null ? apellido : "") + "%");
            stmt.setString(5, apellido);

            try (ResultSet rs = stmt.executeQuery()) {
                return mapearResultadosVista(rs);
            }
        }
    }

}