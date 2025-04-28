package view;

import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.SesionUsuario;
import model.Usuario;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.util.Arrays;
import java.util.List;
import javafx.scene.text.Font;
import java.util.Objects;


public class LoginView {

    private LoginController loginController;

    public LoginView() {
        loginController = new LoginController();
    }

    public void showLoginWindow(Stage primaryStage) {
        Font.loadFont(
                Objects.requireNonNull(getClass().getResource("/fonts/KantumruyPro-Regular.ttf")).toExternalForm(), 12
        );

        VBox layout = new VBox(10);

        TextField correoField = new TextField();
        correoField.setPromptText("Correo");

        PasswordField contrasenaField = new PasswordField();
        contrasenaField.setPromptText("Contraseña");

        Button loginButton = new Button("Iniciar sesión");

        loginButton.setOnAction(event -> {
            String correo = correoField.getText();
            String contrasena = contrasenaField.getText();

            Usuario usuario = new Usuario(0, "", correo, contrasena);

            boolean autenticado = loginController.autenticarUsuario(usuario);
            if (autenticado) {
                showMainPage(primaryStage);
            } else {
                System.out.println("Credenciales incorrectas");
            }
        });

        layout.getChildren().addAll(correoField, contrasenaField, loginButton);
        Scene scene = new Scene(layout, 400, 250);
        scene.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void showMainPage(Stage primaryStage) {
        Font.loadFont(
                Objects.requireNonNull(getClass().getResource("/fonts/KantumruyPro-Regular.ttf")).toExternalForm(), 12
        );

        Usuario usuario = SesionUsuario.getInstancia().getUsuarioLogueado();
        if (usuario == null) {
            System.out.println("Error: No hay usuario autenticado.");
            return;
        }

        String rol = usuario.getRol();
        BorderPane mainLayout = new BorderPane();

        Label headerLabel = new Label("Bienvenido a BibliotecaFX - Rol: " + rol);
        headerLabel.setStyle("-fx-font-family: 'Kantumruy Pro';");
        HBox header = new HBox(headerLabel);
        header.setId("header");


        VBox sidebar = new VBox(10);
        sidebar.setStyle("-fx-font-family: 'Kantumruy Pro'");
        sidebar.setPadding(new Insets(10));
        sidebar.setPadding(new Insets(40, 10, 10, 10));
        sidebar.setId("sidebar");
        sidebar.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.25)); // 25% del ancho


        GridPane centerContent = new GridPane();
        centerContent.setHgap(20);
        centerContent.setVgap(20);
        centerContent.setPadding(new Insets(20));
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setStyle("-fx-background-color: #FAFAFA;"); /*color contenedor detras tarjetas */

        Label footer = new Label("BibliotecaFX © 2025");
        footer.setId("footer");
        footer.setMaxWidth(Double.MAX_VALUE); // 💥 Hace que el Label se expanda en el BorderPane

