package controller;

import db.DBHelper;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {

    // Método para agregar un usuario a la base de datos
    public void agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, contraseña, rol, numero_estudiante, fecha_registro) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContraseña()); // Se ajusta a getPassword()
            stmt.setString(4, usuario.getRol());
            stmt.setString(5, usuario.getNumeroEstudiante());
            stmt.setDate(6, new java.sql.Date(usuario.getFechaRegistro().getTime()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuario agregado: " + usuario.getNombre());
            }

        } catch (SQLException e) {
            System.out.println("Error al agregar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ Método para editar un usuario
    public boolean editarUsuario(int id, String nombre, String correo, String numeroEstudiante) {
        String sql = "UPDATE usuarios SET nombre = ?, correo = ?, numero_estudiante = ? WHERE id = ? AND rol = 'ESTUDIANTE'";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, numeroEstudiante);
            stmt.setInt(4, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Usuario actualizado correctamente.");
                return true;
            } else {
                System.out.println("No se encontró el usuario o no es un estudiante.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Método para eliminar un usuario
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ? AND rol = 'ESTUDIANTE'";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Usuario eliminado correctamente.");
                return true;
            } else {
                System.out.println("No se encontró el usuario o no es un estudiante.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar la contraseña de un estudiante
    public boolean actualizarContraseña(int usuarioId, String contraseñaActual, String nuevaContraseña) {
        // Validar que la nueva contraseña tenga exactamente 5 dígitos
        if (!nuevaContraseña.matches("\\d{5}")) {
            System.out.println("La nueva contraseña debe contener exactamente 5 números.");
            return false;
        }

        String sqlVerificar = "SELECT contraseña FROM usuarios WHERE id = ? AND rol = 'ESTUDIANTE'";
        String sqlActualizar = "UPDATE usuarios SET contraseña = ? WHERE id = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmtVerificar = conn.prepareStatement(sqlVerificar)) {

            stmtVerificar.setInt(1, usuarioId);
            ResultSet rs = stmtVerificar.executeQuery();

            if (rs.next()) {
                String contraseñaBD = rs.getString("contraseña");

                // Verificar si la contraseña actual ingresada es correcta
                if (!contraseñaBD.equals(contraseñaActual)) {
                    System.out.println("La contraseña actual es incorrecta.");
                    return false;
                }

                // Actualizar la contraseña si la verificación fue exitosa
                try (PreparedStatement stmtActualizar = conn.prepareStatement(sqlActualizar)) {
                    stmtActualizar.setString(1, nuevaContraseña);
                    stmtActualizar.setInt(2, usuarioId);

                    int rowsUpdated = stmtActualizar.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Contraseña actualizada correctamente.");
                        return true;
                    } else {
                        System.out.println("Error al actualizar la contraseña.");
                        return false;
                    }
                }
            } else {
                System.out.println("Usuario no encontrado o no tiene permiso.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar contraseña: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Método para obtener la lista de estudiantes
    public List<Usuario> obtenerEstudiantes() {
        List<Usuario> estudiantes = new ArrayList<>();
        String sql = "SELECT id, nombre, correo, numero_estudiante FROM usuarios WHERE rol = 'ESTUDIANTE'";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String numeroEstudiante = rs.getString("numero_estudiante");

                estudiantes.add(new Usuario(id, nombre, correo, "", "ESTUDIANTE", numeroEstudiante, null));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de estudiantes: " + e.getMessage());
            e.printStackTrace();
        }

        return estudiantes;
    }
}
