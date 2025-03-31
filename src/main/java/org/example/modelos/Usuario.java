package org.example.modelos;
//Desarrollado por David Jonathan Yepez Proaño
//Fecha de creación 27-03-2025

public class Usuario {
    private int id;           // Identificador único del usuario (autoincremental)
    private String nombre;    // Nombre del usuario
    private String apellido;  // Apellido del usuario
    private String usuario;   // Nombre de usuario (único)
    private String clave;     // Contraseña del usuario
    private String rol;       // Rol del usuario (Cliente, Entrenador, Administrador)
    private String correo;    // Correo del usuario
    private String telefono;  // Teléfono del usuario
    private String cedula;    // Cédula del usuario (única)
    private String direccion; // Dirección del usuario

    // Constructor vacío
    public Usuario() {

    }

    // Constructor para crear un nuevo usuario (sin ID ya que es autoincremental)
    public Usuario(String nombre, String apellido, String usuario, String clave, String rol,
                   String correo, String telefono, String cedula, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
        this.correo = correo;
        this.telefono = telefono;
        this.cedula = cedula;
        this.direccion = direccion;
    }

    // Getters y setters para cada campo

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
