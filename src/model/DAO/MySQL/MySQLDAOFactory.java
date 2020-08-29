package model.DAO.MySQL;

import model.DAO.*;

public class MySQLDAOFactory extends DAOFactory {
    @Override
    public CategoryDAO getCategoryDAO() {
        return new MySQLCategoryDAO();
    }
    @Override
    public CityDAO getCityDAO() {
        return new MySQLCityDAO();
    }

    @Override
    public CountryDAO getCountryDAO(){
        return new MySQLCountryDAO();
    }

    @Override
    public ManufacturerDAO getManufacturerDAO() {
        return new MySQLManufacturerDAO();
    }
//
//    @Override
//    public PriceProductDAO getPriceProductDAO() {
//        return new MySQLPriceProductDAO();
//    }
//
//    @Override
//    public ProductDAO getProductDAO() {
//        return new MySQLProductDAO();
//    }

    @Override
    public SupplierCityDAO getSupplierCityDAO(){
        return new MySQLSupplierCityDAO();
    }

    @Override
    public SupplierDAO getSupplierDAO() {
        return new MySQLSupplierDAO();
    }
//
//    @Override
//    public SupplierProductDAO getSupplierProductDAO() {
//        return new MySQLSupplierProductDAO();
//    }
//
//    @Override
//    public VehicleDAO getVehicleDAO() {
//        return new MySQLVehicleDAO();
//    }
//
//    @Override
//    public VehicleProductDAO getVehicleProductDAO() {
//        return new MySQLVehicleProductDAO();
//    }
}
