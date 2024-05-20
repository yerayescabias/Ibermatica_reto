package ibermatica.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.sql.ResultSetMetaData;

import ibermatica.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

public class sql {
    private String server = "localhost";
    private String user = "ibermaticaAdmin";
    private String pass = "Pa$$W0rd";
    private String db = "ibermatica_db";
    public static String id_sesion;
    

    public sql() {

    }

    public Connection konektatu() {
        String url = "jdbc:mariadb://" + server + "/" + db;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println(server + " zerbidoreko " + db + " datu-basera konektatu zara.");
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
        }
        return conn;

    }

    public ArrayList<User> users() {
        String sql = "SELECT * FROM users ";

        // try-with-resources (closes all the resources when try finishes)
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            ArrayList<User> trabajadores_list = new ArrayList<User>();
            while (rs.next()) {
                User trabajador = new User(rs.getString("user_id"), rs.getString("name"), rs.getString("surname"),
                        rs.getString("email"), rs.getInt("tlf_num"), rs.getString("username"), rs.getString("password"),
                        rs.getInt("type"),rs.getDate("register_date"));
                trabajadores_list.add(trabajador);
            }
            return trabajadores_list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void Inicio_sesion(String usuario, String contraseña) throws IOException {

        // try-with-resources (closes all the resources when try finishes)
        try (Connection conn = konektatu();) {
            boolean found = true;
            Iterator buscador = users().iterator();

            while (buscador.hasNext() && found) {
                User comp_user = (User) buscador.next();
                if (comp_user.getType() == 0 && comp_user.getUsername().equals(usuario)
                        && comp_user.getPassword().equals(contraseña)) {
                    App.setRoot("Menu_admin");
                    id_sesion = comp_user.getUser_id();
                    found = false;
                } else if ((comp_user.getType() == 1 && comp_user.getUsername().equals(usuario)
                        && comp_user.getPassword().equals(contraseña))) {
                    App.setRoot("Reservas");
                    id_sesion = comp_user.getUser_id();
                    found = false;
                } else if (buscador.hasNext() == false && found) {
                    Alert iniciofallido = new Alert(AlertType.WARNING);
                    iniciofallido.initStyle(StageStyle.UNDECORATED);
                    iniciofallido.setContentText("No se ha encontrado ese usuario");
                    iniciofallido.showAndWait();
                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public boolean usersADD(User alta) {
        String sql = "INSERT INTO users (user_id,name,surname,email,tlf_num,username,password,type) VALUES (?,?,?,?,?,?,?,?) ";

        // try-with-resources (closes all the resources when try finishes)
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, alta.getUser_id());
            pstmt.setString(2, alta.getName());
            pstmt.setString(3, alta.getSurname());
            pstmt.setString(4, alta.getEmail());
            pstmt.setInt(5, alta.getTelefono());
            pstmt.setString(6, alta.getUsername());
            pstmt.setString(7, alta.getPassword());
            pstmt.setInt(8, alta.getType());
            pstmt.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void modificar(User modificar) {
        String sql = "UPDATE users SET user_id=?,name=?,surname=?,email=?,tlf_num=?,username=?,password=?,type=? WHERE user_id=?";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, modificar.getUser_id());
            pstmt.setString(2, modificar.getName());
            pstmt.setString(3, modificar.getSurname());
            pstmt.setString(4, modificar.getEmail());
            pstmt.setInt(5, modificar.getTelefono());
            pstmt.setString(6, modificar.getUsername());
            pstmt.setString(7, modificar.getPassword());
            pstmt.setInt(8, modificar.getType());
            pstmt.setString(9, modificar.getUser_id());
            pstmt.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public User buscar(String dni) {
        Iterator<User> busqueda = users().iterator();
        while (busqueda.hasNext()) {
            User datos = (User) busqueda.next();
            if (dni.equals(datos.getUser_id())) {
                return datos;
            }

        }
        return null;
    }

    public void borrar_usuario(String dni) {
        String sql = "DELETE FROM users where user_id= ?";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    public void contraseña_change(String contraseña) {
        String sql = "UPDATE FROM users SET password= ? WHERE=?";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, contraseña);
            pstmt.setString(2, id_sesion);
            pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    public ArrayList<String> nombre_tablas() {
        String sql = "Show Tables";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            ArrayList<String> nombre_tablas = new ArrayList<>();
            while (rs.next()) {
                nombre_tablas.add(rs.getString("Tables_in_ibermatica_db"));
            }
            return nombre_tablas;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public ArrayList<String> nombre_columnas(String tabla) {

        String sql = "show COLUMNS FROM "+tabla;
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            ArrayList<String> nombre_columnas = new ArrayList<>();
            while (rs.next()) {
                nombre_columnas.add(rs.getString("Field"));
                
            }
            return nombre_columnas;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
    



    
    public ResultSet informacion_tabla(String tabla){
        String sql="Select * FROM "+tabla;
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            
            return rs;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }


    }


}
