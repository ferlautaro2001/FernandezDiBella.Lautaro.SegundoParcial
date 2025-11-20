package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Robot;
import models.RobotDomestico;
import models.RobotIndustrial;

public class FormularioController implements Initializable {

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtSerie;

    @FXML
    private TextField txtEnergia;

    @FXML
    private Label lblDatoEspecifico;

    @FXML
    private TextField txtDatoEspecifico;

    @FXML
    private Button btnCancelar;

    private Robot entidad;
    private boolean confirmado = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cmbTipo.getItems().addAll("Industrial", "Doméstico");

        cmbTipo.getSelectionModel().selectFirst();

        this.actualizarCampoEspecifico();
    }

    @FXML
    private void seleccionarTipo() {
        actualizarCampoEspecifico();
    }

    private void actualizarCampoEspecifico() {
        String tipo = cmbTipo.getValue();
        if (tipo != null) {
            if (tipo.equals("Industrial")) {
                lblDatoEspecifico.setText("Capacidad Carga (kg):");
                txtDatoEspecifico.setPromptText("Ejemplo: 500");
            } else {
                lblDatoEspecifico.setText("Cantidad Tareas:");
                txtDatoEspecifico.setPromptText("Ejemplo: 10");
            }
        }
    }

    public void setEntidad(Robot robot) {
        this.entidad = robot;
        if (robot != null) {
            txtNombre.setText(robot.getNombre());
            txtSerie.setText(String.valueOf(robot.getNumeroSerie()));
            txtSerie.setDisable(true);
            txtEnergia.setText(String.valueOf(robot.getNivelEnergia()));

            if (robot instanceof RobotIndustrial) {
                cmbTipo.setValue("Industrial");
                RobotIndustrial ri = (RobotIndustrial) robot;
                txtDatoEspecifico.setText(String.valueOf(ri.getCapacidadCarga()));
            } else if (robot instanceof RobotDomestico) {
                cmbTipo.setValue("Doméstico");
                RobotDomestico rd = (RobotDomestico) robot;
                txtDatoEspecifico.setText(String.valueOf(rd.getCantidadTareas()));
            }

            cmbTipo.setDisable(true);
            actualizarCampoEspecifico();
        }
    }

    public Robot getEntidad() {
        return confirmado ? entidad : null;
    }

    @FXML
    private void confirmar() {
        if (!validarCampos()) {
            return;
        }

        try {
            String nombre = txtNombre.getText().trim();
            int serie = Integer.parseInt(txtSerie.getText().trim());
            int energia = Integer.parseInt(txtEnergia.getText().trim());

            if (energia < 0 || energia > 100) {
                mostrarError("La energía debe estar entre 0 y 100");
                return;
            }

            String tipo = cmbTipo.getValue();

            if (tipo.equals("Industrial")) {
                double capacidad = Double.parseDouble(txtDatoEspecifico.getText().trim());
                entidad = new RobotIndustrial(nombre, energia, serie, capacidad);
            } else {
                int tareas = Integer.parseInt(txtDatoEspecifico.getText().trim());
                entidad = new RobotDomestico(nombre, energia, serie, tareas);
            }

            confirmado = true;
            cerrarVentana();

        } catch (NumberFormatException e) {
            mostrarError("Por favor ingrese valores numéricos válidos");
        }
    }

    @FXML
    private void cancelar() {
        confirmado = false;
        cerrarVentana();
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("Debe ingresar un nombre");
            return false;
        }
        if (txtSerie.getText().trim().isEmpty()) {
            mostrarError("Debe ingresar un número de serie");
            return false;
        }
        if (txtEnergia.getText().trim().isEmpty()) {
            mostrarError("Debe ingresar el nivel de energía");
            return false;
        }
        if (txtDatoEspecifico.getText().trim().isEmpty()) {
            mostrarError("Debe completar el dato específico");
            return false;
        }
        if (cmbTipo.getValue() == null) {
            mostrarError("Debe seleccionar un tipo");
            return false;
        }
        return true;
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
