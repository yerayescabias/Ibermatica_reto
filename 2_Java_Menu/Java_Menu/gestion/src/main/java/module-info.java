module ibermatica {
    requires javafx.controls;
    requires javafx.fxml;

    opens ibermatica to javafx.fxml;
    exports ibermatica;
}
