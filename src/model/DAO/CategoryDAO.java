package model.DAO;

import model.DTO.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> categories();
    Category categoryByID(Integer ID);
    boolean insert(Category category);
    boolean update(Category category);
}
