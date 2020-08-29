package model.DTO;

import java.util.Objects;

public class Supplier {
    private Integer ID;
    private String name;
    private Boolean isActive;

    public Supplier(Integer ID, String name, Boolean isActive) {
        this.ID = ID;
        this.name = name;
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
        Supplier supplier = (Supplier) o;
        return ID.equals(supplier.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
