package org.example.modelos;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 27-03-2025

import java.sql.Timestamp;

public class AsistenciaVista {
    private int idAsistencia;
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String cedula;
    private String rol;
    private Timestamp fechaAsistencia;
    private String tipoAsistencia;
    private String nombreRegistrador;
    private String apellidoRegistrador;
    private String rolRegistrador;
    private String fechaAsistenciaFormatted; // Solo para el formulario web

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula; }
    public String getCedula() { return cedula; }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

    public String getNombreRegistrador() {
        return nombreRegistrador;
    }

    public void setNombreRegistrador(String nombreRegistrador) {
        this.nombreRegistrador = nombreRegistrador;
    }

    public String getApellidoRegistrador() {
        return apellidoRegistrador;
    }

    public void setApellidoRegistrador(String apellidoRegistrador) {
        this.apellidoRegistrador = apellidoRegistrador;
    }

    public String getRolRegistrador() {
        return rolRegistrador;
    }

    public void setRolRegistrador(String rolRegistrador) {
        this.rolRegistrador = rolRegistrador;
    }
    public String getFechaAsistenciaFormatted() {
        return fechaAsistenciaFormatted;
    }

    public void setFechaAsistenciaFormatted(String fechaAsistenciaFormatted) {
        this.fechaAsistenciaFormatted = fechaAsistenciaFormatted;
    }

}