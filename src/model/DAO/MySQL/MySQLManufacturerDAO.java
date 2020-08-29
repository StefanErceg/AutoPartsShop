package model.DAO.MySQL;

import model.DAO.ManufacturerDAO;
import model.DTO.City;
import model.DTO.Country;
import model.DTO.Manufacturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLManufacturerDAO implements ManufacturerDAO {
    @Override
    public List<Manufacturer> manufacturers() {
        List<Manufacturer> returnValue = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select manufacturer.ID as ManufacturerID, manufacturer.Name as ManufacturerName, manufacturer.Description as ManufacturerDescription, manufacturer.Headquarters as ManufacturerHeadquarters,\n" +
                "\t\tcity.ID as CityID, city.Name as CityName, city.Postcode as CityPostcode, \n" +
                "\t\tcountry.ID as CountryID, country.Name as CountryName, country.Abbreviation as CountryAbbreviation from manufacturer\n" +
                "        inner join city on manufacturer.City_ID = city.ID\n" +
                "        inner join country on city.Country_ID = country.ID\n" +
                "        where manufacturer.IsActive = 1 and city.IsActive = 1;";
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
                        resultSet.getString("ManufacturerDescription"), resultSet.getString("ManufacturerHeadquarters"), tempCity, true ));
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
    public Manufacturer manufacturerByID(Integer ID) {
        Manufacturer returnValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select manufacturer.ID as ManufacturerID, manufacturer.Name as ManufacturerName, manufacturer.Description as ManufacturerDescription, manufacturer.Headquarters as ManufacturerHeadquarters,\n" +
                "\t\tcity.ID as CityID, city.Name as CityName, city.Postcode as CityPostcode, \n" +
                "\t\tcountry.ID as CountryID, country.Name as CountryName, country.Abbreviation as CountryAbbreviation from manufacturer\n" +
                "        inner join city on manufacturer.City_ID = city.ID\n" +
                "        inner join country on city.Country_ID = country.ID\n" +
                "        where manufacturer.IsActive = 1 and city.IsActive = 1 and ManufacturerID = ?;";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID);
            resultSet = preparedStatement.executeQuery();
            Country tempCountry = null;
            City tempCity = null;
            if (resultSet.next()) {
                tempCountry = new Country(resultSet.getInt("CountryID"),
                        resultSet.getString("CountryName"), resultSet.getString("CountryAbbreviation"));
                tempCity = new City(resultSet.getInt("CityID"), tempCountry,
                        resultSet.getString("CityPostcode"), resultSet.getString("CityName"), true);
                returnValue = new Manufacturer(resultSet.getInt("ManufacturerID"), resultSet.getString("ManufacturerName"),
                        resultSet.getString("ManufacturerDescription"), resultSet.getString("ManufacturerHeadquarters"), tempCity, true );
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
    public boolean insert(Manufacturer manufacturer) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "insert into manufacturer (Name, Description, Headquarters, City_ID) values (?, ?, ?, ?)";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getDescription());
            preparedStatement.setString(3, manufacturer.getHeadquarters());
            preparedStatement.setInt(4, manufacturer.getCity().getID());
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
    public boolean update(Manufacturer manufacturer) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update manufacturer set Name=?, Description=?, Headquarters=?, City_ID=? where manufacturer.ID = ?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getDescription());
            preparedStatement.setString(3, manufacturer.getHeadquarters());
            preparedStatement.setInt(4, manufacturer.getCity().getID());
            preparedStatement.setInt(5, manufacturer.getID());
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
    public boolean delete(Manufacturer manufacturer) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update manufacturer set IsActive = 0 where manufacturer.ID = ?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, manufacturer.getID());
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
