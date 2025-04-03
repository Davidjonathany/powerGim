package org.example.modelos;
// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 30-03-2025
import java.sql.Date;

public class Membresia {
    private int id;
    private int idCliente;
    private String tipo;
    private Date fechaInicio;
    private Date fechaVencimiento;
    private int diasRestantes;
    private String estado;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public int getDiasRestantes() { return diasRestantes; }
    public void setDiasRestantes(int diasRestantes) { this.diasRestantes = diasRestantes; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}