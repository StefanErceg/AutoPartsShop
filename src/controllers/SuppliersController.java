package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.Supplier;
import model.DTO.SupplierCity;
import view.AddEditSupplier;
import view.AlertBox;

import java.util.List;

public class SuppliersController {

    private Stage stage;

    @FXML
    private Button backButton;

    @FXML
    private TableView<SupplierCity> supplierCityTable;

    @FXML
    private TableColumn<SupplierCity, String> supplierNameColumn, cityNameColumn, addressColumn, postcodeColumn, countryNameColumn;

    private List<SupplierCity> suppliersCities;

    public void initialize(Stage stage) {
        this.stage = stage;
        loadSuppliersCities();
        supplierNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplier().getName()));
        cityNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity().getName()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        postcodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity().getPostcode()));
        countryNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity().getCountry().getName()));
        displaySuppliersCities();

    }

    public void addSupplier() {
        new AddEditSupplier().display(null);
        loadSuppliersCities();
        displaySuppliersCities();
    }

    public void removeSupplier() {
        SupplierCity selected = supplierCityTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (MySQLDAOFactory.getDAOFactory().getSupplierCityDAO().delete(selected)) {
                new AlertBox().display("Supplier successfully removed!");
                loadSuppliersCities();
                displaySuppliersCities();
            } else new AlertBox().display("Error happened while removing supplier.");
        } else {
            new AlertBox().display("Supplier must be selected for removal!");
        }
    }

    public void backToDash() {
        stage.close();
    }

    private void loadSuppliersCities() {
        suppliersCities = MySQLDAOFactory.getDAOFactory().getSupplierCityDAO().suppliersCities();
    }

    private void displaySuppliersCities() {
        supplierCityTable.getItems().clear();
        supplierCityTable.getItems().addAll(suppliersCities);
        supplierCityTable.refresh();
    }
}
