package model.DAO.MySQL;

import model.DAO.VehicleDAO;
import model.DTO.City;
import model.DTO.Country;
import model.DTO.Manufacturer;
import model.DTO.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLVehicleDAO implements VehicleDAO {
    @Override
    public List<Vehicle> vehicles() {
        List<Vehicle> returnValue = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select vehicle.ID as VehicleID, vehicle.Model as VehicleModel, " +
                "vehicle.ProductionStart as VehicleProdStart, vehicle.ProductionEnd as VehicleProdEnd, " +
                "manufacturer.ID as ManufacturerID, manufacturer.Name as ManufacturerName, " +
                "manufacturer.Description as ManufacturerDescription, manufacturer.Headquarters as ManufacturerHeadquarters, " +
                "city.ID as CityID, city.Name as CityName, city.Postcode as CityPostcode, " +
                "country.ID as CountryID, country.Name as CountryName, country.Abbreviation as CountryAbbreviation, " +
                "vehicle.Engine as vehicleEngine from vehicle " +
                "inner join manufacturer on manufacturer.ID = vehicle.Manufacturer_ID " +
                "inner join city on manufacturer.City_ID = city.ID " +
                "inner join country on city.Country_ID = country.ID " +
                "where manufacturer.IsActive = 1 and city.IsActive = 1 and vehicle.IsActive = 1;";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            Country tempCountry = null;
            City tempCity = null;
            Manufacturer tempManufacturer = null;
            while (resultSet.next()) {
                tempCountry = new Country(resultSet.getInt("CountryID"), resultSet.getString("CountryName"),
                        resultSet.getString("CountryAbbreviation"));

                tempCity = new City(resultSet.getInt("CityID"), tempCountry, resultSet.getString("CityPostcode"),
                        resultSet.getString("CityName"), true);

                tempManufacturer = new Manufacturer(resultSet.getInt("ManufacturerID"),
                        resultSet.getString("ManufacturerName"),
                        resultSet.getString("ManufacturerDescription"),
                        resultSet.getString("ManufacturerHeadquarters"), tempCity, true);
                returnValue.add(new Vehicle(resultSet.getInt("VehicleID"),
                        resultSet.getString("VehicleModel"),
                        resultSet.getTimestamp("VehicleProdStart"),
                        resultSet.getTimestamp("VehicleProdEnd"),
                        tempManufacturer,
                        resultSet.getString("VehicleEngine"),
                        true));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            MySQLUtilities.getInstance().close(preparedStatement, resultSet);
        }
        return returnValue;
    }

    @Override
    public Vehicle vehicleByID(Integer ID) {
        Vehicle returnValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select vehicle.ID as VehicleID, vehicle.Model as VehicleModel, " +
                "vehicle.ProductionStart as VehicleProdStart, vehicle.ProductionEnd as VehicleProdEnd, " +
                "manufacturer.ID as ManufacturerID, manufacturer.Name as ManufacturerName, " +
                "manufacturer.Description as ManufacturerDescription, manufacturer.Headquarters as ManufacturerHeadquarters, " +
                "city.ID as CityID, city.Name as CityName, city.Postcode as CityPostcode, " +
                "country.ID as CountryID, country.Name as CountryName, country.Abbreviation as CountryAbbreviation, " +
                "vehicle.Engine as vehicleEngine from vehicle " +
                "inner join manufacturer on manufacturer.ID = vehicle.Manufacturer_ID " +
                "inner join city on manufacturer.City_ID = city.ID " +
                "inner join country on city.Country_ID = country.ID " +
                "where manufacturer.IsActive = 1 and city.IsActive = 1 and vehicle.IsActive = 1 and vehicle.ID = ?;";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID);
            resultSet = preparedStatement.executeQuery();
            Country tempCountry = null;
            City tempCity = null;
            Manufacturer tempManufacturer = null;
            while (resultSet.next()) {
                tempCountry = new Country(resultSet.getInt("CountryID"), resultSet.getString("CountryName"),
                        resultSet.getString("CountryAbbreviation"));

                tempCity = new City(resultSet.getInt("CityID"), tempCountry, resultSet.getString("CityPostcode"),
                        resultSet.getString("CityName"), true);

                tempManufacturer = new Manufacturer(resultSet.getInt("ManufacturerID"),
                        resultSet.getString("ManufacturerName"),
                        resultSet.getString("ManufacturerDescription"),
                        resultSet.getString("ManufacturerHeadquarters"), tempCity, true);
                returnValue = new Vehicle(resultSet.getInt("VehicleID"),
                        resultSet.getString("VehicleModel"),
                        resultSet.getTimestamp("VehicleProdStart"),
                        resultSet.getTimestamp("VehicleProdEnd"),
                        tempManufacturer,
                        resultSet.getString("VehicleEngine"),
                        true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            MySQLUtilities.getInstance().close(preparedStatement, resultSet);
        }
        return returnValue;
    }

    @Override
    public boolean insert(Vehicle vehicle) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "insert into vehicle (Model, ProductionStart, ProductionEnd, Manufacturer_ID, Engine) " +
                "values (?, ?, ?, ?, ?)";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vehicle.getModel());
            preparedStatement.setTimestamp(2, vehicle.getProductionStart());
            preparedStatement.setTimestamp(3, vehicle.getProductionEnd());
            preparedStatement.setInt(4, vehicle.getManufacturer().getID());
            preparedStatement.setString(5, vehicle.getEngine());
            returnValue = preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            MySQLUtilities.getInstance().close(preparedStatement);
        }
        return returnValue;
    }

    @Override
    public boolean update(Vehicle vehicle) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update vehicle set Model=?, ProductionStart=?, ProductionEnd=?, Manufacturer_ID=?, Engine=? where ID=?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vehicle.getModel());
            preparedStatement.setTimestamp(2, vehicle.getProductionStart());
            preparedStatement.setTimestamp(3, vehicle.getProductionEnd());
            preparedStatement.setInt(4, vehicle.getManufacturer().getID());
            preparedStatement.setString(5, vehicle.getEngine());
            preparedStatement.setInt(6, vehicle.getID());
            returnValue = preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            MySQLUtilities.getInstance().close(preparedStatement);
        }
        return returnValue;
    }

    @Override
    public boolean delete(Vehicle vehicle) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update vehicle set IsActive=0 where ID=?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, vehicle.getID());
            returnValue = preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            MySQLUtilities.getInstance().close(preparedStatement);
        }
        return returnValue;
    }
}
