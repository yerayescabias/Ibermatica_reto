package ibermatica.model;
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;


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
    public static String maquinas(String estado){
        if(estado.equals("Operativa")){
            return "operational";
        }else if(estado.equals("Averiada")){
            return "not_operational";
        }else{
            return null;
        }
    }
    public static String maquinasfromdatabase(String estado){
        if(estado.equals("operational")){
            return "Operativa";
        }else if(estado.equals("not_operational")){
            return "Averiada";
        }else{
            return null;
        }
    }
    @FXML
    public void cerrar (){
        Platform.exit();
    }

    
}
