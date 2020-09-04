package model.DTO;

import java.util.Objects;

public class City {
    private Integer ID;
    private Country country;
    private String postcode;
    private String name;
    private Boolean isActive;

    public City(Integer ID, Country country, String postcode, String name, Boolean isActive) {
        this.ID = ID;
        this.country = country;
        this.postcode = postcode;
        this.name = name;
        this.isActive = isActive;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return country.equals(city.country) &&
                postcode.equals(city.postcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, postcode);
    }

    @Override
    public String toString() {
        return "City{" +
                "ID=" + ID +
                ", country=" + country +
                ", postcode='" + postcode + '\'' +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
