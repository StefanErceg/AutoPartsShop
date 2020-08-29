package model.DAO;

import model.DTO.PriceProduct;

import java.util.List;

public interface PriceProductDAO {
    List<PriceProduct> pricesForProduct(Integer productID);
    PriceProduct currentPriceForProduct(Integer productID);
    boolean insert(PriceProduct priceProduct);
    boolean update(PriceProduct priceProduct);
}
