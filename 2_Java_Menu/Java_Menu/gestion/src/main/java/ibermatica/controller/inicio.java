package ibermatica.controller;
import java.io.IOException;

import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import ibermatica.multidioma.Idioma;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class inicio{
    sql database = new sql();
    static Idioma idioma;
    @FXML
    TextField in_user;
    @FXML
    PasswordField in_password;
    @FXML
    Label in_usuario,in_contra;
    @FXML
    Button in_btn;


    @FXML
    public void iniciar() throws IOException{
        String usuario=Validaciones.inicio(in_user.getText(), in_password.getText());
        database.users();
        database.Inicio_sesion(usuario, in_password.getText());

    }

    @FXML
    public void cerrar() throws IOException{
        Platform.exit();
    }

    @FXML
    public void Español(){
        idioma("Español");
        idioma.setLan(true);
    }
    @FXML
    public void Ingles(){
        idioma("Ingles");
        idioma.setLan(false);
    }
    public void idioma(String lenguaje){
        idioma = new Idioma(lenguaje);
        in_btn.setText(idioma.getProperty("in_btn"));
        in_usuario.setText(idioma.getProperty("in_usuario"));
        in_contra.setText(idioma.getProperty("in_contra"));
    }
}
