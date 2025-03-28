package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creacion 26-03-2025
import org.example.modelos.UsuarioLogin;
import org.example.repositorio.UserReposiImplement;

import java.sql.Connection;
import java.util.Optional;

public class UsuarioLoginServiceImplement implements UsuarioLoginService {
    private Connection conn;

    public UsuarioLoginServiceImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Optional<UsuarioLogin> autenticar(String usuario, String clave) {
        // Aquí delegamos la lógica al repositorio
        UserReposiImplement repo = new UserReposiImplement(conn);
        if (repo.isValidUser(usuario, clave)) {
            return repo.buscarPorUsuario(usuario);  // Devolvemos directamente el Optional
        }
        return Optional.empty();
    }
}

