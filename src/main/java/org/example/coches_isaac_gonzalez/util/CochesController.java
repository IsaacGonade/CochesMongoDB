package org.example.coches_isaac_gonzalez.util;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.coches_isaac_gonzalez.DAO.CocheDAO;
import org.example.coches_isaac_gonzalez.domain.Coche;

import java.net.URL;
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
    private TableColumn<Coche, String> clTipo;

    @FXML
    private TableColumn<Coche, String> colMarca;

    @FXML
    private TableColumn<Coche, String> colMatricula;

    @FXML
    private TableColumn<Coche, String> colModelo;

    @FXML
    private TableView<Coche> tablaCoches;

    private final CocheDAO cocheDAO = new CocheDAO();
    private Coche cocheseleccionado;

    public void cargarDatos() {
        //limpia los campos
        tablaCoches.getItems().clear();
        try {
            //cargo los datos en la tabla
            List<Coche> coches = cocheDAO.obtenerCoche();
            tablaCoches.setItems(FXCollections.observableList(coches));

            //cargamos los datos en las columnas
            colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
            colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
            colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
            clTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

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

            //llamo al metodo
            cocheDAO.eliminarCoche(coche);

            //cargo los datos para refrescar la tabla
            cargarDatos();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onInsertar(ActionEvent event) {
        String matricula = matriculaTF.getText();

        //valido que el campo de la matricula no este vacio y que cumpla con los requisitos
        if (matricula.isEmpty()) {
            Alerts.mostrarError("La marca es un campo obligatorio");
            return;
        }else if (!Validar.comprobarMatricula(matriculaTF.getText())){
            Alerts.mostrarError("La matricula no cumple los requisitos");
            return;
        }

        //recojo los datos de los campos de texto y los guardoe en un nuevo objeto
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
            Alerts.mostrarError("La matricula es un campo obligatorio");
            return;
        }else if (!Validar.comprobarMatricula(matriculaTF.getText())){
            Alerts.mostrarError("La matricula no cumple los requisitos");
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


    //metodo para meter los datos de un coche
    private void cargarCoche(Coche coche) {
        marcaTF.setText(coche.getMarca());
        matriculaTF.setText(coche.getMatricula());
        modeloTF.setText(coche.getModelo());
        cbTipo.setValue(coche.getTipo());
    }


    //metodo para seleccionar un coche desde la tabla
    @FXML
    void seleccionarCoche(MouseEvent event) {
        cocheseleccionado = tablaCoches.getSelectionModel().getSelectedItem();
        cargarCoche(cocheseleccionado);
    }

    //metodo para limpiar todos los campos
    @FXML
    void onLimpiar(ActionEvent event) {
        marcaTF.setText("");
        matriculaTF.setText("");
        modeloTF.setText("");
        cbTipo.setValue("Selecciona tipo");
        matriculaTF.requestFocus();
    }

    //metodo que conecta con la base de datos y carga los datos desde el inicio
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
