package model.DAO;

import model.DTO.City;

import java.util.List;

public interface CityDAO {
    List<City> cities();
    City cityByID(Integer cityID);
    boolean insert(City city);
    boolean update(City city);
    boolean delete(City city);
}
