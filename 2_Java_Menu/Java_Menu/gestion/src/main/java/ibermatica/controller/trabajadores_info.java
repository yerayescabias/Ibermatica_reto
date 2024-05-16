package ibermatica.controller;



import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


public class trabajadores_info {
    sql database = new sql();
    String id_inicio=database.id_sesion;
    @FXML
    Pane cambiar,info;
    @FXML
    Label usuario,telefono,email,nombre,apellido,tipo,dni,fecha;


    public void initialize(){
        info.setVisible(true);
        set_valores();
    }
    public void set_valores(){
        
        usuario.setText(database.buscar(id_inicio).getUsername());
        telefono.setText(String.valueOf(database.buscar(id_inicio).getTelefono()));
        email.setText(database.buscar(id_inicio).getEmail());
        nombre.setText(database.buscar(id_inicio).getName());
        apellido.setText(database.buscar(id_inicio).getSurname());
        tipo.setText(Validaciones.tipo(database.buscar(id_inicio)));
        dni.setText(database.buscar(id_inicio).getUsername());
        fecha.setText(database.buscar(id_inicio).getUsername());
    }
}
