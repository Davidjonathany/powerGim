package org.example.modelos;

// Desarrollado por David Jonathan Yepez Proaño
// Fecha de creación 01-04-2025
public class ReporteCliente {
    // Datos Cliente
    private int idCliente;
    private String nombre;
    private String apellido;
    private String usuario;
    private String correo;
    private String telefono;
    private String cedula; // Agregando la cédula

    // Datos Membresía
    private String tipoMembresia;
    private String fechaInicio;
    private String fechaVencimiento;
    private int diasRestantes;
    private String estadoMembresia;

    // Progreso de Peso
    private double pesoInicial;
    private double pesoActual;
    private double progresoPeso;
    private String ultimaActualizacionPeso;

    // Asistencias
    private int totalAsistencias;
    private String ultimaAsistencia;
    private int asistenciasUltimaSemana;
    private int asistenciasUltimoMes;

    // Rutinas y Entrenadores
    private int totalRutinas;
    private String entrenadores;

    private String rol;

    // Getters y Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

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

    public double getPesoInicial() { return pesoInicial; }
    public void setPesoInicial(double pesoInicial) { this.pesoInicial = pesoInicial; }

    public double getPesoActual() { return pesoActual; }
    public void setPesoActual(double pesoActual) { this.pesoActual = pesoActual; }

    public double getProgresoPeso() { return progresoPeso; }
    public void setProgresoPeso(double progresoPeso) { this.progresoPeso = progresoPeso; }

    public String getUltimaActualizacionPeso() { return ultimaActualizacionPeso; }
    public void setUltimaActualizacionPeso(String ultimaActualizacionPeso) { this.ultimaActualizacionPeso = ultimaActualizacionPeso; }

    public int getTotalAsistencias() { return totalAsistencias; }
    public void setTotalAsistencias(int totalAsistencias) { this.totalAsistencias = totalAsistencias; }

    public String getUltimaAsistencia() { return ultimaAsistencia; }
    public void setUltimaAsistencia(String ultimaAsistencia) { this.ultimaAsistencia = ultimaAsistencia; }

    public int getAsistenciasUltimaSemana() { return asistenciasUltimaSemana; }
    public void setAsistenciasUltimaSemana(int asistenciasUltimaSemana) { this.asistenciasUltimaSemana = asistenciasUltimaSemana; }

    public int getAsistenciasUltimoMes() { return asistenciasUltimoMes; }
    public void setAsistenciasUltimoMes(int asistenciasUltimoMes) { this.asistenciasUltimoMes = asistenciasUltimoMes; }

    public int getTotalRutinas() { return totalRutinas; }
    public void setTotalRutinas(int totalRutinas) { this.totalRutinas = totalRutinas; }

    public String getEntrenadores() { return entrenadores; }
    public void setEntrenadores(String entrenadores) { this.entrenadores = entrenadores; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}