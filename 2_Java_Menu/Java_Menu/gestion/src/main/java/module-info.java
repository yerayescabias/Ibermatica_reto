module ibermatica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens ibermatica to javafx.fxml;
    exports ibermatica;
}
