package org.example.modelos;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de actualización 29-03-2025

public class VistaRutina {
    private int idRutina;
    private int idCliente;
    private int idEntrenador;
    private String clienteNombre;
    private String clienteApellido;
    private String clienteRol;
    private String entrenadorNombre;
    private String entrenadorApellido;
    private String entrenadorRol;
    private String tipoEntrenamiento;
    private String observaciones;

    // Constructor vacío
    public VistaRutina() {
    }

    // Constructor con parámetros completo
    public VistaRutina(int idRutina, int idCliente, int idEntrenador,
                       String clienteNombre, String clienteApellido, String clienteRol,
                       String entrenadorNombre, String entrenadorApellido, String entrenadorRol,
                       String tipoEntrenamiento, String observaciones) {
        this.idRutina = idRutina;
        this.idCliente = idCliente;
        this.idEntrenador = idEntrenador;
        this.clienteNombre = clienteNombre;
        this.clienteApellido = clienteApellido;
        this.clienteRol = clienteRol;
        this.entrenadorNombre = entrenadorNombre;
        this.entrenadorApellido = entrenadorApellido;
        this.entrenadorRol = entrenadorRol;
        this.tipoEntrenamiento = tipoEntrenamiento;
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
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

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteApellido() {
        return clienteApellido;
    }

    public void setClienteApellido(String clienteApellido) {
        this.clienteApellido = clienteApellido;
    }

    public String getClienteRol() {
        return clienteRol;
    }

    public void setClienteRol(String clienteRol) {
        this.clienteRol = clienteRol;
    }

    public String getEntrenadorNombre() {
        return entrenadorNombre;
    }

    public void setEntrenadorNombre(String entrenadorNombre) {
        this.entrenadorNombre = entrenadorNombre;
    }

    public String getEntrenadorApellido() {
        return entrenadorApellido;
    }

    public void setEntrenadorApellido(String entrenadorApellido) {
        this.entrenadorApellido = entrenadorApellido;
    }

    public String getEntrenadorRol() {
        return entrenadorRol;
    }

    public void setEntrenadorRol(String entrenadorRol) {
        this.entrenadorRol = entrenadorRol;
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

    // Método toString() actualizado
    @Override
    public String toString() {
        return "VistaRutina{" +
                "idRutina=" + idRutina +
                ", idCliente=" + idCliente +
                ", idEntrenador=" + idEntrenador +
                ", clienteNombre='" + clienteNombre + '\'' +
                ", clienteApellido='" + clienteApellido + '\'' +
                ", clienteRol='" + clienteRol + '\'' +
                ", entrenadorNombre='" + entrenadorNombre + '\'' +
                ", entrenadorApellido='" + entrenadorApellido + '\'' +
                ", entrenadorRol='" + entrenadorRol + '\'' +
                ", tipoEntrenamiento='" + tipoEntrenamiento + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}