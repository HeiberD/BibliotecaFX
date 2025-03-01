package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;  // Importa LoginView

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Mostrar la ventana de Login al iniciar la aplicación
        LoginView loginView = new LoginView();
        loginView.showLoginWindow(primaryStage);  // Llamamos a showLoginWindow con el Stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}
