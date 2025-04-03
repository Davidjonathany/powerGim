package org.example.repositorio;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 01-04-2025

import org.example.modelos.Peso;
import org.example.modelos.PesoVista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PesoRepositorio {
    private final Connection conn;

    public PesoRepositorio(Connection conn) {
        this.conn = conn;
    }

    public boolean crear(Peso peso) throws SQLException {
        String sql = "INSERT INTO Pesos (id_cliente, peso_inicial, peso_actual, fecha_inicio) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, peso.getIdCliente());
            stmt.setDouble(2, peso.getPesoInicial());
            stmt.setDouble(3, peso.getPesoActual());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        peso.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public boolean actualizar(Peso peso) throws SQLException {
        String sql = "UPDATE Pesos SET peso_actual = ?, fecha_registro = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, peso.getPesoActual());
            stmt.setInt(2, peso.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    private Peso mapearPeso(ResultSet rs) throws SQLException {
        Peso peso = new Peso();
        peso.setId(rs.getInt("id"));
        peso.setIdCliente(rs.getInt("id_cliente"));
        peso.setPesoInicial(rs.getDouble("peso_inicial"));
        peso.setPesoActual(rs.getDouble("peso_actual"));
        peso.setFechaInicio(rs.getString("fecha_inicio"));
        peso.setFechaRegistro(rs.getString("fecha_registro"));
        return peso;
    }

    public List<PesoVista> listarPorEntrenador(int idEntrenador, String filtro) throws SQLException {
        String sql = "SELECT DISTINCT p.*, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, u.cedula AS cedula_cliente " +
                "FROM Pesos p " +
                "JOIN Usuarios u ON p.id_cliente = u.id " +
                "JOIN Rutinas r ON u.id = r.id_cliente " +
                "WHERE r.id_entrenador = ? AND u.rol = 'Cliente'";

        if (filtro != null && !filtro.isEmpty()) {
            sql += " AND (u.nombre LIKE ? OR u.apellido LIKE ?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntrenador);
            if (filtro != null && !filtro.isEmpty()) {
                String likeFilter = "%" + filtro + "%";
                stmt.setString(2, likeFilter);
                stmt.setString(3, likeFilter);
            }

            return mapearResultados(stmt.executeQuery());
        }
    }
    
    public List<PesoVista> listarPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT p.*, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, u.cedula AS cedula_cliente " +
                "FROM Pesos p JOIN Usuarios u ON p.id_cliente = u.id " +
                "WHERE p.id_cliente = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            return mapearResultados(stmt.executeQuery());
        }
    }

    public Peso obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Pesos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPeso(rs);
                }
            }
        }
        return null;
    }

    public PesoVista obtenerVistaPorId(int id) throws SQLException {
        String sql = "SELECT p.*, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, u.cedula AS cedula_cliente " +
                "FROM Pesos p JOIN Usuarios u ON p.id_cliente = u.id " +
                "WHERE p.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PesoVista vista = new PesoVista();
                    vista.setId(rs.getInt("id"));
                    vista.setIdCliente(rs.getInt("id_cliente"));
                    vista.setNombreCliente(rs.getString("nombre_cliente"));
                    vista.setApellidoCliente(rs.getString("apellido_cliente"));
                    vista.setCedulaCliente(rs.getString("cedula_cliente"));
                    vista.setPesoInicial(rs.getDouble("peso_inicial"));
                    vista.setPesoActual(rs.getDouble("peso_actual"));
                    vista.setFechaInicio(rs.getString("fecha_inicio"));
                    vista.setFechaRegistro(rs.getString("fecha_registro"));
                    return vista;
                }
            }
        }
        return null;
    }


    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Pesos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean verificarRelacionEntrenadorCliente(int idEntrenador, int idCliente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Rutinas WHERE id_entrenador = ? AND id_cliente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntrenador);
            stmt.setInt(2, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private List<PesoVista> mapearResultados(ResultSet rs) throws SQLException {
        List<PesoVista> pesos = new ArrayList<>();
        while (rs.next()) {
            pesos.add(mapearPesoVista(rs));
        }
        return pesos;
    }


    private PesoVista mapearPesoVista(ResultSet rs) throws SQLException {
        PesoVista vista = new PesoVista();
        vista.setId(rs.getInt("id"));
        vista.setIdCliente(rs.getInt("id_cliente"));
        vista.setNombreCliente(rs.getString("nombre_cliente"));
        vista.setApellidoCliente(rs.getString("apellido_cliente"));
        vista.setCedulaCliente(rs.getString("cedula_cliente"));
        vista.setPesoInicial(rs.getDouble("peso_inicial"));
        vista.setPesoActual(rs.getDouble("peso_actual"));
        vista.setFechaRegistro(rs.getString("fecha_registro"));
        vista.setFechaInicio(rs.getString("fecha_inicio"));
        return vista;
    }
    public List<PesoVista> listarTodosClientes() throws SQLException {
        String sql = "SELECT u.id AS id_cliente, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, u.cedula AS cedula_cliente " +
                "FROM Usuarios u WHERE u.rol = 'Cliente'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            return mapearClientes(stmt.executeQuery());
        }
    }

    public List<PesoVista> listarClientesPorEntrenador(int idEntrenador) throws SQLException {
        String sql = "SELECT DISTINCT u.id AS id_cliente, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, u.cedula AS cedula_cliente " +
                "FROM Usuarios u JOIN Rutinas r ON u.id = r.id_cliente " +
                "WHERE r.id_entrenador = ? AND u.rol = 'Cliente'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEntrenador);
            return mapearClientes(stmt.executeQuery());
        }
    }

    private List<PesoVista> mapearClientes(ResultSet rs) throws SQLException {
        List<PesoVista> clientes = new ArrayList<>();
        while (rs.next()) {
            PesoVista cliente = new PesoVista();
            cliente.setIdCliente(rs.getInt("id_cliente"));
            cliente.setNombreCliente(rs.getString("nombre_cliente"));
            cliente.setApellidoCliente(rs.getString("apellido_cliente"));
            cliente.setCedulaCliente(rs.getString("cedula_cliente"));
            clientes.add(cliente);
        }
        return clientes;
    }
    public boolean existeRegistroPeso(int idCliente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM pesos WHERE id_cliente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
    public List<PesoVista> listarTodos(String filtro) throws SQLException {
        String sql = "SELECT p.*, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, u.cedula AS cedula_cliente " +
                "FROM Pesos p JOIN Usuarios u ON p.id_cliente = u.id " +
                "WHERE u.rol = 'Cliente'";

        if (filtro != null && !filtro.isEmpty()) {
            sql += " AND (u.nombre LIKE ? OR u.apellido LIKE ? OR u.cedula LIKE ?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (filtro != null && !filtro.isEmpty()) {
                String likeFilter = "%" + filtro + "%";
                stmt.setString(1, likeFilter);
                stmt.setString(2, likeFilter);
                stmt.setString(3, likeFilter);
            }

            return mapearResultados(stmt.executeQuery());
        }
    }
}