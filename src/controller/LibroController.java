package controller;

import db.DBHelper;
import model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroController {

    // ✅ Método para agregar un libro a la base de datos con fecha_publicacion
    public void agregarLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, stock, categoria_id, fecha_publicacion) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setInt(3, libro.getStock());
            stmt.setString(4, libro.getCategoria());
            stmt.setDate(5, new java.sql.Date(libro.getFechaPublicacion().getTime())); // Convertir a SQL Date

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Libro agregado: " + libro.getTitulo());
            }

        } catch (SQLException e) {
            System.out.println("Error al agregar libro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ **Nuevo método para obtener libros disponibles**
    public List<Libro> obtenerLibrosDisponibles() {
        List<Libro> librosDisponibles = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, stock, categoria_id, fecha_publicacion FROM libros WHERE stock > 0";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                int stock = rs.getInt("stock");
                String categoria = rs.getString("categoria_id");
                java.util.Date fechaPublicacion = rs.getDate("fecha_publicacion"); // Obtener fecha

                librosDisponibles.add(new Libro(id, titulo, autor, stock, categoria, fechaPublicacion));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return librosDisponibles;
    }
}
