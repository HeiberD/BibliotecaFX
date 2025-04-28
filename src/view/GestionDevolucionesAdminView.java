package view;

import controller.PrestamoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Prestamo;

import java.util.List;

public class GestionDevolucionesAdminView {

    private PrestamoController prestamoController;
    private TableView<Prestamo> tablaPrestamos;
    private TextField buscarField;

    public GestionDevolucionesAdminView() {
        prestamoController = new PrestamoController();
    }

    public void showGestionDevolucionesAdminWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // 🔍 Campo de búsqueda por ID de estudiante
        buscarField = new TextField();
        buscarField.setPromptText("Buscar por ID de Estudiante");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setOnAction(event -> actualizarListaPrestamos());

        // 📌 Tabla para mostrar préstamos activos
        tablaPrestamos = new TableView<>();

        TableColumn<Prestamo, Integer> colId = new TableColumn<>("ID Préstamo");
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        TableColumn<Prestamo, Integer> colUsuarioId = new TableColumn<>("ID Usuario");
        colUsuarioId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getUsuarioId()).asObject());

        TableColumn<Prestamo, String> colLibro = new TableColumn<>("Libro");
        colLibro.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLibroTitulo()));

        TableColumn<Prestamo, String> colFechaPrestamo = new TableColumn<>("Fecha Préstamo");
        colFechaPrestamo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFechaPrestamo().toString()));

        tablaPrestamos.getColumns().addAll(colId, colUsuarioId, colLibro, colFechaPrestamo);

        // 📌 Botón para registrar devolución
        Button btnDevolver = new Button("Registrar Devolución");
        btnDevolver.setOnAction(event -> registrarDevolucion());

        // 📌 Cargar préstamos activos
        actualizarListaPrestamos();

        // 📌 Agregar elementos al layout
        layout.getChildren().addAll(buscarField, btnBuscar, tablaPrestamos, btnDevolver);

        // 📌 Crear y configurar la escena
        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Gestionar Devoluciones - ADMIN");
        stage.show();
    }

    // ✅ Método para actualizar la lista de préstamos activos
    private void actualizarListaPrestamos() {
        String filtroUsuario = buscarField.getText().trim();
        List<Prestamo> listaPrestamos;

        if (!filtroUsuario.isEmpty() && filtroUsuario.matches("\\d+")) {
            int usuarioId = Integer.parseInt(filtroUsuario);
            listaPrestamos = prestamoController.obtenerPrestamosActivosPorUsuario(usuarioId);
        } else {
            listaPrestamos = prestamoController.obtenerPrestamosActivos();
        }

        ObservableList<Prestamo> prestamosObservable = FXCollections.observableArrayList(listaPrestamos);
        tablaPrestamos.setItems(prestamosObservable);
    }

    // ✅ Método para registrar una devolución
    private void registrarDevolucion() {
        Prestamo prestamoSeleccionado = tablaPrestamos.getSelectionModel().getSelectedItem();

        if (prestamoSeleccionado != null) {
            boolean devuelto = prestamoController.registrarDevolucionPorAdmin(prestamoSeleccionado.getId());

            if (devuelto) {
                actualizarListaPrestamos(); // Recargar tabla
            }
        } else {
            System.out.println("Seleccione un préstamo para registrar la devolución.");
        }
    }
}