        if ("ADMIN".equals(rol)) {
            String[][] opciones = {
                    {"Gestionar Libros", "/images/libros.png"},
                    {"Gestionar Usuarios", "/images/usuarios.png"},
                    {"Gestionar Préstamos", "/images/prestamos.png"},
                    {"Gestionar Estudiantes", "/images/estudiantes.png"},
                    {"Gestionar Devoluciones", "/images/devoluciones.png"},
                    {"Historial de Préstamos", "/images/historial.png"}
            };

            for (int i = 0; i < opciones.length; i++) {
                String nombre = opciones[i][0];
                String ruta = opciones[i][1];

                VBox tarjeta = new VBox(10);
                tarjeta.getStyleClass().add("tarjeta-dashboard");

                ImageView img = new ImageView(new Image(getClass().getResource(ruta).toExternalForm()));
                img.setFitWidth(100);
                img.setFitHeight(100);

                Label label = new Label(nombre);
                label.getStyleClass().add("tarjeta-label");

                tarjeta.getChildren().addAll(img, label);
                tarjeta.setAlignment(Pos.CENTER);

                final int index = i;
                tarjeta.setOnMouseClicked(e -> {
                    switch (index) {
                        case 0 -> new LibroView().showLibroWindow();
                        case 1 -> new UsuarioView().showUsuarioWindow();
                        case 2 -> new PrestamoView().showPrestamoWindow();
                        case 3 -> new GestionEstudiantesView().showGestionEstudiantesWindow();
                        case 4 -> new GestionDevolucionesAdminView().showGestionDevolucionesAdminWindow();
                        case 5 -> new HistorialPrestamosView().showHistorialWindow();
                    }
                });

                centerContent.add(tarjeta, i % 3, i / 3);
            }

            Button btnLibros = new Button("Gestionar Libros");
            btnLibros.setOnAction(e -> new LibroView().showLibroWindow());

            Button btnUsuarios = new Button("Gestionar Usuarios");
            btnUsuarios.setOnAction(e -> new UsuarioView().showUsuarioWindow());

            Button btnPrestamos = new Button("Gestionar Préstamos");
            btnPrestamos.setOnAction(e -> new PrestamoView().showPrestamoWindow());

            Button btnGestionEstudiantes = new Button("Gestionar Estudiantes");
            btnGestionEstudiantes.setOnAction(e -> new GestionEstudiantesView().showGestionEstudiantesWindow());

            Button btnGestionDevoluciones = new Button("Gestionar Devoluciones");
            btnGestionDevoluciones.setOnAction(e -> new GestionDevolucionesAdminView().showGestionDevolucionesAdminWindow());

            Button btnHistorial = new Button("Historial de Préstamos");
            btnHistorial.setOnAction(e -> new HistorialPrestamosView().showHistorialWindow());

            Button btnVerificarMora = new Button("Verificar Mora");
            btnVerificarMora.setOnAction(e -> new VerificarMoraView().showVerificarMoraWindow());

            // Aplicar ancho con bucle
            List<Button> botonesSidebar = Arrays.asList(
                    btnLibros, btnUsuarios, btnPrestamos,
                    btnGestionEstudiantes, btnGestionDevoluciones,
                    btnHistorial, btnVerificarMora
            );

            for (Button b : botonesSidebar) {
                b.setPrefWidth(200);
                b.getStyleClass().add("button-sidebar");
            }

            sidebar.getChildren().addAll(
                    btnLibros,
                    btnUsuarios,
                    btnPrestamos,
                    btnGestionEstudiantes,
                    btnGestionDevoluciones,
                    btnHistorial,
                    btnVerificarMora
            );
        } else if ("ESTUDIANTE".equals(rol)) {
            Button btnLibrosDisponibles = new Button("Ver Libros Disponibles");
            btnLibrosDisponibles.setOnAction(e -> new LibrosDisponiblesView().showLibrosDisponiblesWindow());

            Button btnMisPrestamos = new Button("Mis Préstamos");
            btnMisPrestamos.setOnAction(e -> new PrestamosEstudianteView().showPrestamosEstudianteWindow());

            Button btnDevolucion = new Button("Gestionar Devolución");
            btnDevolucion.setOnAction(e -> new DevolucionView().showDevolucionWindow());

            Button btnPerfil = new Button("Mi Perfil");
            btnPerfil.setOnAction(e -> new PerfilEstudianteView().showPerfilEstudianteWindow());

            sidebar.getChildren().addAll(btnLibrosDisponibles, btnMisPrestamos, btnDevolucion, btnPerfil);
        }

        mainLayout.setTop(header);
        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(centerContent);
        mainLayout.setBottom(footer);

        Scene scene = new Scene(mainLayout, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/login.css").toExternalForm());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        primaryStage.setScene(scene);
        primaryStage.setTitle("BibliotecaFX - Inicio");
        primaryStage.show();
    }
}
