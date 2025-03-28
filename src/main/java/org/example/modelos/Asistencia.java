package org.example.modelos;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025
import java.util.Date;

public class Asistencia {
    private int idAsistencia;
    private int idCliente;
    private Date fechaAsistencia;

    // Getters y Setters
    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(Date fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }
}
