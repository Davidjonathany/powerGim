package org.example.modelos;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025
import java.sql.Date;

public class EjercicioVista {
    private int id;
    private int idRutina;
    private String nombreCliente;
    private String apellidoCliente;
    private String cedulaCliente;
    private String nombreEjercicio;
    private int repeticiones;
    private int series;
    private int tiempo;
    private int descanso;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdRutina() { return idRutina; }
    public void setIdRutina(int idRutina) { this.idRutina = idRutina; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public String getApellidoCliente() { return apellidoCliente; }
    public void setApellidoCliente(String apellidoCliente) { this.apellidoCliente = apellidoCliente; }
    public String getCedulaCliente() { return cedulaCliente; }
    public void setCedulaCliente(String cedulaCliente) { this.cedulaCliente = cedulaCliente; }
    public String getNombreEjercicio() { return nombreEjercicio; }
    public void setNombreEjercicio(String nombreEjercicio) { this.nombreEjercicio = nombreEjercicio; }
    public int getRepeticiones() { return repeticiones; }
    public void setRepeticiones(int repeticiones) { this.repeticiones = repeticiones; }
    public int getSeries() { return series; }
    public void setSeries(int series) { this.series = series; }
    public int getTiempo() { return tiempo; }
    public void setTiempo(int tiempo) { this.tiempo = tiempo; }
    public int getDescanso() { return descanso; }
    public void setDescanso(int descanso) { this.descanso = descanso; }
}