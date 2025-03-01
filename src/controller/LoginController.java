package controller;

import db.DBHelper;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    // Método para autenticar al usuario con la base de datos
    public boolean autenticarUsuario(Usuario usuario) {
        // Consulta SQL para verificar las credenciales
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";

        try (Connection conn = DBHelper.getConnection();  // Obtener la conexión
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer los parámetros en la consulta
            stmt.setString(1, usuario.getCorreo());
            stmt.setString(2, usuario.getContraseña());

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            // Si se encuentra un usuario que coincide con el correo y la contraseña, se autentica
            if (rs.next()) {
                System.out.println("Usuario autenticado: " + usuario.getCorreo());
                return true;
            } else {
                System.out.println("Credenciales incorrectas para el usuario: " + usuario.getCorreo());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al autenticar usuario: " + e.getMessage());
            e.printStackTrace();  // Mostrar detalles del error
            return false;
        }
    }
}
