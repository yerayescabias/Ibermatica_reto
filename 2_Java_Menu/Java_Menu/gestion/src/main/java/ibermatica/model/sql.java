package ibermatica.model;

import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

import java.util.ArrayList;
import java.util.Iterator;

import ibermatica.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;

public class sql {
    private String server = "localhost";
    private String user = "ibermaticaAdmin";
    private String pass = "Pa$$W0rd";
    private String db = "ibermatica_db";
    public static String id_sesion;
    public static String serail_num;

    public sql() {

    }

    public Connection konektatu() {
        String url = "jdbc:mariadb://" + server + "/" + db;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
            // System.out.println(server + " zerbidoreko " + db + " datu-basera konektatu
            // zara.");
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
                        rs.getInt("type"), rs.getDate("register_date"));
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
                } else if (buscador.hasNext() == false && found && Validaciones.rellenar==true) {
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

        String sql = "show COLUMNS FROM " + tabla;
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

    @SuppressWarnings("exports")
    public ResultSet informacion_tabla(String tabla) {
        String sql = "Select * FROM " + tabla;
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            return rs;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public ResultSet reservasdefault(int i) {
        if (i == 0) {
            String sql = "SELECT users.name, machines.name, start_date,end_date FROM reservation_machines Inner Join users On reservation_machines.user_id=users.user_id Inner Join machines On reservation_machines.serial_num=machines.serial_num";
            try (Connection conn = konektatu();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {

                ResultSet rs = pstmt.executeQuery();

                return rs;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } else if (i == 1) {
            String sql = "SELECT reservation_machines.serial_num, machines.name, start_date,end_date FROM reservation_machines Inner Join machines On reservation_machines.serial_num=machines.serial_num where user_id=?";
            try (Connection conn = konektatu();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, id_sesion);

                ResultSet rs = pstmt.executeQuery();

                return rs;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public ResultSet reservas(String maquina, int tipo) {
        if (tipo == 0) {
            String sql = "SELECT users.name , machines.name, start_date,end_date FROM reservation_machines Inner Join users On reservation_machines.user_id=users.user_id Inner Join machines On reservation_machines.serial_num=machines.serial_num WHERE machines.name=?";
            try (Connection conn = konektatu();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, maquina);
                ResultSet rs = pstmt.executeQuery();

                return rs;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } else if (tipo == 1) {
            String sql = "SELECT reservation_id,reservation_machines.serial_num , machines.name, start_date,end_date FROM reservation_machines Inner Join machines On reservation_machines.serial_num=machines.serial_num WHERE machines.name=?";
            try (Connection conn = konektatu();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, maquina);
                ResultSet rs = pstmt.executeQuery();

                return rs;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            return null;
        }

    }

    public void nuevareserva(LocalDate start, LocalDate end) {
        String sql = "INSERT INTO reservation_machines(user_id,serial_num,start_date,end_date) VALUES (?,?,?,?)";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id_sesion);
            pstmt.setString(2, serail_num);
            pstmt.setString(3, (start).toString());
            pstmt.setString(4, end.toString());
            pstmt.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public void serial_num(String maquina) {
        String sql = "Select serial_num from machines where name=? ";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maquina);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                serail_num = rs.getString(1);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public void insert_maquinas(String serialnum, String name, String estado) {
        String sql = "INSERT INTO machines (serial_num,name,status) Values (?,?,?)";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, serialnum);
            pstmt.setString(2, name);
            pstmt.setString(3, estado);

            pstmt.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    public void modificar_maquinas(String serialnum, String name, String estado, Date adquisition_date) {
        String sql = "Update machines set serial_num=? , name=?, adquisition_date=?, status=? WHERE serial_num=?";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, serialnum);
            pstmt.setString(2, name);
            pstmt.setDate(3, adquisition_date);
            pstmt.setString(4, estado);
            pstmt.setString(5, serialnum);

            pstmt.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }

    public String fecha(String serialnum) {
        String fecha = "";
        String sql = "Select adquisition_date From machines Where serial_num=?";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, serialnum);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                fecha = rs.getString(1);

            }
            return fecha;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResultSet info_maquina(String serialnum) {

        String sql = "Select * From machines Where serial_num=?";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, serialnum);

            ResultSet rs = pstmt.executeQuery();

            return rs;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ResultSet tablas(Button m, Button a) {

        if (m.isPressed()) {

            String sql = "Select * from machines";
            try (Connection conn = konektatu();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {

                ResultSet rs = pstmt.executeQuery();

                return rs;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } else if (a.isPressed()) {

            String sql = "Select * from breakdowns";
            try (Connection conn = konektatu();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {

                ResultSet rs = pstmt.executeQuery();

                return rs;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            return null;
        }

    }

    public ArrayList<String> maquinas_reservadas() {
        String sql = "Select machines.name From reservation_machines Inner join machines on reservation_machines.serial_num=machines.serial_num Where user_id=? group by machines.name";

        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id_sesion);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<String> nombremaquinas = new ArrayList<>();
            while (rs.next()) {
                nombremaquinas.add(rs.getString(1));

            }
            return nombremaquinas;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public void borrar_reserva(String ID) {
        String sql = "Delete from reservation_machines WHERE reservation_id=?";
        try (Connection conn = konektatu();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ID);
            pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }
}
