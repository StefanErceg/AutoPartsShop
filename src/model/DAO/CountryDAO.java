package model.DAO;

import model.DTO.Country;

import java.util.List;

public interface CountryDAO {
    List<Country> countries();
    Country countryByID(Integer ID);
    boolean insert(Country country);
    boolean update(Country country);
}
