package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAO.MySQL.MySQLDAOFactory;
import model.DTO.Manufacturer;
import view.AddEditManufacturer;
import view.AlertBox;

import java.util.List;
import java.util.stream.Collectors;

public class ManufacturersController {

    private Stage stage;

    @FXML
    private Button backButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableColumn<Manufacturer, String> nameColumn, cityColumn, countryColumn, headquartersColumn, descriptionColumn;
    @FXML
    private TableView<Manufacturer> manufacturersTable;

    private List<Manufacturer> manufacturers;

    public void initialize(Stage stage) {
        this.stage = stage;
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity().getName()));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity().getCountry().getName()));
        headquartersColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHeadquarters()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        loadManufacturers();
        displayManufacturers();
    }

    public void addManufacturer() {
        new AddEditManufacturer().display(null);
        loadManufacturers();
        displayManufacturers();
    }

    public void editManufacturer() {
        Manufacturer selected = manufacturersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            new AddEditManufacturer().display(selected);
            loadManufacturers();
            displayManufacturers();
        } else {
            new AlertBox().display("Manufacturer must be selected for edit!");
        }
    }

    public void removeManufacturer() {
        Manufacturer selected = manufacturersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
             if (MySQLDAOFactory.getDAOFactory().getManufacturerDAO().delete(selected)) {
                 new AlertBox().display("Manufacturer successfully deleted!");
                 loadManufacturers();
                 displayManufacturers();
             } else new AlertBox().display("Error happened while removing manufacturer.");
        } else {
            new AlertBox().display("Manufacturer must be selected for delete!");
        }
    }

    public void backToDash() {
        stage.close();
    }

    public void searchManufacturers() {
        String keyword = searchTextField.getText().toUpperCase();
        manufacturersTable.getItems().clear();
        manufacturersTable.getItems().addAll(manufacturers.stream()
                .filter(manufacturer -> manufacturer.getName().toUpperCase().contains(keyword)).collect(Collectors.toList()));
        manufacturersTable.refresh();
    }

    private void loadManufacturers() {
        manufacturers = MySQLDAOFactory.getDAOFactory().getManufacturerDAO().manufacturers();
    }

    private void displayManufacturers() {
        manufacturersTable.getItems().clear();
        manufacturersTable.getItems().addAll(manufacturers);
        manufacturersTable.refresh();
    }
}
