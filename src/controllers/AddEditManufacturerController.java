package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.City;
import model.DTO.Manufacturer;
import view.AlertBox;

import java.util.List;
import java.util.stream.Collectors;

public class AddEditManufacturerController {

    @FXML
    private Label title;

    @FXML
    private TextField nameTextField, hqTextField;

    @FXML
    private ComboBox<String> cityComboBox;

    @FXML
    private TextArea descriptionTextArea;

    private Stage stage;
    private Manufacturer manufacturer;

    private List<City> cities;

    public void initialize(Stage stage, Manufacturer manufacturer) {
        this.stage = stage;
        this.manufacturer = manufacturer;
        cities = MySQLDAOFactory.getDAOFactory().getCityDAO().cities();
        cityComboBox.getItems().addAll(cities.stream().map(City::getName).collect(Collectors.toList()));

        if (manufacturer == null) {
            title.setText("Add manufacturer");
        }
        else {
            title.setText("Edit manufacturer");
            nameTextField.setText(manufacturer.getName());
            hqTextField.setText(manufacturer.getHeadquarters());
            cityComboBox.getSelectionModel().select(manufacturer.getCity().getName());
            descriptionTextArea.setText(manufacturer.getDescription());
        }
    }

    public void save() {
        String name = nameTextField.getText();
        String headquarters = hqTextField.getText();
        City selectedCity = cities.stream()
                .filter(city -> city.getName().equals(cityComboBox.getSelectionModel().getSelectedItem()))
                .findFirst().orElse(null);
        String description = descriptionTextArea.getText();
        if (!name.isEmpty() && selectedCity != null) {
            if (manufacturer == null) {
                if (MySQLDAOFactory.getDAOFactory().getManufacturerDAO().insert(new Manufacturer(
                        null, name, description, headquarters, selectedCity, true
                ))) {
                    new AlertBox().display("Manufacturer successfully added!");
                    stage.close();
                } else {
                    new AlertBox().display("Error happened while adding manufacturer. Please check input options.");
                }
            } else {
                manufacturer.setName(name);
                manufacturer.setHeadquarters(headquarters);
                manufacturer.setCity(selectedCity);
                manufacturer.setDescription(description);
                if (MySQLDAOFactory.getDAOFactory().getManufacturerDAO().update(manufacturer)) {
                    new AlertBox().display("Manufacturer successfully edited!");
                    stage.close();
                } else {
                    new AlertBox().display("Error happened while editing manufacturer. Please check input options.");
                }
            }
        } else {
            new AlertBox().display("Name field must be entered and city must be selected!");
        }
    }

    public void cancel() {
        stage.close();
    }
}
