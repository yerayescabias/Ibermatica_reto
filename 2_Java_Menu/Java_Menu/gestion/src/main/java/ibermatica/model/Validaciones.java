package ibermatica.model;

import java.time.LocalDate;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class Validaciones {
    static sql database = new sql();
    static boolean rellenar =true;

    public static String usuarioAUTO(String nombre, String apellido) {
        return nombre.substring(0, 3) + apellido.substring(0, 3);
    }

    public static String contraseñasAUTO(String nombre) {
        return "Ayesa." + nombre + LocalDate.now().getYear();
    }

    public static String tipo(User user) {
        if (user.getType() == 1) {
            return "Obrero";
        } else if (user.getType() == 0) {
            return "Oficinista";
        } else {
            return "";
        }

    }

    @FXML
    public static Integer input_tipo(ComboBox tipo) {
        if (tipo.getSelectionModel().getSelectedItem().equals("Oficinista")) {
            return 0;
        } else if ((tipo.getSelectionModel().getSelectedItem().equals("Obrero"))) {
            return 1;
        } else {
            return null;
        }

    }

    public static String maquinas(String estado) {
        if (estado.equals("Operativa")) {
            return "operational";
        } else if (estado.equals("Averiada")) {
            return "not_operational";
        } else {
            return null;
        }
    }

    public static String maquinasfromdatabase(String estado) {
        if (estado.equals("operational")) {
            return "Operativa";
        } else if (estado.equals("not_operational")) {
            return "Averiada";
        } else {
            return null;
        }
    }

    @FXML
    public void cerrar() {
        Platform.exit();
    }

    @FXML
    public static String inicio(String usuario, String Contraseña) {
        rellenar=true;
        if (usuario.isEmpty() || Contraseña.isEmpty()) {
            Alert rellena = new Alert(AlertType.WARNING);
            rellena.setContentText("Rellena todos los campos porfavor");
            rellena.showAndWait();
            rellenar=false;

        } else if (!(usuario.isEmpty())) {
            usuario = usuario.replaceAll("\\s", "");
            usuario = usuario.replaceAll("\\d", "");
            usuario = usuario.replaceAll("[^a-zA-Z_]", "");

        }
        return usuario;
    }

    @FXML
    public static boolean dni(TextField dni){
        
        if(dni.getText().isEmpty()){
            
            dni.setText("No DNI");
            return false;
          
        }else if ((!(dni.getText().matches("^(?=.*?[A-Z]).{1,9}$")))) {
            dni.setText("Formato");
            return false;

        }else{
            return true;
        }

    }
    @FXML 
    public static boolean nombre(TextField nombre, TextField apellido,ComboBox comboBox, TextField email){
        
            if ((nombre.getText().isEmpty())){
                nombre.setText("Rellena el nombre");
                return false;
            }
            if(apellido.getText().isEmpty()){
                apellido.setText("Rellena el apellido");
                return false;
            }
            if(email.getText().isEmpty()){
                email.setText("Rellena el email");
                return false;
            } 
            if (comboBox.getSelectionModel().equals(null)){
                comboBox.setPromptText("Selecciona uno");
                return false;
            }
            
            
            
        
        if(!(nombre.getText().isEmpty() && apellido.getText().isEmpty() && email.getText().isEmpty() )){
            nombre.setText(nombre.getText().replaceAll("[\\s\\d]", ""));
            apellido.setText(apellido.getText().replaceAll("[\\s\\d]", ""));
            return true;
            

        }else if(!(email.getText().matches("@+"))){
            email.setText("Formato ...@...");
            return false;
        }else{
            return false;
        }

    }
    @FXML
    public static boolean telefono(TextField telefono){
        if(telefono.getText().isEmpty()){
            telefono.setText("Meta el numero telefonico");
            return false;
        }else{
            return true;
        }
             

    }
    @FXML
    public static void  limite(TextField dni){
        dni.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String valorAnterior, final String valorActual) {
    
                if(dni.getText().length()>=9){
            String s = dni.getText().substring(0,9);
            
            dni.setText(s.toUpperCase());
        }
    }
    
        });
    }
    @FXML
    public static void limitetelefono(TextField telefono){
        telefono.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String valorAnterior, final String valorActual) {
    
                if(telefono.getText().length()>=9){
            String s = telefono.getText().substring(0,9);
            
            telefono.setText(s.replaceAll("[^0-9]",""));
        }
    }
    
        });
    }
    

}
