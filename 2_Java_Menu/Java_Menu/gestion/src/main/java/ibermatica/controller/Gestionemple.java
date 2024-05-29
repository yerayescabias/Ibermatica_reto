package ibermatica.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.control.TableColumn;
import ibermatica.App;
import ibermatica.model.Reserva;
import ibermatica.model.User;
import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Gestionemple {
    static sql database = new sql();
    int type = 0;
    User nuevo;
    ArrayList<String> parametros = new ArrayList<>();
    boolean validaciones = true;

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
        Validaciones.limite(dni_alta,9);
        Validaciones.limitetelefono(telefono_alta);
        Validaciones.limite(dni_modificar,9);
        Validaciones.limitetelefono(telefono_modificar);
        Validaciones.limite(Dni_buscar,9);
    }

    @FXML
    public void alta() throws NumberFormatException, SQLException {

        if (Validaciones.dni(dni_alta,9) == false || Validaciones.telefono(telefono_alta) == false || Validaciones.nombre(nombre_alta)==false
                || Validaciones.apellido( apellido_alta, email_alta) == false ||  Validaciones.combo(tipo_alta)==false||(Validaciones.basededatos("users",dni_alta )== true)){
                    

        } else {
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
    public void modificar() throws NumberFormatException, SQLException {
        if (Validaciones.dni(dni_modificar,9) == false || Validaciones.telefono(telefono_modificar) == false || Validaciones.nombre(nombre_modificar)==false
                || Validaciones.apellido( apellido_modificar, email_modificar) == false ||  Validaciones.combo(tipo_moficar)==false ) {
        } else {
            User modifiUser = new User(dni_modificar.getText(), nombre_modificar.getText(),
                    apellido_modificar.getText(),
                    email_modificar.getText(), Integer.parseInt(telefono_modificar.getText()),
                    Validaciones.usuarioAUTO(nombre_modificar.getText(), apellido_modificar.getText()),
                    Validaciones.contraseñasAUTO(nombre_modificar.getText()), Validaciones.input_tipo(tipo_moficar));
            database.modificar(modifiUser);
            clear();
        }

    }

    @FXML
    public void tabla_dni() {
        if (Validaciones.dni(Dni_buscar,9) == false) {

        } else {
            Tabla.getItems().clear();
            Tabla.getItems().add(database.buscar(Dni_buscar.getText()));
        }

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
    public void borrar_usuario() throws SQLException {
        
        if(reservas_borrado((Dni_buscar.getText()))==false){
        }else{
            database.borrar_usuario(Dni_buscar.getText());
            clear();
            informacion_tabla();
        }
        
    }

    @FXML
    public void cerrar() {
        Platform.exit();
    }

    @FXML
    public void atras() throws IOException {
        App.setRoot("Menu_admin");
    }

    
    public boolean reservas_borrado(String id) throws SQLException{
        ArrayList<Reserva> reserbArrayList =database.resrevas_array();
        User eliminado = database.buscar(id);
        int contador =0;
        boolean verificado= true;
        for (Reserva reserva : reserbArrayList){
            if(reserva.getUser_id().equals(eliminado.getName())){
                contador++;
                verificado = false;
            }
        }

        if (verificado==false){
            Stage decision = new Stage();
            Pane pane = new Pane();
            Scene escena = new Scene(pane);
            Label aviso = new Label("El usuario "+eliminado.getName()+" tiene " + contador+ " reservas pendientes que quieres hacer:"); 
            HBox botones = new HBox();
            Button eliminar = new Button("Eliminar");
            eliminar.setOnAction((ActionEvent event)->{
                database.borrar_usuario(eliminado.getUser_id());
                
            });
            Button modificar = new Button("Modificar");
            modificar.setOnAction((ActionEvent event)->{
                Pane panee = new Pane();
                Scene cambio = new Scene(panee);
                Label label = new Label("Escoge al trabajador que le quieres pasar las reservas");
                ComboBox combo = new ComboBox();
                for (User user : database.users()) {
                    if(!(user.getName().equals(eliminado.getName())) && user.getType()==1){
                        combo.getItems().add(user.getName());
                        clear();
                            informacion_tabla();
                            decision.close();
                            
                    }
                        
                    
                }
                
                
                Button aceptar= new Button("Aceptar");
                aceptar.setOnAction((ActionEvent eveent)->{
                    for (User user : database.users()) {
                        if(user.getName().equals(combo.getSelectionModel().getSelectedItem().toString())){
                            database.update_reservas(eliminado.getUser_id(),user.getUser_id());
                            database.borrar_usuario(eliminado.getUser_id());
                            clear();
                            informacion_tabla();
                            decision.close();
                            
                        }
                    }
                    
                });
                panee.getChildren().addAll(label,combo,aceptar);
                decision.setScene(cambio);
                decision.show();
            });
            botones.getChildren().addAll(eliminar,modificar);
            pane.getChildren().addAll(aviso,botones);
            botones.setLayoutX(115);
            botones.setLayoutY(25);
        
            
            decision.setScene(escena);
            decision.show();
            decision.setResizable(false);
            

        }
        return false;
        
    }
}
