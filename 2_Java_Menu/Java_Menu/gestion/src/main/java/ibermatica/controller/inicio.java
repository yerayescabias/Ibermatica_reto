package ibermatica.controller;
import java.io.IOException;

import ibermatica.model.sql;
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
        database.users();
        database.Inicio_sesion(user.getText(), password.getText());

    }
}
