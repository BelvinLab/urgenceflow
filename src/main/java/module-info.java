module it.urgence {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // FXMLLoader deve poter creare i controller tramite riflessione
    opens it.urgence.controller to javafx.fxml;

    exports it.urgence;
}
