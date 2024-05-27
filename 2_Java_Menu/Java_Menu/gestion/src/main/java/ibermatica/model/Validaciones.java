package ibermatica.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class Validaciones {
    static sql database = new sql();
    static boolean rellenar = true;
    static Alert alerta = new Alert(AlertType.WARNING);

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
        rellenar = true;
        if (usuario.isEmpty() || Contraseña.isEmpty()) {
            Alert rellena = new Alert(AlertType.WARNING);
            rellena.setContentText("Rellena todos los campos porfavor");
            rellena.showAndWait();
            rellenar = false;

        } else if (!(usuario.isEmpty())) {
            usuario = usuario.replaceAll("\\s", "");
            usuario = usuario.replaceAll("\\d", "");
            usuario = usuario.replaceAll("[^a-zA-Z_]", "");

        }
        return usuario;
    }

    @FXML
    public static boolean dni(TextField dni, int maximo) {
        final int maximus=maximo-1;
        if (dni.getText().isEmpty()) {
            alerta.setContentText("Rellena el dni");
            alerta.showAndWait();
            dni.setText("");
            return false;

        } else if ((!(dni.getText().matches("^\\d{" + maximus + "}[A-Z]$")))) {
            alerta.setContentText("El Formato es inadecaudo " + maximo + " numeros + 1 letra");
            alerta.showAndWait();
            dni.setText("");
            return false;

        } else {
            return true;
        }

    }

    @FXML
    public static boolean apellido(TextField apellido, TextField email) {

        if (apellido.getText().isEmpty()) {
            alerta.setContentText("Rellena el apellido");
            alerta.showAndWait();
            return false;
        }
        if (email.getText().isEmpty()) {
            alerta.setContentText("Rellena el email");
            alerta.showAndWait();
            return false;
        }

        if (!(apellido.getText().isEmpty() && email.getText().isEmpty()) && email.getText().matches((".*@+.*"))) {
            apellido.setText(apellido.getText().replaceAll("[\\s\\d]", ""));
            apellido.setText(apellido.getText().substring(0, 1).toUpperCase() + apellido.getText().substring(1));
            return true;

        } else if (!email.getText().matches((".*@+.*"))) {
            alerta.setContentText("El formato no es válido, recuerda ..@..");
            alerta.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    @FXML
    public static boolean nombre(TextField nombre) {
        if ((nombre.getText().isEmpty())) {
            alerta.setContentText("Rellena el nombre");
            alerta.showAndWait();
            return false;
        } else if (!(nombre.getText().isEmpty())) {
            nombre.setText(nombre.getText().replaceAll("[\\s\\d]", ""));
            nombre.setText(nombre.getText().substring(0, 1).toUpperCase() + nombre.getText().substring(1));
            return true;
        } else {
            return true;
        }
    }

    @FXML
    public static boolean combo(ComboBox comboBox) {
        if (comboBox.getValue() == (null)) {
            alerta.setContentText("Selecciona un valor");
            alerta.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    @FXML
    public static boolean telefono(TextField telefono) {
        if (telefono.getText().isEmpty()) {
            alerta.setContentText("Meta el numero telefonico");
            alerta.showAndWait();
            return false;
        } else {
            return true;
        }

    }

    @FXML
    public static void limite(TextField dni, int maximo) {
        dni.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String valorAnterior,
                    final String valorActual) {

                if (dni.getText().length() >= maximo) {
                    String s = dni.getText().substring(0, maximo);

                    dni.setText(s.toUpperCase());
                }
            }

        });
    }

    @FXML
    public static void limitetelefono(TextField telefono) {
        telefono.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String valorAnterior,
                    final String valorActual) {

                if (telefono.getText().length() >= 9) {
                    String s = telefono.getText().substring(0, 9);

                    telefono.setText(s.replaceAll("[^0-9]", ""));
                }
            }

        });
    }

    public static boolean basededatos(String tabla, TextField id) throws SQLException {
        ResultSet rs = database.informacion_tabla(tabla);
        boolean comprobacion = true;
        while (rs.next()) {
            String a = (rs.getString(1));
            String v = id.getText();
            if (a.equals(v)) {
                comprobacion = true;
                alerta.setContentText("El ese id ya esta registrado ");
                alerta.showAndWait();
                break;
            } else if (rs.wasNull()) {
                comprobacion = false;
            }

        }
        return comprobacion;
    }

    public static boolean fechas (TextField fecha){
        if(fecha.getText().isEmpty()){
            alerta.setContentText("Rellena la fecha");
            alerta.showAndWait();
            return false;
        }else if( !(fecha.getText().isEmpty())){
            try {
                LocalDate.parse(fecha.getText());
                return true;
            } catch (Exception e) {
                alerta.setContentText("Fecha invalida");
                alerta.showAndWait();
                return false;
            }
        
        }else{
            return true;
        }

    }
    public static boolean horas (DatePicker fecha,DatePicker fecha_final ){
        if(fecha.getValue().equals(null) ||  fecha_final.getValue().equals(null) ){
            alerta.setContentText("Rellena la fecha");
            alerta.showAndWait();
            return false;
        }else if (fecha_final.getValue().isBefore(fecha.getValue())){
            alerta.setContentText("Primero la fecha de inicio y despues la fecha final");
            alerta.showAndWait();
            return false;
        
        }else{
            return true;
        }

    }
    
    
}
