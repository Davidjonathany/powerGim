package org.example.repositorio;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025
import org.example.modelos.Ejercicio;
import org.example.modelos.EjercicioVista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EjercicioRepositorio {
    private final Connection conn;

    public EjercicioRepositorio(Connection conn) {
        this.conn = conn;
    }

    // CRUD básico
    public boolean crear(Ejercicio ejercicio) throws SQLException {
        String sql = "INSERT INTO Ejercicios (id_rutina, nombre, repeticiones, series, tiempo, descanso) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, ejercicio.getIdRutina());
            stmt.setString(2, ejercicio.getNombre());
            stmt.setInt(3, ejercicio.getRepeticiones());
            stmt.setInt(4, ejercicio.getSeries());
            stmt.setInt(5, ejercicio.getTiempo());
            stmt.setInt(6, ejercicio.getDescanso());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ejercicio.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }
    }

    // Para Administrador: Todos los ejercicios
    public List<EjercicioVista> listarTodos(String cedulaFiltro) throws SQLException {
        String sql = "SELECT e.*, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, u.cedula AS cedula_cliente " +
                "FROM Ejercicios e " +
                "JOIN Rutinas r ON e.id_rutina = r.id " +
                "JOIN Usuarios u ON r.id_cliente = u.id";

        if (cedulaFiltro != null && !cedulaFiltro.isEmpty()) {
            sql += " WHERE u.cedula LIKE ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + cedulaFiltro + "%");
                return ejecutarConsultaConVista(stmt);
            }
        }

        return ejecutarConsultaConVista(sql);
    }

    public List<EjercicioVista> listarPorEntrenador(int idEntrenador, String cedulaFiltro) throws SQLException {
        String sql = "SELECT e.*, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, u.cedula AS cedula_cliente " +
                "FROM Ejercicios e " +
                "JOIN Rutinas r ON e.id_rutina = r.id " +
                "JOIN Usuarios u ON r.id_cliente = u.id " +
                "WHERE r.id_entrenador = ?";

        if (cedulaFiltro != null && !cedulaFiltro.trim().isEmpty()) {
            sql += " AND u.cedula LIKE ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idEntrenador);
                stmt.setString(2, "%" + cedulaFiltro.trim() + "%");
                return ejecutarConsultaConVista(stmt);
            }
        } else {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idEntrenador);
                return ejecutarConsultaConVista(stmt);
            }
        }
    }

    public List<EjercicioVista> listarPorEntrenadorYNombre(int idEntrenador, String nombreFiltro) throws SQLException {
        String sql = "SELECT e.*, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, u.cedula AS cedula_cliente " +
                "FROM Ejercicios e " +
                "JOIN Rutinas r ON e.id_rutina = r.id " +
                "JOIN Usuarios u ON r.id_cliente = u.id " +
                "WHERE r.id_entrenador = ? AND " +
                "(CONCAT(u.nombre, ' ', u.apellido) LIKE ? OR u.nombre LIKE ? OR u.apellido LIKE ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String likePattern = "%" + nombreFiltro.trim() + "%";
            stmt.setInt(1, idEntrenador);
            stmt.setString(2, likePattern);
            stmt.setString(3, likePattern);
            stmt.setString(4, likePattern);
            return ejecutarConsultaConVista(stmt);
        }
    }
    // Para Cliente: Solo sus ejercicios
    public List<EjercicioVista> listarPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT e.*, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, u.cedula AS cedula_cliente " +
                "FROM Ejercicios e " +
                "JOIN Rutinas r ON e.id_rutina = r.id " +
                "JOIN Usuarios u ON r.id_cliente = u.id " +
                "WHERE r.id_cliente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            return ejecutarConsultaConVista(stmt);
        }
    }

    public Ejercicio obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Ejercicios WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEjercicio(rs);
                }
            }
        }
        return null;
    }
    public EjercicioVista obtenerVistaPorId(int id) throws SQLException {
        String sql = "SELECT e.*, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, " +
                "u.cedula AS cedula_cliente FROM Ejercicios e " +
                "JOIN Rutinas r ON e.id_rutina = r.id " +
                "JOIN Usuarios u ON r.id_cliente = u.id WHERE e.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                EjercicioVista vista = new EjercicioVista();
                vista.setId(rs.getInt("id"));
                vista.setIdRutina(rs.getInt("id_rutina"));
                vista.setNombreEjercicio(rs.getString("nombre")); // Columna correcta
                vista.setRepeticiones(rs.getInt("repeticiones"));
                vista.setSeries(rs.getInt("series"));
                vista.setTiempo(rs.getInt("tiempo"));
                vista.setDescanso(rs.getInt("descanso"));
                vista.setNombreCliente(rs.getString("nombre_cliente"));
                vista.setApellidoCliente(rs.getString("apellido_cliente"));
                vista.setCedulaCliente(rs.getString("cedula_cliente"));
                return vista;
            }
            return null;
        }
    }

    public boolean actualizar(Ejercicio ejercicio) throws SQLException {
        String sql = "UPDATE Ejercicios SET nombre = ?, repeticiones = ?, series = ?, tiempo = ?, descanso = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ejercicio.getNombre());
            stmt.setInt(2, ejercicio.getRepeticiones());
            stmt.setInt(3, ejercicio.getSeries());
            stmt.setInt(4, ejercicio.getTiempo());
            stmt.setInt(5, ejercicio.getDescanso());
            stmt.setInt(6, ejercicio.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM ejercicios WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Métodos auxiliares
    private List<EjercicioVista> ejecutarConsultaConVista(String sql) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return mapearResultadosVista(rs);
        }
    }

    private List<EjercicioVista> ejecutarConsultaConVista(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            return mapearResultadosVista(rs);
        }
    }

    private List<EjercicioVista> mapearResultadosVista(ResultSet rs) throws SQLException {
        List<EjercicioVista> ejercicios = new ArrayList<>();
        while (rs.next()) {
            ejercicios.add(mapearEjercicioVista(rs));
        }
        return ejercicios;
    }

    private EjercicioVista mapearEjercicioVista(ResultSet rs) throws SQLException {
        EjercicioVista vista = new EjercicioVista();
        vista.setId(rs.getInt("id"));
        vista.setIdRutina(rs.getInt("id_rutina"));
        vista.setNombreCliente(rs.getString("nombre_cliente"));
        vista.setApellidoCliente(rs.getString("apellido_cliente"));
        vista.setCedulaCliente(rs.getString("cedula_cliente"));
        vista.setNombreEjercicio(rs.getString("nombre"));
        vista.setRepeticiones(rs.getInt("repeticiones"));
        vista.setSeries(rs.getInt("series"));
        vista.setTiempo(rs.getInt("tiempo"));
        vista.setDescanso(rs.getInt("descanso"));
        return vista;
    }

    private Ejercicio mapearEjercicio(ResultSet rs) throws SQLException {
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId(rs.getInt("id"));
        ejercicio.setIdRutina(rs.getInt("id_rutina"));
        ejercicio.setNombre(rs.getString("nombre"));
        ejercicio.setRepeticiones(rs.getInt("repeticiones"));
        ejercicio.setSeries(rs.getInt("series"));
        ejercicio.setTiempo(rs.getInt("tiempo"));
        ejercicio.setDescanso(rs.getInt("descanso"));
        return ejercicio;
    }
    public List<EjercicioVista> listarTodosPorNombre(String nombreFiltro) throws SQLException {
        String sql = "SELECT e.*, u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, u.cedula AS cedula_cliente " +
                "FROM Ejercicios e " +
                "JOIN Rutinas r ON e.id_rutina = r.id " +
                "JOIN Usuarios u ON r.id_cliente = u.id " +
                "WHERE CONCAT(u.nombre, ' ', u.apellido) LIKE ? OR u.nombre LIKE ? OR u.apellido LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String likePattern = "%" + nombreFiltro + "%";
            stmt.setString(1, likePattern);
            stmt.setString(2, likePattern);
            stmt.setString(3, likePattern);
            return ejecutarConsultaConVista(stmt);
        }
    }
}