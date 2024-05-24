package ibermatica.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;


import ibermatica.App;
import ibermatica.model.sql;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Reservas {
    int tipos=0;
    sql database = new sql();
    ArrayList<Reservas> reservas = new ArrayList<>();
    Boolean presionado = true;
    @FXML
    Button procesados, embalaje, tornos, freasoras, lijadoras, selladora, soldadura, hornos;
    @FXML
    ResultSet rs;
    @FXML
    TableView reservas_maquina;

    @FXML
    MenuItem mis_reservas,perfil;
    @FXML
    TextField inicio_hora, inicio_min, fin_hora, fin_min;

    @FXML
    DatePicker fin_dia, inicio_dia;

    @FXML
    public void initialize() throws SQLException {
        tabladereserva();
        
        
    }

    @FXML
    public void buttonpressed() throws SQLException {
       if(inicio_hora.getText().isEmpty()|| inicio_min.getText().isEmpty()|| fin_hora.getText().isEmpty()|| fin_min.getText().isEmpty()|| fin_dia.getValue().toString().isEmpty()|| inicio_dia.getValue().toString().isEmpty()){
        if ((procesados.isPressed())) {
            rs = database.reservas(procesados.getText(), tipos);
            reservas_tabla(rs);
        } else if (embalaje.isPressed()) {
            rs = database.reservas(embalaje.getText(), tipos);
            reservas_tabla(rs);
        } else if (tornos.isPressed()) {
            rs = database.reservas(tornos.getText(), tipos);
            reservas_tabla(rs);
        } else if (freasoras.isPressed()) {
            rs = database.reservas(freasoras.getText(), tipos);
            reservas_tabla(rs);
        } else if (lijadoras.isPressed()) {
            rs = database.reservas(lijadoras.getText(), tipos);
            reservas_tabla(rs);
        } else if (selladora.isPressed()) {
            rs = database.reservas(selladora.getText(), tipos);
            reservas_tabla(rs);
        } else if (soldadura.isPressed()) {
            rs = database.reservas(soldadura.getText(), tipos);
            reservas_tabla(rs);
        } else if (hornos.isPressed()) {
            rs = database.reservas(hornos.getText(), tipos);
            reservas_tabla(rs);
        }
       }else{
        if ((procesados.isPressed())) {
            database.serial_num(procesados.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue(),LocalTime.of(Integer.parseInt(inicio_hora.getText()),Integer.parseInt(inicio_min.getText())),LocalTime.of(Integer.parseInt(fin_hora.getText()),Integer.parseInt(fin_min.getText())));
            rs = database.reservas(procesados.getText(), tipos);
            reservas_tabla(rs);
            clear();

        } else if (embalaje.isPressed()) {
            database.serial_num(embalaje.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue(),LocalTime.of(Integer.parseInt(inicio_hora.getText()),Integer.parseInt(inicio_min.getText())),LocalTime.of(Integer.parseInt(fin_hora.getText()),Integer.parseInt(fin_min.getText())));
            rs = database.reservas(embalaje.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (tornos.isPressed()) {
            database.serial_num(tornos.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue(),LocalTime.of(Integer.parseInt(inicio_hora.getText()),Integer.parseInt(inicio_min.getText())),LocalTime.of(Integer.parseInt(fin_hora.getText()),Integer.parseInt(fin_min.getText())));
            rs = database.reservas(tornos.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (freasoras.isPressed()) {
            database.serial_num(freasoras.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue(),LocalTime.of(Integer.parseInt(inicio_hora.getText()),Integer.parseInt(inicio_min.getText())),LocalTime.of(Integer.parseInt(fin_hora.getText()),Integer.parseInt(fin_min.getText())));
            rs = database.reservas(freasoras.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (lijadoras.isPressed()) {
            database.serial_num(lijadoras.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue(),LocalTime.of(Integer.parseInt(inicio_hora.getText()),Integer.parseInt(inicio_min.getText())),LocalTime.of(Integer.parseInt(fin_hora.getText()),Integer.parseInt(fin_min.getText())));
            rs = database.reservas(lijadoras.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (selladora.isPressed()) {
            database.serial_num(selladora.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue(),LocalTime.of(Integer.parseInt(inicio_hora.getText()),Integer.parseInt(inicio_min.getText())),LocalTime.of(Integer.parseInt(fin_hora.getText()),Integer.parseInt(fin_min.getText())));
            rs = database.reservas(selladora.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (soldadura.isPressed()) {
            database.serial_num(soldadura.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue(),LocalTime.of(Integer.parseInt(inicio_hora.getText()),Integer.parseInt(inicio_min.getText())),LocalTime.of(Integer.parseInt(fin_hora.getText()),Integer.parseInt(fin_min.getText())));
            rs = database.reservas(soldadura.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (hornos.isPressed()) {
            database.serial_num(hornos.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue(),LocalTime.of(Integer.parseInt(inicio_hora.getText()),Integer.parseInt(inicio_min.getText())),LocalTime.of(Integer.parseInt(fin_hora.getText()),Integer.parseInt(fin_min.getText())));
            rs = database.reservas(hornos.getText(), tipos);
            reservas_tabla(rs);
            clear();
        }
       }
        
    }

    @SuppressWarnings("unchecked")
    @FXML
    public void reservas_tabla(ResultSet rs) throws SQLException {
        reservas_maquina.getColumns().clear();
        reservas_maquina.getItems().clear();
        ArrayList<ArrayList<String>> columnas = new ArrayList<>();
        for (int index = 1; index <= rs.getMetaData().getColumnCount(); index++) {
            final int col = index - 1;
            TableColumn<ArrayList<String>, String> columa = new TableColumn<>(rs.getMetaData().getColumnName((index)));
            columa.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(col)));
            reservas_maquina.getColumns().add(columa);
        }
        while (rs.next()) {
            ArrayList<String> reservasdatos = new ArrayList<>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                reservasdatos.add(rs.getString(i));
            }
            columnas.add(reservasdatos);
        }
        reservas_maquina.getItems().addAll(columnas);
    }

    public void clear(){
        fin_dia.setValue(null); 
        inicio_dia.setValue(null);
        inicio_hora.setText("");
        inicio_min.setText("");
        fin_hora.setText("");
        fin_min.setText("");
    }
    
    public void tabladereserva() throws SQLException{
        ResultSet rs = database.reservasdefault(tipos);
        reservas_maquina.getColumns().clear();
        reservas_maquina.getItems().clear();
        ArrayList<ArrayList<String>> columnas = new ArrayList<>();
        for (int index = 1; index <= rs.getMetaData().getColumnCount(); index++) {
            final int col = index - 1;
            TableColumn<ArrayList<String>, String> columa = new TableColumn<>(rs.getMetaData().getColumnName((index)));
            columa.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(col)));
            reservas_maquina.getColumns().add(columa);
        }
        while (rs.next()) {
            ArrayList<String> reservasdatos = new ArrayList<>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                reservasdatos.add(rs.getString(i));
            }
            columnas.add(reservasdatos);
        }
        reservas_maquina.getItems().addAll(columnas);
    }
    @FXML
    public void info() throws IOException {
        App.setRoot("Usuario");

    }
    @FXML
    public void mis_reservas() throws IOException{
        App.setRoot("mis_reservas");
    }

    @FXML
    public void atras() throws IOException {
        App.setRoot("Inicio");
    }
}
