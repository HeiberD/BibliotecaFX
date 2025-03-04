package view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrestamosEstudianteView {
    public void showPrestamosEstudianteWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.getChildren().add(new Label("Aquí se mostrarán los préstamos del estudiante"));

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Mis Préstamos");
        stage.show();
    }
}
