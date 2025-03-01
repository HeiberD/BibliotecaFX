package view;

import controller.LoginController;
import model.Usuario;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import view.LibroView;
import view.UsuarioView;
import view.PrestamoView;
import view.HistorialPrestamosView; // Importamos la nueva vista

public class LoginView {

    private LoginController loginController;

    public LoginView() {
        loginController = new LoginController();  // Crear instancia del controlador de login
    }

    // Mostrar la ventana de login
    public void showLoginWindow(Stage primaryStage) {
        VBox layout = new VBox(10);

        // Campos de texto para ingresar el correo y la contraseña
        TextField correoField = new TextField();
        correoField.setPromptText("Correo");

        PasswordField contraseñaField = new PasswordField();
        contraseñaField.setPromptText("Contraseña");

        // Botón para autenticar el usuario
        Button loginButton = new Button("Iniciar sesión");

        // Acción al presionar el botón de login
        loginButton.setOnAction(event -> {
            String correo = correoField.getText();
            String contraseña = contraseñaField.getText();

            // Crear un objeto Usuario con las credenciales ingresadas
            Usuario usuario = new Usuario(0, "", correo, contraseña);

            // Llamar al controlador para autenticar al usuario
            boolean autenticado = loginController.autenticarUsuario(usuario);

            if (autenticado) {
                // Si el usuario es autenticado, mostrar la página principal
                System.out.println("Login exitoso");
                showMainPage(primaryStage);  // Llamar al método que muestra la página principal
            } else {
                // Si las credenciales no son válidas, mostrar un mensaje de error
                System.out.println("Credenciales incorrectas");
            }
        });

        layout.getChildren().addAll(correoField, contraseñaField, loginButton);

        // Crear y configurar la escena de login
        Scene scene = new Scene(layout, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    // Método para mostrar la página principal después de la autenticación exitosa
    private void showMainPage(Stage primaryStage) {
        // Botón para abrir la ventana de gestión de libros
        Button btnLibros = new Button("Gestionar Libros");
        btnLibros.getStyleClass().add("btn-primary");

        // Botón para abrir la ventana de gestión de usuarios
        Button btnUsuarios = new Button("Gestionar Usuarios");
        btnUsuarios.getStyleClass().add("btn-primary");

        // Botón para abrir la ventana de gestión de préstamos
        Button btnPrestamos = new Button("Gestionar Préstamos");
        btnPrestamos.getStyleClass().add("btn-primary");

        // 🔹 **Nuevo botón para ver historial de préstamos**
        Button btnHistorial = new Button("Historial de Préstamos");
        btnHistorial.getStyleClass().add("btn-primary");

        // Evento al presionar el botón de libros
        btnLibros.setOnAction(event -> {
            LibroView libroView = new LibroView();
            libroView.showLibroWindow();
        });

        // Evento al presionar el botón de usuarios
        btnUsuarios.setOnAction(event -> {
            UsuarioView usuarioView = new UsuarioView();
            usuarioView.showUsuarioWindow();
        });

        // Evento al presionar el botón de préstamos
        btnPrestamos.setOnAction(event -> {
            PrestamoView prestamoView = new PrestamoView();
            prestamoView.showPrestamoWindow();
        });

        // Evento al presionar el botón de historial de préstamos
        btnHistorial.setOnAction(event -> {
            HistorialPrestamosView historialView = new HistorialPrestamosView();
            historialView.showHistorialWindow();
        });

        // Usamos VBox para organizar los botones verticalmente
        VBox layout = new VBox(10);
        layout.getChildren().addAll(btnLibros, btnUsuarios, btnPrestamos, btnHistorial);

        // Crear y configurar la escena
        Scene scene = new Scene(layout, 300, 300);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        // Cambiar la escena a la ventana principal
        primaryStage.setTitle("BibliotecaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
