package org.example.services;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025
import org.example.modelos.Usuario;
import org.example.repositorio.UsuarioRepositorio;  // Cambié aquí el nombre

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioServiceImplement implements UsuarioService {

    private UsuarioRepositorio repositorio;  // Cambié aquí el nombre

    public UsuarioServiceImplement(Connection conn) {
        this.repositorio = new UsuarioRepositorio(conn);  // Cambié aquí el nombre
    }

    @Override
    public List<Usuario> listar() {
        try {
            return repositorio.listar();
        } catch (SQLException ex) {
            throw new ServiceException("Error al listar usuarios", ex);
        }
    }

    @Override
    public Optional<Usuario> porId(int id) {
        try {
            return Optional.ofNullable(repositorio.porId(id));
        } catch (SQLException ex) {
            throw new ServiceException("Error al buscar usuario por ID", ex);
        }
    }

    @Override
    public void guardar(Usuario usuario) {
        try {
            // Verificar si la cédula ya existe
            if (repositorio.usuarioExistePorCedula(usuario.getCedula())) {
                throw new ServiceException("La cédula " + usuario.getCedula() + " ya está registrada");
            }

            // Si no existe, guardar
            repositorio.guardar(usuario);
        } catch (SQLException ex) {
            throw new ServiceException("Error al guardar usuario: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void eliminar(int id) {
        try {
            repositorio.eliminar(id);
        } catch (SQLException ex) {
            throw new ServiceException("Error al eliminar usuario", ex);
        }
    }

    @Override
    public void actualizar(int id, Usuario usuario) {
        try {
            repositorio.actualizar(id, usuario);
        } catch (SQLException ex) {
            throw new ServiceException("Error al actualizar usuario", ex);
        }
    }

    @Override
    public Optional<Usuario> buscarPorCedula(String cedula) {
        try {
            Usuario usuario = repositorio.buscarPorCedula(cedula);
            return Optional.ofNullable(usuario);
        } catch (SQLException ex) {
            throw new ServiceException("Error al buscar usuario por cédula", ex);
        }
    }

    @Override
    public boolean isValidUser(String usuario, String clave) {
        try {
            return repositorio.isValidUser(usuario, clave);
        } catch (SQLException ex) {
            throw new ServiceException("Error al validar el usuario", ex);
        }
    }
}
