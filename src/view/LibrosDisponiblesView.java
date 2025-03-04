package view;

import controller.LibroController;
import controller.PrestamoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Libro;
import model.Prestamo;
import model.SesionUsuario;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class LibrosDisponiblesView {

    private LibroController libroController;
    private PrestamoController prestamoController;

    public LibrosDisponiblesView() {
        libroController = new LibroController();
        prestamoController = new PrestamoController();
    }

    public void showLibrosDisponiblesWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // Tabla para mostrar los libros disponibles
        TableView<Libro> tablaLibros = new TableView<>();

        // Columnas de la tabla
        TableColumn<Libro, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Libro, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Libro, String> colAutor = new TableColumn<>("Autor");
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));

        TableColumn<Libro, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Libro, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        // Agregar columnas a la tabla
        tablaLibros.getColumns().addAll(colId, colTitulo, colAutor, colStock, colCategoria);

        // Cargar datos desde la base de datos
        List<Libro> listaLibros = libroController.obtenerLibrosDisponibles();
        ObservableList<Libro> librosObservable = FXCollections.observableArrayList(listaLibros);
        tablaLibros.setItems(librosObservable);

        // Botón para solicitar préstamo
        Button btnSolicitarPrestamo = new Button("Solicitar Préstamo");
        btnSolicitarPrestamo.setOnAction(event -> {
            Libro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
            if (libroSeleccionado != null && libroSeleccionado.getStock() > 0) {
                int usuarioId = SesionUsuario.getInstancia().getUsuarioLogueado().getId(); // Obtener el usuario logueado
                int libroId = libroSeleccionado.getId();
                LocalDate fechaPrestamo = LocalDate.now();

                Prestamo nuevoPrestamo = new Prestamo(0, usuarioId, libroId, Date.valueOf(fechaPrestamo), null);
                prestamoController.registrarPrestamo(libroId);

                // Recargar la tabla de libros disponibles
                List<Libro> nuevaListaLibros = libroController.obtenerLibrosDisponibles();
                tablaLibros.setItems(FXCollections.observableArrayList(nuevaListaLibros));
            } else {
                System.out.println("Seleccione un libro con stock disponible.");
            }
        });

        // Agregar la tabla y el botón al layout
        layout.getChildren().addAll(tablaLibros, btnSolicitarPrestamo);

        // Configurar y mostrar la ventana
        Scene scene = new Scene(layout, 600, 450);
        stage.setScene(scene);
        stage.setTitle("Libros Disponibles");
        stage.show();
    }
}
