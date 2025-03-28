package org.example.services;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

import org.example.modelos.Asistencia;
import org.example.modelos.AsistenciaVista;

import java.util.List;

public interface AsistenciaService {
    void agregar(Asistencia asistencia);
    List<Asistencia> listar();
    List<AsistenciaVista> listarConDetalles();
    Asistencia obtener(int idAsistencia); // Método abstracto
}


