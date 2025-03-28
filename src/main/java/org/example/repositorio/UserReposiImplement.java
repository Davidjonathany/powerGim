package org.example.repositorio;

// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 26-03-2025

import org.example.modelos.UsuarioLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserReposiImplement {

    private Connection conn;

    public UserReposiImplement(Connection conn) {
        this.conn = conn;
    }

    // Método para verificar si el usuario y contraseña son válidos
    public boolean isValidUser(String usuario, String clave) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPasswordHash = rs.getString("clave");
                    return checkPassword(clave, storedPasswordHash); // Método para verificar el hash
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al validar usuario", e);
        }
        return false;
    }

    private boolean checkPassword(String password, String storedPasswordHash) {
        // Usar un método de hashing, por ejemplo bcrypt, para comparar la clave con el hash
        // Ejemplo con bcrypt: return BCrypt.checkpw(password, storedPasswordHash);
        return password.equals(storedPasswordHash); // Esto es solo un ejemplo, reemplaza por un método de hashing seguro
    }

    // Método para obtener el usuario por su nombre
    public Optional<UsuarioLogin> buscarPorUsuario(String usuario) {
        String sql = "SELECT usuario, clave, rol FROM usuarios WHERE usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new UsuarioLogin(
                            rs.getString("usuario"),
                            rs.getString("clave"),
                            rs.getString("rol")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario", e);
        }
        return Optional.empty();
    }

}

