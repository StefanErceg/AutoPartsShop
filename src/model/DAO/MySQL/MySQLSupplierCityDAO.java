package model.DAO.MySQL;

import model.DAO.SupplierCityDAO;
import model.DTO.City;
import model.DTO.Country;
import model.DTO.Supplier;
import model.DTO.SupplierCity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLSupplierCityDAO implements SupplierCityDAO {

    @Override
    public List<SupplierCity> suppliersCities() {
        List<SupplierCity> returnValue = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select supplier.ID as SupplierID, supplier.Name as SupplierName, city.ID as CityID, " +
                "city.Name as CityName,  city.Postcode as CityPostcode, " +
                "country.ID as CountryID, country.Name as CountryName, country.Abbreviation as CountryAbbreviation," +
                "supplier_city.Address as Address from supplier " +
                "inner join supplier_city on supplier_city.Supplier_ID = supplier.ID " +
                "inner join city on city.ID = supplier_city.City_ID " +
                "inner join country on city.Country_ID = country.ID " +
                "where supplier.IsActive=1 and supplier_city.IsActive=1 and city.IsActive=1";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            Supplier supplier = null;
            City city = null;
            Country country = null;
            String address = "";
            while (resultSet.next()) {
                supplier = new Supplier(resultSet.getInt("SupplierID"), resultSet.getString("SupplierName"), true);
                country = new Country (resultSet.getInt("CountryID"), resultSet.getString("CountryName"), resultSet.getString("CountryAbbreviation"));
                city =  new City(resultSet.getInt("CityID"), country,resultSet.getString("CityPostcode"), resultSet.getString("CityName"),  true);
                address = resultSet.getString("Address");
                returnValue.add(new SupplierCity(supplier, city, address, true));
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
    public SupplierCity supplierCityByIDs(Integer supplierID, Integer cityID) {
        SupplierCity returnValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select supplier.ID as SupplierID, supplier.Name as SupplierName, city.ID as CityID, " +
                "city.Name as CityName,  city.Postcode as CityPostcode, " +
                "country.ID as CountryID, country.Name as CountryName, country.Abbreviation as CountryAbbreviation," +
                "supplier_city.Address as Address from supplier " +
                "inner join supplier_city on supplier_city.Supplier_ID = supplier.ID " +
                "inner join city on city.ID = supplier_city.City_ID " +
                "inner join country on city.Country_ID = country.ID " +
                "where supplier.IsActive=1 and supplier_city.IsActive=1 and city.IsActive=1 and supplier.ID = ? and city.ID = ?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, supplierID);
            preparedStatement.setInt(2, cityID);
            resultSet = preparedStatement.executeQuery();
            Supplier supplier = null;
            City city = null;
            Country country = null;
            String address = "";
            if (resultSet.next()) {
                supplier = new Supplier(resultSet.getInt("SupplierID"), resultSet.getString("SupplierName"), true);
                country = new Country (resultSet.getInt("CountryID"), resultSet.getString("CountryName"), resultSet.getString("CountryAbbreviation"));
                city =  new City(resultSet.getInt("CityID"), country,resultSet.getString("CityPostcode"), resultSet.getString("CityName"),  true);
                address = resultSet.getString("Address");
                returnValue = new SupplierCity(supplier, city, address, true);
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
    public boolean insert(SupplierCity supplierCity) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "insert into supplier_city (Supplier_ID, City_ID, Address) values (?, ?, ?)";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, supplierCity.getSupplier().getID());
            preparedStatement.setInt(2, supplierCity.getCity().getID());
            preparedStatement.setString(3, supplierCity.getAddress());
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
    public boolean update(SupplierCity supplierCity) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update supplier_city set Address=? where Supplier_ID=? and City_ID=?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplierCity.getAddress());
            preparedStatement.setInt(2, supplierCity.getSupplier().getID());
            preparedStatement.setInt(3, supplierCity.getCity().getID());
            returnValue  = preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            MySQLUtilities.getInstance().close(preparedStatement);
        }
        return returnValue;
    }

    @Override
    public boolean delete(SupplierCity supplierCity) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update supplier_city set IsActive = 0 where Supplier_ID = ? and City_ID = ?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, supplierCity.getSupplier().getID());
            preparedStatement.setInt(2, supplierCity.getCity().getID());
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
