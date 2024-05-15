package ibermatica.model;

public class Validaciones {
    

    public static String usuarioAUTO(String nombre, String apellido){
        return nombre.substring(0,3)+apellido.substring(0,3);
    }

    public static String contraseñasAUTO(String nombre){
        return "Ayesa."+nombre;
    }
    
}
