package model.DAO.MySQL;

import model.DAO.CityDAO;
import model.DTO.City;
import model.DTO.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLCityDAO implements CityDAO {

    @Override
    public List<City> cities() {
        List<City> returnValue = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select city.ID as ID, city.Name as Name, Postcode, Country_ID, city.IsActive as cityActive, "
                        + "country.Name as CountryName, country.Abbreviation as CountryAbbreviation "
                        + "from city inner join country on Country_ID = country.ID where city.IsActive = 1";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                returnValue.add(new City(
                        resultSet.getInt("ID"),
                        new Country(

                                resultSet.getInt("Country_ID"),
                                resultSet.getString("CountryName"),
                                resultSet.getString("CountryAbbreviation")

                        ),
                        resultSet.getString("Postcode"),
                        resultSet.getString("Name"),
                        resultSet.getBoolean("cityActive")

                ));
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
    public City cityByIDs(Integer countryID, String cityPostcode) {
        return null;
    }

    @Override
    public boolean insert(City city) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "insert into city (Name, Postcode, Country_ID) values(?, ?, ?)";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getPostcode());
            preparedStatement.setInt(3, city.getCountry().getID());
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
    public boolean update(City city) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update city set Name=?, Postcode=?, Country_ID=? where ID=?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getPostcode());
            preparedStatement.setInt(3, city.getCountry().getID());
            preparedStatement.setInt(4, city.getID());
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
    public boolean delete(City city) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update city set IsActive=0 where ID=?";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, city.getID());
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
