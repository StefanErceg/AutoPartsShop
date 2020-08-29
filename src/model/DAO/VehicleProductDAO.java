package model.DAO;

import model.DTO.Product;
import model.DTO.Vehicle;
import model.DTO.VehicleProduct;

import java.util.List;

public interface VehicleProductDAO {
    List<Vehicle> vehiclesForProduct(Integer productID);
    List<Product> productsForVehicle(Integer vehicleID);
    boolean insert(VehicleProduct vehicleProduct);
    boolean update(VehicleProduct vehicleProduct);
}
