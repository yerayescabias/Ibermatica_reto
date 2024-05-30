package ibermatica.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import ibermatica.App;
import ibermatica.model.Reserva;
import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import ibermatica.multidioma.Idioma;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Reservas {
    int tipos = 0;
    sql database = new sql();
    Reserva reserva;
    Idioma idioma ;
    ArrayList<Reserva> reservas = new ArrayList<>();
    @FXML
    MenuItem re_mis_reservas,re_perfil,re_cerrar_sesion;
    @FXML
    Text re_reservas,re_iniciore,re_finre,re_iniciodia,re_findia;
    @FXML
    Button re_procesados, re_embalaje, re_tornos, re_freasoras, re_lijadoras, re_selladora, re_soldadura, re_hornos;
    @FXML
    ResultSet rs;
    @FXML
    TableView reservas_maquina;
    @FXML
    DatePicker re_fin_dia, re_inicio_dia;

    @FXML
    public void initialize() throws SQLException {
        tabladereserva();
        idioma(idioma.idioma_default());
    }

    @FXML
    public void buttonpressed() throws SQLException {


        if (re_fin_dia.getValue() == null || re_inicio_dia.getValue() == null) {
            if ((re_procesados.isPressed())) {
                rs = database.reservas(re_procesados.getText(), tipos);
                reservas_tabla(rs);
            } else if (re_embalaje.isPressed()) {
                rs = database.reservas(re_embalaje.getText(), tipos);
                reservas_tabla(rs);
            } else if (re_tornos.isPressed()) {
                rs = database.reservas(re_tornos.getText(), tipos);
                reservas_tabla(rs);
            } else if (re_freasoras.isPressed()) {
                rs = database.reservas(re_freasoras.getText(), tipos);
                reservas_tabla(rs);
            } else if (re_lijadoras.isPressed()) {
                rs = database.reservas(re_lijadoras.getText(), tipos);
                reservas_tabla(rs);
            } else if (re_selladora.isPressed()) {
                rs = database.reservas(re_selladora.getText(), tipos);
                reservas_tabla(rs);
            } else if (re_soldadura.isPressed()) {
                rs = database.reservas(re_soldadura.getText(), tipos);
                reservas_tabla(rs);
            } else if (re_hornos.isPressed()) {
                rs = database.reservas(re_hornos.getText(), tipos);
                reservas_tabla(rs);
            }
        } else {
            if (Validaciones.horas(re_inicio_dia, re_fin_dia) == false) {

            } else {
                if ((re_procesados.isPressed())) {
                    reservas(re_procesados.getText());

                } else if (re_embalaje.isPressed()) {
                    reservas(re_embalaje.getText());
                    clear();
                } else if (re_tornos.isPressed()) {
                    reservas(re_tornos.getText());
                    clear();
                } else if (re_freasoras.isPressed()) {
                    reservas(re_freasoras.getText());
                } else if (re_lijadoras.isPressed()) {
                    reservas(re_lijadoras.getText());
                } else if (re_selladora.isPressed()) {
                    reservas(re_selladora.getText());
                } else if (re_soldadura.isPressed()) {
                    reservas(re_soldadura.getText());

                } else if (re_hornos.isPressed()) {
                    reservas(re_hornos.getText());
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

    public void clear() {
        re_fin_dia.setValue(null);
        re_inicio_dia.setValue(null);
    }

    public void tabladereserva() throws SQLException {
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

    public void reservas(String maquina) throws SQLException {
        ResultSet rs = database.reservasdefault(0);
        Alert mal = new Alert(AlertType.WARNING);
        boolean reservar = true;

        while (rs.next()) {
            reservas.add(new Reserva(rs.getString(1),rs.getString(2), rs.getDate(3).toLocalDate(), (rs.getDate(4).toLocalDate())));
        }
        int contador=0;
        for (Reserva reserva : reservas) {

            ArrayList<LocalDate> datas = reserva.fechas(maquina);
            if (reserva.getMaquina().equals(maquina)) {

                for (LocalDate fechas : datas) {

                    if (fechas.equals(re_inicio_dia.getValue()) && contador == 0
                            || fechas.equals(re_fin_dia.getValue()) && contador == 0) {
                        mal.setContentText("Esos dias ya hay una reserva de esa maquina");
                        mal.showAndWait();
                        re_fin_dia.setValue(null);
                        re_inicio_dia.setValue(null);
                        reservar = false;
                        contador++;
                        break;

                    }
                }
            }
        }
        if (reservar) {
            database.serial_num(maquina);
            database.nuevareserva(re_inicio_dia.getValue(), re_fin_dia.getValue());
            rs = database.reservas(maquina, tipos);
            reservas_tabla(rs);
            clear();
        }
    }

    @FXML
    public void info() throws IOException {
        App.setRoot("Usuario");

    }

    @FXML
    public void mis_reservas() throws IOException {
        App.setRoot("mis_reservas");
    }

    @FXML
    public void atras() throws IOException {
        App.setRoot("Inicio");
    }

     @FXML
    public void Español(){
        idioma("Español");
    }
    @FXML
    public void Ingles(){
        idioma("Ingles");
    }
    public void idioma(String lenguaje){
        idioma = new Idioma(lenguaje);
        Button[] buttones={re_procesados, re_embalaje, re_tornos, re_freasoras, re_lijadoras, re_selladora, re_soldadura, re_hornos};
        idioma.botones(buttones);
        MenuItem[] menuitems={re_mis_reservas,re_perfil,re_cerrar_sesion};
        idioma.menu_item(menuitems);
        Text[] textos={ re_reservas,re_iniciore,re_finre,re_iniciodia,re_findia};
        idioma.text(textos);
    }
}
