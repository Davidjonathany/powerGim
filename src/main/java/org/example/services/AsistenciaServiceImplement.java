package org.example.services;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

import org.example.modelos.Asistencia;
import org.example.modelos.AsistenciaVista;
import org.example.repositorio.AsistenciaRepositorio;
import java.util.List;
import java.sql.Connection;

public class AsistenciaServiceImplement implements AsistenciaService {
    private AsistenciaRepositorio asistenciaRepositorio;

    public AsistenciaServiceImplement(Connection conn) {
        this.asistenciaRepositorio = new AsistenciaRepositorio(conn);
    }

    @Override
    public void agregar(Asistencia asistencia) {
        asistenciaRepositorio.agregar(asistencia); // Guarda la asistencia en la base de datos
    }

    @Override
    public List<Asistencia> listar() {
        return asistenciaRepositorio.listarAsistenciasNormales(); // Crea este método en el repositorio
    }

    @Override
    public List<AsistenciaVista> listarConDetalles() {
        return asistenciaRepositorio.listar(); // Retorna la lista de la vista
    }

    @Override
    public Asistencia obtener(int idAsistencia) {
        return asistenciaRepositorio.porId(idAsistencia);  // Obtiene la asistencia por su ID
    }
}
