package view;

import controller.PrestamoController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Prestamo;

import java.text.SimpleDateFormat;
import java.util.List;

public class VerificarMoraView {

    private PrestamoController prestamoController;

    public VerificarMoraView() {
        prestamoController = new PrestamoController();
    }

    public void showVerificarMoraWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // ✅ Marcar los préstamos vencidos como "En mora"
        prestamoController.marcarPrestamosEnMora();

        // ✅ Obtener la lista de préstamos en mora
        List<Prestamo> prestamosEnMora = prestamoController.obtenerPrestamosEnMora();
        ObservableList<Prestamo> prestamosObservable = FXCollections.observableArrayList(prestamosEnMora);

        TableView<Prestamo> tableView = new TableView<>();

        TableColumn<Prestamo, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

        TableColumn<Prestamo, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuarioNombre()));

        TableColumn<Prestamo, String> colLibro = new TableColumn<>("Libro");
        colLibro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibroTitulo()));

        TableColumn<Prestamo, String> colFecha = new TableColumn<>("Fecha Préstamo");
        colFecha.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return new SimpleStringProperty(sdf.format(cellData.getValue().getFechaPrestamo()));
        });

        TableColumn<Prestamo, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado()));

        tableView.getColumns().addAll(colId, colUsuario, colLibro, colFecha, colEstado);
        tableView.setItems(prestamosObservable);

        layout.getChildren().add(tableView);

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Préstamos en Mora");
        stage.show();
    }
}
