package view;

import controller.LoginController;
import model.Usuario;
import model.SesionUsuario;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import view.PrestamosEstudianteView;
import view.DevolucionView;

public class LoginView {

    private LoginController loginController;

    public LoginView() {
        loginController = new LoginController();
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
                System.out.println("Login exitoso");
                showMainPage(primaryStage);  // Llamar al método que muestra la página principal según el rol
            } else {
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

    // Método para mostrar la página principal según el rol del usuario autenticado
    private void showMainPage(Stage primaryStage) {
        // Obtener el usuario autenticado desde la sesión
        Usuario usuario = SesionUsuario.getInstancia().getUsuarioLogueado();
        if (usuario == null) {
            System.out.println("Error: No hay usuario autenticado.");
            return;
        }

        String rol = usuario.getRol();
        System.out.println("Mostrando menú para el rol: " + rol);

        VBox layout = new VBox(10);

        if ("ADMIN".equals(rol)) {
            // Opciones solo para el ADMIN
            Button btnLibros = new Button("Gestionar Libros");
            btnLibros.getStyleClass().add("btn-primary");
            btnLibros.setOnAction(event -> {
                LibroView libroView = new LibroView();
                libroView.showLibroWindow();
            });

            Button btnUsuarios = new Button("Gestionar Usuarios");
            btnUsuarios.getStyleClass().add("btn-primary");
            btnUsuarios.setOnAction(event -> {
                UsuarioView usuarioView = new UsuarioView();
                usuarioView.showUsuarioWindow();
            });

            Button btnPrestamos = new Button("Gestionar Préstamos");
            btnPrestamos.getStyleClass().add("btn-primary");
            btnPrestamos.setOnAction(event -> {
                PrestamoView prestamoView = new PrestamoView();
                prestamoView.showPrestamoWindow();
            });

            Button btnHistorial = new Button("Historial de Préstamos");
            btnHistorial.getStyleClass().add("btn-primary");
            btnHistorial.setOnAction(event -> {
                HistorialPrestamosView historialView = new HistorialPrestamosView();
                historialView.showHistorialWindow();
            });

            Button btnGestionEstudiantes = new Button("Gestionar Estudiantes");
            btnGestionEstudiantes.getStyleClass().add("btn-primary");
            btnGestionEstudiantes.setOnAction(event -> {
                GestionEstudiantesView gestionEstudiantesView = new GestionEstudiantesView();
                gestionEstudiantesView.showGestionEstudiantesWindow();
            });

            // Agregar el nuevo botón al layout
            layout.getChildren().addAll(btnLibros, btnUsuarios, btnPrestamos, btnHistorial, btnGestionEstudiantes);
        } else if ("ESTUDIANTE".equals(rol)) {
            // Opciones solo para el ESTUDIANTE
            Button btnLibrosDisponibles = new Button("Ver Libros Disponibles");
            btnLibrosDisponibles.getStyleClass().add("btn-primary");
            btnLibrosDisponibles.setOnAction(event -> {
                LibrosDisponiblesView librosView = new LibrosDisponiblesView();
                librosView.showLibrosDisponiblesWindow();
            });

            Button btnMisPrestamos = new Button("Mis Préstamos");
            btnMisPrestamos.getStyleClass().add("btn-primary");
            btnMisPrestamos.setOnAction(event -> {
                PrestamosEstudianteView prestamosView = new PrestamosEstudianteView();
                prestamosView.showPrestamosEstudianteWindow();
            });

            Button btnDevolucion = new Button("Gestionar Devolución");
            btnDevolucion.getStyleClass().add("btn-primary");
            btnDevolucion.setOnAction(event -> {
                DevolucionView devolucionView = new DevolucionView();
                devolucionView.showDevolucionWindow();
            });

            layout.getChildren().addAll(btnLibrosDisponibles, btnMisPrestamos, btnDevolucion);
        } else {
            System.out.println("Rol desconocido. No se pueden mostrar opciones.");
        }

        // Crear y configurar la escena
        Scene scene = new Scene(layout, 300, 300);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        // Cambiar la escena a la ventana principal
        primaryStage.setTitle("BibliotecaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
