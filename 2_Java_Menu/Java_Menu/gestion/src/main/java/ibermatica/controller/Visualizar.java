package ibermatica.controller;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import java.sql.ResultSetMetaData;

import ibermatica.App;
import ibermatica.model.sql;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class Visualizar {
    sql database= new sql();

    
    @FXML
    ComboBox visualizar_picker;

    @FXML
    TableView users_table;


    @FXML
    public void initialize() throws SQLException{
        Iterator<String> tablas_nombre= database.nombre_tablas().iterator(); 
        while(tablas_nombre.hasNext()){
            visualizar_picker.getItems().add(tablas_nombre.next());
        }

    }

    @SuppressWarnings("unchecked")
    @FXML
    public void tablas() throws SQLException{
        
        String tabla= (String) visualizar_picker.getSelectionModel().getSelectedItem();
        ResultSet rs = database.informacion_tabla(tabla);
        for (int i=1;rs.getMetaData().getColumnCount()>=i;++i) {
                if(i>rs.getMetaData().getColumnCount()){
                    break;
                }else{
                        TableColumn<Object,Object> column= new TableColumn<Object,Object>(database.nombre_columnas(tabla).get(i-1));
                        column.setCellValueFactory( new PropertyValueFactory<>((database.nombre_columnas(tabla).get(i))));
                    users_table.getColumns().add(column);
                        
                    }
                }
                
         while(rs.next()){
           
            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++){
                String dato = rs.getString(i);
                users_table.getItems().add(dato);
                
            }
            
        }
    }
    
    
     @FXML
    public void cerrar(){
        Platform.exit();
    }
    @FXML
    public void atras()throws IOException{
        App.setRoot("Menu_admin");
    }
}
