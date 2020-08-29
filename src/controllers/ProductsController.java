package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.AddEditProduct;
import view.Dashboard;

public class ProductsController {

    private Stage stage;

    @FXML
    Button backButton;

    public void initialize(Stage stage) {
        this.stage = stage;
    }

    public void addProduct() {
        new AddEditProduct().display(null);
    }

    public void backToDash() {
        stage.close();
    }



}
