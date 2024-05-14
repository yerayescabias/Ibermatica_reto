package ibermatica.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Iterator;

import ibermatica.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

public class sql {
    private String server="localhost";
    private String user="ibermaticaAdmin";
    private String pass="Pa$$W0rd";
    private String db="ibermatica_db";
    private String id_sesion;
    

    public sql(){

    }
    
    
    
    public Connection konektatu(){
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
    public ArrayList<User> users(){
        String sql ="SELECT * FROM users " ;
    
        // try-with-resources (closes all the resources when try finishes)
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            ResultSet rs = pstmt.executeQuery();
            ArrayList<User> trabajadores_list = new ArrayList<User>();
            while(rs.next()){
                User trabajador = new User(rs.getString("user_id"),rs.getString("name"),rs.getString("surname"),rs.getString("email"),rs.getInt("tlf_num"),rs.getString("username"),rs.getString("password"),rs.getDate("register_date"),rs.getInt("type"));
                trabajadores_list.add(trabajador);
            }
            return trabajadores_list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public void Inicio_sesion(String usuario,String contraseña) throws IOException{
        
    
        // try-with-resources (closes all the resources when try finishes)
        try (Connection conn = konektatu();){
            boolean found=true;
            Iterator buscador = users().iterator();
            while(found){
                while(buscador.hasNext()){
                    User comp_user= (User) buscador.next();
                    if(comp_user.getType()==0 && comp_user.getUsername().equals(usuario) && comp_user.getPassword().equals(contraseña) ){
                        App.setRoot("Menu_admin");
                        id_sesion=comp_user.getUser_id();
                    }else if((comp_user.getType()==1 && comp_user.getUsername().equals(usuario) && comp_user.getPassword().equals(contraseña) )){
                        App.setRoot("Menu_trabajador");
                        id_sesion=comp_user.getUser_id();
                    }
                    
                }
                found=false;
            }
            Alert iniciofallido = new Alert(AlertType.WARNING);
                    iniciofallido.initStyle(StageStyle.UNDECORATED);
                    iniciofallido.setContentText("No se ha encontrado ese usuario");
                    iniciofallido.showAndWait();
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
    
}
