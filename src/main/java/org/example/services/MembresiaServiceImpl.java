package org.example.services;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025
import org.example.modelos.Membresia;
import org.example.modelos.MembresiaVista;
import org.example.repositorio.MembresiaRepositorio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MembresiaServiceImpl implements MembresiaService {
    private final MembresiaRepositorio repositorio;

    public MembresiaServiceImpl(Connection conn) {
        this.repositorio = new MembresiaRepositorio(conn);
    }

    @Override
    public List<MembresiaVista> listarTodas() throws SQLException {
        return repositorio.listarTodas();
    }

    @Override
    public List<MembresiaVista> listarPorCliente(int idCliente) throws SQLException {
        return repositorio.listarPorCliente(idCliente);
    }

    @Override
    public List<MembresiaVista> listarPorEntrenador(int idEntrenador) throws SQLException {
        return repositorio.listarPorEntrenador(idEntrenador);
    }

    @Override
    public Membresia obtenerPorId(int id) throws SQLException {
        return repositorio.obtenerPorId(id);
    }

    @Override
    public boolean crear(Membresia membresia) throws SQLException {
        if (membresia.getFechaVencimiento().before(membresia.getFechaInicio())) {
            throw new SQLException("La fecha de vencimiento no puede ser anterior a la fecha de inicio");
        }

        long diffInMillis = membresia.getFechaVencimiento().getTime() - membresia.getFechaInicio().getTime();
        int diasRestantes = (int) (diffInMillis / (1000 * 60 * 60 * 24));
        membresia.setDiasRestantes(diasRestantes);

        return repositorio.crear(membresia);
    }

    @Override
    public boolean actualizar(Membresia membresia) throws SQLException {
        if ("Activa".equals(membresia.getEstado()) &&
                membresia.getFechaVencimiento().before(new java.util.Date())) {
            throw new SQLException("No se puede reactivar una membresía vencida");
        }

        long diffInMillis = membresia.getFechaVencimiento().getTime() - new java.util.Date().getTime();
        int diasRestantes = (int) (diffInMillis / (1000 * 60 * 60 * 24));
        membresia.setDiasRestantes(Math.max(diasRestantes, 0));

        return repositorio.actualizar(membresia);
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        return repositorio.eliminar(id);
    }
    @Override
    public MembresiaVista obtenerVistaPorId(int id) throws SQLException {
        return repositorio.obtenerVistaPorId(id);
    }

    @Override
    public List<MembresiaVista> listarPorCedula(String cedula) throws SQLException {
        return repositorio.listarPorCedula(cedula);
    }

    @Override
    public List<MembresiaVista> listarPorEntrenadorYCedula(int idEntrenador, String cedula) throws SQLException {
        return repositorio.listarPorEntrenadorYCedula(idEntrenador, cedula);
    }
    @Override
    public List<MembresiaVista> listarPorNombreApellido(String nombre, String apellido) throws SQLException {
        return repositorio.listarPorNombreApellido(nombre, apellido);
    }

    @Override
    public List<MembresiaVista> listarPorEntrenadorYNombreApellido(int idEntrenador, String nombre, String apellido) throws SQLException {
        return repositorio.listarPorEntrenadorYNombreApellido(idEntrenador, nombre, apellido);
    }
}
