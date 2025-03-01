package controller;

import db.DBHelper;
import model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibroController {

    // Método para agregar un libro a la base de datos
    public void agregarLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, stock, categoria_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBHelper.getConnection();  // Obtener la conexión
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer los valores del PreparedStatement
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setInt(3, libro.getStock());
            stmt.setString(4, libro.getCategoria());

            // Ejecutar la consulta de inserción
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Libro agregado: " + libro.getTitulo());
            }

        } catch (SQLException e) {
            System.out.println("Error al agregar libro: " + e.getMessage());
            e.printStackTrace();  // Mostrar detalles de la excepción si ocurre un error
        }
    }
}
