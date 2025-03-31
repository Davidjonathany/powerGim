package org.example.modelos;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 27-03-2025

import java.sql.Timestamp;

public class Asistencia {
    private int idAsistencia;
    private int idUsuario;          // Cambiado de idCliente a idUsuario
    private int idRegistrador;      // Nuevo campo
    private Timestamp fechaAsistencia; // Cambiado de Date a Timestamp
    private String tipoAsistencia;  // Nuevo campo

    // Getters y Setters
    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRegistrador() {
        return idRegistrador;
    }

    public void setIdRegistrador(int idRegistrador) {
        this.idRegistrador = idRegistrador;
    }

    public Timestamp getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(Timestamp fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

    public String getTipoAsistencia() {
        return tipoAsistencia;
    }

    public void setTipoAsistencia(String tipoAsistencia) {
        this.tipoAsistencia = tipoAsistencia;
    }

    // Método toString() para facilitar la depuración
    @Override
    public String toString() {
        return "Asistencia{" +
                "idAsistencia=" + idAsistencia +
                ", idUsuario=" + idUsuario +
                ", idRegistrador=" + idRegistrador +
                ", fechaAsistencia=" + fechaAsistencia +
                ", tipoAsistencia='" + tipoAsistencia + '\'' +
                '}';
    }
}