package ibermatica.controller;

import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class trabajadores_info {
    sql database = new sql();
    String id_inicio = sql.id_sesion;
    @FXML
    Pane cambiar, info;
    @FXML
    Label usuario, telefono, email, nombre, apellido, tipo, dni, fecha;
    @FXML
    TextField contraseña_antigua, contraseña_nueva, contraseña_confir;

    public void initialize() {
        info.setVisible(true);
        set_valores();
    }

    public void set_valores() {

        usuario.setText(database.buscar(id_inicio).getUsername());
        telefono.setText(String.valueOf(database.buscar(id_inicio).getTelefono()));
        email.setText(database.buscar(id_inicio).getEmail());
        nombre.setText(database.buscar(id_inicio).getName());
        apellido.setText(database.buscar(id_inicio).getSurname());
        tipo.setText(Validaciones.tipo(database.buscar(id_inicio)));
        dni.setText(database.buscar(id_inicio).getUsername());
        fecha.setText(String.valueOf(database.buscar(id_inicio).getRegisterDate()));
        
    }

    public void cambio_contraseña() {
        info.setVisible(false);
        cambiar.setVisible(true);
    }

    public void contra() {
        Alert contraseñas = new Alert(AlertType.WARNING);
        if (database.buscar(id_inicio).getPassword().equals(contraseña_antigua.getText())
                && contraseña_nueva.getText() == contraseña_confir.getText()) {
            database.contraseña_change(contraseña_confir.getText());
            Alert confirmacion = new Alert(AlertType.CONFIRMATION);
            confirmacion.setContentText("Contraseña cambiada");
            cambiar.setVisible(false);
            info.setVisible(true);
        } else if (!(database.buscar(id_inicio).getPassword().equals(contraseña_antigua.getText()))) {
            contraseñas.setContentText("La contraseña es incorrecta de la actual");
            contraseñas.showAndWait();
        } else if (contraseña_nueva.getText() == contraseña_confir.getText()) {
            contraseñas.setContentText("Las contraseñas no son iguales");
            contraseñas.showAndWait();
        }

    }
}
