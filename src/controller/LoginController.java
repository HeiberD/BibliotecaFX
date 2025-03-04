package controller;

import db.DBHelper;
import model.Usuario;
import model.SesionUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    // Método para autenticar al usuario con la base de datos y guardar sesión
    public boolean autenticarUsuario(Usuario usuario) {
        String sql = "SELECT id, nombre, correo, contraseña, rol FROM usuarios WHERE correo = ? AND contraseña = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getCorreo());
            stmt.setString(2, usuario.getContraseña());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Se obtiene la información del usuario autenticado
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String contraseña = rs.getString("contraseña");
                String rol = rs.getString("rol");

                // Crear un objeto Usuario con los datos
                Usuario usuarioAutenticado = new Usuario(id, nombre, correo, contraseña, rol, null, null);

                // Guardar el usuario en la sesión
                SesionUsuario.getInstancia().iniciarSesion(usuarioAutenticado);

                System.out.println("Usuario autenticado: " + usuario.getCorreo() + " - Rol: " + rol);
                return true;
            } else {
                System.out.println("Credenciales incorrectas para el usuario: " + usuario.getCorreo());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al autenticar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
