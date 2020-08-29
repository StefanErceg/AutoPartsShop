package model.DAO;

import model.DTO.Supplier;

import java.util.List;

public interface SupplierDAO {
    List<Supplier> suppliers();
    Supplier supplierByID(Integer ID);
    boolean insert(Supplier supplier);
    boolean update(Supplier supplier);
    boolean delete(Supplier supplier);
    Integer lastID();
}
