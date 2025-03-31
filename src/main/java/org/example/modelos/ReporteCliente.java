package org.example.modelos;

public class ReporteCliente {
    // Datos Cliente
    private String nombreCliente;
    private int idCliente;
    private String contactoCliente;
    private String correo;

    // Datos Membresía
    private String tipoMembresia;
    private String fechaInicio;
    private String fechaVencimiento;
    private int diasRestantes;
    private String estadoMembresia;

    // Datos Entrenador
    private String nombreEntrenador;
    private String contactoEntrenador;

    // Datos Asistencia y Rutina
    private String fechaAsistencia;
    private String tipoEntrenamiento;
    private String ejercicio;
    private int repeticiones;
    private double peso;
    private String tiempo;

    // Getters y Setters (completos)
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getContactoCliente() { return contactoCliente; }
    public void setContactoCliente(String contactoCliente) { this.contactoCliente = contactoCliente; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTipoMembresia() { return tipoMembresia; }
    public void setTipoMembresia(String tipoMembresia) { this.tipoMembresia = tipoMembresia; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public int getDiasRestantes() { return diasRestantes; }
    public void setDiasRestantes(int diasRestantes) { this.diasRestantes = diasRestantes; }

    public String getEstadoMembresia() { return estadoMembresia; }
    public void setEstadoMembresia(String estadoMembresia) { this.estadoMembresia = estadoMembresia; }

    public String getNombreEntrenador() { return nombreEntrenador; }
    public void setNombreEntrenador(String nombreEntrenador) { this.nombreEntrenador = nombreEntrenador; }

    public String getContactoEntrenador() { return contactoEntrenador; }
    public void setContactoEntrenador(String contactoEntrenador) { this.contactoEntrenador = contactoEntrenador; }

    public String getFechaAsistencia() { return fechaAsistencia; }
    public void setFechaAsistencia(String fechaAsistencia) { this.fechaAsistencia = fechaAsistencia; }

    public String getTipoEntrenamiento() { return tipoEntrenamiento; }
    public void setTipoEntrenamiento(String tipoEntrenamiento) { this.tipoEntrenamiento = tipoEntrenamiento; }

    public String getEjercicio() { return ejercicio; }
    public void setEjercicio(String ejercicio) { this.ejercicio = ejercicio; }

    public int getRepeticiones() { return repeticiones; }
    public void setRepeticiones(int repeticiones) { this.repeticiones = repeticiones; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public String getTiempo() { return tiempo; }
    public void setTiempo(String tiempo) { this.tiempo = tiempo; }
}