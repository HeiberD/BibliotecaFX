package view;

import controller.UsuarioController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.SesionUsuario;
import model.Usuario;

public class PerfilEstudianteView {

    private UsuarioController usuarioController;

    public PerfilEstudianteView() {
        usuarioController = new UsuarioController();
    }

    public void showPerfilEstudianteWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // Obtener usuario logueado
        Usuario usuario = SesionUsuario.getInstancia().getUsuarioLogueado();
        if (usuario == null) {
            System.out.println("Error: No hay un estudiante autenticado.");
            return;
        }

        // 📌 Etiqueta con el nombre del usuario
        Label lblNombre = new Label("Estudiante: " + usuario.getNombre());

        // 📌 Campo para la contraseña actual
        PasswordField contraseñaActualField = new PasswordField();
        contraseñaActualField.setPromptText("Contraseña Actual");

        // 📌 Campo para la nueva contraseña
        PasswordField nuevaContraseñaField = new PasswordField();
        nuevaContraseñaField.setPromptText("Nueva Contraseña (5 números)");

        // 📌 Botón para actualizar la contraseña
        Button btnActualizarContraseña = new Button("Actualizar Contraseña");
        btnActualizarContraseña.setOnAction(event -> {
            String contraseñaActual = contraseñaActualField.getText();
            String nuevaContraseña = nuevaContraseñaField.getText();

            if (contraseñaActual.isEmpty() || nuevaContraseña.isEmpty()) {
                System.out.println("Por favor, complete todos los campos.");
                return;
            }

            // Llamar al método para actualizar la contraseña
            boolean actualizada = usuarioController.actualizarContraseña(usuario.getId(), contraseñaActual, nuevaContraseña);

            if (actualizada) {
                System.out.println("Contraseña cambiada correctamente.");
                stage.close(); // Cierra la ventana después de actualizar
            } else {
                System.out.println("No se pudo actualizar la contraseña.");
            }
        });

        // 📌 Agregar elementos al layout
        layout.getChildren().addAll(lblNombre, contraseñaActualField, nuevaContraseñaField, btnActualizarContraseña);

        // 📌 Crear y configurar la escena
        Scene scene = new Scene(layout, 400, 250);
        stage.setScene(scene);
        stage.setTitle("Perfil del Estudiante");
        stage.show();
    }
}
