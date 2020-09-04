package model.DTO;

import java.util.Objects;

public class Manufacturer {
    private Integer ID;
    private String name;
    private String description;
    private String headquarters;
    private City city;
    private Boolean isActive;

    public Manufacturer(Integer ID, String name, String description, String headquarters, City city, Boolean isActive) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.headquarters = headquarters;
        this.city = city;
        this.isActive = isActive;
    }

    public Manufacturer(Integer ID, String name, City city, Boolean isActive) {
        this.ID = ID;
        this.name = name;
        this.city = city;
        this.isActive = isActive;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return ID.equals(that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return  name + ", " + city.getCountry().getAbbreviation();
    }
}
