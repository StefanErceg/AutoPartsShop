package model.DAO.MySQL;

import model.DAO.ProductDAO;
import model.DTO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLProductDAO implements ProductDAO {
    @Override
    public List<Product> products() {
        List<Product> returnValue = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select product.ID as ProductID, product.Name as ProductName, product.Quantity as ProductQuantity, " +
                "product.Barcode as ProductBarcode, product.price as ProductPrice, product.Description as ProductDescription, " +
                "manufacturer.ID as ManufacturerID, manufacturer.Name as ManufacturerName, " +
                "manufacturer.Description as ManufacturerDescription, manufacturer.Headquarters as ManufacturerHeadquarters, " +
                "city.ID as CityID, city.Name as CityName, city.Postcode as CityPostcode, " +
                "country.ID as CountryID, country.Name as CountryName, country.Abbreviation as CountryAbbreviation, " +
                "category.ID as CategoryID, category.Name as CategoryName " +
                "from product " +
                "inner join manufacturer on product.Manufacturer_ID = manufacturer.ID " +
                "inner join city on manufacturer.City_ID = city.ID " +
                "inner join country on city.Country_ID = country.ID " +
                "inner join category on product.Category_ID = category.ID " +
                "where product.IsActive = 1 and manufacturer.IsActive = 1 and city.IsActive = 1;";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            Country tempCountry = null;
            City tempCity = null;
            Manufacturer tempManufacturer = null;
            Category tempCategory = null;
            while (resultSet.next()) {
                tempCountry = new Country(resultSet.getInt("CountryID"), resultSet.getString("CountryName"),
                        resultSet.getString("CountryAbbreviation"));

                tempCity = new City(resultSet.getInt("CityID"), tempCountry, resultSet.getString("CityPostcode"),
                        resultSet.getString("CityName"), true);

                tempManufacturer = new Manufacturer(resultSet.getInt("ManufacturerID"),
                        resultSet.getString("ManufacturerName"),
                        resultSet.getString("ManufacturerDescription"),
                        resultSet.getString("ManufacturerHeadquarters"), tempCity, true);

                tempCategory = new Category(resultSet.getInt("CategoryID"), resultSet.getString("CategoryName"));
                returnValue.add(new Product(resultSet.getInt("ProductID"),
                        resultSet.getString("ProductName"),
                        resultSet.getBigDecimal("ProductQuantity"),
                        resultSet.getString("ProductBarcode"),
                        resultSet.getBigDecimal("ProductPrice"),
                        tempManufacturer, tempCategory,
                        resultSet.getString("ProductDescription"), true));
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
    public Product productByID(Integer ID) {
        Product returnValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select product.ID as ProductID, product.Name as ProductName, product.Quantity as ProductQuantity, " +
                "product.Barcode as ProductBarcode, product.price as ProductPrice, product.Description as ProductDescription, " +
                "manufacturer.ID as ManufacturerID, manufacturer.Name as ManufacturerName, " +
                "manufacturer.Description as ManufacturerDescription, manufacturer.Headquarters as ManufacturerHeadquarters, " +
                "city.ID as CityID, city.Name as CityName, city.Postcode as CityPostcode, " +
                "country.ID as CountryID, country.Name as CountryName, country.Abbreviation as CountryAbbreviation, " +
                "category.ID as CategoryID, category.Name as CategoryName " +
                "from product " +
                "inner join manufacturer on product.Manufacturer_ID = manufacturer.ID " +
                "inner join city on manufacturer.City_ID = city.ID " +
                "inner join country on city.Country_ID = country.ID " +
                "inner join category on product.Category_ID = category.ID " +
                "where product.IsActive = 1 and manufacturer.IsActive = 1 and city.IsActive = 1 and product.ID = ?;";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID);
            resultSet = preparedStatement.executeQuery();
            Country tempCountry = null;
            City tempCity = null;
            Manufacturer tempManufacturer = null;
            Category tempCategory = null;
            if (resultSet.next()) {
                tempCountry = new Country(resultSet.getInt("CountryID"), resultSet.getString("CountryName"),
                        resultSet.getString("CountryAbbreviation"));

                tempCity = new City(resultSet.getInt("CityID"), tempCountry, resultSet.getString("CityPostcode"),
                        resultSet.getString("CityName"), true);

                tempManufacturer = new Manufacturer(resultSet.getInt("ManufacturerID"),
                        resultSet.getString("ManufacturerName"),
                        resultSet.getString("ManufacturerDescription"),
                        resultSet.getString("ManufacturerHeadquarters"), tempCity, true);

                tempCategory = new Category(resultSet.getInt("CategoryID"), resultSet.getString("CategoryName"));
                returnValue = new Product(resultSet.getInt("ProductID"),
                        resultSet.getString("ProductName"),
                        resultSet.getBigDecimal("ProductQuantity"),
                        resultSet.getString("ProductBarcode"),
                        resultSet.getBigDecimal("ProductPrice"),
                        tempManufacturer, tempCategory,
                        resultSet.getString("ProductDescription"), true);
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
    public boolean insert(Product product) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = "insert into product (Name, Quantity, Barcode, Price, Description, Manufacturer_ID, Category_ID)" +
                "values (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getQuantity());
            preparedStatement.setString(3, product.getBarcode());
            preparedStatement.setBigDecimal(4, product.getPrice());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getManufacturer().getID());
            preparedStatement.setInt(7, product.getCategory().getID());
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
    public boolean update(Product product) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update product set Name=?, Quantity=?, Barcode=?, Price=?, Description=?, Manufacturer_ID=?, Category_ID=? where ID=?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getQuantity());
            preparedStatement.setString(3, product.getBarcode());
            preparedStatement.setBigDecimal(4, product.getPrice());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getManufacturer().getID());
            preparedStatement.setInt(7, product.getCategory().getID());
            preparedStatement.setInt(8, product.getID());
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
    public boolean delete(Product product) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update product set IsActive = 0 where ID=?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, product.getID());
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
    public Integer lastID() {
        Integer returnValue = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select max(ID) from product";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                returnValue = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            MySQLUtilities.getInstance().close(preparedStatement, resultSet);
        }
        return returnValue;
    }
}
