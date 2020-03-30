module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;

    opens client to javafx.fxml;
    exports client;
}