package org.example.repositorio;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 29-03-2025
import org.example.modelos.Rutina;
import org.example.modelos.VistaRutina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RutinaRepositorio {
    private final Connection connection;

    public RutinaRepositorio(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean crear(Rutina rutina) {
        String sql = "INSERT INTO Rutinas (id_cliente, id_entrenador, tipo_entrenamiento, observaciones) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, rutina.getIdCliente());
            ps.setInt(2, rutina.getIdEntrenador());
            ps.setString(3, rutina.getTipoEntrenamiento());
            ps.setString(4, rutina.getObservaciones());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rutina.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear rutina: " + e.getMessage());
        }
        return false;
    }

    public boolean actualizar(Rutina rutina) {
        String sql = "UPDATE Rutinas SET id_cliente = ?, id_entrenador = ?, " +
                "tipo_entrenamiento = ?, observaciones = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, rutina.getIdCliente());
            ps.setInt(2, rutina.getIdEntrenador());
            ps.setString(3, rutina.getTipoEntrenamiento());
            ps.setString(4, rutina.getObservaciones());
            ps.setInt(5, rutina.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar rutina: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminar(int idRutina) {
        String sql = "DELETE FROM Rutinas WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idRutina);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar rutina: " + e.getMessage());
        }
        return false;
    }

    public Rutina obtenerPorId(int idRutina) {
        String sql = "SELECT * FROM Rutinas WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idRutina);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearRutina(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener rutina por ID: " + e.getMessage());
        }
        return null;
    }

    public List<VistaRutina> listarTodas() {
        List<VistaRutina> rutinas = new ArrayList<>();
        String sql = "SELECT * FROM VistaRutinasConRoles ORDER BY cliente_nombre, entrenador_nombre";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rutinas.add(mapearVistaRutina(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar todas las rutinas: " + e.getMessage());
        }
        return rutinas;
    }

    public List<VistaRutina> filtrarPorTipoEntrenamiento(String tipo) {
        List<VistaRutina> rutinas = new ArrayList<>();
        String sql = "SELECT * FROM VistaRutinasConRoles WHERE tipo_entrenamiento LIKE ? ORDER BY cliente_nombre";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + tipo + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rutinas.add(mapearVistaRutina(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar rutinas por tipo: " + e.getMessage());
        }
        return rutinas;
    }

    public List<VistaRutina> listarPorCliente(int idCliente) {
        List<VistaRutina> rutinas = new ArrayList<>();
        String sql = "SELECT * FROM VistaRutinasConRoles WHERE id_cliente = ? ORDER BY tipo_entrenamiento";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rutinas.add(mapearVistaRutina(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar rutinas por cliente: " + e.getMessage());
        }
        return rutinas;
    }

    public List<VistaRutina> listarPorEntrenador(int idEntrenador) {
        List<VistaRutina> rutinas = new ArrayList<>();
        String sql = "SELECT * FROM VistaRutinasConRoles WHERE id_entrenador = ? ORDER BY cliente_nombre";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rutinas.add(mapearVistaRutina(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar rutinas por entrenador: " + e.getMessage());
        }
        return rutinas;
    }

    public VistaRutina obtenerVistaPorId(int idRutina) {
        String sql = "SELECT * FROM VistaRutinasConRoles WHERE id_rutina = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idRutina);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearVistaRutina(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener vista de rutina por ID: " + e.getMessage());
        }
        return null;
    }

    public List<VistaRutina> listarPorEntrenadorYFiltro(int idEntrenador, String tipoEntrenamiento) {
        List<VistaRutina> rutinas = new ArrayList<>();
        String sql = "SELECT * FROM VistaRutinasConRoles WHERE id_entrenador = ? AND tipo_entrenamiento LIKE ? " +
                "ORDER BY cliente_nombre";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            ps.setString(2, "%" + tipoEntrenamiento + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rutinas.add(mapearVistaRutina(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar rutinas de entrenador: " + e.getMessage());
        }
        return rutinas;
    }

    public List<VistaRutina> filtrarRutinasAvanzado(String nombreCliente, String nombreEntrenador,
                                                    String tipoEntrenamiento, int offset, int limit) {
        List<VistaRutina> rutinas = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM VistaRutinasConRoles WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            sql.append("AND (cliente_nombre LIKE ? OR cliente_apellido LIKE ?) ");
            params.add("%" + nombreCliente + "%");
            params.add("%" + nombreCliente + "%");
        }

        if (nombreEntrenador != null && !nombreEntrenador.isEmpty()) {
            sql.append("AND (entrenador_nombre LIKE ? OR entrenador_apellido LIKE ?) ");
            params.add("%" + nombreEntrenador + "%");
            params.add("%" + nombreEntrenador + "%");
        }

        if (tipoEntrenamiento != null && !tipoEntrenamiento.isEmpty()) {
            sql.append("AND tipo_entrenamiento = ? ");
            params.add(tipoEntrenamiento);
        }

        sql.append("ORDER BY cliente_nombre LIMIT ?, ?");
        params.add(offset);
        params.add(limit);

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rutinas.add(mapearVistaRutina(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar rutinas avanzado: " + e.getMessage());
        }
        return rutinas;
    }

    public int contarRutinasFiltradas(String nombreCliente, String nombreEntrenador,
                                      String tipoEntrenamiento) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM VistaRutinasConRoles WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            sql.append("AND (cliente_nombre LIKE ? OR cliente_apellido LIKE ?) ");
            params.add("%" + nombreCliente + "%");
            params.add("%" + nombreCliente + "%");
        }

        if (nombreEntrenador != null && !nombreEntrenador.isEmpty()) {
            sql.append("AND (entrenador_nombre LIKE ? OR entrenador_apellido LIKE ?) ");
            params.add("%" + nombreEntrenador + "%");
            params.add("%" + nombreEntrenador + "%");
        }

        if (tipoEntrenamiento != null && !tipoEntrenamiento.isEmpty()) {
            sql.append("AND tipo_entrenamiento = ? ");
            params.add(tipoEntrenamiento);
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al contar rutinas filtradas: " + e.getMessage());
            return 0;
        }
    }

    public List<VistaRutina> listarTodasConPaginacion(int offset, int limit) {
        List<VistaRutina> rutinas = new ArrayList<>();
        String sql = "SELECT * FROM VistaRutinasConRoles ORDER BY cliente_nombre LIMIT ?, ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rutinas.add(mapearVistaRutina(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar rutinas con paginación: " + e.getMessage());
        }
        return rutinas;
    }

    public int contarTotalRutinas() {
        String sql = "SELECT COUNT(*) FROM VistaRutinasConRoles";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            System.err.println("Error al contar rutinas: " + e.getMessage());
            return 0;
        }
    }

    public boolean existeRelacionClienteEntrenador(int idCliente, int idEntrenador) {
        String sql = "SELECT COUNT(*) FROM Rutinas WHERE id_cliente = ? AND id_entrenador = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idEntrenador);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar relación cliente-entrenador: " + e.getMessage());
            return false;
        }
    }

    // Métodos de mapeo
    private Rutina mapearRutina(ResultSet rs) throws SQLException {
        Rutina rutina = new Rutina();
        rutina.setId(rs.getInt("id"));
        rutina.setIdCliente(rs.getInt("id_cliente"));
        rutina.setIdEntrenador(rs.getInt("id_entrenador"));
        rutina.setTipoEntrenamiento(rs.getString("tipo_entrenamiento"));
        rutina.setObservaciones(rs.getString("observaciones"));
        return rutina;
    }

    public VistaRutina mapearVistaRutina(ResultSet rs) throws SQLException {
        VistaRutina vista = new VistaRutina();
        vista.setIdRutina(rs.getInt("id_rutina"));
        vista.setIdCliente(rs.getInt("id_cliente")); // Nuevo
        vista.setIdEntrenador(rs.getInt("id_entrenador")); // Nuevo
        vista.setClienteNombre(rs.getString("cliente_nombre"));
        vista.setClienteApellido(rs.getString("cliente_apellido"));
        vista.setClienteRol(rs.getString("cliente_rol"));
        vista.setEntrenadorNombre(rs.getString("entrenador_nombre"));
        vista.setEntrenadorApellido(rs.getString("entrenador_apellido"));
        vista.setEntrenadorRol(rs.getString("entrenador_rol"));
        vista.setTipoEntrenamiento(rs.getString("tipo_entrenamiento"));
        vista.setObservaciones(rs.getString("observaciones"));
        return vista;
    }

    public VistaRutina obtenerVistaRutinaCompletaPorId(int idRutina) {
        String sql = "SELECT r.id AS idRutina, r.id_cliente, r.id_entrenador, " +
                "c.nombre AS clienteNombre, c.apellido AS clienteApellido, c.rol AS clienteRol, " +
                "e.nombre AS entrenadorNombre, e.apellido AS entrenadorApellido, e.rol AS entrenadorRol, " +
                "r.tipo_entrenamiento, r.observaciones " +
                "FROM Rutinas r " +
                "JOIN Usuarios c ON r.id_cliente = c.id " +
                "LEFT JOIN Usuarios e ON r.id_entrenador = e.id " +
                "WHERE r.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idRutina);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    VistaRutina vistaRutina = new VistaRutina();
                    vistaRutina.setIdRutina(rs.getInt("idRutina"));
                    vistaRutina.setIdCliente(rs.getInt("id_cliente"));
                    vistaRutina.setIdEntrenador(rs.getInt("id_entrenador"));
                    vistaRutina.setClienteNombre(rs.getString("clienteNombre"));
                    vistaRutina.setClienteApellido(rs.getString("clienteApellido"));
                    vistaRutina.setClienteRol(rs.getString("clienteRol"));
                    vistaRutina.setEntrenadorNombre(rs.getString("entrenadorNombre"));
                    vistaRutina.setEntrenadorApellido(rs.getString("entrenadorApellido"));
                    vistaRutina.setEntrenadorRol(rs.getString("entrenadorRol"));
                    vistaRutina.setTipoEntrenamiento(rs.getString("tipo_entrenamiento"));
                    vistaRutina.setObservaciones(rs.getString("observaciones"));
                    return vistaRutina;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}