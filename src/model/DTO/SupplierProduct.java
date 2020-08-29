package model.DTO;

import java.util.Objects;

public class SupplierProduct {
    private Supplier supplier;
    private Product product;

    public SupplierProduct(Supplier supplier, Product product) {
        this.supplier = supplier;
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
        SupplierProduct that = (SupplierProduct) o;
        return supplier.equals(that.supplier) &&
                product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier, product);
    }
}
