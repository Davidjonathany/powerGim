package org.example.services;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creacion 26-03-2025
import org.example.modelos.UsuarioLogin;
import java.util.Optional;

public interface UsuarioLoginService {
    Optional<UsuarioLogin> autenticar(String usuario, String clave);
}

