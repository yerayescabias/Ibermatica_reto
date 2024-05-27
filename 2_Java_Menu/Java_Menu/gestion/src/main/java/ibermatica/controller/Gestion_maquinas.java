package ibermatica.controller;


import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ibermatica.App;
import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Gestion_maquinas {
    sql database = new sql();
    @FXML
    TextField n_num, n_tipo, m_num, m_tipo, m_fecha;
    @FXML
    ComboBox<String> m_estado, n_estado;
    @FXML
    TableView maquinas_tabla;
    @FXML
    Button Maquinas, Averias;

    @FXML
    public void initialize() {
        m_estado.getItems().addAll("Operativa", "Averiada");
        n_estado.getItems().addAll("Operativa", "Averiada");
        Validaciones.limite(n_num, 10);
        Validaciones.limite(m_num,10);
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
        if(Validaciones.dni(n_num, 10)==false || Validaciones.nombre(n_tipo)==false || Validaciones.combo(n_estado)== false || Validaciones.basededatos("machines", n_num)==false  ){

        }else{
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
        if(Validaciones.dni(m_num, 10)==false || Validaciones.nombre(m_tipo)==false || Validaciones.combo(m_estado)== false || Validaciones.fechas(m_fecha)==false  ){
            
        }else{
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

        ResultSet rs = database.tablas(Maquinas, Averias);
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
}
