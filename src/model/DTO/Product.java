package model.DTO;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Integer ID;
    private String name;
    private BigDecimal quantity;
    private String barcode;
    private BigDecimal price;
    private Manufacturer manufacturer;
    private Category category;
    private Boolean isActive;
    private String description;

    public Product(Integer ID, String name, BigDecimal quantity, String barcode, BigDecimal price, Manufacturer manufacturer, Category category, String description, Boolean isActive) {
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
        this.barcode = barcode;
        this.price = price;
        this.manufacturer = manufacturer;
        this.category = category;
        this.isActive = isActive;
        this.description = description;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        Product product = (Product) o;
        return ID.equals(product.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", barcode='" + barcode + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", category=" + category +
                ", isActive=" + isActive +
                ", description='" + description + '\'' +
                '}';
    }
}
