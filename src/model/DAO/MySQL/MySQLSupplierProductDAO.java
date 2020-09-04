package model.DAO.MySQL;

import model.DAO.SupplierProductDAO;
import model.DTO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLSupplierProductDAO implements SupplierProductDAO {

    @Override
    public List<SupplierProduct> suppliersProducts() {
        List<SupplierProduct> returnValue = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select supplier.ID as SupplierID, " +
                "supplier.Name as SupplierName, " +
                "product.ID as ProductID, product.Name as ProductName, product.Quantity as ProductQuantity, " +
                "product.Barcode as ProductBarcode, product.price as ProductPrice, product.Description as ProductDescription, " +
                "manufacturer.ID as ManufacturerID, manufacturer.Name as ManufacturerName, " +
                "manufacturer.Description as ManufacturerDescription, manufacturer.Headquarters as ManufacturerHeadquarters, " +
                "city.ID as CityID, city.Name as CityName, city.Postcode as CityPostcode, " +
                "country.ID as CountryID, country.Name as CountryName, country.Abbreviation as CountryAbbreviation, " +
                "category.ID as CategoryID, category.Name as CategoryName " +
                "from supplier " +
                "inner join supplier_product on supplier_product.Supplier_ID = supplier.ID " +
                "inner join product on product.ID = supplier_product.Product_ID " +
                "inner join manufacturer on product.Manufacturer_ID = manufacturer.ID " +
                "inner join city on manufacturer.City_ID = city.ID " +
                "inner join country on city.Country_ID = country.ID " +
                "inner join category on product.Category_ID = category.ID " +
                "where supplier.IsActive = 1 and product.IsActive = 1 and manufacturer.IsActive = 1 and city.IsActive = 1;";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            Supplier supplier = null;
            Product product = null;
            Country tempCountry = null;
            City tempCity = null;
            Manufacturer tempManufacturer = null;
            Category tempCategory = null;
            while (resultSet.next()) {
                supplier = new Supplier(resultSet.getInt("SupplierID"), resultSet.getString("SupplierName"), true);
                tempCountry = new Country(resultSet.getInt("CountryID"), resultSet.getString("CountryName"),
                        resultSet.getString("CountryAbbreviation"));

                tempCity = new City(resultSet.getInt("CityID"), tempCountry, resultSet.getString("CityPostcode"),
                        resultSet.getString("CityName"), true);

                tempManufacturer = new Manufacturer(resultSet.getInt("ManufacturerID"),
                        resultSet.getString("ManufacturerName"),
                        resultSet.getString("ManufacturerDescription"),
                        resultSet.getString("ManufacturerHeadquarters"), tempCity, true);

                tempCategory = new Category(resultSet.getInt("CategoryID"), resultSet.getString("CategoryName"));
                product = (new Product(resultSet.getInt("ProductID"),
                        resultSet.getString("ProductName"),
                        resultSet.getBigDecimal("ProductQuantity"),
                        resultSet.getString("ProductBarcode"),
                        resultSet.getBigDecimal("ProductPrice"),
                        tempManufacturer, tempCategory,
                        resultSet.getString("ProductDescription"), true));
                returnValue.add(new SupplierProduct(supplier, product));
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
    public List<Supplier> suppliersForProduct(Product product) {
        List<Supplier> returnValue = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select distinct supplier.ID as SupplierID, supplier.name as SupplierName from supplier " +
                "inner join supplier_product on supplier.ID = supplier_product.Supplier_ID " +
                "inner join product on supplier_product.Product_ID = ?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, product.getID());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                returnValue.add(new Supplier(resultSet.getInt("SupplierID"), resultSet.getString("SupplierName"), true));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            MySQLUtilities.getInstance().close(preparedStatement);
        }
        return returnValue;
    }

    @Override
    public boolean insert(SupplierProduct supplierProduct) {
        boolean returnValue = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String query = "insert into supplier_product values (?, ?)";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, supplierProduct.getSupplier().getID());
            preparedStatement.setInt(2, supplierProduct.getProduct().getID());
            returnValue  = preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
            MySQLUtilities.getInstance().close(preparedStatement);
        }
        return returnValue;
    }


}
