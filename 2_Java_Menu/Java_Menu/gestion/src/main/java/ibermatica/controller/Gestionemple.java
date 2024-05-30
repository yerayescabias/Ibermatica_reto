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
import ibermatica.multidioma.Idioma;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gestionemple {
    static sql database = new sql();
    int type = 0;
    User nuevo;
    ArrayList<String> parametros = new ArrayList<>();
    boolean validaciones = true;
    Idioma idioma ;

    @FXML
    TextField gu_al_dni, gu_al_nombre, gu_al_apellido, gu_al_telefono, gu_al_email, gu_mo_apellido, gu_mo_nombre,
            gu_mo_telefono, gu_mo_email, gu_mo_usuario, gu_mo_contraseña, gu_mo_dni, gu_ba_dnibuscar;

    @FXML
    ComboBox gu_al_tipo, gu_mo_tipo;

    @FXML
    Tab gu_baja,gu_alta;

    @FXML
    Button gu_ba_eliminar,gu_al_daralta,gu_clear,gu_modificiar; 

    @FXML
    TableView Tabla;

    @FXML
    Text gu_dni,gu_nombre,gu_apellido,gu_telefono,gu_email,gu_tipo;

    public void initialize() {
        gu_al_tipo.getItems().addAll("Oficinista", "Obrero");
        gu_mo_tipo.getItems().addAll("Oficinista", "Obrero");
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
        Validaciones.limite(gu_al_dni,9);
        Validaciones.limitetelefono(gu_al_telefono);
        Validaciones.limite(gu_mo_dni,9);
        Validaciones.limitetelefono(gu_mo_telefono);
        Validaciones.limite(gu_ba_dnibuscar,9);
        idioma(idioma.idioma_default());
    }

    @FXML
    public void alta() throws NumberFormatException, SQLException {

        if (Validaciones.dni(gu_al_dni,9) == false || Validaciones.telefono(gu_al_telefono) == false || Validaciones.nombre(gu_al_nombre)==false
                || Validaciones.apellido( gu_al_apellido, gu_al_email) == false ||  Validaciones.combo(gu_al_tipo)==false||(Validaciones.basededatos("users",gu_al_dni )== true)){
                    

        } else {
            nuevo = new User(gu_al_dni.getText(), gu_al_nombre.getText(), gu_al_apellido.getText(), gu_al_email.getText(),
                    Integer.parseInt(gu_al_telefono.getText()),
                    Validaciones.usuarioAUTO(gu_al_nombre.getText(), gu_al_apellido.getText()),
                    Validaciones.contraseñasAUTO(gu_al_nombre.getText()), Validaciones.input_tipo(gu_al_tipo));
            database.usersADD(nuevo);

            clear();
            gu_mo_apellido.setText(nuevo.getSurname());
            gu_mo_nombre.setText(nuevo.getName());
            gu_mo_telefono.setText(String.valueOf(nuevo.getTelefono()));
            gu_mo_email.setText(nuevo.getEmail());
            gu_mo_usuario.setText(nuevo.getUsername());
            gu_mo_contraseña.setText(nuevo.getPassword());
            gu_mo_dni.setText(nuevo.getUser_id());
            gu_mo_tipo.getSelectionModel().select(Validaciones.tipo(nuevo));
        }

    }

    @FXML
    public void clear() {
        gu_al_dni.setText("");
        gu_al_nombre.setText("");
        gu_al_apellido.setText("");
        gu_al_telefono.setText("");
        gu_al_email.setText("");
        gu_al_tipo.getSelectionModel().clearSelection();
        gu_mo_apellido.setText("");
        gu_mo_nombre.setText("");
        gu_mo_telefono.setText("");
        gu_mo_email.setText("");
        gu_mo_usuario.setText("");
        gu_mo_contraseña.setText("");
        gu_mo_dni.setText("");
        gu_mo_tipo.getSelectionModel().clearSelection();
        gu_ba_dnibuscar.setText("");
    }

    @FXML
    public void modificar_rellenar() {
        User modibusca = database.buscar(gu_mo_dni.getText());
        gu_mo_apellido.setText(modibusca.getSurname());
        gu_mo_nombre.setText(modibusca.getName());
        gu_mo_telefono.setText(String.valueOf(modibusca.getTelefono()));
        gu_mo_email.setText(modibusca.getEmail());
        gu_mo_usuario.setText(modibusca.getUsername());
        gu_mo_contraseña.setText(modibusca.getPassword());
        gu_mo_tipo.getSelectionModel().select(Validaciones.tipo(modibusca));

    }

    @FXML
    public void modificar() throws NumberFormatException, SQLException {
        if (Validaciones.dni(gu_mo_dni,9) == false || Validaciones.telefono(gu_mo_telefono) == false || Validaciones.nombre(gu_mo_nombre)==false
                || Validaciones.apellido( gu_mo_apellido, gu_mo_email) == false ||  Validaciones.combo(gu_mo_tipo)==false ) {
        } else {
            User modifiUser = new User(gu_mo_dni.getText(), gu_mo_nombre.getText(),
                    gu_mo_apellido.getText(),
                    gu_mo_email.getText(), Integer.parseInt(gu_mo_telefono.getText()),
                    Validaciones.usuarioAUTO(gu_mo_nombre.getText(), gu_mo_apellido.getText()),
                    Validaciones.contraseñasAUTO(gu_mo_nombre.getText()), Validaciones.input_tipo(gu_mo_tipo));
            database.modificar(modifiUser);
            clear();
        }

    }

    @FXML
    public void tabla_dni() {
        if (Validaciones.dni(gu_ba_dnibuscar,9) == false) {

        } else {
            Tabla.getItems().clear();
            Tabla.getItems().add(database.buscar(gu_ba_dnibuscar.getText()));
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
        
        if(reservas_borrado((gu_ba_dnibuscar.getText()))==false){
        }else{
            database.borrar_usuario(gu_ba_dnibuscar.getText());
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
     @FXML
    public void Español() {
        idioma("Español");
    }

    @FXML
    public void Ingles() {
        idioma("Ingles");
    }

    public void idioma(String lenguaje){
        idioma = new Idioma(lenguaje);
        Tab[] tabs={gu_baja,gu_alta};
        idioma.tab(tabs);
        Text[] textos={gu_dni,gu_nombre,gu_apellido,gu_telefono,gu_email,gu_tipo};
        idioma.text(textos);
        ComboBox[] combo={gu_al_tipo, gu_mo_tipo};
        idioma.combo(combo);
        TextField[] field={gu_al_dni, gu_al_nombre, gu_al_apellido, gu_al_telefono, gu_al_email, gu_mo_apellido, gu_mo_nombre,
            gu_mo_telefono, gu_mo_email, gu_mo_usuario, gu_mo_contraseña, gu_mo_dni, gu_ba_dnibuscar};
        idioma.textfield(field);
        Button[] button={gu_ba_eliminar,gu_al_daralta,gu_clear,gu_modificiar};
        idioma.botones(button);
    }
}