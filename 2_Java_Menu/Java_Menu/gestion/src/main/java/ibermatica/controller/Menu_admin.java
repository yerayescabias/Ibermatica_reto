package ibermatica.controller;

import java.io.IOException;

import ibermatica.App;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class Menu_admin {
    

    
    @FXML
    public void cerrarsesion()throws IOException{
        App.setRoot("Inicio");
    }
    @FXML
    public void gestion() throws IOException{
        App.setRoot("gestion_Usuarios");
    }

    @FXML
    public void estado() throws IOException{
        App.setRoot("gestion_maquinas");
    }

    @FXML
    public void visualizar() throws IOException{
        App.setRoot("Visualozar_Datos");
    }

    @FXML
    public void cerrar() throws IOException{
        Platform.exit();
    }
}
