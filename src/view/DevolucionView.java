package view;

import controller.PrestamoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Prestamo;
import model.SesionUsuario;

import java.sql.Date;
import java.util.List;

public class DevolucionView {

    private PrestamoController prestamoController;

    public DevolucionView() {
        prestamoController = new PrestamoController();
    }

    public void showDevolucionWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // Tabla para mostrar los préstamos activos del usuario logueado
        TableView<Prestamo> tablaPrestamos = new TableView<>();

        // Columnas de la tabla
        TableColumn<Prestamo, Integer> colId = new TableColumn<>("ID Préstamo");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Prestamo, String> colLibro = new TableColumn<>("Libro");
        colLibro.setCellValueFactory(new PropertyValueFactory<>("libroTitulo"));

        TableColumn<Prestamo, Date> colFechaPrestamo = new TableColumn<>("Fecha Préstamo");
        colFechaPrestamo.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));

        TableColumn<Prestamo, Date> colFechaDevolucion = new TableColumn<>("Fecha Devolución");
        colFechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));

        // Agregar columnas a la tabla
        tablaPrestamos.getColumns().addAll(colId, colLibro, colFechaPrestamo, colFechaDevolucion);

        // Obtener usuario logueado
        int usuarioId = SesionUsuario.getInstancia().getUsuarioLogueado().getId();

        // Cargar los préstamos activos del usuario logueado
        List<Prestamo> listaPrestamos = prestamoController.obtenerPrestamosActivosPorUsuario(usuarioId);
        ObservableList<Prestamo> prestamosObservable = FXCollections.observableArrayList(listaPrestamos);
        tablaPrestamos.setItems(prestamosObservable);

        // Botón para registrar la devolución
        Button btnDevolver = new Button("Registrar Devolución");
        btnDevolver.setOnAction(event -> {
            Prestamo prestamoSeleccionado = tablaPrestamos.getSelectionModel().getSelectedItem();
            if (prestamoSeleccionado != null) {
                boolean devolucionExitosa = prestamoController.registrarDevolucion(prestamoSeleccionado.getId());
                if (devolucionExitosa) {
                    System.out.println("Devolución registrada correctamente.");
                    // Recargar la tabla
                    List<Prestamo> nuevaListaPrestamos = prestamoController.obtenerPrestamosActivosPorUsuario(usuarioId);
                    tablaPrestamos.setItems(FXCollections.observableArrayList(nuevaListaPrestamos));
                } else {
                    System.out.println("Error al registrar la devolución.");
                }
            } else {
                System.out.println("Seleccione un préstamo para devolver.");
            }
        });

        // Agregar elementos al layout
        layout.getChildren().addAll(tablaPrestamos, btnDevolver);

        // Configurar y mostrar la ventana
        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Gestionar Devoluciones");
        stage.show();
    }
}