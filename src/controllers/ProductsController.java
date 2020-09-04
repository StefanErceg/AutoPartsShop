package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.Product;
import model.DTO.Vehicle;
import model.DTO.VehicleProduct;
import view.AddEditProduct;
import view.AlertBox;
import view.Dashboard;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsController {

    private Stage stage;
    private List<VehicleProduct> products;

    @FXML
    private TableColumn<VehicleProduct, String> nameColumn, manufacturerColumn, quantityColumn, barcodeColumn, categoryColumn, descriptionColumn, priceColumn, vehicleColumn;

    @FXML
    private TableView<VehicleProduct> productsTable;

    @FXML
    private TextField searchTextField;

    public void initialize(Stage stage) {
        this.stage = stage;
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getName()));
        manufacturerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getManufacturer().getName()));
        quantityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getQuantity().toString()));
        barcodeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getBarcode()));
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getCategory().getName()));
        descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getDescription()));
        priceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getPrice().toString()));
        vehicleColumn.setCellValueFactory(data -> {
            Vehicle vehicle = data.getValue().getVehicle();
            return new SimpleStringProperty(vehicle.getManufacturer().getName() + " " + vehicle.getModel() + " " + vehicle.getEngine());

        });
        loadProducts();
        displayProducts();

    }

    public void addProduct() {
        new AddEditProduct().display(null);
        loadProducts();
        displayProducts();
    }

    public void editProduct() {
        VehicleProduct selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new AlertBox().display("Product must be selected for editing!");
        } else {
            new AddEditProduct().display(selected);
            loadProducts();
            displayProducts();
        }
    }

    public void deleteProduct() {
        VehicleProduct selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new AlertBox().display("Product must be selected for delete!");
        } else {
            if (MySQLDAOFactory.getDAOFactory().getVehicleProductDAO().delete(selected)) {
                new AlertBox().display("Product successfully deleted!");
                loadProducts();
                displayProducts();
            } else {
                new AlertBox().display("Error happened while deleting product!");
            }
        }
    }

    public void search() {
        String keyword = searchTextField.getText().toUpperCase();
        productsTable.getItems().clear();
        productsTable.getItems().addAll(products.stream()
                .filter(product -> product.getProduct().getName().toUpperCase().contains(keyword)).collect(Collectors.toList()));
        productsTable.refresh();

    }

    public void backToDash() {
        stage.close();
    }

    private void loadProducts() {
        products = MySQLDAOFactory.getDAOFactory().getVehicleProductDAO().vehiclesProducts();
    }

    private void displayProducts() {
        productsTable.getItems().clear();
        productsTable.getItems().addAll(products);
        productsTable.refresh();
    }


}
