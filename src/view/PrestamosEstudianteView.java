package view;

import controller.PrestamoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Prestamo;
import model.SesionUsuario;

import java.util.List;

public class PrestamosEstudianteView {

    private PrestamoController prestamoController;

    public PrestamosEstudianteView() {
        prestamoController = new PrestamoController();
    }

    public void showPrestamosEstudianteWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // Tabla para mostrar los préstamos del estudiante
        TableView<Prestamo> tablaPrestamos = new TableView<>();

        // Columnas de la tabla
        TableColumn<Prestamo, Integer> colId = new TableColumn<>("ID Préstamo");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Prestamo, String> colLibro = new TableColumn<>("Libro");
        colLibro.setCellValueFactory(new PropertyValueFactory<>("libroTitulo"));

        TableColumn<Prestamo, String> colFechaPrestamo = new TableColumn<>("Fecha Préstamo");
        colFechaPrestamo.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));

        // Agregar columnas a la tabla
        tablaPrestamos.getColumns().addAll(colId, colLibro, colFechaPrestamo);

        // Obtener el usuario logueado
        int usuarioId = SesionUsuario.getInstancia().getUsuarioLogueado().getId();

        // Cargar datos desde la base de datos
        List<Prestamo> listaPrestamos = prestamoController.obtenerPrestamosActivosPorUsuario(usuarioId);
        ObservableList<Prestamo> prestamosObservable = FXCollections.observableArrayList(listaPrestamos);
        tablaPrestamos.setItems(prestamosObservable);

        // Agregar la tabla al layout
        layout.getChildren().add(tablaPrestamos);

        // Configurar y mostrar la ventana
        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Mis Préstamos");
        stage.show();
    }
}
