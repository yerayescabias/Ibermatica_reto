package ibermatica.controller;

import java.util.Iterator;

import ibermatica.model.User;
import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Gestionemple {
    sql database = new sql();
    int type=0;
    User nuevo;
    @FXML
    TextField dni_alta,nombre_alta,apellido_alta,telefono_alta,email_alta,apellido_modificar,nombre_modificar,telefono_modificar,email_modificar,usuario_modificar,contraseña_modificar,dni_modificar;
    @FXML
    ComboBox tipo_alta,tipo_moficar;

    
    public void initialize(){
        tipo_alta.getItems().addAll("Oficinista","Obrero");
        tipo_moficar.getItems().addAll("Oficinista","Obrero");

    }

    public void alta(){
        
        if(tipo_alta.getSelectionModel().getSelectedItem().equals("Oficinista")){
            type=0;
        }else if((tipo_alta.getSelectionModel().getSelectedItem().equals("Obrero"))){
            type=1;
        }
        nuevo = new User(dni_alta.getText(), nombre_alta.getText(), apellido_alta.getText(), email_alta.getText(),Integer.parseInt(telefono_alta.getText()) , Validaciones.usuarioAUTO(nombre_alta.getText(), apellido_alta.getText()), Validaciones.contraseñasAUTO(nombre_alta.getText()), type);
        database.usersADD(nuevo);
        
        clear();
        apellido_modificar.setText(nuevo.getSurname());
        nombre_modificar.setText(nuevo.getName());
        telefono_modificar.setText(String.valueOf(nuevo.getTelefono()));
        email_modificar.setText(nuevo.getEmail());
        usuario_modificar.setText(nuevo.getUsername());
        contraseña_modificar.setText(nuevo.getPassword());
        dni_modificar.setText(nuevo.getUser_id());
        if(nuevo.getType()==0){
            tipo_moficar.getSelectionModel().select("Oficinista");
        }else if(nuevo.getType()==1){
            tipo_moficar.getSelectionModel().select("Obrero");
        }
       
    }
    public void clear(){
        dni_alta.setText("");
        nombre_alta.setText("");
        apellido_alta.setText("");
        telefono_alta.setText("");
        email_alta.setText("");
        tipo_alta.getSelectionModel().clearSelection();
        apellido_modificar.setText("");
        nombre_modificar.setText("");
        telefono_modificar.setText("");
        email_modificar.setText("");
        usuario_modificar.setText("");
        contraseña_modificar.setText("");
        dni_modificar.setText("");
        tipo_moficar.getSelectionModel().clearSelection();
    }
    public void modificar_rellenar(){
        User modibusca=database.buscar(dni_modificar.getText());
        apellido_modificar.setText(modibusca.getSurname());
        nombre_modificar.setText(modibusca.getName());
        telefono_modificar.setText(String.valueOf(modibusca.getTelefono()));
        email_modificar.setText(modibusca.getEmail());
        usuario_modificar.setText(modibusca.getUsername());
        contraseña_modificar.setText(modibusca.getPassword());
        if(modibusca.getType()==0){
            tipo_moficar.getSelectionModel().select("Oficinista");
        }else if(modibusca.getType()==1){
            tipo_moficar.getSelectionModel().select("Obrero");
        }

    }
    public void modificar(){
        if(tipo_moficar.getSelectionModel().getSelectedItem().equals("Oficinista")){
            type=0;
        }else if((tipo_moficar.getSelectionModel().getSelectedItem().equals("Obrero"))){
            type=1;
        }
        User modifiUser= new User(dni_modificar.getText(), nombre_modificar.getText(),  apellido_modificar.getText(),  email_modificar.getText(), Integer.parseInt(telefono_modificar.getText()),Validaciones.usuarioAUTO(nombre_modificar.getText(),apellido_modificar.getText()) , Validaciones.contraseñasAUTO(nombre_modificar.getText()) ,type);
        database.modificar(modifiUser);
        clear();


    }

    public void 
}
