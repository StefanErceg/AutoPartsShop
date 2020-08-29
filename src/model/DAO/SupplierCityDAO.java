package model.DAO;

import model.DTO.City;
import model.DTO.Supplier;
import model.DTO.SupplierCity;

import java.util.List;

public interface SupplierCityDAO {
    List<SupplierCity> suppliersCities();
    SupplierCity supplierCityByIDs(Integer supplierID, Integer cityID);
    boolean insert(SupplierCity supplierCity);
    boolean delete(SupplierCity supplierCity);

}
