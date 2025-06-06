package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

import org.example.modelos.Asistencia;
import org.example.modelos.AsistenciaVista;
import org.example.modelos.Usuario;
import org.example.modelos.UsuarioLogin;
import org.example.repositorio.AsistenciaRepositorio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaServiceImplement implements AsistenciaService {
    private final AsistenciaRepositorio asistenciaRepositorio;

    public AsistenciaServiceImplement(Connection conn) {
        this.asistenciaRepositorio = new AsistenciaRepositorio(conn);
    }

    @Override
    public boolean agregar(Asistencia asistencia) {
        return asistenciaRepositorio.agregar(asistencia);
    }

    @Override
    public boolean actualizar(Asistencia asistencia) throws SQLException {
        String sql = "UPDATE Asistencias SET id_usuario = ?, tipo_asistencia = ?, " +
                "fecha_asistencia = ? WHERE id = ?";
        try (PreparedStatement ps = asistenciaRepositorio.getConnection().prepareStatement(sql)) {
            ps.setInt(1, asistencia.getIdUsuario());
            ps.setString(2, asistencia.getTipoAsistencia());
            ps.setTimestamp(3, asistencia.getFechaAsistencia());
            ps.setInt(4, asistencia.getIdAsistencia());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Asistencia> listar() {
        String sql = "SELECT * FROM Asistencias ORDER BY fecha_asistencia DESC";
        List<Asistencia> asistencias = new ArrayList<>();

        try (PreparedStatement ps = asistenciaRepositorio.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                asistencias.add(mapearAsistencia(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar asistencias: " + e.getMessage());
        }
        return asistencias;
    }

    @Override
    public List<AsistenciaVista> listarConDetalles() {
        return asistenciaRepositorio.listarTodasConDetalles();
    }

    @Override
    public List<AsistenciaVista> listarPorUsuario(int idUsuario) {
        return asistenciaRepositorio.listarPorUsuario(idUsuario);
    }

    @Override
    public Asistencia obtener(int idAsistencia) {
        String sql = "SELECT * FROM Asistencias WHERE id = ?";

        try (PreparedStatement ps = asistenciaRepositorio.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idAsistencia);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearAsistencia(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener asistencia: " + e.getMessage());
        }
        return null;
    }

    @Override
    public AsistenciaVista obtenerVistaPorId(int idAsistencia) {
        return obtenerConDetalles(idAsistencia);
    }

    @Override
    public AsistenciaVista obtenerConDetalles(int idAsistencia) {
        String sql = "SELECT * FROM VistaAsistencias WHERE id_asistencia = ?";

        try (PreparedStatement ps = asistenciaRepositorio.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idAsistencia);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return asistenciaRepositorio.mapearAsistenciaVista(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener asistencia con detalles: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int obtenerIdUsuarioPorCedula(String cedula) {
        String sql = "SELECT id FROM Usuarios WHERE cedula = ?";

        try (PreparedStatement ps = asistenciaRepositorio.getConnection().prepareStatement(sql)) {
            ps.setString(1, cedula);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por cédula: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public List<AsistenciaVista> listarPorCedula(String busqueda) {
        return asistenciaRepositorio.listarPorCedula(busqueda);
    }

    @Override
    public boolean validarClienteDeEntrenador(int idCliente, int idEntrenador) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Rutinas WHERE id_cliente = ? AND id_entrenador = ?";
        try (PreparedStatement ps = asistenciaRepositorio.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.setInt(2, idEntrenador);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public List<UsuarioLogin> obtenerClientesPorEntrenador(int idEntrenador) {
        List<UsuarioLogin> clientes = new ArrayList<>();
        String sql = "SELECT u.id, u.usuario, u.cedula, u.rol FROM Usuarios u " +
                "JOIN Rutinas r ON u.id = r.id_cliente " +
                "WHERE r.id_entrenador = ? AND u.rol = 'Cliente'";

        try (PreparedStatement ps = asistenciaRepositorio.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UsuarioLogin cliente = new UsuarioLogin(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        null, // No necesitamos la clave aquí
                        rs.getString("rol"),
                        rs.getString("cedula")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error obteniendo clientes del entrenador: " + e.getMessage());
        }
        return clientes;
    }

    private Asistencia mapearAsistencia(ResultSet rs) throws SQLException {
        Asistencia asistencia = new Asistencia();
        asistencia.setIdAsistencia(rs.getInt("id"));
        asistencia.setIdUsuario(rs.getInt("id_usuario"));
        asistencia.setIdRegistrador(rs.getInt("id_registrador"));
        asistencia.setFechaAsistencia(rs.getTimestamp("fecha_asistencia"));
        asistencia.setTipoAsistencia(rs.getString("tipo_asistencia"));
        return asistencia;
    }

    @Override
    public List<UsuarioLogin> listarTodosUsuarios() {
        List<UsuarioLogin> usuarios = new ArrayList<>();
        String sql = "SELECT id, usuario, cedula, rol FROM Usuarios ORDER BY usuario";

        try (PreparedStatement ps = asistenciaRepositorio.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UsuarioLogin usuario = new UsuarioLogin(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        null, // No incluir la contraseña por seguridad
                        rs.getString("rol"),
                        rs.getString("cedula")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar todos los usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    @Override
    public Asistencia obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Asistencias WHERE id = ?";
        try (PreparedStatement ps = asistenciaRepositorio.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Asistencia asistencia = new Asistencia();
                    asistencia.setIdAsistencia(rs.getInt("id"));
                    asistencia.setIdUsuario(rs.getInt("id_usuario"));
                    asistencia.setIdRegistrador(rs.getInt("id_registrador"));
                    asistencia.setTipoAsistencia(rs.getString("tipo_asistencia"));
                    asistencia.setFechaAsistencia(rs.getTimestamp("fecha_asistencia"));
                    return asistencia;
                }
            }
        }
        return null;
    }

    @Override
    public boolean eliminarAsistencia(int idAsistencia) throws SQLException {
        String sql = "DELETE FROM Asistencias WHERE id = ?";
        try (PreparedStatement ps = asistenciaRepositorio.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idAsistencia);
            return ps.executeUpdate() > 0;
        }
    }
}