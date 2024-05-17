package ibermatica.controller;


import java.util.Iterator;





import ibermatica.model.sql;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class Visualizar {
    sql database= new sql();
    
    @FXML
    ComboBox visualizar_picker;

    @FXML
    TableView users_table;


    @FXML
    public void initialize(){
        Iterator<String> tablas_nombre= database.nombre_tablas().iterator(); 
        while(tablas_nombre.hasNext()){
            visualizar_picker.getItems().add(tablas_nombre.next());
        }
        

    }

    @FXML
    public void tablas(){
        users_table.getColumns().clear();
        String tabla= (String) visualizar_picker.getSelectionModel().getSelectedItem();
        for (int i=1;database.cantidad_columnas(tabla)>=i;++i) {
                if(i>database.cantidad_columnas(tabla)){
                    break;
                }else{
                    TableColumn<Object,Object> column= new TableColumn<>(database.nombre_columnas(tabla).get(i-1));
                    users_table.getColumns().add(column);
                }
                
            
        }
    }
    }

