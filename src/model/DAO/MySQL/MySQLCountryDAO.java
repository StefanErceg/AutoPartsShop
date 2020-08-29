package model.DAO.MySQL;

import model.DAO.CountryDAO;
import model.DTO.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLCountryDAO implements CountryDAO {

    @Override
    public List<Country> countries() {
        List<Country> returnValue = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select * from country";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                returnValue.add(new Country(resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getString("Abbreviation")));
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
    public Country countryByID(Integer ID) {
        Country returnValue = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select * from country where ID=?";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ID);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                returnValue = new Country(resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getString("Abbreviation"));
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
    public boolean insert(Country country) {
        boolean returnValue = false;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "insert into country (Name, Abbreviation) values (?, ?)";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country.getName());
            preparedStatement.setString(2, country.getAbbreviation());

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
    public boolean update(Country country) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "update country set Name=?, Abbreviation=? where ID=?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country.getName());
            preparedStatement.setString(2, country.getAbbreviation());
            preparedStatement.setInt(3, country.getID());

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
