package controller;

import db.DBHelper;
import model.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoController {

    // Método para registrar un préstamo en la base de datos
    public void registrarPrestamo(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (usuario_id, libro_id, fecha_prestamo, fecha_devolucion, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer los valores del PreparedStatement
            stmt.setInt(1, prestamo.getUsuarioId());
            stmt.setInt(2, prestamo.getLibroId());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));  // Convertir Date a SQL Date
            stmt.setDate(4, new java.sql.Date(prestamo.getFechaDevolucion().getTime()));  // Convertir Date a SQL Date
            stmt.setString(5, "Activo"); // Se registra como "Activo" en la tabla prestamos

            // Ejecutar la consulta de inserción
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Préstamo registrado en 'prestamos': Usuario ID " + prestamo.getUsuarioId() + ", Libro ID " + prestamo.getLibroId());
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar préstamo en 'prestamos': " + e.getMessage());
            e.printStackTrace();  // Mostrar detalles de la excepción si ocurre un error
        }
    }

    // Método para obtener el historial de préstamos desde la tabla 'prestamos'
    public List<Prestamo> obtenerHistorialCompleto() {
        List<Prestamo> historial = new ArrayList<>();
        String sql = "SELECT h.id, h.usuario_id, h.libro_id, h.fecha_prestamo, h.fecha_devolucion " +
                "FROM historial_prestamos h";

        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int usuarioId = rs.getInt("usuario_id");
                int libroId = rs.getInt("libro_id");

                // 🔹 Detectar si la fecha está en formato "YYYY-MM-DD" o en timestamp
                String fechaPrestamoStr = rs.getString("fecha_prestamo"); // Si está en formato "YYYY-MM-DD"
                String fechaDevolucionStr = rs.getString("fecha_devolucion");

                Date fechaPrestamo = null;
                Date fechaDevolucion = null;

                if (fechaPrestamoStr != null) {
                    if (fechaPrestamoStr.matches("\\d{4}-\\d{2}-\\d{2}")) { // Formato "YYYY-MM-DD"
                        fechaPrestamo = Date.valueOf(fechaPrestamoStr);
                    } else {
                        fechaPrestamo = new Date(Long.parseLong(fechaPrestamoStr)); // Convertir timestamp a Date
                    }
                }

                if (fechaDevolucionStr != null) {
                    if (fechaDevolucionStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        fechaDevolucion = Date.valueOf(fechaDevolucionStr);
                    } else {
                        fechaDevolucion = new Date(Long.parseLong(fechaDevolucionStr));
                    }
                }

                historial.add(new Prestamo(id, usuarioId, libroId, fechaPrestamo, fechaDevolucion));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historial;
    }
}