package model.DAO;

import model.DTO.Manufacturer;
import model.DTO.Product;
import model.DTO.Vehicle;
import model.DTO.VehicleProduct;

import java.util.List;

public interface VehicleProductDAO {
    List<VehicleProduct> vehiclesProducts();
    VehicleProduct vehicleProductByIDS(Integer vehicleID, Integer productID);
    boolean insert(VehicleProduct vehicleProduct);
    boolean delete(VehicleProduct vehicleProduct);
    List<Manufacturer> vehicleManufacturers();
}
