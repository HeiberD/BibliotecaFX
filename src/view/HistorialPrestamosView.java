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

        TableColumn<Prestamo, String> colUsuarioNombre = new TableColumn<>("Usuario");
        colUsuarioNombre.setCellValueFactory(new PropertyValueFactory<>("usuarioNombre")); // ✅ Se usa usuarioNombre

        TableColumn<Prestamo, String> colLibroTitulo = new TableColumn<>("Libro");
        colLibroTitulo.setCellValueFactory(new PropertyValueFactory<>("libroTitulo")); // ✅ Se usa libroTitulo

        TableColumn<Prestamo, String> colFechaPrestamo = new TableColumn<>("Fecha Préstamo");
        colFechaPrestamo.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));

        TableColumn<Prestamo, String> colFechaDevolucion = new TableColumn<>("Fecha Devolución");
        colFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));

        // 🔸 **Agregar columnas a la tabla**
        tablaHistorial.getColumns().addAll(colId, colUsuarioNombre, colLibroTitulo, colFechaPrestamo, colFechaDevolucion);

        // 🔹 **Cargar datos desde la base de datos**
        List<Prestamo> listaHistorial = prestamoController.obtenerHistorialCompleto(); // 🔴 Se usa el método corregido
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
