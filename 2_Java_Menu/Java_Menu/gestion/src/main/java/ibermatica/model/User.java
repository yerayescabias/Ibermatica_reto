package ibermatica.model;

import java.util.Date;

public class User {
    public String user_id;
    public String name;
    public String surname;
    public String email;
    public int telefono;
    public String username;
    public String password;
    public Date registerDate;
    public int type;
    public Date deleted_Date;

    public User(String user_id, String name, String surname, String email, int telefono, String username,
            String password, int type,Date registerDate) {
        this.user_id = user_id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telefono = telefono;
        this.username = username; // setUsername(name, surname);
        this.password = password;
        this.registerDate=registerDate;

        this.type = type;
    }
    public User(String user_id, String name, String surname, String email, int telefono, String username,
            String password, int type) {
        this.user_id = user_id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telefono = telefono;
        this.username = username; // setUsername(name, surname);
        this.password = password;
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String nombre, String apellido, String username) {
        /*
         * String username=nombre.substring(0, 3) + apellido.substring(0,3);
         * return username;
         */
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    

}
