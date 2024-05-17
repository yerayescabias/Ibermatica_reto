package ibermatica.controller;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.control.TableColumn;


import ibermatica.model.User;
import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Gestionemple {
    sql database = new sql();
    int type = 0;
    User nuevo;
    ArrayList<String> parametros = new ArrayList<>();

    @FXML
    TextField dni_alta, nombre_alta, apellido_alta, telefono_alta, email_alta, apellido_modificar, nombre_modificar,
            telefono_modificar, email_modificar, usuario_modificar, contraseña_modificar, dni_modificar, Dni_buscar;

    @FXML
    ComboBox tipo_alta, tipo_moficar;

    @FXML
    TableView Tabla;

    public void initialize() {
        tipo_alta.getItems().addAll("Oficinista", "Obrero");
        tipo_moficar.getItems().addAll("Oficinista", "Obrero");
        TableColumn<User, String> columna1 = new TableColumn<>("DNI");
        columna1.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        TableColumn<User, String> columna2 = new TableColumn<>("Nombre");
        columna2.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<User, String> columna3 = new TableColumn<>("Apellido");
        columna3.setCellValueFactory(new PropertyValueFactory<>("surname"));
        TableColumn<User, String> columna4 = new TableColumn<>("Email");
        columna4.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<User, Integer> columna5 = new TableColumn<>("Telefono");
        columna5.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        TableColumn<User, String> columna6 = new TableColumn<>("Usuario");
        columna6.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<User, String> columna7 = new TableColumn<>("Contraseña");
        columna7.setCellValueFactory(new PropertyValueFactory<>("password"));
        TableColumn<User, Integer> columna8 = new TableColumn<>("Tipo");
        columna8.setCellValueFactory(new PropertyValueFactory<>("type"));
        Tabla.getColumns().add(columna1);
        Tabla.getColumns().add(columna2);
        Tabla.getColumns().add(columna3);
        Tabla.getColumns().add(columna4);
        Tabla.getColumns().add(columna5);
        Tabla.getColumns().add(columna6);
        Tabla.getColumns().add(columna7);
        Tabla.getColumns().add(columna8);
        

    }
    @FXML
    public void alta() {
        nuevo = new User(dni_alta.getText(), nombre_alta.getText(), apellido_alta.getText(), email_alta.getText(),
                Integer.parseInt(telefono_alta.getText()),
                Validaciones.usuarioAUTO(nombre_alta.getText(), apellido_alta.getText()),
                Validaciones.contraseñasAUTO(nombre_alta.getText()), Validaciones.input_tipo(tipo_alta));
        database.usersADD(nuevo);

        clear();
        apellido_modificar.setText(nuevo.getSurname());
        nombre_modificar.setText(nuevo.getName());
        telefono_modificar.setText(String.valueOf(nuevo.getTelefono()));
        email_modificar.setText(nuevo.getEmail());
        usuario_modificar.setText(nuevo.getUsername());
        contraseña_modificar.setText(nuevo.getPassword());
        dni_modificar.setText(nuevo.getUser_id());
        tipo_moficar.getSelectionModel().select(Validaciones.tipo(nuevo));
        
    }
    @FXML
    public void clear() {
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
        Dni_buscar.setText("");
    }
    @FXML
    public void modificar_rellenar() {
        User modibusca = database.buscar(dni_modificar.getText());
        apellido_modificar.setText(modibusca.getSurname());
        nombre_modificar.setText(modibusca.getName());
        telefono_modificar.setText(String.valueOf(modibusca.getTelefono()));
        email_modificar.setText(modibusca.getEmail());
        usuario_modificar.setText(modibusca.getUsername());
        contraseña_modificar.setText(modibusca.getPassword());
        tipo_moficar.getSelectionModel().select(Validaciones.tipo(modibusca));
        
        

    }
    @FXML
    public void modificar() {

        
        User modifiUser = new User(dni_modificar.getText(), nombre_modificar.getText(), apellido_modificar.getText(),
                email_modificar.getText(), Integer.parseInt(telefono_modificar.getText()),
                Validaciones.usuarioAUTO(nombre_modificar.getText(), apellido_modificar.getText()),
                Validaciones.contraseñasAUTO(nombre_modificar.getText()), Validaciones.input_tipo(tipo_moficar));
        database.modificar(modifiUser);
        clear();

    }
    @FXML
    public void tabla_dni() {
        Tabla.getItems().clear();
        Tabla.getItems().add(database.buscar(Dni_buscar.getText()));
    }
    @FXML
    public void informacion_tabla() {
        Iterator usuarios = database.users().iterator();
        if (Tabla.getItems().isEmpty()) {

            while (usuarios.hasNext()) {
                User trabajador = (User) usuarios.next();
                Tabla.getItems().add(trabajador);
            }
        } else if (!(Tabla.getItems().isEmpty())) {
            Tabla.getItems().clear();
            while (usuarios.hasNext()) {
                User trabajador = (User) usuarios.next();
                Tabla.getItems().add(trabajador);
            }
        }

    }
    @FXML
    public void borrar_usuario() {
        database.borrar_usuario(Dni_buscar.getText());
        clear();
        informacion_tabla();
    }
    
    @FXML
    public static void cerrar (){
        Platform.exit();
    }
}
