package model.DTO;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;

public class Vehicle {
    private Integer ID;
    private String model;
    private Timestamp productionStart;
    private Timestamp productionEnd;
    private Manufacturer manufacturer;
    private String engine;
    private Boolean isActive;

    public Vehicle(Integer ID, String model, Timestamp productionStart, Timestamp productionEnd, Manufacturer manufacturer, String engine, Boolean isActive) {
        this.ID = ID;
        this.model = model;
        this.productionStart = productionStart;
        this.productionEnd = productionEnd;
        this.manufacturer = manufacturer;
        this.engine = engine;
        this.isActive = isActive;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Timestamp getProductionStart() {
        return productionStart;
    }

    public void setProductionStart(Timestamp productionStart) {
        this.productionStart = productionStart;
    }

    public Timestamp getProductionEnd() {
        return productionEnd;
    }

    public void setProductionEnd(Timestamp productionEnd) {
        this.productionEnd = productionEnd;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
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
        Vehicle vehicle = (Vehicle) o;
        return ID.equals(vehicle.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return manufacturer.getName() + " " + model + " " + engine + ", " + productionStart.toLocalDateTime().getYear() + " - " +
                productionEnd.toLocalDateTime().getYear();
    }
}
