package model;

import java.util.Date;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contraseña; // Cambio de "password" a "contraseña"
    private String rol;
    private String numeroEstudiante;
    private Date fechaRegistro;

    // Constructor para creación de usuario con todos los campos
    public Usuario(int id, String nombre, String correo, String contraseña, String rol, String numeroEstudiante, Date fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
        this.numeroEstudiante = numeroEstudiante;
        this.fechaRegistro = fechaRegistro;
    }

    // Constructor básico para autenticación
    public Usuario(int id, String nombre, String correo, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContraseña() { return contraseña; } // Cambio a "getContraseña"
    public void setContraseña(String contraseña) { this.contraseña = contraseña; } // Cambio a "setContraseña"

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getNumeroEstudiante() { return numeroEstudiante; }
    public void setNumeroEstudiante(String numeroEstudiante) { this.numeroEstudiante = numeroEstudiante; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
