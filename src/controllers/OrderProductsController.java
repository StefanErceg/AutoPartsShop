package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.Vehicle;
import model.DTO.VehicleProduct;
import view.AlertBox;
import view.OrderProductsDetails;

import java.util.List;

public class OrderProductsController {

    private Stage stage;

    private List<VehicleProduct> products;

    @FXML
    private TableColumn<VehicleProduct, String> nameColumn, manufacturerColumn, quantityColumn, barcodeColumn,
            categoryColumn, vehicleColumn, priceColumn;

    @FXML
    private TableView<VehicleProduct> productsTable;

    public void initialize(Stage stage) {
        this.stage = stage;
        products = MySQLDAOFactory.getDAOFactory().getVehicleProductDAO().vehiclesProducts();
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getName()));
        manufacturerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getManufacturer().getName()));
        quantityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getQuantity().toString()));
        barcodeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getBarcode()));
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getCategory().getName()));
        priceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getPrice().toString()));
        vehicleColumn.setCellValueFactory(data -> {
            Vehicle vehicle = data.getValue().getVehicle();
            return new SimpleStringProperty(vehicle.getManufacturer().getName() + " " + vehicle.getModel() + " " + vehicle.getEngine());
        });
        productsTable.getItems().addAll(products);
        productsTable.refresh();
    }

    public void backToDash() {
        stage.close();
    }

    public void next() {
        VehicleProduct selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            new OrderProductsDetails().display(selected.getProduct());
        } else {
            new AlertBox().display("Product must be selected for order!");
        }
    }
}
