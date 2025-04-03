package org.example.repositorio;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

import org.example.modelos.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio implements Repositorio<Usuario> {

    private Connection conn;

    public UsuarioRepositorio(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setClave(rs.getString("clave"));
                usuario.setRol(rs.getString("rol"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setCedula(rs.getString("cedula"));
                usuario.setDireccion(rs.getString("direccion"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    @Override
    public Usuario porId(int id) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setUsuario(rs.getString("usuario"));
                    usuario.setClave(rs.getString("clave"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setCedula(rs.getString("cedula"));
                    usuario.setDireccion(rs.getString("direccion"));
                }
            }
        }
        return usuario;
    }

    @Override
    public void guardar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellido, usuario, clave, rol, correo, telefono, cedula, direccion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getUsuario());
            stmt.setString(4, usuario.getClave());
            stmt.setString(5, usuario.getRol());
            stmt.setString(6, usuario.getCorreo());
            stmt.setString(7, usuario.getTelefono());
            stmt.setString(8, usuario.getCedula());
            stmt.setString(9, usuario.getDireccion());
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(int id, Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, usuario = ?, clave = ?, rol = ?, correo = ?, telefono = ?, cedula = ?, direccion = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getUsuario());
            stmt.setString(4, usuario.getClave());
            stmt.setString(5, usuario.getRol());
            stmt.setString(6, usuario.getCorreo());
            stmt.setString(7, usuario.getTelefono());
            stmt.setString(8, usuario.getCedula());
            stmt.setString(9, usuario.getDireccion());
            stmt.setInt(10, id);
            stmt.executeUpdate();
        }
    }

    // Método para verificar si un usuario con la cédula ya existe
    @Override
    public boolean usuarioExistePorCedula(String cedula) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE cedula = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    // Método para buscar un usuario por su cédula
    @Override
    public Usuario buscarPorCedula(String cedula) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE cedula = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setUsuario(rs.getString("usuario"));
                    usuario.setClave(rs.getString("clave"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setCedula(rs.getString("cedula"));
                    usuario.setDireccion(rs.getString("direccion"));
                }
            }
        }
        return usuario;
    }
    public boolean isValidUser(String usuario, String clave) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ? AND clave = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, clave);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }
    public boolean esCliente(int idUsuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE id = ? AND rol = 'Cliente'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public boolean esEntrenador(int idUsuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE id = ? AND rol = 'Entrenador'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public List<Usuario> buscarPorNombreApellidoOCedula(String query, String rol) throws SQLException {
        String sql = "SELECT id, nombre, apellido, cedula FROM Usuarios " +
                "WHERE (nombre LIKE ? OR apellido LIKE ? OR cedula LIKE ?) " +
                "AND rol = ? LIMIT 10";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String likeQuery = "%" + query + "%";
            stmt.setString(1, likeQuery);
            stmt.setString(2, likeQuery);
            stmt.setString(3, likeQuery);
            stmt.setString(4, rol);

            ResultSet rs = stmt.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setCedula(rs.getString("cedula"));
                usuarios.add(usuario);
            }

            return usuarios;
        }
    }
    public Usuario buscarClientePorCedula(String cedula) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE cedula = ? AND rol = 'Cliente'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCedula(rs.getString("cedula"));
                    // Solo asignar los campos necesarios
                }
            }
        }
        return usuario;
    }

        public List<Usuario> listarPorRol(String rol) throws SQLException {
            String sql = "SELECT * FROM Usuarios WHERE rol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, rol);
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Usuario> usuarios = new ArrayList<>();
                    while (rs.next()) {
                        usuarios.add(mapearUsuario(rs));
                    }
                    return usuarios;
                }
            }
        }

        public List<Usuario> listarClientesPorEntrenador(int idEntrenador) throws SQLException {
            String sql = "SELECT u.* FROM Usuarios u JOIN Rutinas r ON u.id = r.id_cliente " +
                    "WHERE r.id_entrenador = ? AND u.rol = 'Cliente'";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idEntrenador);
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Usuario> clientes = new ArrayList<>();
                    while (rs.next()) {
                        clientes.add(mapearUsuario(rs));
                    }
                    return clientes;
                }
            }
        }

        private Usuario mapearUsuario(ResultSet rs) throws SQLException {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellido(rs.getString("apellido"));
            usuario.setUsuario(rs.getString("usuario"));
            usuario.setClave(rs.getString("clave"));
            usuario.setRol(rs.getString("rol"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setTelefono(rs.getString("telefono"));
            usuario.setCedula(rs.getString("cedula"));
            usuario.setDireccion(rs.getString("direccion"));
            return usuario;
        }
    public List<Usuario> buscarUsuariosCompleto(String query) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE " +
                "(nombre LIKE ? OR " +
                "apellido LIKE ? OR " +
                "cedula LIKE ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String likeQuery = "%" + query + "%";
            stmt.setString(1, likeQuery);
            stmt.setString(2, likeQuery);
            stmt.setString(3, likeQuery);

            ResultSet rs = stmt.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            return usuarios;
        }
    }
    }

