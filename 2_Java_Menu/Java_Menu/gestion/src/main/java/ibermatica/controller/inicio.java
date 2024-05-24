package ibermatica.controller;
import java.io.IOException;

import ibermatica.model.Validaciones;
import ibermatica.model.sql;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class inicio {
    sql database = new sql();
    @FXML
    TextField user;
    @FXML
    PasswordField password;


    @FXML
    public void iniciar() throws IOException{
        String usuario=Validaciones.inicio(user.getText(), password.getText());
        database.users();
        database.Inicio_sesion(usuario, password.getText());

    }

    @FXML
    public void cerrar() throws IOException{
        Platform.exit();
    }
}
