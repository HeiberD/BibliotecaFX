package view;

import controller.UsuarioController;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Usuario;
import java.util.Date;

public class UsuarioView {

    private UsuarioController usuarioController;

    public UsuarioView() {
        usuarioController = new UsuarioController();
    }

    public void showUsuarioWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // Campos de texto para ingresar la información del usuario
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");

        TextField correoField = new TextField();
        correoField.setPromptText("Correo");

        PasswordField contraseñaField = new PasswordField();
        contraseñaField.setPromptText("Contraseña");

        // 🔹 **Campo para seleccionar el rol**
        ComboBox<String> rolComboBox = new ComboBox<>(FXCollections.observableArrayList("ADMIN", "ESTUDIANTE"));
        rolComboBox.setPromptText("Seleccione el rol");

        // 🔹 **Campo para el número de estudiante (solo si es ESTUDIANTE)**
        TextField numeroEstudianteField = new TextField();
        numeroEstudianteField.setPromptText("Número de Estudiante");
        numeroEstudianteField.setDisable(true); // Se desactiva por defecto

        // Cambiar estado del campo número de estudiante según el rol seleccionado
        rolComboBox.setOnAction(event -> {
            if ("ESTUDIANTE".equals(rolComboBox.getValue())) {
                numeroEstudianteField.setDisable(false);
            } else {
                numeroEstudianteField.setDisable(true);
                numeroEstudianteField.clear();
            }
        });

        // Botón para agregar usuario
        Button addButton = new Button("Agregar Usuario");

        // Acción al presionar el botón
        addButton.setOnAction(event -> {
            String nombre = nombreField.getText();
            String correo = correoField.getText();
            String contraseña = contraseñaField.getText();
            String rol = rolComboBox.getValue();
            String numeroEstudiante = numeroEstudianteField.getText();

            // Validaciones básicas
            if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || rol == null) {
                System.out.println("Todos los campos son obligatorios, excepto el número de estudiante.");
                return;
            }

            // Si el rol es ESTUDIANTE, validar el número de estudiante
            if ("ESTUDIANTE".equals(rol) && numeroEstudiante.isEmpty()) {
                System.out.println("El número de estudiante es obligatorio para los estudiantes.");
                return;
            }

            // Crear un nuevo usuario con la información ingresada
            Usuario nuevoUsuario = new Usuario(0, nombre, correo, contraseña, rol, numeroEstudiante, new Date());


            // Llamar al controlador para agregar el usuario
            usuarioController.agregarUsuario(nuevoUsuario);
        });

        layout.getChildren().addAll(nombreField, correoField, contraseñaField, rolComboBox, numeroEstudianteField, addButton);

        // Crear y configurar la escena
        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Gestión de Usuarios");
        stage.show();
    }
}
