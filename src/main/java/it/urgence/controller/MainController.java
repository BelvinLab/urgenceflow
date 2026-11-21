package it.urgence.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller della finestra principale (menu laterale + contenuto).
 * Allo Sprint 4 gestirà il cambio di vista.
 */
public class MainController {

    @FXML
    private Label messaggio;

    @FXML
    private void initialize() {
        messaggio.setText("UrgenceFlow – Sprint 0 completato");
    }
}
