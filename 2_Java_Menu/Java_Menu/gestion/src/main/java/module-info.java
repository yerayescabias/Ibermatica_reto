module ibermatica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ibermatica to javafx.fxml;
    opens ibermatica.model to javafx.fxml;
    opens ibermatica.controller to javafx.fxml;
 
    exports ibermatica;    
    exports ibermatica.model;
    exports ibermatica.controller;
}
