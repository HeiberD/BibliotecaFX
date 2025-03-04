package controller;

import db.DBHelper;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioController {

    // Método para agregar un usuario a la base de datos
    public void agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, contraseña, rol, numero_estudiante, fecha_registro) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBHelper.getConnection();  // Obtener la conexión
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer los valores del PreparedStatement
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContraseña());  // Se cambió de getPassword() a getContraseña()
            stmt.setString(4, usuario.getRol());
            stmt.setString(5, usuario.getNumeroEstudiante());
            stmt.setDate(6, new java.sql.Date(usuario.getFechaRegistro().getTime()));

            // Ejecutar la consulta de inserción
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuario agregado: " + usuario.getNombre());
            }

        } catch (SQLException e) {
            System.out.println("Error al agregar usuario: " + e.getMessage());
            e.printStackTrace();  // Mostrar detalles de la excepción si ocurre un error
        }
    }
}
