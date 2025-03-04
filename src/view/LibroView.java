package view;

import controller.LibroController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Libro;

import java.sql.Date;
import java.time.LocalDate;

public class LibroView {

    private LibroController libroController;

    public LibroView() {
        libroController = new LibroController();
    }

    public void showLibroWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // Campos de texto para ingresar información del libro
        TextField tituloField = new TextField();
        tituloField.setPromptText("Título");

        TextField autorField = new TextField();
        autorField.setPromptText("Autor");

        TextField categoriaField = new TextField();
        categoriaField.setPromptText("Categoría");

        // Campo para ingresar la fecha de publicación
        DatePicker fechaPublicacionField = new DatePicker();
        fechaPublicacionField.setPromptText("Fecha de Publicación");

        // Botón para agregar libro
        Button addButton = new Button("Agregar Libro");

        // Acción al presionar el botón
        addButton.setOnAction(event -> {
            // Crear un nuevo libro con la información ingresada
            String titulo = tituloField.getText();
            String autor = autorField.getText();
            String categoria = categoriaField.getText();
            LocalDate fechaPublicacion = fechaPublicacionField.getValue();

            if (fechaPublicacion == null) {
                System.out.println("Por favor, seleccione una fecha de publicación.");
                return;
            }

            Libro nuevoLibro = new Libro(0, titulo, autor, 1, categoria, Date.valueOf(fechaPublicacion));

            // Llamar al controlador para agregar el libro
            libroController.agregarLibro(nuevoLibro);
        });

        layout.getChildren().addAll(tituloField, autorField, categoriaField, fechaPublicacionField, addButton);

        // Crear y configurar la escena
        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Gestión de Libros");
        stage.show();
    }
}
