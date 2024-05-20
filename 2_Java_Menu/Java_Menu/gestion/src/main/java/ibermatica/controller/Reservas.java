package ibermatica.controller;

import java.io.IOException;

import ibermatica.App;
import javafx.fxml.FXML;

public class Reservas {

    @FXML
    public void info() throws IOException{
        App.setRoot("Usuario");
    }
    
    @FXML
    public void atras()throws IOException{
        App.setRoot("Inicio");
    }
}
