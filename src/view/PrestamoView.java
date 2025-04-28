package view;

import controller.PrestamoController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Prestamo;

import java.sql.Date;
import java.time.LocalDate;

public class PrestamoView {

    private PrestamoController prestamoController;

    public PrestamoView() {
        prestamoController = new PrestamoController();
    }

    public void showPrestamoWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // Campos de texto para ingresar los datos del préstamo
        TextField usuarioIdField = new TextField();
        usuarioIdField.setPromptText("ID Usuario");

        TextField libroIdField = new TextField();
        libroIdField.setPromptText("ID Libro");

        DatePicker fechaPrestamoField = new DatePicker();
        fechaPrestamoField.setPromptText("Fecha de Préstamo");

        // Botón para agregar préstamo
        Button addButton = new Button("Registrar Préstamo");

        // Acción al presionar el botón
        addButton.setOnAction(event -> {
            try {
                // Obtener los valores de los campos
                String usuarioIdText = usuarioIdField.getText();
                String libroIdText = libroIdField.getText();

                // Validar que los campos obligatorios no estén vacíos
                if (usuarioIdText.isEmpty() || libroIdText.isEmpty() || fechaPrestamoField.getValue() == null) {
                    System.out.println("Por favor, complete los campos obligatorios.");
                    return;
                }

                // Validar que los ID sean enteros
                if (!usuarioIdText.matches("\\d+") || !libroIdText.matches("\\d+")) {
                    System.out.println("Por favor, ingrese valores válidos para los ID de usuario y libro.");
                    return;
                }

                // Convertir los campos de texto a enteros
                int usuarioId = Integer.parseInt(usuarioIdText);
                int libroId = Integer.parseInt(libroIdText);

                // Obtener la fecha de préstamo (Obligatoria)
                LocalDate fechaPrestamo = fechaPrestamoField.getValue();

                // Crear un nuevo objeto Prestamo con los datos
                Prestamo nuevoPrestamo = new Prestamo(0, usuarioId, libroId, Date.valueOf(fechaPrestamo), null);

                // ✅ Llamar al controlador para registrar el préstamo con el usuarioId ingresado
                prestamoController.registrarPrestamo(usuarioId, libroId);

                System.out.println("Préstamo registrado exitosamente para el usuario ID: " + usuarioId);

            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese valores válidos para los ID de usuario y libro.");
            }
        });

        // Añadir los controles al layout
        layout.getChildren().addAll(usuarioIdField, libroIdField, fechaPrestamoField, addButton);

        // Crear y configurar la escena
        Scene scene = new Scene(layout, 400, 250);
        stage.setScene(scene);
        stage.setTitle("Préstamos");
        stage.show();
    }
}
