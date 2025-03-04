package controller;

import db.DBHelper;
import model.Prestamo;
import model.SesionUsuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoController {

    // Método para registrar un préstamo en la base de datos y actualizar el stock del libro
    public boolean registrarPrestamo(int libroId) {
        String sqlPrestamo = "INSERT INTO prestamos (usuario_id, libro_id, fecha_prestamo, estado) VALUES (?, ?, ?, ?)";
        String sqlActualizarStock = "UPDATE libros SET stock = stock - 1 WHERE id = ? AND stock > 0";
        String sqlVerificarStock = "SELECT stock FROM libros WHERE id = ?";

        int usuarioId = SesionUsuario.getInstancia().getUsuarioLogueado().getId();
        // Obtener ID del usuario logueado
        java.util.Date fechaActual = new java.util.Date();

        try (Connection conn = DBHelper.getConnection()) {
            conn.setAutoCommit(false); // Iniciar transacción

            // Verificar si el libro tiene stock disponible
            try (PreparedStatement stmtVerificar = conn.prepareStatement(sqlVerificarStock)) {
                stmtVerificar.setInt(1, libroId);
                ResultSet rs = stmtVerificar.executeQuery();

                if (rs.next()) {
                    int stockActual = rs.getInt("stock");

                    if (stockActual <= 0) {
                        System.out.println("No se puede realizar el préstamo. El libro no tiene stock disponible.");
                        return false;
                    }
                } else {
                    System.out.println("El libro no existe en la base de datos.");
                    return false;
                }
            }

            // Registrar el préstamo
            try (PreparedStatement stmtPrestamo = conn.prepareStatement(sqlPrestamo)) {
                stmtPrestamo.setInt(1, usuarioId);
                stmtPrestamo.setInt(2, libroId);
                stmtPrestamo.setDate(3, new java.sql.Date(fechaActual.getTime()));
                stmtPrestamo.setString(4, "Activo");

                int rowsPrestamo = stmtPrestamo.executeUpdate();
                if (rowsPrestamo > 0) {
                    System.out.println("Préstamo registrado correctamente.");
                } else {
                    System.out.println("Error al registrar el préstamo.");
                    conn.rollback();
                    return false;
                }
            }

            // Actualizar el stock del libro
            try (PreparedStatement stmtStock = conn.prepareStatement(sqlActualizarStock)) {
                stmtStock.setInt(1, libroId);

                int rowsStock = stmtStock.executeUpdate();
                if (rowsStock > 0) {
                    System.out.println("Stock del libro actualizado.");
                } else {
                    System.out.println("Error al actualizar el stock: el libro ya no tiene unidades disponibles.");
                    conn.rollback();
                    return false;
                }
            }

            conn.commit(); // Confirmar transacción
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar préstamo y actualizar stock: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener el historial completo con nombres en lugar de IDs
    public List<Prestamo> obtenerHistorialCompleto() {
        List<Prestamo> historial = new ArrayList<>();
        String sql = "SELECT h.id, u.nombre AS usuario_nombre, l.titulo AS libro_titulo, h.fecha_prestamo, h.fecha_devolucion " +
                "FROM prestamos h " +
                "JOIN usuarios u ON h.usuario_id = u.id " +
                "JOIN libros l ON h.libro_id = l.id";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String usuarioNombre = rs.getString("usuario_nombre");
                String libroTitulo = rs.getString("libro_titulo");

                // Obtener fechas con validación para evitar valores nulos
                Date fechaPrestamo = rs.getDate("fecha_prestamo");
                Date fechaDevolucion = rs.getDate("fecha_devolucion"); // Puede ser NULL

                if (usuarioNombre == null) {
                    usuarioNombre = "Desconocido";
                }
                if (libroTitulo == null) {
                    libroTitulo = "Sin título";
                }

                System.out.println("Registro obtenido -> ID: " + id + ", Usuario: " + usuarioNombre + ", Libro: " + libroTitulo);

                historial.add(new Prestamo(id, usuarioNombre, libroTitulo, fechaPrestamo, fechaDevolucion));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historial;
    }
}
