package org.example.modelos;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 29-03-2025

public class Rutina {
    private int id;
    private int idCliente;
    private int idEntrenador;
    private String tipoEntrenamiento;
    private String observaciones;

    // Constructor vacío
    public Rutina() {
    }

    // Constructor con parámetros
    public Rutina(int id, int idCliente, int idEntrenador, String tipoEntrenamiento, String observaciones) {
        this.id = id;
        this.idCliente = idCliente;
        this.idEntrenador = idEntrenador;
        this.tipoEntrenamiento = tipoEntrenamiento;
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(int idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public String getTipoEntrenamiento() {
        return tipoEntrenamiento;
    }

    public void setTipoEntrenamiento(String tipoEntrenamiento) {
        this.tipoEntrenamiento = tipoEntrenamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // Método toString()
    @Override
    public String toString() {
        return "Rutina{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", idEntrenador=" + idEntrenador +
                ", tipoEntrenamiento='" + tipoEntrenamiento + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}