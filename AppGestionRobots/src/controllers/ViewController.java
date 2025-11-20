package controllers;

import exceptions.EscrituraArchivoException;
import exceptions.RobotException;
import exceptions.LecturaArchivoException;
import exceptions.RobotDuplicadoException;
import interfaces.IGestorRobots;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.GestorRobots;
import models.Robot;

public class ViewController implements Initializable {

    @FXML
    private ListView<Robot> listViewRobots;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    private IGestorRobots gestor;

    private List<Robot> listaRobots;
    private ObservableList<Robot> listaObservable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestor = new GestorRobots();

        try {
            listaRobots = gestor.cargarRobots();
        } catch (LecturaArchivoException e) {
            mostrarMensaje("Error de Lectura", e.getMessage(), Alert.AlertType.ERROR);
            listaRobots = new java.util.ArrayList<>();
        }

        listaObservable = FXCollections.observableArrayList(listaRobots);
        listViewRobots.setItems(listaObservable);
    }

    @FXML
    private void agregarRobot() {
        try {
            Robot nuevoRobot = abrirFormulario(null);

            if (nuevoRobot != null) {
                validarDuplicado(nuevoRobot);
                listaRobots.add(nuevoRobot);
                actualizarVistaYPersistencia();
                mostrarMensaje("Éxito", "Robot agregado correctamente", Alert.AlertType.INFORMATION);
            }
        } catch (RobotException e) {
            mostrarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void modificarRobot() {
        Robot robotSeleccionado = listViewRobots.getSelectionModel().getSelectedItem();
        if (robotSeleccionado == null) {
            mostrarMensaje("Advertencia", "Debe seleccionar un robot", Alert.AlertType.WARNING);
            return;
        }

        try {
            Robot robotModificado = abrirFormulario(robotSeleccionado);

            if (robotModificado != null) {
                int index = listaRobots.indexOf(robotSeleccionado);
                if (index != -1) {
                    listaRobots.set(index, robotModificado);
                    actualizarVistaYPersistencia();
                    mostrarMensaje("Éxito", "Robot modificado correctamente", Alert.AlertType.INFORMATION);
                }
            }
        } catch (EscrituraArchivoException e) {
            mostrarMensaje("Error de Escritura", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarMensaje("Error Inesperado", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void eliminarRobot() {
        Robot robotSeleccionado = listViewRobots.getSelectionModel().getSelectedItem();
        if (robotSeleccionado == null) {
            mostrarMensaje("Advertencia", "Debe seleccionar un robot", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> resultado = mostrarConfirmacion(
                "Confirmar Eliminación",
                "¿Está seguro de eliminar al robot " + robotSeleccionado.getNombre() + "?"
        );

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                listaRobots.remove(robotSeleccionado);
                actualizarVistaYPersistencia();
                mostrarMensaje("Éxito", "Robot eliminado correctamente", Alert.AlertType.INFORMATION);
            } catch (EscrituraArchivoException e) {
                mostrarMensaje("Error de Escritura", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void validarDuplicado(Robot robot) throws RobotDuplicadoException {
        if (listaRobots.contains(robot)) {
            throw new RobotDuplicadoException("Ya existe un robot con el número de serie: " + robot.getNumeroSerie());
        }
    }

    private void actualizarVistaYPersistencia() throws EscrituraArchivoException {
        listaObservable.setAll(listaRobots);
        gestor.guardarRobots(listaRobots);
    }

    private Robot abrirFormulario(Robot robotParaEditar) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formulario.fxml"));
            Parent root = loader.load();

            FormularioController controller = loader.getController();

            if (robotParaEditar != null) {
                controller.setEntidad(robotParaEditar);
            }

            Stage stage = new Stage();
            stage.setTitle(robotParaEditar == null ? "Nuevo Robot" : "Modificar Robot");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

            return controller.getEntidad();

        } catch (IOException e) {
            mostrarMensaje("Error de UI", "No se pudo abrir el formulario: " + e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private Optional<ButtonType> mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert.showAndWait();
    }
}
