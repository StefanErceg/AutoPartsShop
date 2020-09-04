package model.DAO;

import model.DTO.Vehicle;

import java.util.List;

public interface VehicleDAO {
    List<Vehicle> vehicles();
    Vehicle vehicleByID(Integer ID);
    boolean insert(Vehicle vehicle);
    boolean update(Vehicle vehicle);
    boolean delete(Vehicle vehicle);
}
