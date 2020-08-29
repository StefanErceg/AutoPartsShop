package model.DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class Vehicle {
    private Integer ID;
    private String model;
    private Timestamp productionStart;
    private Timestamp productionEnd;
    private Manufacturer manufacturer;
    private String engine;

    public Vehicle(Integer ID, String model, Timestamp productionStart, Timestamp productionEnd, Manufacturer manufacturer, String engine) {
        this.ID = ID;
        this.model = model;
        this.productionStart = productionStart;
        this.productionEnd = productionEnd;
        this.manufacturer = manufacturer;
        this.engine = engine;
    }

    public Vehicle(Integer ID, String model, Timestamp productionStart, Manufacturer manufacturer) {
        this.ID = ID;
        this.model = model;
        this.productionStart = productionStart;
        this.manufacturer = manufacturer;
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
}
