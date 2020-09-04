package model.DAO;

import model.DTO.Product;
import model.DTO.Supplier;
import model.DTO.SupplierProduct;

import java.util.List;

public interface SupplierProductDAO {
    List<SupplierProduct> suppliersProducts();
    List<Supplier> suppliersForProduct(Product product);
    boolean insert(SupplierProduct supplierProduct);
}
