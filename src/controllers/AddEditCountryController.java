package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.Country;
import view.AlertBox;

public class AddEditCountryController {

    private Stage stage;
    private Country country;

    @FXML
    private Label title;

    @FXML
    private TextField nameTextField, abbreviationTextField;

    public void initialize(Stage stage, Country country) {
        this.stage = stage;
        this.country = country;
        if (country == null) {
            title.setText("Add country");
        } else {
            title.setText("Edit country");
            nameTextField.setText(country.getName());
            abbreviationTextField.setText(country.getAbbreviation());
        }
    }

    public void save() {
        String name = nameTextField.getText();
        String abbreviation = abbreviationTextField.getText();
        if (!name.isEmpty() && !abbreviation.isEmpty()) {
            if (country == null) {
                if (MySQLDAOFactory.getDAOFactory().getCountryDAO().insert(new Country(null, name, abbreviation))) {
                    new AlertBox().display("Country successfully added!");
                }
                else {
                    new AlertBox().display("Error happened while adding country. Please check input options.");
                }
            } else if (!name.equals(country.getName()) || !abbreviation.equals(country.getAbbreviation())) {
                country.setName(name);
                country.setAbbreviation(abbreviation);

                if (MySQLDAOFactory.getDAOFactory().getCountryDAO().update(country)) {
                    new AlertBox().display("Country successfully edited!");
                }
                else {
                    new AlertBox().display("Error happened while editing country. Please check input options.");
                }
            }
            stage.close();
        }
    }

    public void cancel() {
        stage.close();
    }
}
