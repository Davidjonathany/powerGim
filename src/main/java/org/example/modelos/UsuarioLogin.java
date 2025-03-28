package org.example.modelos;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025
public class UsuarioLogin {
    private String usuario;
    private String clave;
    private String rol;

    public UsuarioLogin(String usuario, String clave, String rol) {
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
    }

    public String getUsuario() { return usuario; }
    public String getClave() { return clave; }
    public String getRol() { return rol; }

    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setClave(String clave) { this.clave = clave; }
    public void setRol(String rol) { this.rol = rol; }
}
