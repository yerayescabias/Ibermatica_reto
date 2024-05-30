package ibermatica.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ibermatica.App;
import ibermatica.model.Reserva;
import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import ibermatica.multidioma.Idioma;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Gestion_maquinas {
    ArrayList<String> maquinas_borrar = new ArrayList<>();
    Idioma idioma ;
     static public sql database = new sql();
    @FXML
    TextField n_num, n_tipo, m_num, m_tipo, m_fecha;
    @FXML
    ComboBox<String> m_estado, n_estado;
    @FXML
    TableView maquinas_tabla;
    @FXML
    Button gm_maquinaria,gm_averias,gm_nueva,gm_modificar;
    @FXML
    Text gm_gestionmaquinaria,gm_n_numero,gm_n_tipoma,gm_n_estadoma,gm_m_numero,gm_m_tipoma,gm_m_estadoma,gm_m_fechaad;


    @FXML
    public void initialize() {
        m_estado.getItems().addAll("Operativa", "Averiada");
        n_estado.getItems().addAll("Operativa", "Averiada");
        Validaciones.limite(n_num, 10);
        Validaciones.limite(m_num, 10);
        idioma(idioma.idioma_default());
    }

    @FXML
    public void cerrar() {
        Platform.exit();
    }

    @FXML
    public void atras() throws IOException {
        App.setRoot("Menu_admin");

    }

    @FXML
    public void nuevamaquina() throws SQLException {
        if (Validaciones.dni(n_num, 10) == false || Validaciones.nombre(n_tipo) == false
                || Validaciones.combo(n_estado) == false || Validaciones.basededatos("machines", n_num) == false) {

        } else {
            database.insert_maquinas(n_num.getText(), n_tipo.getText(),
                    Validaciones.maquinas(n_estado.getSelectionModel().getSelectedItem().toString()));
            m_num.setText(n_num.getText());
            m_tipo.setText(n_tipo.getText());
            m_fecha.setText(database.fecha(n_num.getText()));
            m_estado.getSelectionModel().select(n_estado.getSelectionModel().getSelectedItem().toString());
            n_num.setText("");
            n_tipo.setText("");
            n_estado.getSelectionModel().clearSelection();
        }

    }

    @FXML
    public void modificar_maquina() {
        if (Validaciones.dni(m_num, 10) == false || Validaciones.nombre(m_tipo) == false
                || Validaciones.combo(m_estado) == false || Validaciones.fechas(m_fecha) == false) {

        } else {
            database.modificar_maquinas(m_num.getText(), m_tipo.getText(),
                    Validaciones.maquinas(m_estado.getSelectionModel().getSelectedItem().toString()),
                    Date.valueOf(m_fecha.getText()));
            clear();
        }

    }

    @FXML
    public void rellenar() throws SQLException {
        ResultSet rs = database.info_maquina(m_num.getText());
        while (rs.next()) {
            m_num.setText(rs.getString("serial_num"));
            m_tipo.setText(rs.getString("name").toString());
            m_fecha.setText(rs.getString("adquisition_date"));
            m_estado.getSelectionModel().select(Validaciones.maquinasfromdatabase(rs.getString("status")));
        }
    }

    @SuppressWarnings("unchecked")
    @FXML
    public void radios() throws SQLException {

        ResultSet rs = database.tablas(gm_maquinaria, gm_averias);
        maquinas_tabla.getColumns().clear();
        maquinas_tabla.getItems().clear();

        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            final int colIndex = i - 1;
            TableColumn<ArrayList<String>, String> column = new TableColumn<>(rs.getMetaData().getColumnName(i));
            column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(colIndex)));
            maquinas_tabla.getColumns().add(column);
        }

        while (rs.next()) {
            ArrayList<String> informacion = new ArrayList<>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {

                informacion.add(rs.getString(i));
            }
            data.add(informacion);

        }

        maquinas_tabla.getItems().addAll(data);
        tabladinamica();

    }

    @FXML
    public void clear() {
        n_num.setText("");
        n_tipo.setText("");
        m_num.setText("");
        m_tipo.setText("");
        m_fecha.setText("");
        n_estado.getSelectionModel().clearSelection();
        m_estado.getSelectionModel().clearSelection();
    }

    @FXML
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
                            btn.setOnAction((ActionEvent event) -> {
                                boolean a=true;
                                maquinas_borrar = getTableView().getItems().get(getIndex());
                                try {
                                    ArrayList<Reserva> reservas = database.resrevas_array();
                                    for (Reserva string : reservas) {
                                        if (maquinas_borrar.get(1).equals(string.getMaquina())) {
                                            a=false;
                                        }
                                    }
                                    if(a==false){
                                        Stage decision = new Stage();
                                        Pane pane = new Pane();
                                        Scene escena = new Scene(pane);
                                        Label aviso = new Label("Se van a eliminar las reservas con este id");
                                        HBox botones = new HBox();
                                        Button eliminar = new Button("Eliminar");

                                        eliminar.setOnAction((ActionEvent eevent) -> {

                                            database.eliminar_maquinita(maquinas_borrar.get(0));
                                            database.borrar_maquina(maquinas_borrar.get(0));
                                            maquinas_tabla.getItems().clear();
                                            decision.close();
                                        });
                                        botones.getChildren().addAll(eliminar);
                                        pane.getChildren().addAll(aviso, botones);
                                        botones.setLayoutX(115);
                                        botones.setLayoutY(25);

                                        decision.setScene(escena);
                                        decision.show();
                                        decision.setResizable(false);
                                    }else{
                                            database.borrar_maquina(maquinas_borrar.get(0));
                                            maquinas_tabla.getItems().clear();
                                    }
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
        maquinas_tabla.getColumns().add(actionCol);

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
        Button[] butones={gm_maquinaria,gm_averias,gm_nueva,gm_modificar};
        idioma.botones(butones);
        Text[] textos={ gm_gestionmaquinaria,gm_n_numero,gm_n_tipoma,gm_n_estadoma,gm_m_numero,gm_m_tipoma,gm_m_estadoma,gm_m_fechaad};
        idioma.text(textos);
    }
}
