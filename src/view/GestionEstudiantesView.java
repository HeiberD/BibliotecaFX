package view;

import controller.UsuarioController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Usuario;

import java.util.List;

public class GestionEstudiantesView {

    private UsuarioController usuarioController;
    private TableView<Usuario> tablaEstudiantes;
    private TextField nombreField;
    private TextField correoField;
    private TextField numeroEstudianteField;

    public GestionEstudiantesView() {
        usuarioController = new UsuarioController();
    }

    public void showGestionEstudiantesWindow() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // 📌 Tabla para mostrar estudiantes
        tablaEstudiantes = new TableView<>();

        TableColumn<Usuario, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId())); // ✅ Se usa asObject()

        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        TableColumn<Usuario, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));

        TableColumn<Usuario, String> colNumero = new TableColumn<>("Número Estudiante");
        colNumero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroEstudiante()));

        tablaEstudiantes.getColumns().addAll(colId, colNombre, colCorreo, colNumero);

        // 📌 Cargar los estudiantes desde la base de datos
        actualizarListaEstudiantes();

        // 📌 Campos de edición
        nombreField = new TextField();
        nombreField.setPromptText("Nombre");

        correoField = new TextField();
        correoField.setPromptText("Correo");

        numeroEstudianteField = new TextField();
        numeroEstudianteField.setPromptText("Número de Estudiante");

        // 📌 Botón para actualizar estudiante
        Button btnActualizar = new Button("Actualizar Estudiante");
        btnActualizar.setOnAction(event -> actualizarEstudiante());

        // 📌 Botón para eliminar estudiante
        Button btnEliminar = new Button("Eliminar Estudiante");
        btnEliminar.setOnAction(event -> eliminarEstudiante());

        // 📌 Selección en la tabla
        tablaEstudiantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nombreField.setText(newSelection.getNombre());
                correoField.setText(newSelection.getCorreo());
                numeroEstudianteField.setText(newSelection.getNumeroEstudiante());
            }
        });

        // 📌 Agregar los elementos al layout
        layout.getChildren().addAll(tablaEstudiantes, nombreField, correoField, numeroEstudianteField, btnActualizar, btnEliminar);

        // 📌 Crear y configurar la escena
        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Gestión de Estudiantes");
        stage.show();
    }

    // ✅ Método para actualizar la lista de estudiantes
    private void actualizarListaEstudiantes() {
        List<Usuario> listaEstudiantes = usuarioController.obtenerEstudiantes();
        ObservableList<Usuario> estudiantesObservable = FXCollections.observableArrayList(listaEstudiantes);
        tablaEstudiantes.setItems(estudiantesObservable);
    }

    // ✅ Método para actualizar un estudiante seleccionado
    private void actualizarEstudiante() {
        Usuario estudianteSeleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();
        if (estudianteSeleccionado != null) {
            boolean actualizado = usuarioController.editarUsuario(
                    estudianteSeleccionado.getId(),
                    nombreField.getText(),
                    correoField.getText(),
                    numeroEstudianteField.getText()
            );

            if (actualizado) {
                actualizarListaEstudiantes(); // Recargar la tabla
            }
        } else {
            System.out.println("Seleccione un estudiante para actualizar.");
        }
    }

    // ✅ Método para eliminar un estudiante seleccionado
    private void eliminarEstudiante() {
        Usuario estudianteSeleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();
        if (estudianteSeleccionado != null) {
            boolean eliminado = usuarioController.eliminarUsuario(estudianteSeleccionado.getId());

            if (eliminado) {
                actualizarListaEstudiantes(); // Recargar la tabla
            }
        } else {
            System.out.println("Seleccione un estudiante para eliminar.");
        }
    }
}
