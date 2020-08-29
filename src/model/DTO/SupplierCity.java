package model.DTO;

import java.util.Objects;

public class SupplierCity {
    private Supplier supplier;
    private City city;
    private String address;
    private Boolean isActive;

    public SupplierCity(Supplier supplier, City city, String address, Boolean isActive) {
        this.supplier = supplier;
        this.city = city;
        this.address = address;
        this.isActive = isActive;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        SupplierCity that = (SupplierCity) o;
        return supplier.equals(that.supplier) &&
                city.equals(that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier, city);
    }
}
