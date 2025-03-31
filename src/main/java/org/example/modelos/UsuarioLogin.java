package org.example.modelos;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

public class UsuarioLogin {
    private int id;
    private String usuario;
    private String clave;
    private String rol;
    private String cedula;

    public UsuarioLogin(int id, String usuario, String clave, String rol, String cedula) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
        this.cedula = cedula;
    }

    // Getters
    public int getId() { return id; }
    public String getUsuario() { return usuario; }
    public String getClave() { return clave; }
    public String getRol() { return rol; }
    public String getCedula() { return cedula; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setClave(String clave) { this.clave = clave; }
    public void setRol(String rol) { this.rol = rol; }
    public void setCedula(String cedula) { this.cedula = cedula; }
}
