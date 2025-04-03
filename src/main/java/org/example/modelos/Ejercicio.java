package org.example.modelos;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025
public class Ejercicio {
    private int id;
    private int idRutina;
    private String nombre;
    private int repeticiones;
    private int series;
    private int tiempo;
    private int descanso;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdRutina() { return idRutina; }
    public void setIdRutina(int idRutina) { this.idRutina = idRutina; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getRepeticiones() { return repeticiones; }
    public void setRepeticiones(int repeticiones) { this.repeticiones = repeticiones; }
    public int getSeries() { return series; }
    public void setSeries(int series) { this.series = series; }
    public int getTiempo() { return tiempo; }
    public void setTiempo(int tiempo) { this.tiempo = tiempo; }
    public int getDescanso() { return descanso; }
    public void setDescanso(int descanso) { this.descanso = descanso; }
}