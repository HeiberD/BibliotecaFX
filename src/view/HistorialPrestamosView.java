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

import java.util.List;

public class HistorialPrestamosView {

    private PrestamoController prestamoController;

    public HistorialPrestamosView() {
        prestamoController = new PrestamoController();
    }

    public void showHistorialWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // 🔹 **Tabla para mostrar el historial de préstamos**
        TableView<Prestamo> tablaHistorial = new TableView<>();

        // 🔸 **Columnas de la tabla**
        TableColumn<Prestamo, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Prestamo, Integer> colUsuarioId = new TableColumn<>("Usuario ID");
        colUsuarioId.setCellValueFactory(new PropertyValueFactory<>("usuarioId"));

        TableColumn<Prestamo, Integer> colLibroId = new TableColumn<>("Libro ID");
        colLibroId.setCellValueFactory(new PropertyValueFactory<>("libroId"));

        TableColumn<Prestamo, String> colFechaPrestamo = new TableColumn<>("Fecha Préstamo");
        colFechaPrestamo.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));

        TableColumn<Prestamo, String> colFechaDevolucion = new TableColumn<>("Fecha Devolución");
        colFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));

        // 🔸 **Agregar columnas a la tabla**
        tablaHistorial.getColumns().addAll(colId, colUsuarioId, colLibroId, colFechaPrestamo, colFechaDevolucion);

        // 🔹 **Cargar datos desde la tabla 'historial_prestamos' en la base de datos**
        List<Prestamo> listaHistorial = prestamoController.obtenerHistorialCompleto(); // 🔴 Se llama al nuevo método
        ObservableList<Prestamo> historialObservable = FXCollections.observableArrayList(listaHistorial);
        tablaHistorial.setItems(historialObservable);

        // 🔹 **Agregar la tabla al layout**
        layout.getChildren().add(tablaHistorial);

        // 🔹 **Configurar y mostrar la ventana**
        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Historial de Préstamos");
        stage.show();
    }
}
