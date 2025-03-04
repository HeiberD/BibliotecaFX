package model;

import java.util.Date;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int stock;
    private String categoria;
    private Date fechaPublicacion; // Nuevo campo

    // Constructor actualizado
    public Libro(int id, String titulo, String autor, int stock, String categoria, Date fechaPublicacion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.stock = stock;
        this.categoria = categoria;
        this.fechaPublicacion = fechaPublicacion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Date getFechaPublicacion() { return fechaPublicacion; } // Nuevo getter
    public void setFechaPublicacion(Date fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; } // Nuevo setter
}
