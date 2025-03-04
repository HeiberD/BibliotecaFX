package model;

import java.util.Date;

public class Prestamo {
    private int id;
    private int usuarioId;
    private int libroId;
    private String usuarioNombre; // Nuevo atributo para mostrar el nombre del usuario
    private String libroTitulo;   // Nuevo atributo para mostrar el título del libro
    private Date fechaPrestamo;
    private Date fechaDevolucion;

    // Constructor para registro de préstamos (usa IDs)
    public Prestamo(int id, int usuarioId, int libroId, Date fechaPrestamo, Date fechaDevolucion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.libroId = libroId;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Constructor para mostrar el historial (usa nombres y títulos en vez de IDs)
    public Prestamo(int id, String usuarioNombre, String libroTitulo, Date fechaPrestamo, Date fechaDevolucion) {
        this.id = id;
        this.usuarioNombre = usuarioNombre;
        this.libroTitulo = libroTitulo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getLibroId() { return libroId; }
    public void setLibroId(int libroId) { this.libroId = libroId; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public String getLibroTitulo() { return libroTitulo; }
    public void setLibroTitulo(String libroTitulo) { this.libroTitulo = libroTitulo; }

    public Date getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(Date fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public Date getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(Date fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
}
