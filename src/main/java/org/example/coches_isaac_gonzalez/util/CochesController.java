package org.example.coches_isaac_gonzalez.util;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.example.coches_isaac_gonzalez.DAO.CocheDAO;
import org.example.coches_isaac_gonzalez.domain.Coche;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CochesController implements Initializable {

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private TextField marcaTF;

    @FXML
    private TextField matriculaTF;

    @FXML
    private TextField modeloTF;

    @FXML
    private TableView<Coche> tablaCoches;

    private final CocheDAO cocheDAO = new CocheDAO();
    private Coche cocheseleccionado;

    public void cargarDatos() {
        //limpia los campos
        tablaCoches.getItems().clear();
        try {
            //cargo los datos en la lista
            List<Coche> coches = cocheDAO.obtenerCoche();
            tablaCoches.setItems(FXCollections.observableList(coches));
            //relleno el combobox
            String[] tipos = new String[]{"Familiar","SUV","Compacto","Deportivo"};
            cbTipo.setItems(FXCollections.observableArrayList(tipos));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onEliminar(ActionEvent event) {
        Coche coche = tablaCoches.getSelectionModel().getSelectedItem();
        if (coche == null){
            Alerts.mostrarError("No se ha seleccionado ningun coche");
            return;
        }

        try {
            //alerta para confirmar la accion
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("¿Eliminar producto?");
            confirmacion.setContentText("¿Estás seguro?");
            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE){
                return;
            }


            cocheDAO.eliminarCoche(coche);

            cargarDatos();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onInsertar(ActionEvent event) {
        String matricula = matriculaTF.getText();

        if (matricula.isEmpty()) {
            Alerts.mostrarError("La marca es un campo obligatorio");
            return;
        }

        String marca = marcaTF.getText();
        String modelo = modeloTF.getText();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();
        Coche coche = new Coche(matricula, marca, modelo, tipo);

        try {
            //alerta para confirmar la accion
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("¿Añadir producto?");
            confirmacion.setContentText("¿Estás seguro?");
            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE){
                return;
            }

            cocheDAO.insertarCoche(coche);

            cargarDatos();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onModificar(ActionEvent event) {
        String matricula = matriculaTF.getText();

        if (matricula.isEmpty()) {
            Alerts.mostrarError("La marca es un campo obligatorio");
            return;
        }

        String marca = marcaTF.getText();
        String modelo = modeloTF.getText();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();
        Coche coche = new Coche(matricula, marca, modelo, tipo);

        try {
            //alerta para confirmar la accion
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("¿Añadir producto?");
            confirmacion.setContentText("¿Estás seguro?");
            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE){
                return;
            }

            cocheDAO.modificarCoche(cocheseleccionado, coche);

            cargarDatos();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    //metodo para meter los datos de un producto
    private void cargarCoche(Coche coche) {
        marcaTF.setText(coche.getMarca());
        matriculaTF.setText(coche.getMatricula());
        modeloTF.setText(coche.getModelo());
        cbTipo.setValue(coche.getTipo());
    }


    @FXML
    void seleccionarCoche(MouseEvent event) {
        cocheseleccionado = tablaCoches.getSelectionModel().getSelectedItem();
        cargarCoche(cocheseleccionado);
    }

    @FXML
    void onLimpiar(ActionEvent event) {
        marcaTF.setText("");
        matriculaTF.setText("");
        modeloTF.setText("");
        cbTipo.setValue("Selecciona tipo");
        matriculaTF.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ConexionBBDD.conectar();
        } catch (Exception ioe) {
            Alerts.mostrarError("Error al cargar la base de datos");
        }

        cargarDatos();
    }
}
