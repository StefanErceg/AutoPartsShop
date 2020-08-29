package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAO.DAOFactory;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.City;
import model.DTO.Country;
import view.AlertBox;

import java.util.List;
import java.util.stream.Collectors;

public class AddEditCityController {

    private Stage stage;
    private City city;
    private List<Country> countries;

    @FXML
    private Label title;

    @FXML
    private TextField nameTextField, postcodeTextField;
    @FXML
    private ComboBox<String> countriesComboBox;


    public void initialize(Stage stage, City city) {
        this.stage = stage;
        this.city = city;
        countries = DAOFactory.getDAOFactory().getCountryDAO().countries();
        countriesComboBox.getItems().addAll(countries.stream().map(Country::getName).collect(Collectors.toList()));

        if (city == null) {
            title.setText("Add city");
        }
        else {
            title.setText("Edit city");
            nameTextField.setText(city.getName());
            postcodeTextField.setText(city.getPostcode());
            countriesComboBox.getSelectionModel().select(city.getCountry().getName());
        }
    }

    public void save() {

        String name = nameTextField.getText();
        String postcode = postcodeTextField.getText();
        Country selectedCountry = countries.stream()
                .filter(country -> country.getName().equals(countriesComboBox.getSelectionModel().getSelectedItem()))
                .findFirst()
                .orElse(null);
        if (!name.isEmpty() && !postcode.isEmpty() && selectedCountry != null) {
            if (city == null) {
                if (MySQLDAOFactory.getDAOFactory().getCityDAO().insert(new City(null,selectedCountry, postcode, name, null))) {
                    new AlertBox().display("City successfully added!");
                } else {
                    new AlertBox().display("Error happened while adding city. Please check input options.");
                }
            } else if (!name.equals(city.getName()) || !postcode.equals(city.getPostcode()) || 
                    !selectedCountry.equals(city.getCountry())) {
                city.setName(name);
                city.setPostcode(postcode);
                city.setCountry(selectedCountry);
                if (MySQLDAOFactory.getDAOFactory().getCityDAO().update(city)) {
                    new AlertBox().display("City successfully edited!");
                }else {
                    new AlertBox().display("Error happened while editing city. Please check input options.");
                }
            }
            stage.close();
        }

    }

    public void cancel() {
        stage.close();
    }
}
