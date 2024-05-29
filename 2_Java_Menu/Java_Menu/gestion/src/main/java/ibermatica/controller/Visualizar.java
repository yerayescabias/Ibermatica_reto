package ibermatica.controller;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import ibermatica.App;
import ibermatica.model.sql;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
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
    public void initialize() throws SQLException{
        Iterator<String> tablas_nombre= database.nombre_tablas().iterator(); 
        while(tablas_nombre.hasNext()){
            visualizar_picker.getItems().add(tablas_nombre.next());
        }
        visualizar_picker.getSelectionModel().select(1);

    }

    @SuppressWarnings("unchecked")
    @FXML
    public void tablas() throws SQLException{
       
        String tabla= (String) visualizar_picker.getSelectionModel().getSelectedItem();
        ResultSet rs = database.informacion_tabla(tabla);
        users_table.getColumns().clear();
        users_table.getItems().clear();
    
        ArrayList<ArrayList<String>> data = new ArrayList<>();
    
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            final int colIndex = i - 1;
            TableColumn<ArrayList<String>, String> column = new TableColumn<>(database.nombre_columnas(tabla).get(i-1));
            column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(colIndex)));
            users_table.getColumns().add(column);
        }
    

        while (rs.next()) {
                ArrayList<String> informacion= new ArrayList<>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                
                informacion.add(rs.getString(i));
            }
            data.add(informacion);
            
        }
        
        users_table.getItems().addAll(data);
        
       
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
