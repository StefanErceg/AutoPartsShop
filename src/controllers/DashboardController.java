package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.*;

public class DashboardController {

    @FXML
    HBox products, orderProducts, manufacturers, suppliers, locations;

    private Stage stage;

    public void initialize(Stage stage) {
        this.stage = stage;
    }

    public void toProducts() {
        new Products().display();
    }

    public void toOrderProducts() {
        new OrderProducts().display();
    }

    public void toManufacturers() {
        new Manufacturers().display();
    }

    public void toSuppliers() {new Suppliers().display(); }

    public void toLocations() {new Locations().display(); }
}
