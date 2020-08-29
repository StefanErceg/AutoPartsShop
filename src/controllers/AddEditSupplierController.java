package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.City;
import model.DTO.Supplier;
import model.DTO.SupplierCity;
import view.AlertBox;

import java.util.List;
import java.util.stream.Collectors;

public class AddEditSupplierController {

    @FXML
    private Label title;
    @FXML
    private TextField nameTextField, addressTextField;
    @FXML
    private ComboBox<String> citiesComboBox;

    private Stage stage;
    private SupplierCity supplier;

    private List<City> cities;

    public void initialize(Stage stage, SupplierCity supplier) {
        this.stage = stage;
        this.supplier = supplier;
        this.cities = MySQLDAOFactory.getDAOFactory().getCityDAO().cities();
        citiesComboBox.getItems().addAll(cities.stream().map(City::getName).collect(Collectors.toList()));
        if (supplier == null) {
            title.setText("Add supplier");
        } else {
            title.setText("Edit supplier");

        }
    }

    public void save() {
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        City selectedCity = cities.stream().filter(city -> city.getName().equals(citiesComboBox.getSelectionModel().getSelectedItem()))
                .findFirst().orElse(null);
        if (!name.isEmpty() && !address.isEmpty() && selectedCity != null) {
            if (supplier == null) {
                Supplier supplierToAdd = new Supplier(null, name, true);

                if (MySQLDAOFactory.getDAOFactory().getSupplierDAO().insert(supplierToAdd)) {
                    supplierToAdd.setID(MySQLDAOFactory.getDAOFactory().getSupplierDAO().lastID());
                    SupplierCity supplierCityToAdd = new SupplierCity(supplierToAdd, selectedCity, address, true);
                    if (MySQLDAOFactory.getDAOFactory().getSupplierCityDAO().insert(supplierCityToAdd)) {
                        new AlertBox().display("Supplier successfully added!");
                    } else {
                        new AlertBox().display("Error happened while adding supplier. Please check input options.");
                    }

                } else {
                    new AlertBox().display("Error happened while adding supplier. Please check input options.");
                }
            }
        }
        stage.close();
    }

    public void cancel() {
        stage.close();
    }
}
