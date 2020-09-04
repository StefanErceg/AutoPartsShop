package model.DAO.MySQL;

import model.DAO.VehicleProductDAO;
import model.DTO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLVehicleProductDAO implements VehicleProductDAO {
    @Override
    public List<VehicleProduct> vehiclesProducts() {
        List<VehicleProduct> returnValue = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select * from vehicle_product inner join vehicle_all on vehicle_product.Vehicle_ID = vehicle_all.VehicleID " +
                "inner join product_all on vehicle_product.Product_ID = product_all.ProductID where vehicle_product.IsActive = 1;";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            Country productCountry = null;
            City productCity = null;
            Manufacturer productManufacturer = null;
            Category productCategory = null;
            Product product = null;

            Country vehicleCountry = null;
            City vehicleCity = null;
            Manufacturer vehicleManufacturer = null;
            Vehicle vehicle = null;
            while (resultSet.next()) {
                vehicleCountry = new Country(resultSet.getInt("VehicleCountryID"), resultSet.getString("VehicleCountryName"),
                        resultSet.getString("VehicleCountryAbbreviation"));

                vehicleCity = new City(resultSet.getInt("VehicleCityID"), vehicleCountry, resultSet.getString("VehicleCityPostcode"),
                        resultSet.getString("VehicleCityName"), true);

                vehicleManufacturer = new Manufacturer(resultSet.getInt("VehicleManufacturerID"),
                        resultSet.getString("VehicleManufacturerName"), resultSet.getString("VehicleManufacturerDescription"),
                        resultSet.getString("VehicleManufacturerHeadquarters"), vehicleCity, true);

                vehicle = new Vehicle(resultSet.getInt("VehicleID"), resultSet.getString("VehicleModel"),
                        resultSet.getTimestamp("vehicleProdStart"), resultSet.getTimestamp("vehicleProdEnd"), vehicleManufacturer,
                        resultSet.getString("VehicleEngine"), true);

                productCountry = new Country(resultSet.getInt("ProductCountryID"), resultSet.getString("ProductCountryName"),
                        resultSet.getString("ProductCountryAbbreviation"));

                productCity = new City(resultSet.getInt("ProductCityID"), productCountry, resultSet.getString("ProductCityPostcode"),
                        resultSet.getString("ProductCityName"), true);

                productManufacturer = new Manufacturer(resultSet.getInt("ProductManufacturerID"),
                        resultSet.getString("ProductManufacturerName"), resultSet.getString("ProductManufacturerDescription"),
                        resultSet.getString("ProductManufacturerHeadquarters"), productCity, true);

                productCategory = new Category(resultSet.getInt("ProductCategoryID"), resultSet.getString("ProductCategoryName"));

                product = new Product(resultSet.getInt("ProductID"), resultSet.getString("ProductName"),
                        resultSet.getBigDecimal("ProductQuantity"), resultSet.getString("ProductBarcode"),
                        resultSet.getBigDecimal("ProductPrice"), productManufacturer, productCategory, resultSet.getString("ProductDescription"), true);
                returnValue.add(new VehicleProduct(vehicle, product, true));
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
    public VehicleProduct vehicleProductByIDS(Integer vehicleID, Integer productID) {
        VehicleProduct returnValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select * from vehicle_product inner join vehicle_all on vehicle_product.Vehicle_ID = vehicle_all.VehicleID " +
                "inner join product_all on vehicle_product.Product_ID = product_all.ProductID where product_all.ProductID = ? and vehicle_all.VehicleID = ? and vehicle_product.IsActive = 1;";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, productID);
            preparedStatement.setInt(2, vehicleID);
            resultSet = preparedStatement.executeQuery();
            Country productCountry = null;
            City productCity = null;
            Manufacturer productManufacturer = null;
            Category productCategory = null;
            Product product = null;

            Country vehicleCountry = null;
            City vehicleCity = null;
            Manufacturer vehicleManufacturer = null;
            Vehicle vehicle = null;
            if (resultSet.next()) {
                vehicleCountry = new Country(resultSet.getInt("VehicleCountryID"), resultSet.getString("VehicleCountryName"),
                        resultSet.getString("VehicleCountryAbbreviation"));

                vehicleCity = new City(resultSet.getInt("VehicleCityID"), vehicleCountry, resultSet.getString("VehicleCityPostcode"),
                        resultSet.getString("VehicleCityName"), true);

                vehicleManufacturer = new Manufacturer(resultSet.getInt("VehicleManufacturerID"),
                        resultSet.getString("VehicleManufacturerName"), resultSet.getString("VehicleManufacturerDescription"),
                        resultSet.getString("VehicleManufacturerHeadquarters"), vehicleCity, true);

                vehicle = new Vehicle(resultSet.getInt("VehicleID"), resultSet.getString("VehicleModel"),
                        resultSet.getTimestamp("vehicleProdStart"), resultSet.getTimestamp("vehicleProdEnd"), vehicleManufacturer,
                        resultSet.getString("VehicleEngine"), true);

                productCountry = new Country(resultSet.getInt("ProductCountryID"), resultSet.getString("ProductCountryName"),
                        resultSet.getString("ProductCountryAbbreviation"));

                productCity = new City(resultSet.getInt("ProductCityID"), productCountry, resultSet.getString("ProductCityPostcode"),
                        resultSet.getString("ProductCityName"), true);

                productManufacturer = new Manufacturer(resultSet.getInt("ProductManufacturerID"),
                        resultSet.getString("ProductManufacturerName"), resultSet.getString("ProductManufacturerDescription"),
                        resultSet.getString("ProductManufacturerHeadquarters"), productCity, true);

                productCategory = new Category(resultSet.getInt("ProductCategoryID"), resultSet.getString("ProductCategoryName"));

                product = new Product(resultSet.getInt("ProductID"), resultSet.getString("ProductName"),
                        resultSet.getBigDecimal("ProductQuantity"), resultSet.getString("ProductBarcode"),
                        resultSet.getBigDecimal("ProductPrice"), productManufacturer, productCategory, resultSet.getString("ProductDescription"), true);
                returnValue = new VehicleProduct(vehicle, product, true);
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
    public boolean insert(VehicleProduct vehicleProduct) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "insert into vehicle_product (Vehicle_ID, Product_ID) values(?, ?);";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, vehicleProduct.getVehicle().getID());
            preparedStatement.setInt(2, vehicleProduct.getProduct().getID());
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
    public boolean delete(VehicleProduct vehicleProduct) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update vehicle_product set IsActive = 0 where Vehicle_ID = ? and Product_ID = ?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, vehicleProduct.getVehicle().getID());
            preparedStatement.setInt(2, vehicleProduct.getProduct().getID());
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
    public List<Manufacturer> vehicleManufacturers() {
        List<Manufacturer> returnValue = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select distinct manufacturer.ID as ManufacturerID, manufacturer.Name as ManufacturerName, " +
                "manufacturer.Description as ManufacturerDescription, manufacturer.Headquarters as ManufacturerHeadquarters, " +
                "city.ID as CityID, city.Name as CityName, city.Postcode as CityPostcode, " +
                "country.ID as CountryID, country.Name as CountryName, country.Abbreviation as CountryAbbreviation from vehicle_product " +
                "inner join vehicle on vehicle_product.Vehicle_ID = vehicle.ID " +
                "inner join manufacturer on vehicle.Manufacturer_ID = manufacturer.ID " +
                "inner join city on manufacturer.City_ID = city.ID " +
                "inner join country on city.Country_ID = country.ID " +
                "where vehicle.IsActive = 1 and manufacturer.IsActive = 1 and city.IsActive = 1;";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            Country tempCountry = null;
            City tempCity = null;
            while (resultSet.next()) {
                tempCountry = new Country(resultSet.getInt("CountryID"),
                        resultSet.getString("CountryName"), resultSet.getString("CountryAbbreviation"));
                tempCity = new City(resultSet.getInt("CityID"), tempCountry,
                        resultSet.getString("CityPostcode"), resultSet.getString("CityName"), true);
                returnValue.add(new Manufacturer(resultSet.getInt("ManufacturerID"), resultSet.getString("ManufacturerName"),
                        resultSet.getString("ManufacturerDescription"), resultSet.getString("ManufacturerHeadquarters"), tempCity, true));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            MySQLUtilities.getInstance().close(preparedStatement);
        }
        return returnValue;
    }

}
