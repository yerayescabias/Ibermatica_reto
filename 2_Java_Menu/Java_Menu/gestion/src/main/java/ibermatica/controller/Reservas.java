package ibermatica.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ibermatica.App;
import ibermatica.model.Reserva;
import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class Reservas {
    int tipos=0;
    sql database = new sql();
    ArrayList<Reserva> reservas = new ArrayList<>();
    @FXML
    Button procesados, embalaje, tornos, freasoras, lijadoras, selladora, soldadura, hornos;
    @FXML
    ResultSet rs;
    @FXML
    TableView reservas_maquina;

    @FXML
    MenuItem mis_reservas,perfil;

    @FXML
    DatePicker fin_dia, inicio_dia;

    @FXML
    public void initialize() throws SQLException {
        tabladereserva();
        
        
    }

    @FXML
    public void buttonpressed() throws SQLException  {
        
       if(fin_dia.getValue() == null || inicio_dia.getValue() == null){
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
            if(Validaciones.horas(inicio_dia,fin_dia)==false ){

            }else{
                if ((procesados.isPressed())) {
            database.serial_num(procesados.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue());
            rs = database.reservas(procesados.getText(), tipos);
            reservas_tabla(rs);
            clear();

        } else if (embalaje.isPressed()) {
            database.serial_num(embalaje.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue());
            rs = database.reservas(embalaje.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (tornos.isPressed()) {
            database.serial_num(tornos.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue());
            rs = database.reservas(tornos.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (freasoras.isPressed()) {
            database.serial_num(freasoras.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue());
            rs = database.reservas(freasoras.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (lijadoras.isPressed()) {
            database.serial_num(lijadoras.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue());
            rs = database.reservas(lijadoras.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (selladora.isPressed()) {
            database.serial_num(selladora.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue());
            rs = database.reservas(selladora.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (soldadura.isPressed()) {
            database.serial_num(soldadura.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue());
            rs = database.reservas(soldadura.getText(), tipos);
            reservas_tabla(rs);
            clear();
        } else if (hornos.isPressed()) {
            database.serial_num(hornos.getText());
            database.nuevareserva(inicio_dia.getValue(),fin_dia.getValue());
            rs = database.reservas(hornos.getText(), tipos);
            reservas_tabla(rs);
            clear();
        }
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

    public void reservas() throws SQLException{
        ResultSet rs =database.reservasdefault(0);
        while (rs.next()) {
            for ( int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                reservas.add(new Reserva(rs.getString(2),rs.getDate(3),rs.getDate(4)));
                
            }
        }
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
