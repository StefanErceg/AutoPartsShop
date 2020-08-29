package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DAO.DAOFactory;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.City;
import model.DTO.Country;
import view.AddEditCity;
import view.AddEditCountry;
import view.AlertBox;

import java.util.List;
import java.util.stream.Collectors;


public class LocationsController {

    @FXML
    private TableView<Country> countriesTable;
    @FXML
    private TableView<City> citiesTable;

    @FXML
    private TextField citiesSearch, countriesSearch;

    private Stage stage;

    private List<Country> countries;
    private List<City> cities;


    public void initialize(Stage stage) {
        this.stage = stage;
        loadCountries();
        loadCities();
        countriesTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        countriesTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("abbreviation"));
        citiesTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        citiesTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("postcode"));
        displayCountries();
        displayCities();
        countriesTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filterCitiesForCountry(newValue);
            }
        }));

    }


    public void addCity() {
        new AddEditCity().display(null);
        loadCities();
        clearCountrySelection();
        displayCities();
    }

    public void addCountry() {
        new AddEditCountry().display(null);
        loadCountries();
        clearCountrySelection();
        displayCountries();
    }

    public void editCountry() {
        new AddEditCountry().display(countriesTable.getSelectionModel().getSelectedItem());
        loadCountries();
        clearCountrySelection();
        displayCountries();
    }

    public void editCity() {
        new AddEditCity().display(citiesTable.getSelectionModel().getSelectedItem());
        loadCities();
        displayCities();
    }

    public void deleteCity() {
        City selectedCity = citiesTable.getSelectionModel().getSelectedItem();
        if (selectedCity != null) {
            if (MySQLDAOFactory.getDAOFactory().getCityDAO().delete(selectedCity)) {
                new AlertBox().display("City successfully removed!");
                loadCities();
                displayCities();
            } else {
                new AlertBox().display("Error happened while city removal!");
            }
        } else {
            new AlertBox().display("City must be selected for delete!");
        }
    }


    public void showAll() {
        clearCountrySelection();
        displayCities();
        citiesSearch.clear();
    }

    public void searchCities() {
        String keyword = citiesSearch.getText().toUpperCase();
        citiesTable.getItems().clear();
        citiesTable.getItems().addAll(cities.stream()
                .filter(city -> city.getName().toUpperCase().contains(keyword)).collect(Collectors.toList()));
        citiesTable.refresh();
    }

    public void searchCountries() {
        String keyword = countriesSearch.getText().toUpperCase();
        countriesTable.getItems().clear();
        countriesTable.getItems().addAll(countries.stream()
                .filter(country -> country.getName().toUpperCase().contains(keyword)).collect(Collectors.toList()));
        countriesTable.refresh();
    }


    public void backToDash() {
        stage.close();
    }
    private void displayCountries() {
        countriesTable.getItems().clear();
        countriesTable.getItems().addAll(countries);
        countriesTable.refresh();
    }

    private void displayCities() {
        citiesTable.getItems().clear();
        citiesTable.getItems().addAll(cities);
        citiesTable.refresh();
    }

    private void filterCitiesForCountry(Country country) {
        citiesTable.getItems().clear();
        citiesTable.getItems().addAll(cities.stream().filter(city -> city.getCountry().equals(country)).collect(Collectors.toList()));
        citiesTable.refresh();
    }

    private void loadCities() {
        cities = DAOFactory.getDAOFactory().getCityDAO().cities();
    }

    private void loadCountries() {
        countries = DAOFactory.getDAOFactory().getCountryDAO().countries();
    }

    private void clearCountrySelection() {
        countriesTable.getSelectionModel().clearSelection();
    }

}