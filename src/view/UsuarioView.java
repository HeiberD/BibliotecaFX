package view;

import controller.UsuarioController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Usuario;

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

        // Botón para agregar usuario
        Button addButton = new Button("Agregar Usuario");

        // Acción al presionar el botón
        addButton.setOnAction(event -> {
            // Crear un nuevo usuario con la información ingresada
            String nombre = nombreField.getText();
            String correo = correoField.getText();
            String contraseña = contraseñaField.getText();
            Usuario nuevoUsuario = new Usuario(0, nombre, correo, contraseña);

            // Llamar al controlador para agregar el usuario
            usuarioController.agregarUsuario(nuevoUsuario);
        });

        layout.getChildren().addAll(nombreField, correoField, contraseñaField, addButton);

        // Crear y configurar la escena
        Scene scene = new Scene(layout, 400, 250);
        stage.setScene(scene);
        stage.setTitle("Gestión de Usuarios");
        stage.show();
    }
}
