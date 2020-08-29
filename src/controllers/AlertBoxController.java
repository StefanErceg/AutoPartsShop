package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertBoxController {

    @FXML
    private Label message;

    private Stage stage;


    public void initialize(Stage stage, String message) {
        this.stage = stage;
        this.message.setText(message);
    }

    public void OK() {
        stage.close();
    }
}
