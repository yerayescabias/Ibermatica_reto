package ibermatica.controller;

import java.io.IOException;

import java.sql.Time;
import java.time.LocalDate;

import java.util.Calendar;
import java.util.TimeZone;

import ibermatica.App;
import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class trabajadores_info {
    sql database = new sql();
    int cont=0;
    String id_inicio = sql.id_sesion;
    @FXML
    Pane cambiar, info;
    @FXML
    Label usuario, telefono, email, nombre, apellido, tipo, dni, fecha,contra_libre;
    @FXML
    TextField contraseña_antigua, contraseña_nueva, contraseña_confir;
    @FXML
    PasswordField contraseña;

    public void initialize() {
        info.setVisible(true);
        cambiar.setVisible(false);
        set_valores();
    }

    
    public void set_valores() {
        
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Spain"));
        cal.setTime(database.buscar(id_inicio).getRegisterDate());
        String años = String.valueOf((cal.get(Calendar.YEAR))-LocalDate.now().getYear());
        usuario.setText(database.buscar(id_inicio).getUsername());
        telefono.setText(String.valueOf(database.buscar(id_inicio).getTelefono()));
        email.setText(database.buscar(id_inicio).getEmail());
        nombre.setText(database.buscar(id_inicio).getName());
        apellido.setText(database.buscar(id_inicio).getSurname());
        tipo.setText(Validaciones.tipo(database.buscar(id_inicio)));
        fecha.setText(años);
        dni.setText(database.buscar(id_inicio).getUsername());
        database.buscar(id_inicio).getRegisterDate();
        contraseña.setText(database.buscar(id_inicio).getPassword());
        dni.setText(id_inicio);
        
        
    }
    public void ojo(){
        
        boolean terminao= true;
        if(cont %2==0 && terminao==true){
        contraseña.setVisible(false);
        contra_libre.setVisible(true);
        contra_libre.setText(database.buscar(id_inicio).getPassword());
        cont++;
        terminao=false;
        }else if(!(cont %2==0 && terminao==true)){
            contraseña.setVisible(true);
        contraseña.setText(database.buscar(id_inicio).getPassword());
        contra_libre.setVisible(false);
        cont++;
        terminao=false;
        }
        

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

    @FXML
    public void atras()throws IOException{
        App.setRoot("mis_reservas");
    }
    @FXML
    public void previous()throws IOException{
        App.setRoot("Reservas");
    }
    @FXML
    public void cerrarsesion()throws IOException{
        App.setRoot("Inicio");
    }
    @FXML
    public void info()throws IOException{
        App.setRoot("Usuario");
    }
    @FXML
    public void cambio()throws IOException{
        cambiar.setVisible(false);
        info.setVisible(true);
    }
}