package ibermatica.model;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class Validaciones {

    public static String usuarioAUTO(String nombre, String apellido) {
        return nombre.substring(0, 3) + apellido.substring(0, 3);
    }

    public static String contraseñasAUTO(String nombre) {
        return "Ayesa." + nombre + LocalDate.now().getYear();
    }
    public static String tipo(User user){
        if(user.getType()==1){
            return "Obrero";
        }else if (user.getType()==0){
            return "Oficinista";
        }else{
            return "";
        }

    }
    @FXML
    public static Integer input_tipo(ComboBox tipo){
        if (tipo.getSelectionModel().getSelectedItem().equals("Oficinista")) {
            return  0;
        } else if ((tipo.getSelectionModel().getSelectedItem().equals("Obrero"))) {
            return 1;
        } else {
            return null;
        }

    }
}
