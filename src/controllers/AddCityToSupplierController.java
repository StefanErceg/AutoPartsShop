package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.City;
import model.DTO.Supplier;
import model.DTO.SupplierCity;
import view.AlertBox;

import java.util.List;
import java.util.stream.Collectors;

public class AddCityToSupplierController {

    private Stage stage;
    private Supplier supplier;
    private List<City> cities;

    @FXML
    private ComboBox<String> cityComboBox;
    @FXML
    private TextField addressTextField;

    public void initialize(Stage stage, Supplier supplier) {
        this.stage = stage;
        this.supplier = supplier;
        cities = MySQLDAOFactory.getDAOFactory().getCityDAO().cities();
        cityComboBox.getItems().addAll(cities.stream().map(City::getName).collect(Collectors.toList()));
    }

    public void save() {
        City selectedCity = cities.stream().filter(city -> city.getName()
                .equals(cityComboBox.getSelectionModel().getSelectedItem())).findFirst().orElse(null);
        String address = addressTextField.getText();
        if (!address.isEmpty() && selectedCity != null) {
            if (MySQLDAOFactory.getDAOFactory().getSupplierCityDAO().supplierCityByIDs(supplier.getID(), selectedCity.getID()) != null) {
                new AlertBox().display("Supplier already exists in selected city!");
            } else {
                if (MySQLDAOFactory.getDAOFactory().getSupplierCityDAO()
                        .insert(new SupplierCity(supplier, selectedCity, address, true))) {
                    new AlertBox().display("City successfully added to supplier!");
                    stage.close();
                } else {
                    new AlertBox().display("Error happened while adding city to supplier. Please check input options.");
                }
            }
        } else {
            new AlertBox().display("All fields must be entered!");
        }
    }

    public void cancel() {
        stage.close();
    }
}
