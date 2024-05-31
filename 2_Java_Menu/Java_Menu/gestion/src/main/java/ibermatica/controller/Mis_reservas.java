package ibermatica.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;
import ibermatica.App;
import ibermatica.model.sql;
import ibermatica.multidioma.Idioma;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class Mis_reservas {
    ArrayList<String> paborrar = new ArrayList<>();
    final int tipo = 1;
    String id_inicio = sql.id_sesion;
    Idioma idioma ;
    sql database = new sql();
    @FXML
    ScrollPane buttons;
    @FXML
    TableView tabla_mis_reservas;
    @FXML
    Label fecha,nombre,apellido;
    @FXML
    Text mr_conosotros, mr_anos, mr_perfil, mr_reservas;
    @FXML
    Button mr_info, mr_mr, mr_cs, mr_atras;
    
    

    @FXML
    public void initialize() throws SQLException {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Spain"));
        cal.setTime(database.buscar(id_inicio).getRegisterDate());
        String años = String.valueOf((cal.get(Calendar.YEAR)) - LocalDate.now().getYear());
        fecha.setText(años);
        nombre.setText(database.buscar(id_inicio).getName());
        apellido.setText(database.buscar(id_inicio).getSurname());
        buttonbar();

        ResultSet rs = database.reservasdefault(tipo);
        tabla_mis_reservas.getColumns().clear();
        tabla_mis_reservas.getItems().clear();
        ArrayList<ArrayList<String>> columnas = new ArrayList<>();
        for (int index = 1; index <= rs.getMetaData().getColumnCount(); index++) {
            final int col = index - 1;
            TableColumn<ArrayList<String>, String> columa = new TableColumn<>(rs.getMetaData().getColumnName((index)));
            columa.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(col)));
            tabla_mis_reservas.getColumns().add(columa);
        }
        while (rs.next()) {
            ArrayList<String> reservasdatos = new ArrayList<>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                reservasdatos.add(rs.getString(i));

            }
            columnas.add(reservasdatos);
        }
        tabla_mis_reservas.getItems().addAll(columnas);
        idioma(idioma.idioma_default());
    }

    @SuppressWarnings("unchecked")
    @FXML
    public void datos(ResultSet rs) throws SQLException {

        tabla_mis_reservas.getColumns().clear();
        tabla_mis_reservas.getItems().clear();
        ArrayList<ArrayList<String>> columnas = new ArrayList<>();
        for (int index = 1; index <= rs.getMetaData().getColumnCount(); index++) {
            final int col = index - 1;
            TableColumn<ArrayList<String>, String> columa = new TableColumn<>(rs.getMetaData().getColumnName((index)));
            columa.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(col)));
            tabla_mis_reservas.getColumns().add(columa);
        }
        while (rs.next()) {
            ArrayList<String> reservasdatos = new ArrayList<>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                reservasdatos.add(rs.getString(i));
            }
            columnas.add(reservasdatos);
        }
        tabla_mis_reservas.getItems().addAll(columnas);
        tabladinamica();
    }

    public void tabladinamica() {
        TableColumn actionCol = new TableColumn("Action");
        actionCol.setCellValueFactory(new PropertyValueFactory<>(""));

        Callback<TableColumn<ArrayList<String>, String>, TableCell<ArrayList<String>, String>> cellFactory = new Callback<TableColumn<ArrayList<String>, String>, TableCell<ArrayList<String>, String>>() {
            @Override
            public TableCell call(final TableColumn<ArrayList<String>, String> param) {
                final TableCell<ArrayList<String>, String> cell = new TableCell<ArrayList<String>, String>() {

                    final Button btn = new Button("X");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {

                                paborrar = getTableView().getItems().get(getIndex());
                                database.borrar_reserva(paborrar.get(0));
                                tabla_mis_reservas.getItems().clear();
                                try {
                                    datos(database.reservas(paborrar.get(2), tipo));
                                    buttonbar();
                                } catch (SQLException e) {

                                    e.printStackTrace();
                                }

                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        actionCol.setCellFactory(cellFactory);
        tabla_mis_reservas.getColumns().add(actionCol);

    }

    public void buttonbar() {
        HBox kendrick = new HBox();
        Iterator botones = database.maquinas_reservadas().iterator();
        while (botones.hasNext()) {
            String nombre = (String) botones.next();

            Button info = new Button(nombre);
            info.setOnMousePressed((MouseEvent) -> {
                if (info.isPressed()) {
                    try {
                        ResultSet rs = database.reservas(info.getText(), tipo);
                        datos(rs);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            kendrick.getChildren().addAll(info);
        }
        buttons.setContent(kendrick);
    }

    @FXML
    public void atras() throws IOException {
        App.setRoot("mis_reservas");
    }

    @FXML
    public void previous() throws IOException {
        App.setRoot("Reservas");
    }

    @FXML
    public void cerrarsesion() throws IOException {
        App.setRoot("Inicio");
    }

    @FXML
    public void info() throws IOException {
        App.setRoot("Usuario");
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
        Text[] texto={ mr_conosotros, mr_anos, mr_perfil, mr_reservas};
        idioma.text(texto);
        Button[] buttone={mr_info, mr_mr, mr_cs, mr_atras};
        idioma.botones(buttone);
    }
}
