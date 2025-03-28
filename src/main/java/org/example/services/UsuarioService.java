package org.example.services;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025
import org.example.modelos.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();

    Optional<Usuario> porId(int id);

    void guardar(Usuario usuario);

    void eliminar(int id);

    void actualizar(int id, Usuario usuario);

    Optional<Usuario> buscarPorCedula(String cedula);

    boolean isValidUser(String usuario, String clave);
}
