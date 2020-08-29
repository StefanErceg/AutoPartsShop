package model.DAO;

import model.DAO.MySQL.MySQLDAOFactory;

public abstract class DAOFactory {
    public abstract CategoryDAO getCategoryDAO();
    public abstract CityDAO getCityDAO();
    public abstract CountryDAO getCountryDAO();
    public abstract ManufacturerDAO getManufacturerDAO();
//    public abstract PriceProductDAO getPriceProductDAO();
//    public abstract ProductDAO getProductDAO();
    public abstract SupplierCityDAO getSupplierCityDAO();
    public abstract SupplierDAO getSupplierDAO();
//    public abstract SupplierProductDAO getSupplierProductDAO();
//    public abstract VehicleDAO getVehicleDAO();
//    public abstract VehicleProductDAO getVehicleProductDAO();


    public static DAOFactory getDAOFactory() {
        return new MySQLDAOFactory();
    }
}
