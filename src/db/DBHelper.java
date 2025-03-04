package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {

    // URL de la base de datos SQLite
    private static final String URL = "jdbc:sqlite:C:/Users/heibe/AppData/Local/DBeaver/bibliotecaFX.db";

    // Variable de conexión
    private static Connection connection;

    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Crear una conexión a la base de datos
                connection = DriverManager.getConnection(URL);
                connection.setAutoCommit(true);  // Habilitar el autocommit para que los cambios se guarden automáticamente

                // Validación simple de la conexión con una consulta SQL
                String testQuery = "SELECT 1";  // Consulta simple para validar la conexión
                try (PreparedStatement stmt = connection.prepareStatement(testQuery);
                     ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {
                        int result = rs.getInt(1); // Obtener el valor "1" que devuelve la consulta
                        System.out.println("Conexión exitosa a la base de datos. Resultado: " + result);
                    }

                } catch (SQLException e) {
                    System.out.println("Error al ejecutar la consulta de validación:");
                    e.printStackTrace();  // Imprimir los detalles del error
                    throw new SQLException("Error al ejecutar la consulta de validación", e);
                }

            } catch (SQLException e) {
                System.out.println("Error al intentar conectar con la base de datos:");
                e.printStackTrace();  // Imprimir los detalles del error de conexión
                throw new SQLException("Error al conectar con la base de datos.", e);
            }
        }
        return connection;
    }

    // Método para cerrar la conexión
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión:");
            e.printStackTrace();
        }
    }
}
