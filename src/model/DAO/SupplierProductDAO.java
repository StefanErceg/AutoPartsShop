package model.DAO;

import model.DTO.Product;
import model.DTO.Supplier;
import model.DTO.SupplierProduct;

import java.util.List;

public interface SupplierProductDAO {
    List<Supplier> supplierForProduct(Integer productID);
    List<Product> productsForSupplier(Integer SupplierID);
    boolean insert(SupplierProduct supplierProduct);
    boolean update(SupplierProduct supplierProduct);
}
