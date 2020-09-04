package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.*;
import view.AlertBox;

import java.math.BigDecimal;
import java.util.List;

public class AddEditProductController {

    @FXML
    private Label title;

    @FXML
    private TextField nameTextField, barcodeTextField, quantityTextField, priceTextField;

    @FXML
    private ComboBox<Vehicle> vehicleComboBox;

    @FXML ComboBox<Manufacturer> manufacturerComboBox;

    @FXML ComboBox<Category> categoryComboBox;

    @FXML
    private TextArea descriptionTextArea;

    private Stage stage;
    private VehicleProduct vehicleProduct;

    public void initialize(Stage stage, VehicleProduct vehicleProduct) {
        this.stage = stage;
        this.vehicleProduct = vehicleProduct;
        if (vehicleProduct == null) {
            title.setText("Add product");
        } else {
            title.setText("Edit product");
            nameTextField.setText(vehicleProduct.getProduct().getName());
            barcodeTextField.setText(vehicleProduct.getProduct().getBarcode());
            quantityTextField.setText(vehicleProduct.getProduct().getQuantity().toString());
            priceTextField.setText(vehicleProduct.getProduct().getPrice().toString());
            vehicleComboBox.getSelectionModel().select(vehicleProduct.getVehicle());
            manufacturerComboBox.getSelectionModel().select(vehicleProduct.getProduct().getManufacturer());
            categoryComboBox.getSelectionModel().select(vehicleProduct.getProduct().getCategory());
            descriptionTextArea.setText(vehicleProduct.getProduct().getDescription());
            vehicleComboBox.setDisable(true);
        }
        categoryComboBox.getItems().addAll(MySQLDAOFactory.getDAOFactory().getCategoryDAO().categories());
        manufacturerComboBox.getItems().addAll(MySQLDAOFactory.getDAOFactory().getManufacturerDAO().manufacturers());
        vehicleComboBox.getItems().addAll(MySQLDAOFactory.getDAOFactory().getVehicleDAO().vehicles());
    }

    public void save() {
        try {
            String name = nameTextField.getText();
            String barcode = barcodeTextField.getText();
            BigDecimal quantity = quantityTextField.getText().isEmpty() ? null : new BigDecimal(quantityTextField.getText());
            BigDecimal price = priceTextField.getText().isEmpty() ? null : new BigDecimal(priceTextField.getText());
            String description = descriptionTextArea.getText();
            Category selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
            Manufacturer selectedManufacturer = manufacturerComboBox.getSelectionModel().getSelectedItem();
            Vehicle selectedVehicle = vehicleComboBox.getSelectionModel().getSelectedItem();
        if (!(name.isEmpty() || barcode.isEmpty() || quantity == null || selectedCategory == null || selectedManufacturer == null || selectedVehicle == null)) {
            if (vehicleProduct == null) {
                Product product = new Product(null, name, quantity, barcode, price, selectedManufacturer, selectedCategory, description, true);
                if (MySQLDAOFactory.getDAOFactory().getProductDAO().insert(product)) {
                    product.setID(MySQLDAOFactory.getDAOFactory().getProductDAO().lastID());
                    if (MySQLDAOFactory.getDAOFactory().getVehicleProductDAO().insert(new VehicleProduct(selectedVehicle, product, true))) {
                        new AlertBox().display("Product successfully added!");
                        stage.close();
                    } else throw new Exception();
                } else throw new Exception();
            } else {
                Product product = vehicleProduct.getProduct();
                product.setName(name);
                product.setBarcode(barcode);
                product.setQuantity(quantity);
                product.setPrice(price);
                product.setDescription(description);
                product.setCategory(selectedCategory);
                product.setManufacturer(selectedManufacturer);
                if (MySQLDAOFactory.getDAOFactory().getProductDAO().update(product)) {
                    new AlertBox().display("Product successfully edited!");
                    stage.close();
                } else throw new Exception();
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            new AlertBox().display("Error happened while adding product. Please check input options!");
        }

    }

    public void cancel() {
        stage.close();
    }
}
