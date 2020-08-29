package model.DTO;

import java.util.Objects;

public class VehicleProduct {
    private Vehicle vehicle;
    private Product product;

    public VehicleProduct(Vehicle vehicle, Product product) {
        this.vehicle = vehicle;
        this.product = product;
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
}
