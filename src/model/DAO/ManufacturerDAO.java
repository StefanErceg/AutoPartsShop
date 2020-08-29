package model.DAO;

import model.DTO.Manufacturer;

import java.util.List;

public interface ManufacturerDAO {
    List<Manufacturer> manufacturers();
    Manufacturer manufacturerByID(Integer ID);
    boolean insert(Manufacturer manufacturer);
    boolean update(Manufacturer manufacturer);
    boolean delete(Manufacturer manufacturer);
}
