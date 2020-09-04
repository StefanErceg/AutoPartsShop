package model.DTO;

import java.util.Objects;

public class VehicleProduct {
    private Vehicle vehicle;
    private Product product;
    private Boolean isActive;

    public VehicleProduct(Vehicle vehicle, Product product, Boolean isActive) {
        this.vehicle = vehicle;
        this.product = product;
        this.isActive = isActive;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        VehicleProduct that = (VehicleProduct) o;
        return vehicle.equals(that.vehicle) &&
                product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, product);
    }

    @Override
    public String toString() {
        return "VehicleProduct{" +
                "vehicle=" + vehicle +
                ", product=" + product +
                '}';
    }
}
