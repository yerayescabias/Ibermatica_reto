package ibermatica.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import ibermatica.App;
import ibermatica.model.User;
import ibermatica.model.sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Reservas {
    sql database = new sql();
    ArrayList<Reservas> reservas = new ArrayList<>();
    Boolean presionado = true;
    @FXML
    Button procesados, embalaje, tornos, freasoras, lijadoras, selladora, soldadura, hornos;


   

    @FXML
    TableView reservas_maquina;

    @FXML
    public void initialize() {
        TableColumn<Reservas, String> columna1 = new TableColumn<>("Nombre usuario");
        columna1.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        TableColumn<Reservas, String> columna2 = new TableColumn<>("Nombre maquina");
        columna2.setCellValueFactory(new PropertyValueFactory<>("serial_num"));
        TableColumn<Reservas, String> columna3 = new TableColumn<>("Fecha adquision");
        columna3.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        TableColumn<Reservas, String> columna4 = new TableColumn<>("Fin de adquisicion");
        columna4.setCellValueFactory(new PropertyValueFactory<>("end_date"));

        reservas_maquina.getColumns().add(columna1);
        reservas_maquina.getColumns().add(columna2);
        reservas_maquina.getColumns().add(columna3);
        reservas_maquina.getColumns().add(columna4);

        
    }

    @FXML
    public void info() throws IOException {
        App.setRoot("Usuario");

    }

    @FXML
    public void botontabla() {
    if(procesados.isPressed()){
        database.reservar(procesados.getText());


    }else if(embalaje.isPressed()){
        Iterator<Reservas> datosv =database.reservar(embalaje.getText()).iterator();

    }else if(tornos.isPressed()){
        database.reservar(tornos.getText());

    }else if(freasoras.isPressed()){
        database.reservar(freasoras.getText());

    }else if(lijadoras.isPressed()){
        database.reservar(lijadoras.getText());

    }else if(selladora.isPressed()){
        database.reservar(selladora.getText());

    }else if(soldadura.isPressed()){
        database.reservar(soldadura.getText());

    }else if(hornos.isPressed()){
        database.reservar(hornos.getText());

    }
}

    

    @FXML
    public void atras() throws IOException {
        App.setRoot("Inicio");
    }
}
