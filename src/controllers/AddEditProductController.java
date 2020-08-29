package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.DTO.Product;

public class AddEditProductController {

    @FXML
    private Label title;

    private Stage stage;
    private Product product;

    public void initialize(Stage stage, Product product) {
        this.stage = stage;
        this.product = product;
        if (product == null) {
            title.setText("Add product");
        } else {
            title.setText("Edit product");
        }
    }

    public void cancel() {
        stage.close();
    }
}
