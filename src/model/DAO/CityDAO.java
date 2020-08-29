package model.DAO;

import model.DTO.City;

import java.util.List;

public interface CityDAO {
    List<City> cities();
    City cityByIDs(Integer countryID, String cityPostcode);
    boolean insert(City city);
    boolean update(City city);
    boolean delete(City city);
}
