package model.DAO;

import model.DTO.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> products();
    Product productByID(Integer ID);
    boolean insert(Product product);
    boolean update(Product product);
    boolean delete(Product product);
    Integer lastID();
}
