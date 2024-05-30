package ibermatica.controller;

import java.io.IOException;

import ibermatica.App;
import ibermatica.multidioma.Idioma;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Menu_admin {
    @FXML
    Button am_visualizar,am_gestion,am_estado;
    @FXML
    Text am_adminmenu; 
    Idioma idioma ;



    public void initialize(){
        
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
        am_estado.setText(idioma.getProperty("am_estado"));
        am_gestion.setText(idioma.getProperty("am_gestion"));
        am_visualizar.setText(idioma.getProperty("am_visualizar"));
        am_adminmenu.setText(idioma.getProperty("am_adminmenu"));
    }
    @FXML
    public void cerrarsesion() throws IOException {
        App.setRoot("Inicio");
    }

    @FXML
    public void gestion() throws IOException {
        App.setRoot("gestion_Usuarios");
    }

    @FXML
    public void estado() throws IOException {
        App.setRoot("gestion_maquinas");
    }

    @FXML
    public void visualizar() throws IOException {
        App.setRoot("Visualozar_Datos");
    }

    @FXML
    public void cerrar() throws IOException {
        Platform.exit();
    }

}
