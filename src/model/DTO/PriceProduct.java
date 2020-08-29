package model.DTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class PriceProduct {
    private Product product;
    private Timestamp from;
    private Timestamp to;
    private BigDecimal price;

    public PriceProduct(Product product, Timestamp from, Timestamp to, BigDecimal price) {
        this.product = product;
        this.from = from;
        this.to = to;
        this.price = price;
    }

    public PriceProduct(Product product, Timestamp from, BigDecimal price) {
        this.product = product;
        this.from = from;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Timestamp getFrom() {
        return from;
    }

    public void setFrom(Timestamp from) {
        this.from = from;
    }

    public Timestamp getTo() {
        return to;
    }

    public void setTo(Timestamp to) {
        this.to = to;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceProduct that = (PriceProduct) o;
        return product.equals(that.product) &&
                from.equals(that.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, from);
    }
}
