package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.Product;
import model.DTO.Supplier;
import view.AlertBox;

import java.math.BigDecimal;
import java.util.List;

public class OrderProductsDetailsController {

    private Stage stage;
    private Product product;

    @FXML
    private ComboBox<Supplier> supplierComboBox;
    @FXML
    private TextField quantityTextField;

    private List<Supplier> availableSuppliers;

    public void initialize(Stage stage, Product product) {
        this.stage = stage;
        this.product = product;
        availableSuppliers = MySQLDAOFactory.getDAOFactory().getSupplierProductDAO().suppliersForProduct(product);
        supplierComboBox.getItems().addAll(availableSuppliers);
    }

    public void cancel() {
        stage.close();
    }

    public void order() {
        try {
            BigDecimal quantity = quantityTextField.getText().isEmpty() ? null : new BigDecimal(quantityTextField.getText());
            Supplier selectedSupplier = supplierComboBox.getSelectionModel().getSelectedItem();

            if (quantity != null && selectedSupplier != null) {
                product.setQuantity(product.getQuantity().add(quantity));
                if (MySQLDAOFactory.getDAOFactory().getProductDAO().update(product)) {
                    new AlertBox().display("Order successful!");
                    stage.close();
                } else {
                    throw new Exception();
                }
            }
            else throw new Exception();
        } catch (Exception e) {
            new AlertBox().display("Unsuccessful order. Please check input options!");
        }
    }
}
